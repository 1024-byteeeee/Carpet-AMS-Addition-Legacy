package club.mcams.carpet.commands.rule.commandGetSaveSize;

import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.translations.Translator;
import club.mcams.carpet.utils.CommandHelper;
import club.mcams.carpet.utils.Messenger;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import net.minecraft.world.dimension.DimensionType;

import java.io.File;
import java.util.Objects;

public class GetSaveSizeCommandRegistry {
    private static final Translator translator = new Translator("command.getSaveSize");

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
            CommandManager.literal("getSaveSize")
            .requires(source -> CommandHelper.canUseCommand(source, AmsServerSettings.commandGetSaveSize))
            .executes(context -> executeGetSaveSize(context.getSource().getPlayer()))
        );
    }

    private static int executeGetSaveSize(PlayerEntity player) {
        MinecraftServer server = player.getServer();
        saveWorld(server, player);
        long size = getFolderSize(getSaveFolder(server));
        String sizeString = formatSize(size);
        player.sendMessage(
            Messenger.s(
                String.format(
                    "§e%s §a§l§n%s",
                    translator.tr("size_msg").getString(),
                    sizeString
                )
            )
        );
        return 1;
    }

    private static void saveWorld(MinecraftServer server, PlayerEntity player) {
        if (server != null) {
            String saveAllMessage;
            final String SUCCESS_MSG = translator.tr("save_success_msg").getString();
            final String FAIL_MSG = translator.tr("save_fail_msg").getString();
            boolean saveAllSuccess = server.save(false, true, true);
            saveAllMessage = saveAllSuccess ? SUCCESS_MSG : FAIL_MSG;
            player.sendMessage(
                Messenger.s(saveAllMessage).
                setStyle(new Style().setColor(Formatting.GRAY))
            );
        }
    }

    private static File getSaveFolder(MinecraftServer server) {
        return Objects.requireNonNull(server).getWorld(DimensionType.OVERWORLD).getSaveHandler().getWorldDir();
    }

    private static long getFolderSize(File folder) {
        long length = 0;
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    length += file.length();
                } else {
                    length += getFolderSize(file);
                }
            }
        }
        return length;
    }

    private static String formatSize(long size) {
        double sizeGB = size / (1024.0 * 1024.0 * 1024.0);
        return String.format("%.3f GB", sizeGB);
    }
}