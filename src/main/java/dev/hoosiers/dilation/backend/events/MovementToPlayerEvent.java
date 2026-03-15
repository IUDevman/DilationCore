package dev.hoosiers.dilation.backend.events;

import dev.hoosiers.dilation.imp.event.imp.EventCancellable;

/**
 * @author Hoosiers
 * @since 03-14-2026
 */

public final class MovementToPlayerEvent extends EventCancellable {

    public final Type type;

    public MovementToPlayerEvent(Type type) {
        this.type = type;
    }

    public enum Type {
        Block,
        Entity,
        Explosion,
        Liquid,
        Water,
        Lava
    }
}
