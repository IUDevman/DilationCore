package dev.hoosiers.dilation.feature.hacks;

import dev.hoosiers.dilation.backend.events.MovementToPlayerEvent;
import dev.hoosiers.dilation.imp.Category;
import dev.hoosiers.dilation.imp.Hack;
import dev.hoosiers.dilation.imp.event.EventTarget;
import dev.hoosiers.dilation.imp.settings.NumberSetting;
import org.lwjgl.input.Keyboard;

/**
 * @author Hoosiers
 * @since 03-14-2026
 */

public final class Fly extends Hack {

    public final NumberSetting speed = new NumberSetting("Speed", 5, 3, 20, 0);

    public Fly() {
        super("Fly", Category.Movement, Keyboard.KEY_G, false, true, true);
    }

    @Override
    public void onTick() {
        this.getPlayer().motionX = 0;
        this.getPlayer().motionY = 0;
        this.getPlayer().motionZ = 0;

        this.getPlayer().jumpMovementFactor = (float) (speed.getValue() / 3);

        if (this.getCurrentScreen() == null) {
            if (Keyboard.isKeyDown(this.getMinecraft().gameSettings.keyBindJump.keyCode)) {
                this.getPlayer().motionY = this.getPlayer().motionY + (speed.getValue() / 4);
            }

            if (Keyboard.isKeyDown(this.getMinecraft().gameSettings.keyBindSneak.keyCode)) {
                this.getPlayer().motionY = this.getPlayer().motionY - (speed.getValue() / 4);
            }
        }
    }

    @SuppressWarnings("unused")
    @EventTarget
    public void onMovementToPlayerEvent(MovementToPlayerEvent movementToPlayerEvent) {

        if (movementToPlayerEvent.type == MovementToPlayerEvent.Type.Entity
                || movementToPlayerEvent.type == MovementToPlayerEvent.Type.Block
                || movementToPlayerEvent.type == MovementToPlayerEvent.Type.Explosion) {
            return;
        }

        movementToPlayerEvent.setCancelled(true);
    }
}
