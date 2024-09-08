package club.mcams.carpet.utils;

import club.mcams.carpet.AmsServer;
import club.mcams.carpet.translations.Translator;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import net.minecraft.util.Formatting;

import java.util.Arrays;
import java.util.List;

public final class CommandHelper {
    public static final List<String> permissionLevels = Arrays.asList("0", "1", "2", "3", "4");
    private static final Translator translator = new Translator("command.commandHelper");

    private CommandHelper() {}

    public static void notifyPlayersCommandsChanged(ServerPlayerEntity player) {
        try {
            if (player.getServer() != null) {
                player.getServer().getCommandManager().sendCommandTree(player);
                player.sendMessage(
                    Messenger.s(translator.tr("refresh_cmd_tree").getString()).formatted(Formatting.YELLOW)
                );
            }
        }
        catch (NullPointerException e) {
            AmsServer.LOGGER.warn("Exception while refreshing commands, please report this to Carpet", e);
        }
    }

    public static boolean canUseCommand(ServerCommandSource source, Object commandLevel) {
        if (commandLevel instanceof Boolean) return (Boolean) commandLevel;
        String commandLevelString = commandLevel.toString();
        switch (commandLevelString) {
            case "true": return true;
            case "false": return false;
            case "ops": return source.hasPermissionLevel(2);
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
                return source.hasPermissionLevel(Integer.parseInt(commandLevelString));
        }
        return false;
    }
}
