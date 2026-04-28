package dev.hoosiers.dilation.feature.managers;

import dev.hoosiers.dilation.backend.events.ClientTickEvent;
import dev.hoosiers.dilation.feature.hacks.*;
import dev.hoosiers.dilation.imp.Hack;
import dev.hoosiers.dilation.imp.Manager;
import dev.hoosiers.dilation.imp.event.EventTarget;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * @author Hoosiers
 * @since 03-10-2026
 */

public final class HackManager implements Manager {

    public final ArrayList<Hack> HACKS = new ArrayList<>();

    @Override
    public String name() {
        return "HackManager";
    }

    @Override
    public void load(Logger logger) {
        logger.info(this::name);

        this.HACKS.add(new dev.hoosiers.dilation.feature.hacks.ArrayList());
        this.HACKS.add(new AutoSign());
        this.HACKS.add(new CommandPreview());
        this.HACKS.add(new Commands());
        this.HACKS.add(new Coordinates());
        this.HACKS.add(new ESP());
        this.HACKS.add(new FastBreak());
        this.HACKS.add(new Fly());
        this.HACKS.add(new Fullbright());
        this.HACKS.add(new Gassy());
        this.HACKS.add(new Jesus());
        this.HACKS.add(new KillAura());
        this.HACKS.add(new NoExhaustion());
        this.HACKS.add(new NoFall());
        this.HACKS.add(new NoWeather());
        this.HACKS.add(new Sneak());
        this.HACKS.add(new TabGUI());
        this.HACKS.add(new TorchNuker());
        this.HACKS.add(new Tracers());
        this.HACKS.add(new Velocity());
        this.HACKS.add(new Xray());
    }

    @SuppressWarnings("unused")
    @EventTarget
    public void onTick(ClientTickEvent event) {
        if (this.failsNullCheck()) {
            return;
        }

        boolean inScreen = !(this.getCurrentScreen() == null);

        this.HACKS.forEach(hack -> {

            if (!inScreen && hack.getBind().isPressed()) {
                hack.toggle();
            }

            if (hack.isEnabled()) {
                hack.onTick();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T extends Hack> T getHack(Class<T> tClass) {
        return (T) this.HACKS.stream().filter(hack -> hack.getClass().equals(tClass)).findFirst().orElse(null);
    }

    public Hack getHack(String NAME) {
        return this.HACKS.stream().filter(hack -> hack.NAME.equalsIgnoreCase(NAME)).findFirst().orElse(null);
    }
}
