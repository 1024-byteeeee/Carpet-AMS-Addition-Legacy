package club.mcams.carpet.commands.rule.commandCustomBlockBlastResistance;

import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.config.rule.commandCustomBlockBlastResistance.CustomBlockBlastResistanceConfig;
import club.mcams.carpet.translations.Translator;
import club.mcams.carpet.utils.CommandHelper;
import club.mcams.carpet.utils.Messenger;
import club.mcams.carpet.utils.RegexTools;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;

import net.minecraft.block.BlockState;
import net.minecraft.command.arguments.BlockStateArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;

import java.util.HashMap;
import java.util.Map;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class CustomBlockBlastResistanceCommandRegistry {
    private static final Translator translator = new Translator("command.customBlockBlastResistance");
    private static final String MSG_HEAD = "<customBlockBlastResistance> ";
    public static final Map<BlockState, Float> CUSTOM_BLOCK_BLAST_RESISTANCE_MAP = new HashMap<>();

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
            CommandManager.literal("customBlockBlastResistance")
            .requires(source -> CommandHelper.canUseCommand(source, AmsServerSettings.commandCustomBlockBlastResistance))
            .then(literal("set")
            .then(argument("block", BlockStateArgumentType.blockState())
            .then(argument("resistance", FloatArgumentType.floatArg())
            .executes(context -> set(
                BlockStateArgumentType.getBlockState(context, "block").getBlockState(),
                FloatArgumentType.getFloat(context, "resistance"),
                context.getSource().getMinecraftServer(),
                context.getSource().getPlayer()
            )))))
            .then(literal("remove")
            .then(argument("block", BlockStateArgumentType.blockState())
            .executes(context -> remove(
                BlockStateArgumentType.getBlockState(context, "block").getBlockState(),
                context.getSource().getMinecraftServer(),
                context.getSource().getPlayer()
            ))))
            .then(literal("removeAll").executes(context -> removeAll(context.getSource().getMinecraftServer(), context.getSource().getPlayer())))
            .then(literal("list").executes(context -> list(context.getSource().getPlayer())))
            .then(literal("help").executes(context -> help(context.getSource().getPlayer())))
        );
    }

    private static int set(BlockState state, float blastResistance, MinecraftServer server, PlayerEntity player) {
        if (CUSTOM_BLOCK_BLAST_RESISTANCE_MAP.containsKey(state)) {
            float oldBlastResistance = CUSTOM_BLOCK_BLAST_RESISTANCE_MAP.get(state);
            player.sendMessage(
                Messenger.s(
                    MSG_HEAD + getBlockRegisterName(state) + "/" + oldBlastResistance +
                    " -> " + getBlockRegisterName(state) + "/" + blastResistance
                ).setStyle(new Style().setColor(Formatting.GREEN).setBold(true))
            );
        } else {
            player.sendMessage(
                Messenger.s(
                    MSG_HEAD + "+ " + getBlockRegisterName(state) + "/" + blastResistance
                ).setStyle(new Style().setColor(Formatting.GREEN).setBold(true))
            );
        }
        CUSTOM_BLOCK_BLAST_RESISTANCE_MAP.put(state, blastResistance);
        saveToJson(server);
        return 1;
    }

    private static int remove(BlockState state, MinecraftServer server, PlayerEntity player) {
        if (CUSTOM_BLOCK_BLAST_RESISTANCE_MAP.containsKey(state)) {
            float blastResistance = CUSTOM_BLOCK_BLAST_RESISTANCE_MAP.get(state);
            CUSTOM_BLOCK_BLAST_RESISTANCE_MAP.remove(state);
            saveToJson(server);
            player.sendMessage(
                Messenger.s(
                    MSG_HEAD + "- " + getBlockRegisterName(state) + "/" + blastResistance
                ).setStyle(new Style().setColor(Formatting.RED).setBold(true))
            );
            return 1;
        } else {
            player.sendMessage(
                Messenger.s(
                    MSG_HEAD + getBlockRegisterName(state) + translator.tr("not_found").getString()
                ).setStyle(new Style().setColor(Formatting.RED).setBold(true))
            );
            return 0;
        }
    }

    private static int removeAll(MinecraftServer server, PlayerEntity player) {
        CUSTOM_BLOCK_BLAST_RESISTANCE_MAP.clear();
        saveToJson(server);
        player.sendMessage(
            Messenger.s(
                MSG_HEAD + translator.tr("removeAll").getString()
            ).setStyle(new Style().setColor(Formatting.RED).setBold(true))
        );
        return 1;
    }

    private static int list(PlayerEntity player) {
        player.sendMessage(
            Messenger.s(
                translator.tr("list").getString() + "\n-------------------------------"
            ).setStyle(new Style().setColor(Formatting.GREEN).setBold(true))
        );
        for (Map.Entry<BlockState, Float> entry : CUSTOM_BLOCK_BLAST_RESISTANCE_MAP.entrySet()) {
            BlockState state = entry.getKey();
            float blastResistance = entry.getValue();
            String blockName = getBlockRegisterName(state);
            player.sendMessage(
                Messenger.s(blockName + " / " + blastResistance).
                setStyle(new Style().setColor(Formatting.GREEN))
            );
        }
        return 1;
    }

    private static int help(PlayerEntity player) {
        String setHelpText = translator.tr("help.set").getString();
        String removeHelpText = translator.tr("help.remove").getString();
        String removeAllHelpText = translator.tr("help.removeAll").getString();
        String listHelpText = translator.tr("help.list").getString();
        player.sendMessage(
                Messenger.s(
                "\n" +
                setHelpText + "\n" +
                removeHelpText + "\n" +
                removeAllHelpText + "\n" +
                listHelpText
            ).setStyle(new Style().setColor(Formatting.GRAY))
        );
        return 1;
    }

    private static String getBlockRegisterName(BlockState state) {
        return RegexTools.getBlockRegisterName(state.getBlock().toString());
    }

    private static void saveToJson(MinecraftServer server) {
        String CONFIG_FILE_PATH = CustomBlockBlastResistanceConfig.getPath(server);
        CustomBlockBlastResistanceConfig.saveToJson(CUSTOM_BLOCK_BLAST_RESISTANCE_MAP, CONFIG_FILE_PATH);
    }
}
