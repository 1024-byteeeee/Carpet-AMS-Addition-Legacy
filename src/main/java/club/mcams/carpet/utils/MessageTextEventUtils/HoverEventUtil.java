package club.mcams.carpet.utils.MessageTextEventUtils;

import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;

@SuppressWarnings("unused")
public class HoverEventUtil {
    public static HoverEvent.Action SHOW_TEXT = HoverEvent.Action.SHOW_TEXT;
    public static HoverEvent.Action SHOW_ITEM = HoverEvent.Action.SHOW_ITEM;
    public static HoverEvent.Action SHOW_ENTITY = HoverEvent.Action.SHOW_ENTITY;

    public static <T> HoverEvent event(HoverEvent.Action action, Text value) {
        return new HoverEvent(action, value);
    }
}
