package club.mcams.carpet.commands.rule.commandWhere;

import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.helpers.rule.commandWhere.GetPlayerPos;
import club.mcams.carpet.translations.Translator;
import club.mcams.carpet.utils.CommandHelper;
import club.mcams.carpet.utils.Messenger;
import club.mcams.carpet.utils.compat.DimensionWrapper;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import net.minecraft.world.dimension.DimensionType;

import static net.minecraft.server.command.CommandManager.argument;

public class WhereCommandRegistry {
    private static final Translator translator = new Translator("command.where");

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
            CommandManager.literal("where")
            .requires(source -> CommandHelper.canUseCommand(source, AmsServerSettings.commandWhere))
            .then(argument("player", EntityArgumentType.player())
            .executes(context -> sendMessage(
                context.getSource().getMinecraftServer(),
                context.getSource().getPlayer(),
                EntityArgumentType.getPlayer(context, "player")
            )))
        );
    }

    private static int sendMessage(MinecraftServer minecraftServer, PlayerEntity senderPlayer, PlayerEntity targetPlayer) {
        senderPlayer.sendMessage(Messenger.s(message(targetPlayer)));
        sendWhoGetWhoMessage(minecraftServer, senderPlayer, targetPlayer);
        highlightPlayer(targetPlayer);
        return 1;
    }

    private static void sendWhoGetWhoMessage(MinecraftServer minecraftServer, PlayerEntity senderPlayer, PlayerEntity targetPlayer) {
        String senderPlayerName = getPlayerName(senderPlayer);
        String targetPlayerName = getPlayerName(targetPlayer);
        String message = translator.tr("who_get_who", senderPlayerName, targetPlayerName).getString();
        Messenger.sendServerMessage(
            minecraftServer,
            Messenger.s(message).
            setStyle(new Style().setColor(Formatting.GRAY).setItalic(true))
        );
    }

    private static void highlightPlayer(PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 600));
    }

    private static String getPlayerName(PlayerEntity player) {
        return player.getName().getString();
    }

    private static String getCurrentPos(PlayerEntity player) {
        int[] pos = GetPlayerPos.getPos(player);
        return String.format("%d, %d, %d", pos[0], pos[1], pos[2]);
    }

    private static String getOtherPos(PlayerEntity player) {
        DimensionWrapper dimension = DimensionWrapper.of(player.getEntityWorld());
        int[] pos = GetPlayerPos.getPos(player);
        String otherPos = null;
        if (dimension.getValue() == DimensionType.THE_NETHER) {
            otherPos = String.format("%d, %d, %d", pos[0] * 8, pos[1], pos[2] * 8);
        } else if (dimension.getValue() == DimensionType.OVERWORLD) {
            otherPos = String.format("%d, %d, %d", pos[0] / 8, pos[1], pos[2] / 8);
        }
        return otherPos;
    }

    private static String message(PlayerEntity player) {
        DimensionWrapper dimension = DimensionWrapper.of(player.getEntityWorld());
        String playerName = getPlayerName(player);
        String currentPos = getCurrentPos(player);
        String otherPos = getOtherPos(player);
        String message = null;
        if (dimension.getValue() == DimensionType.THE_END) {
            message = String.format("§d[%s] §e%s §b@ §d%s", translator.tr("the_end").getString(), playerName, currentPos);
        } else if (dimension.getValue() == DimensionType.OVERWORLD) {
            message = String.format("§2[%s] §e%s §b@ §2[ %s ] §b-> §4[ %s ]", translator.tr("overworld").getString(), playerName, currentPos, otherPos);
        } else if (dimension.getValue() == DimensionType.THE_NETHER) {
            message = String.format("§4[%s] §e%s §b@ §4[ %s ] §b-> §2[ %s ]", translator.tr("nether").getString(), playerName, currentPos, otherPos);
        }
        return message;
    }
}
