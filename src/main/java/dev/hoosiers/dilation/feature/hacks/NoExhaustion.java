package dev.hoosiers.dilation.feature.hacks;

import dev.hoosiers.dilation.backend.events.ExhaustionEvent;
import dev.hoosiers.dilation.imp.Category;
import dev.hoosiers.dilation.imp.Hack;
import dev.hoosiers.dilation.imp.event.EventTarget;
import org.lwjgl.input.Keyboard;

/**
 * @author Hoosiers
 * @since 03-14-2026
 */

public final class NoExhaustion extends Hack {

    public NoExhaustion() {
        super("NoExhaustion", Category.Movement, Keyboard.KEY_K, false, true, true);
    }

    @SuppressWarnings("unused")
    @EventTarget
    public void onExhuastionEvent(ExhaustionEvent exhaustionEvent) {
        exhaustionEvent.setCancelled(true);
    }
}
