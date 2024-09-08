package club.mcams.carpet.utils;

import club.mcams.carpet.mixin.translations.StyleAccessor;
//#if MC<11500
//$$ import club.mcams.carpet.mixin.translations.MessengerInvoker;
//#endif
import club.mcams.carpet.utils.compat.MessengerCompatFactory;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.BaseText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Objects;

public class Messenger {
    // Compound Text
    public static BaseText c(Object... fields) {
        return MessengerCompatFactory.CarpetCompoundText(fields);
    }

    // Simple Text
    public static BaseText s(Object text) {
        return MessengerCompatFactory.LiteralText(text.toString());
    }

    // Simple Text with carpet style
    public static BaseText s(Object text, String carpetStyle) {
        return formatting(s(text), carpetStyle);
    }

    // Simple Text with formatting
    public static BaseText s(Object text, Formatting textFormatting) {
        return formatting(s(text), textFormatting);
    }

    // Translation Text
    public static BaseText tr(String key, Object... args) {
        return MessengerCompatFactory.TranslatableText(key, args);
    }

    public static BaseText copy(BaseText text) {
        return (BaseText) text.deepCopy();
    }

    private static void __tell(ServerCommandSource source, BaseText text, boolean broadcastToOps) {
        MessengerCompatFactory.sendFeedBack(source, text, broadcastToOps);
    }

    public static void tell(ServerCommandSource source, BaseText text, boolean broadcastToOps) {
        __tell(source, text, broadcastToOps);
    }

    public static void tell(ServerCommandSource source, BaseText text) {
        tell(source, text, false);
    }

    public static BaseText formatting(BaseText text, Formatting... formattings) {
        text.formatted(formattings);
        return text;
    }

    public static BaseText formatting(BaseText text, String carpetStyle) {
        Style textStyle = text.getStyle();
        StyleAccessor parsedStyle = (StyleAccessor) parseCarpetStyle(carpetStyle);
        textStyle.setColor(parsedStyle.getColorField());
        textStyle.setBold(parsedStyle.getBoldField());
        textStyle.setItalic(parsedStyle.getItalicField());
        textStyle.setUnderline(parsedStyle.getUnderlineField());
        textStyle.setStrikethrough(parsedStyle.getStrikethroughField());
        textStyle.setObfuscated(parsedStyle.getObfuscatedField());
        return style(text, textStyle);
    }

    public static BaseText style(BaseText text, Style style) {
        text.setStyle(style);
        return text;
    }

    public static Style parseCarpetStyle(String style) {
        //#if MC<11500
        //$$ return MessengerInvoker.invokeApplyStyleToTextComponent(Messenger.s(""), style).getStyle();
        //#else
        return carpet.utils.Messenger.parseStyle(style);
        //#endif
    }

    public static void sendServerMessage(MinecraftServer server, Text text) {
        Objects.requireNonNull(server, "Server is null, message not delivered !");
        MessengerCompatFactory.sendSystemMessage(server, text);
        server.getPlayerManager().getPlayerList().forEach(player -> MessengerCompatFactory.sendSystemMessage(player, text));
    }
}
