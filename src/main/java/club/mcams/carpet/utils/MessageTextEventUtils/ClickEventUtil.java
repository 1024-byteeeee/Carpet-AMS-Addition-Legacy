package club.mcams.carpet.utils.MessageTextEventUtils;

import net.minecraft.text.ClickEvent;

@SuppressWarnings("unused")
public class ClickEventUtil {
    public static ClickEvent.Action OPEN_URL = ClickEvent.Action.OPEN_URL;
    public static ClickEvent.Action OPEN_FILE = ClickEvent.Action.OPEN_FILE;
    public static ClickEvent.Action RUN_COMMAND = ClickEvent.Action.RUN_COMMAND;
    public static ClickEvent.Action SUGGEST_COMMAND = ClickEvent.Action.SUGGEST_COMMAND;
    public static ClickEvent.Action CHANGE_PAGE = ClickEvent.Action.CHANGE_PAGE;
    //#if MC>=11500
    public static ClickEvent.Action COPY_TO_CLIPBOARD = ClickEvent.Action.COPY_TO_CLIPBOARD;
    //#endif

    public static ClickEvent event(ClickEvent.Action action, String value) {
        return new ClickEvent(action, value);
    }
}
