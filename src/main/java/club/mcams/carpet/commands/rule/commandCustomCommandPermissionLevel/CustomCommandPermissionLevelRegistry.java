package club.mcams.carpet.commands.rule.commandCustomCommandPermissionLevel;

import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.commands.suggestionProviders.ListSuggestionProvider;
import club.mcams.carpet.commands.suggestionProviders.LiteralCommandSuggestionProvider;
import club.mcams.carpet.commands.suggestionProviders.SetSuggestionProvider;
import club.mcams.carpet.config.rule.commandCustomCommandPermissionLevel.CustomCommandPermissionLevelConfig;
import club.mcams.carpet.mixin.rule.commandCustomCommandPermissionLevel.CommandNodeInvoker;
import club.mcams.carpet.translations.Translator;
import club.mcams.carpet.utils.CommandHelper;
import club.mcams.carpet.utils.Messenger;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.CommandNode;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;

import java.util.HashMap;
import java.util.Map;

public class CustomCommandPermissionLevelRegistry {
    private static final Translator translator = new Translator("command.customCommandPermissionLevel");
    private static final String MSG_HEAD = "<customCommandPermissionLevel> ";
    public static final Map<String, Integer> COMMAND_PERMISSION_MAP = new HashMap<>();

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
        CommandManager.literal("customCommandPermissionLevel")
        .requires(source -> CommandHelper.canUseCommand(source, AmsServerSettings.commandCustomCommandPermissionLevel))
        .then(CommandManager.literal("set")
        .then(CommandManager.argument("command", StringArgumentType.string()).suggests(new LiteralCommandSuggestionProvider())
        .then(CommandManager.argument("permissionLevel", IntegerArgumentType.integer())
        .suggests(ListSuggestionProvider.of(CommandHelper.permissionLevels))
        .executes(context -> set(
            context.getSource().getMinecraftServer(),
            context.getSource().getPlayer(),
            StringArgumentType.getString(context, "command"),
            IntegerArgumentType.getInteger(context, "permissionLevel")
        )))))
        .then(CommandManager.literal("remove")
        .then(CommandManager.argument("command", StringArgumentType.string())
        .suggests(SetSuggestionProvider.of(COMMAND_PERMISSION_MAP.keySet()))
        .executes(context -> remove(
            context.getSource().getMinecraftServer(),
            context.getSource().getPlayer(),
            StringArgumentType.getString(context, "command")
        ))))
        .then(CommandManager.literal("removeAll")
        .executes(context -> removeAll(context.getSource().getMinecraftServer(), context.getSource().getPlayer())))
        .then(CommandManager.literal("list")
        .executes(context -> list(context.getSource().getPlayer())))
        .then(CommandManager.literal("help")
        .executes(context -> help(context.getSource().getPlayer()))));
    }

    private static int set(MinecraftServer server, ServerPlayerEntity player, String command, int permissionLevel) {
        if (COMMAND_PERMISSION_MAP.containsKey(command)) {
            int oldPermissionLevel = COMMAND_PERMISSION_MAP.get(command);
            player.sendMessage(
                Messenger.s(
                    String.format("%s%s %d -> %d", MSG_HEAD, command, oldPermissionLevel, permissionLevel)
                ).formatted(Formatting.GREEN)
            );
        } else {
            player.sendMessage(
                Messenger.s(
                    String.format("%s+ %s/%d", MSG_HEAD, command, permissionLevel)
                ).formatted(Formatting.GREEN)
            );
        }
        COMMAND_PERMISSION_MAP.put(command, permissionLevel);
        saveToJson(server);
        setPermission(server,player, command, permissionLevel);
        return 1;
    }

    @SuppressWarnings("unchecked")
    private static void setPermission(MinecraftServer server, ServerPlayerEntity player, String command, int permissionLevel) {
        CommandDispatcher<ServerCommandSource> dispatcher = server.getCommandManager().getDispatcher();
        CommandNode<ServerCommandSource> target = dispatcher.getRoot().getChild(command);
        ((CommandNodeInvoker<ServerCommandSource>)target).setRequirement(source -> source.hasPermissionLevel(permissionLevel));
        CommandHelper.notifyPlayersCommandsChanged(player);
    }

    private static int remove(MinecraftServer server, ServerPlayerEntity player, String command) {
        if (COMMAND_PERMISSION_MAP.containsKey(command)) {
            COMMAND_PERMISSION_MAP.remove(command);
            saveToJson(server);
            player.sendMessage(
                Messenger.s(
                    String.format("%s- %s", MSG_HEAD, command)
                ).formatted(Formatting.RED, Formatting.ITALIC)
            );
            CommandHelper.notifyPlayersCommandsChanged(player);
        } else {
            player.sendMessage(
                Messenger.s(
                    MSG_HEAD + command + translator.tr("not_found").getString()
                ).formatted(Formatting.RED, Formatting.ITALIC)
            );
        }
        return 1;
    }

    private static int removeAll(MinecraftServer server, ServerPlayerEntity player) {
        if (!COMMAND_PERMISSION_MAP.isEmpty()) {
            COMMAND_PERMISSION_MAP.clear();
            saveToJson(server);
        }
        player.sendMessage(
            Messenger.s(
                MSG_HEAD + translator.tr("removeAll").getString()
            ).formatted(Formatting.RED, Formatting.ITALIC)
        );
        CommandHelper.notifyPlayersCommandsChanged(player);
        return 1;
    }

    private static int list(PlayerEntity player) {
        final String LINE = "------------------------------------";
        player.sendMessage(
            Messenger.s(
                translator.tr("list").getString() + "\n" + LINE
            ).formatted(Formatting.AQUA, Formatting.BOLD)
        );
        for (Map.Entry<String, Integer> entry : COMMAND_PERMISSION_MAP.entrySet()) {
            String command = entry.getKey();
            float permissionLevel = entry.getValue();
            player.sendMessage(
                Messenger.s(command + " -> " + permissionLevel).formatted(Formatting.DARK_AQUA)
            );
        }
        return 1;
    }

    private static int help(PlayerEntity player) {
        final String setHelp = translator.tr("help.set").getString();
        final String removeHelp = translator.tr("help.remove").getString();
        final String removeAllHelp = translator.tr("help.removeAll").getString();
        final String listHelp = translator.tr("help.list").getString();
        player.sendMessage(
            Messenger.s(
                setHelp + "\n" + removeHelp + "\n" + removeAllHelp + "\n" + listHelp
            ).formatted(Formatting.GRAY)
        );
        return 1;
    }

    private static void saveToJson(MinecraftServer server) {
        final String CONFIG_PATH = CustomCommandPermissionLevelConfig.getPath(server);
        CustomCommandPermissionLevelConfig.saveToJson(COMMAND_PERMISSION_MAP, CONFIG_PATH);
    }
}
