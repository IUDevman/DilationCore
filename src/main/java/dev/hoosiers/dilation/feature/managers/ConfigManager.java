package dev.hoosiers.dilation.feature.managers;

import dev.hoosiers.dilation.imp.Manager;

import java.util.logging.Logger;

/**
 * @author Hoosiers
 * @since 03-14-2026
 */

public final class ConfigManager implements Manager {

    @Override
    public String name() {
        return "ConfigManager";
    }

    @Override
    public void load(Logger logger) {
        logger.info(this::name);
    }

    public void save() {

    }
}
