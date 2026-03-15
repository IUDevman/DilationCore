package dev.hoosiers.dilation;

import com.fox2code.foxloader.loader.Mod;
import dev.hoosiers.dilation.feature.managers.CommandManager;
import dev.hoosiers.dilation.feature.managers.ConfigManager;
import dev.hoosiers.dilation.feature.managers.HackManager;
import dev.hoosiers.dilation.feature.managers.SettingManager;
import dev.hoosiers.dilation.imp.LinkedMethods;
import dev.hoosiers.dilation.imp.Manager;
import dev.hoosiers.dilation.imp.event.EventHandler;

import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * @author Hoosiers
 * @since 03-10-2026
 */

public final class DilationCore extends Mod implements LinkedMethods {

    public static String MOD_NAME = "DilationCore";
    public static String MOD_VERSION = "d0.5.0";

    private static DilationCore INSTANCE;
    public Logger LOGGER;

    public DilationCore() {
        if (INSTANCE == null) {
            INSTANCE = this;
        }

        LOGGER = LogManager.getLogManager().getLogger(MOD_NAME);
    }

    public static DilationCore getInstance() {
        if (INSTANCE == null) {
            new DilationCore();
        }

        return INSTANCE;
    }

    @Override
    public void onPostInit() {

        this.loadClient();
    }

    public EventHandler EVENT_HANDLER;

    public CommandManager COMMAND_MANAGER;
    public HackManager HACK_MANAGER;
    public SettingManager SETTING_MANAGER;
    public ConfigManager CONFIG_MANAGER;

    private void loadClient() {

        long preSystemTime = System.currentTimeMillis();

        this.EVENT_HANDLER = new EventHandler(this.LOGGER);

        this.HACK_MANAGER = this.returnLoadedManager(new HackManager());
        this.COMMAND_MANAGER = this.returnLoadedManager(new CommandManager());
        this.SETTING_MANAGER = this.returnLoadedManager(new SettingManager());
        this.CONFIG_MANAGER = this.returnLoadedManager(new ConfigManager());

        long postSystemTime = System.currentTimeMillis();

        LOGGER.info("Loaded " + MOD_NAME + "(" + MOD_VERSION + ") in " + (postSystemTime - preSystemTime) + " ms!");
    }

    private <T extends Manager> T returnLoadedManager(T manager) {
        this.EVENT_HANDLER.register(manager);
        manager.load(LOGGER);

        return manager;
    }
}

/**
 * todo:
 *
 * Command to change setting values
 * Module list command (with settings list)
 * Config
 * ArrayList
 * FIGURE OUT HOW THE FUCK TO CONVERT STRINGS INTO KEY INTS AND KEY INTS INTO STRINGS (FOR GUI AND BIND COMMAND)
 */
