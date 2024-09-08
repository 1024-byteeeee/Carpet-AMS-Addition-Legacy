package club.mcams.carpet.commands.rule.commandPacketInternetGroper;

import club.mcams.carpet.AmsServer;
import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.translations.Translator;
import club.mcams.carpet.utils.CommandHelper;
import club.mcams.carpet.utils.Messenger;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class PingCommandRegistry {
    private static final Translator translator = new Translator("command.ping");
    private static final String MSG_HEAD = "<commandPacketInternetGroper>";
    private static final Map<PlayerEntity, PingThread> PING_THREADS = new HashMap<>();

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
            CommandManager.literal("pings")
            .requires(source -> CommandHelper.canUseCommand(source, AmsServerSettings.commandPacketInternetGroper))
            .then(argument("targetIpOrDomainName", StringArgumentType.string())
            .then(argument("pingQuantity", IntegerArgumentType.integer())
            .executes(context -> executePing(
                context.getSource().getPlayer(),
                StringArgumentType.getString(context, "targetIpOrDomainName"),
                IntegerArgumentType.getInteger(context, "pingQuantity")
            ))))
            .then(literal("stop").executes(context -> stopPing(context.getSource().getPlayer())))
            .then(literal("help").executes(context -> help(context.getSource().getPlayer())))
        );
    }

    private static int executePing(PlayerEntity player, String targetIpOrDomainName, int pingQuantity) {
        PingThread thread = PING_THREADS.get(player);
        if (thread != null) {
            thread.setInterrupted();
        }
        PingThread pingThread = new PingThread(pingQuantity, player, targetIpOrDomainName);
        pingThread.start();
        PING_THREADS.put(player, pingThread);
        return 1;
    }

    private static long ping(PlayerEntity player, String targetIpOrDomainName, boolean isFirstPing) {
        try {
            InetAddress inetAddress = InetAddress.getByName(targetIpOrDomainName);
            if (isFirstPing) {
                player.sendMessage(
                    Messenger.s(
                        String.format(
                            "§b%s §ePing §b%s §e[ %s ] §e...",
                            MSG_HEAD, targetIpOrDomainName, inetAddress.getHostAddress()
                        )
                    )
                );
            }
            long startTime = System.currentTimeMillis();
            boolean isReachable = inetAddress.isReachable(5000);
            long endTime = System.currentTimeMillis();
            if (isReachable) {
                long delayTime = endTime - startTime;
                player.sendMessage(
                    Messenger.s(
                        String.format(
                            "§b%s §2Replay from §e[ %s ]§2 Time = %dms",
                            MSG_HEAD, inetAddress.getHostAddress(), delayTime
                        )
                    )
                );
                return delayTime;
            } else {
                player.sendMessage(Messenger.s(String.format("§b%s §4Request time out.", MSG_HEAD)));
                return -1;
            }
        } catch (IOException e) {
            AmsServer.LOGGER.error("[commandPing] An error occurred while performing ping operation");
            return -1;
        }
    }

    private static int stopPing(PlayerEntity player) {
        String stopPing = translator.tr("stop_ping").getString();
        String activePingIsNull = translator.tr("active_ping_is_null").getString();
        PingThread pingThread = PING_THREADS.get(player);
        if (pingThread != null) {
            pingThread.setInterrupted();
            player.sendMessage(Messenger.s(String.format("§b%s §4%s", MSG_HEAD, stopPing)));
        } else {
            player.sendMessage(Messenger.s(String.format("§b%s §4%s", MSG_HEAD, activePingIsNull)));
        }
        return 1;
    }

    private static int help(PlayerEntity player) {
        String pingHelp = translator.tr("help.ping").getString();
        String stopHelp = translator.tr("help.stop").getString();
        player.sendMessage(
            Messenger.s(pingHelp + "\n" + stopHelp).
            setStyle(new Style().setColor(Formatting.GRAY))
        );
        return 1;
    }

    private static void sendFinishMessage(PlayerEntity player, int totalPings, int successfulPings, int failedPings, long averageDelay) {
        player.sendMessage(
            Messenger.s(
                String.format(
                    "§b%s §aSent = %d, Received = %d, Lost = %d, Average delay = %dms",
                    MSG_HEAD, totalPings, successfulPings, failedPings, averageDelay
                )
            )
        );
    }

    private static class PingThread extends Thread {
       private volatile boolean interrupted = false;
       private final int pingQuantity;
       private final PlayerEntity player;
       private final String targetIpOrDomainName;
       private PingThread(int pingQuantity, PlayerEntity player, String targetIpOrDomainName) {
            this.pingQuantity = pingQuantity;
            this.player = player;
            this.targetIpOrDomainName = targetIpOrDomainName;
            this.setDaemon(true);
       }

       @Override
       public void run() {
           int successfulPings = 0;
           int failedPings = 0;
           long totalDelay = 0;
           try {
               boolean isFirstPing = true;
               for (int i = 0; i < pingQuantity; i++) {
                   if (interrupted){
                       return;
                   }
                   long delay = ping(player, targetIpOrDomainName, isFirstPing);
                   if (delay >= 0) {
                       successfulPings++;
                       totalDelay += delay;
                   } else {
                       failedPings++;
                   }
                   isFirstPing = false;
                   Thread.sleep(1000);
               }
               sendFinishMessage(player, pingQuantity, successfulPings, failedPings, successfulPings > 0 ? totalDelay / successfulPings : 0);
           } catch (InterruptedException ignored) {
           } finally {
               PING_THREADS.remove(player);
           }
       }

       private void setInterrupted(){
           this.interrupted = true;
       }
   }
}
