package club.mcams.carpet.commands.rule.commandAnvilInteractionDisabled;

import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.translations.Translator;
import club.mcams.carpet.utils.CommandHelper;
import club.mcams.carpet.utils.Messenger;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;

import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Objects;

import static net.minecraft.server.command.CommandManager.argument;

public class AnvilInteractionDisabledCommandRegistry {
    private static final Translator translator = new Translator("command.anvilInteractionDisabled");
    public static boolean anvilInteractionDisabled = false;

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
            CommandManager.literal("anvilInteractionDisabled")
            .requires(source -> CommandHelper.canUseCommand(source, AmsServerSettings.commandAnvilInteractionDisabled))
            .then(argument("mode", BoolArgumentType.bool())
            .executes(context -> {
                boolean mode = BoolArgumentType.getBool(context, "mode");
                anvilInteractionDisabled = mode;
                Text message =
                        mode ?
                        Messenger.s(translator.tr("disable").getString()).setStyle(new Style().setColor(Formatting.RED)) :
                        Messenger.s(translator.tr("enable").getString()).setStyle(new Style().setColor(Formatting.GREEN));
                Objects.requireNonNull(context.getSource().getPlayer()).sendMessage(message);
                return 1;
        })));
    }
}