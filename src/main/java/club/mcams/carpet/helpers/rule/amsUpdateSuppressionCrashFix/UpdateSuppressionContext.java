package club.mcams.carpet.helpers.rule.amsUpdateSuppressionCrashFix;

import club.mcams.carpet.AmsServer;
import club.mcams.carpet.translations.Translator;
import club.mcams.carpet.utils.MessageTextEventUtils.ClickEventUtil;
import club.mcams.carpet.utils.MessageTextEventUtils.HoverEventUtil;
import club.mcams.carpet.utils.Messenger;
import club.mcams.carpet.utils.compat.DimensionWrapper;

import net.minecraft.text.BaseText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UpdateSuppressionContext {
    private static final Translator translator = new Translator("rule.amsUpdateSuppressionCrashFix");

    public static void sendMessageToServer(BlockPos pos, World world, Throwable cause) {
        String suppressionMessage = suppressionMessageText(pos, world, cause);
        final Text copyButton = copyButton(pos);

        Messenger.sendServerMessage(
            AmsServer.minecraftServer,
            Messenger.s(suppressionMessage).formatted(Formatting.RED, Formatting.ITALIC).append(copyButton)
        );
    }

    public static String suppressionMessageText(BlockPos pos, World world, Throwable cause) {
        DimensionWrapper dimension = getSuppressionDimension(world);
        String location = getSuppressionPos(pos);
        // Update suppression location @ minecraft:overworld -> [ 1, 0, -24 ] | reason: StackOverflowError
        return translator.tr("msg", dimension, location, exceptionCauseText(cause)).getString();
    }

    private static Text copyButton(BlockPos pos) {
        BaseText hoverText = Messenger.s(translator.tr("copy").getString(), "y");
        String copyCoordText = getSuppressionPos(pos).replace(",", ""); // 1, 0, -24 -> 1 0 -24

        //#if MC>=11500
        return
            Messenger.s(" [C] ").setStyle(
                new Style().setColor(Formatting.GREEN).setBold(true).
                setClickEvent(ClickEventUtil.event(ClickEventUtil.COPY_TO_CLIPBOARD, copyCoordText)).
                setHoverEvent(HoverEventUtil.event(HoverEventUtil.SHOW_TEXT, hoverText))
            );
        //#else
        //$$ return Messenger.s("");
        //#endif
    }

    private static String getSuppressionPos(BlockPos pos) {
        return pos.getX() + ", " + pos.getY() + ", " + pos.getZ();
    }

    private static DimensionWrapper getSuppressionDimension(World world) {
        return DimensionWrapper.of(world);
    }

    private static String exceptionCauseText(Throwable cause) {
        if (cause instanceof ClassCastException) {
            return ClassCastException.class.getSimpleName();
        }
        if (cause instanceof StackOverflowError) {
            return StackOverflowError.class.getSimpleName();
        }
        if (cause instanceof OutOfMemoryError) {
            return OutOfMemoryError.class.getSimpleName();
        }
        if (cause instanceof IllegalArgumentException) {
            return IllegalArgumentException.class.getSimpleName();
        }
        return "? ? ?";
    }
}
