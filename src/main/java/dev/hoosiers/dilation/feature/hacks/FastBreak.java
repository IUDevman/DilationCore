package dev.hoosiers.dilation.feature.hacks;

import dev.hoosiers.dilation.backend.events.BlockStrengthEvent;
import dev.hoosiers.dilation.imp.Category;
import dev.hoosiers.dilation.imp.Hack;
import dev.hoosiers.dilation.imp.event.EventTarget;
import org.lwjgl.input.Keyboard;

/**
 * @author Hoosiers
 * @since 03-14-2026
 */

public final class FastBreak extends Hack {

    public FastBreak() {
        super("FastBreak", Category.Player, Keyboard.KEY_H, false, true, true);
    }

    @SuppressWarnings("unused")
    @EventTarget
    public void onBlcokStrengthEvent(BlockStrengthEvent blockStrengthEvent) {
        blockStrengthEvent.setCancelled(true);
    }
}
