package dev.hoosiers.dilation.feature.hacks;

import dev.hoosiers.dilation.backend.events.LightValueEvent;
import dev.hoosiers.dilation.imp.Category;
import dev.hoosiers.dilation.imp.Hack;
import dev.hoosiers.dilation.imp.event.EventTarget;
import org.lwjgl.input.Keyboard;

/**
 * @author Hoosiers
 * @since 03-14-2026
 */

public final class Fullbright extends Hack {

    public Fullbright() {
        super("Fullbright", Category.Render, Keyboard.KEY_B, false, true, true);
    }

    @Override
    public void onEnable() {
        if (!this.getHackManager().getHack(Xray.class).isEnabled()) {
            this.resetWorldRenders();
        }
    }

    @Override
    public void onDisable() {
        if (!this.getHackManager().getHack(Xray.class).isEnabled()) {
            this.resetWorldRenders();
        }
    }

    @SuppressWarnings("unused")
    @EventTarget
    public void onLightValueEvent(LightValueEvent lightValueEvent) {
        lightValueEvent.setCancelled(true);
    }
}
