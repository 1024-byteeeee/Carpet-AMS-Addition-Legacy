package club.mcams.carpet.commands.rule.amsUpdateSuppressionCrashFix;

import club.mcams.carpet.translations.Translator;
import club.mcams.carpet.utils.CommandHelper;
import club.mcams.carpet.utils.Messenger;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Objects;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class AmsUpdateSuppressionCrashFixCommandRegistry {
    private static final Translator translator = new Translator("command.amsUpdateSuppressionCrashFixForceMode");
    public static boolean amsUpdateSuppressionCrashFixForceMode = false;

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("amsUpdateSuppressionCrashFixForceMode")
        .requires(source -> CommandHelper.canUseCommand(source, 2))
        .then(argument("mode", BoolArgumentType.bool())
        .executes(context -> {
            boolean mode = BoolArgumentType.getBool(context, "mode");
            amsUpdateSuppressionCrashFixForceMode = mode;
            Text message =
                    mode ?
                    Messenger.s(translator.tr("force_mod").getString()).setStyle(new Style().setColor(Formatting.LIGHT_PURPLE)) :
                    Messenger.s(translator.tr("lazy_mod").getString()).setStyle(new Style().setColor(Formatting.GREEN));
            Objects.requireNonNull(context.getSource().getPlayer()).sendMessage(message);
            return 1;
        })));
    }
}