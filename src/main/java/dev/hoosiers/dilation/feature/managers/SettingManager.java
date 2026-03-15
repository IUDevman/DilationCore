package dev.hoosiers.dilation.feature.managers;

import dev.hoosiers.dilation.imp.Hack;
import dev.hoosiers.dilation.imp.Manager;
import dev.hoosiers.dilation.imp.Setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

/**
 * @author Hoosiers
 * @since 03-03-2026
 */

public final class SettingManager implements Manager {

    private final LinkedHashMap<Setting<?>, Hack> hackSettings = new LinkedHashMap<>();

    @Override
    public String name() {
        return "SettingManager";
    }

    @Override
    public void load(Logger LOGGER) {
        LOGGER.info(this::name);

        this.getHackManager().HACKS.forEach(hack -> Arrays.stream(hack.getClass().getDeclaredFields()).forEach(field -> {

            if (Setting.class.isAssignableFrom(field.getType())) {

                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }

                try {
                    Setting<?> setting = (Setting<?>) field.get(hack);
                    this.hackSettings.put(setting, hack);

                } catch (IllegalAccessException ignored) {

                }
            }
        }));
    }

    public ArrayList<Setting<?>> getSettingsForHack(Hack hack) {
        ArrayList<Setting<?>> settings = new ArrayList<>();

        this.hackSettings.forEach((setting, hack1) -> {
            if (hack1.equals(hack)) {
                settings.add(setting);
            }
        });

        return settings;
    }
}
