package dev.hoosiers.dilation.backend.events;

import dev.hoosiers.dilation.imp.event.imp.EventCancellable;

/**
 * @author Hoosiers
 * @since 03-14-2026
 */

public final class FluidCollisionEvent extends EventCancellable {

    public final int x;
    public final int y;
    public final int z;

    public FluidCollisionEvent(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
