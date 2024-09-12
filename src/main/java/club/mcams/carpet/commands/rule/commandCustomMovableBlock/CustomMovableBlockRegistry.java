package club.mcams.carpet.commands.rule.commandCustomMovableBlock;

import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.config.rule.commandCustomMovableBlock.CustomMovableBlockConfig;
import club.mcams.carpet.translations.Translator;
import club.mcams.carpet.utils.CommandHelper;
import club.mcams.carpet.utils.Messenger;
import club.mcams.carpet.utils.RegexTools;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.block.BlockState;
import net.minecraft.command.arguments.BlockStateArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class CustomMovableBlockRegistry {
    private static final Translator translator = new Translator("command.customMovableBlock");
    private static final String MSG_HEAD = "<customMovableBlock> ";
    public static final List<String> CUSTOM_MOVABLE_BLOCKS = new ArrayList<>();

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
            CommandManager.literal("customMovableBlock")
            .requires(source -> CommandHelper.canUseCommand(source, AmsServerSettings.commandCustomMovableBlock))
            .then(literal("add")
            .then(argument("block", BlockStateArgumentType.blockState())
            .executes(context -> add(
                context.getSource().getMinecraftServer(),
                context.getSource().getPlayer(),
                BlockStateArgumentType.getBlockState(context, "block").getBlockState()
            ))))
            .then(literal("remove")
            .then(argument("block", BlockStateArgumentType.blockState())
            .executes(context -> remove(
                context.getSource().getMinecraftServer(),
                context.getSource().getPlayer(),
                BlockStateArgumentType.getBlockState(context, "block").getBlockState())
            )))
            .then(literal("removeAll")
            .executes(context -> removeAll(context.getSource().getMinecraftServer(), context.getSource().getPlayer())))
            .then(literal("list")
            .executes(context -> list(context.getSource().getPlayer())))
            .then(literal("help")
            .executes(context -> help(context.getSource().getPlayer())))
        );
    }

    private static int add(MinecraftServer server, PlayerEntity player, BlockState blockState) {
        if (!CUSTOM_MOVABLE_BLOCKS.contains(getBlockName(blockState))) {
            CUSTOM_MOVABLE_BLOCKS.add(getBlockName(blockState));
            saveToJson(server);
            player.sendMessage(Messenger.s(MSG_HEAD + "+ " + getBlockName(blockState)).formatted(Formatting.GREEN));
        } else {
            player.sendMessage(
                Messenger.s(
                    MSG_HEAD + getBlockName(blockState) + translator.tr("already_exists").getString()
                ).formatted(Formatting.YELLOW)
            );
        }
        return 1;
    }

    private static int remove(MinecraftServer server, PlayerEntity player, BlockState blockState) {
        if (CUSTOM_MOVABLE_BLOCKS.contains(getBlockName(blockState))) {
            CUSTOM_MOVABLE_BLOCKS.remove(getBlockName(blockState));
            saveToJson(server);
            player.sendMessage(Messenger.s(MSG_HEAD + "- " + getBlockName(blockState)).formatted(Formatting.RED));
        } else {
            player.sendMessage(Messenger.s(MSG_HEAD + getBlockName(blockState) + translator.tr("not_found").getString()).formatted(Formatting.RED));
        }
        return 1;
    }

    private static int removeAll(MinecraftServer server, PlayerEntity player) {
        CUSTOM_MOVABLE_BLOCKS.clear();
        saveToJson(server);
        player.sendMessage(Messenger.s(MSG_HEAD + translator.tr("removeAll").getString()).formatted(Formatting.RED));
        return 1;
    }

    private static int list(ServerPlayerEntity player) {
        player.sendMessage(
            Messenger.s(
            translator.tr("list").getString() + "\n-------------------------------").formatted(Formatting.GREEN)
        );
        for (String blockName : CUSTOM_MOVABLE_BLOCKS) {
            player.sendMessage(Messenger.s(blockName).formatted(Formatting.GREEN));
        }
        return 1;
    }

    private static int help(ServerPlayerEntity player) {
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

    private static void saveToJson(MinecraftServer server) {
        String CONFIG_FILE_PATH = CustomMovableBlockConfig.getPath(server);
        CustomMovableBlockConfig.saveToJson(CUSTOM_MOVABLE_BLOCKS, CONFIG_FILE_PATH);
    }

    private static String getBlockName(BlockState blockState) {
        return RegexTools.getBlockRegisterName(blockState.getBlock().toString());
    }
}
