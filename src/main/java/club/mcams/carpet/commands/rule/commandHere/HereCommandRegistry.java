package club.mcams.carpet.commands.rule.commandHere;

import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.helpers.rule.commandHere.GetCommandSourcePos;
import club.mcams.carpet.translations.Translator;
import club.mcams.carpet.utils.CommandHelper;
import club.mcams.carpet.utils.Messenger;
import club.mcams.carpet.utils.compat.DimensionWrapper;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.world.dimension.DimensionType;

public class HereCommandRegistry {
    private static final Translator translator = new Translator("command.here");

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
            CommandManager.literal("here")
            .requires(source -> CommandHelper.canUseCommand(source, AmsServerSettings.commandHere))
            .executes(context -> sendMessage(
                context.getSource(),
                context.getSource().getMinecraftServer(),
                context.getSource().getPlayer()
            ))
        );
    }

    private static int sendMessage(ServerCommandSource source, MinecraftServer minecraftServer, PlayerEntity player) {
        Messenger.sendServerMessage(minecraftServer, Messenger.s(message(source)));
        highlightPlayer(player);
        return 1;
    }

    private static void highlightPlayer(PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 600));
    }

    private static String getPlayerName(ServerCommandSource source) {
        return source.getName();
    }

    private static String getCurrentPos(ServerCommandSource source) {
        int[] pos = GetCommandSourcePos.getPos(source);
        return String.format("%d, %d, %d", pos[0], pos[1], pos[2]);
    }

    private static String getOtherPos(ServerCommandSource source) {
        DimensionWrapper dimension = DimensionWrapper.of(source.getWorld());
        int[] pos = GetCommandSourcePos.getPos(source);
        String otherPos = null;
        if (dimension.getValue() == DimensionType.THE_NETHER) {
            otherPos = String.format("%d, %d, %d", pos[0] * 8, pos[1], pos[2] * 8);
        } else if (dimension.getValue() == DimensionType.OVERWORLD) {
            otherPos = String.format("%d, %d, %d", pos[0] / 8, pos[1], pos[2] / 8);
        }
        return otherPos;
    }

    private static String message(ServerCommandSource source) {
        DimensionWrapper dimension = DimensionWrapper.of(source.getWorld());
        String playerName = getPlayerName(source);
        String currentPos = getCurrentPos(source);
        String otherPos = getOtherPos(source);
        String message = null;
        if (dimension.getValue() == DimensionType.THE_END) {
            message = String.format("§d[%s] §e%s §b@ §d[ %s ]", translator.tr("the_end").getString(), playerName, currentPos);
        } else if (dimension.getValue() == DimensionType.OVERWORLD) {
            message = String.format("§2[%s] §e%s §b@ §2[ %s ] §b-> §4[ %s ]", translator.tr("overworld").getString(), playerName, currentPos, otherPos);
        } else if (dimension.getValue() == DimensionType.THE_NETHER) {
            message = String.format("§4[%s] §e%s §b@ §4[ %s ] §b-> §2[ %s ]", translator.tr("nether").getString(), playerName, currentPos, otherPos);
        }
        return message;
    }
}
