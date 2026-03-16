package dev.hoosiers.dilation.feature.hacks;

import dev.hoosiers.dilation.backend.events.WeatherEvent;
import dev.hoosiers.dilation.imp.Category;
import dev.hoosiers.dilation.imp.Hack;
import dev.hoosiers.dilation.imp.event.EventTarget;
import org.lwjgl.input.Keyboard;

/**
 * @author Hoosiers
 * @since 03-14-2026
 */

public final class NoWeather extends Hack {

    public NoWeather() {
        super("NoWeather", Category.Render, Keyboard.KEY_N, false, true, true);
    }

    @SuppressWarnings("unused")
    @EventTarget
    public void onWeatherEvent(WeatherEvent weatherEvent) {
        weatherEvent.setCancelled(true);
    }
}
