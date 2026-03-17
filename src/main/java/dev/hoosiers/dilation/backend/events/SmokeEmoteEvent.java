package dev.hoosiers.dilation.backend.events;

import dev.hoosiers.dilation.imp.event.imp.EventCancellable;

/**
 * @author Hoosiers
 * @since 03-16-2026
 *
 * So much effort just for the Gassy hack.
 */

public final class SmokeEmoteEvent extends EventCancellable {

    public final double x;
    public final double y;
    public final double z;

    public SmokeEmoteEvent(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
