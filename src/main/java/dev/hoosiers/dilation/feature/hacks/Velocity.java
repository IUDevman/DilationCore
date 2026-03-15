package dev.hoosiers.dilation.feature.hacks;

import dev.hoosiers.dilation.backend.events.MovementToPlayerEvent;
import dev.hoosiers.dilation.imp.Category;
import dev.hoosiers.dilation.imp.Hack;
import dev.hoosiers.dilation.imp.event.EventTarget;
import org.lwjgl.input.Keyboard;

/**
 * @author Hoosiers
 * @since 03-14-2026
 */

public final class Velocity extends Hack {

    public Velocity() {
        super("Velocity", Category.Player, Keyboard.KEY_I, false, true, true);
    }

    @SuppressWarnings("unused")
    @EventTarget
    public void onMovementToPlayerEvent(MovementToPlayerEvent movementToPlayerEvent) {

        if (movementToPlayerEvent.type == MovementToPlayerEvent.Type.Block
                || movementToPlayerEvent.type == MovementToPlayerEvent.Type.Entity
                || movementToPlayerEvent.type == MovementToPlayerEvent.Type.Water
                || movementToPlayerEvent.type == MovementToPlayerEvent.Type.Explosion) {
            movementToPlayerEvent.setCancelled(true);
        }
    }
}
