package dev.hoosiers.dilation.backend.events;

import dev.hoosiers.dilation.imp.event.imp.EventCancellable;
import net.minecraft.common.entity.Entity;

/**
 * @author Hoosiers
 * @since 03-14-2026
 */

public final class RenderEntityEvent extends EventCancellable {

    public final Entity entity;
    public final double x;
    public final double y;
    public final double z;

    public RenderEntityEvent(Entity entity, double x, double y, double z) {
        this.entity = entity;
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
