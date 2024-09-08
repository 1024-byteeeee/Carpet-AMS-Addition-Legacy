package club.mcams.carpet.helpers.rule.sendPlayerDeathLocation;

import club.mcams.carpet.helpers.FakePlayerHelper;
import club.mcams.carpet.translations.Translator;
import club.mcams.carpet.utils.MessageTextEventUtils.ClickEventUtil;
import club.mcams.carpet.utils.MessageTextEventUtils.HoverEventUtil;
import club.mcams.carpet.utils.Messenger;
import club.mcams.carpet.utils.compat.DimensionWrapper;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.BaseText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class PlayerDeathLocationContext {
    private static final Translator translator = new Translator("rule.sendPlayerDeathLocation");

    public static void sendMessage(MinecraftServer server, ServerPlayerEntity player, World world) {
        final Text copyButton = copyButton(player);
        String message = formatMessage(player, world);
        Messenger.sendServerMessage(server, Messenger.s(message).formatted(Formatting.RED).append(copyButton));
    }

    public static void realPlayerSendMessage(MinecraftServer server, ServerPlayerEntity player, World world) {
        if (!FakePlayerHelper.isFakePlayer(player)) {
            sendMessage(server, player, world);
        }
    }

    public static void fakePlayerSendMessage(MinecraftServer server, ServerPlayerEntity player, World world) {
        if (FakePlayerHelper.isFakePlayer(player)) {
            sendMessage(server, player, world);
        }
    }

    private static String getPlayerName(ServerPlayerEntity player) {
        return player.getName().getString();
    }

    private static DimensionWrapper getDimension(World world) {
        return DimensionWrapper.of(world);
    }

    private static String getPlayerPos(ServerPlayerEntity player) {
        return player.getBlockPos().getX() + ", " + player.getBlockPos().getY() + ", " + player.getBlockPos().getZ();
    }

    private static Text copyButton(ServerPlayerEntity player) {
        BaseText hoverText = Messenger.s(translator.tr("copy").getString(), "y");
        String copyCoordText = getPlayerPos(player).replace(",", ""); // 1, 0, -24 -> 1 0 -24

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

    // Alex 死亡位置 @ minecraft:overworld -> [ 888, 20, 999 ]
    private static String formatMessage(ServerPlayerEntity player, World world) {
        String playerName = getPlayerName(player);
        DimensionWrapper dimension = getDimension(world);
        return String.format(
            "%s %s @ %s -> [ %s ]",
            playerName,
            translator.tr("location").getString(),
            dimension,
            getPlayerPos(player)
        );
    }
}
