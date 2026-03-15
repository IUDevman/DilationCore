package dev.hoosiers.dilation.imp.event.imp;

/**
 * @author DarkMagician6
 * @since 08-27-2013
 */

public class EventCancellable implements Event, Cancellable {

    private boolean cancelled;

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
