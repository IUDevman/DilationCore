package dev.hoosiers.dilation.feature.hacks;

import dev.hoosiers.dilation.backend.events.Packet10FlyingEvent;
import dev.hoosiers.dilation.imp.Category;
import dev.hoosiers.dilation.imp.Hack;
import dev.hoosiers.dilation.imp.event.EventTarget;
import org.lwjgl.input.Keyboard;

/**
 * @author Hoosiers
 * @since 03-14-2026
 */

public final class NoFall extends Hack {

    public NoFall() {
        super("NoFall", Category.Player, Keyboard.KEY_L, false, true, true);
    }

    @Override
    public void onTick() {
        this.getPlayer().fallDistance = 0;
    }

    @SuppressWarnings("unused")
    @EventTarget
    public void onPacket10FlyingEvent(Packet10FlyingEvent packet10FlyingEvent) {
        packet10FlyingEvent.setCancelled(true);
    }
}
