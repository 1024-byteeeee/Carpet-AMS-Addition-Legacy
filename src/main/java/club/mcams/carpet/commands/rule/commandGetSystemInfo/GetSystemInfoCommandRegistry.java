package club.mcams.carpet.commands.rule.commandGetSystemInfo;

import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.translations.Translator;
import club.mcams.carpet.utils.CommandHelper;
import club.mcams.carpet.utils.Messenger;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Formatting;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

public class GetSystemInfoCommandRegistry {
    private static final Translator translator = new Translator("command.getSystemInfo");

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
            CommandManager.literal("getSystemInfo")
            .requires(source -> CommandHelper.canUseCommand(source, AmsServerSettings.commandGetSystemInfo))
            .executes(context -> executeGetSystemInfo(context.getSource().getPlayer()))
        );
    }

    private static int executeGetSystemInfo(PlayerEntity player) {
        String formatInfo = formatInfo();
        player.sendMessage(Messenger.s(formatInfo).formatted(Formatting.DARK_AQUA));
        return 1;
    }

    private static String formatInfo() {
        Runtime runtime = Runtime.getRuntime();
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        final String line = "===========================";
        String os = osBean.getName() + " - " + osBean.getVersion();
        String osArch = osBean.getArch();
        String availableProcessors = String.valueOf(runtime.availableProcessors());
        String totalMemory = runtime.totalMemory() / 1024 / 1024 + "MB";
        String freeMemory = runtime.freeMemory() / 1024 / 1024 + "MB";
        return String.format(
            "%s\n%s %s\n%s %s\n%s %s\n%s %s\n%s %s\n%s",
            line,
            translator.tr("os").getString(), os,
            translator.tr("os_arch").getString(), osArch,
            translator.tr("available_processors").getString(), availableProcessors,
            translator.tr("total_memory").getString(), totalMemory,
            translator.tr("free_memory").getString(), freeMemory,
            line
        );
    }
}
