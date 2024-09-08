package club.mcams.carpet.commands.rule.commandPlayerChunkLoadController;

import club.mcams.carpet.AmsServer;
import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.helpers.rule.commandPlayerChunkLoadController.ChunkLoading;
import club.mcams.carpet.utils.CommandHelper;
import club.mcams.carpet.utils.Messenger;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;

import static com.mojang.brigadier.arguments.BoolArgumentType.getBool;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class PlayerChunkLoadControllerCommandRegistry {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("playerChunkLoading")
            .requires((player) -> CommandHelper.canUseCommand(player, AmsServerSettings.commandPlayerChunkLoadController))
            .executes((c) -> listPlayerInteractions(c.getSource(), c.getSource().getName()))
            .then(argument("boolean", BoolArgumentType.bool())
            .executes((c) -> setPlayerInteraction(c.getSource(), c.getSource().getName(), getBool(c, "boolean"))))
        );
    }

    private static int setPlayerInteraction(ServerCommandSource source, String playerName, boolean b) {
        PlayerEntity player = source.getMinecraftServer().getPlayerManager().getPlayer(playerName);
        ChunkLoading.setPlayerInteraction(playerName, b, true);
        if (player == null) {
            Messenger.sendServerMessage(
                AmsServer.minecraftServer, Messenger.s("No player specified").
                setStyle(new Style().setColor(Formatting.RED).setBold(true))
            );
            return 0;
        } else {
            player.sendMessage(
                Messenger.s((playerName + " chunk loading " + b)).
                setStyle(new Style().setColor(Formatting.LIGHT_PURPLE).setBold(true))
            );
            return 1;
        }
    }

    private static int listPlayerInteractions(ServerCommandSource source, String playerName) {
        boolean playerInteractions = ChunkLoading.onlinePlayerMap.getOrDefault(playerName, true);
        PlayerEntity player = source.getMinecraftServer().getPlayerManager().getPlayer(playerName);
        if (player == null) {
            Messenger.sendServerMessage(
                AmsServer.minecraftServer, Messenger.s("No player specified").
                setStyle(new Style().setColor(Formatting.RED).setBold(true))
            );
            return 0;
        }
        if (playerInteractions) {
            player.sendMessage(
                Messenger.s((playerName + " chunk loading: true")).
                setStyle(new Style().setColor(Formatting.LIGHT_PURPLE).setBold(true))
            );
        } else {
            player.sendMessage(
                Messenger.s((playerName + " chunk loading: false")).
                setStyle(new Style().setColor(Formatting.LIGHT_PURPLE).setBold(true))
            );
        }
        return 1;
    }
}