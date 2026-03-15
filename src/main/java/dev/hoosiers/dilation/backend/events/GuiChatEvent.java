package dev.hoosiers.dilation.backend.events;

import dev.hoosiers.dilation.imp.event.imp.EventCancellable;

/**
 * @author Hoosiers
 * @since 03-15-2026
 */

public final class GuiChatEvent extends EventCancellable {

    public final String message;
    public final int x1;
    public final int x2;
    public final int y1;
    public final int y2;

    public GuiChatEvent(String message, int x1, int x2, int y1, int y2) {
        this.message = message;

        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }
}
