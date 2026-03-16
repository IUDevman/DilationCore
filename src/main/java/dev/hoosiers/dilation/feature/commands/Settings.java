package dev.hoosiers.dilation.feature.commands;

import dev.hoosiers.dilation.imp.Command;
import dev.hoosiers.dilation.imp.Setting;
import dev.hoosiers.dilation.imp.settings.BooleanSetting;
import dev.hoosiers.dilation.imp.settings.ColorSetting;
import dev.hoosiers.dilation.imp.settings.EnumSetting;
import dev.hoosiers.dilation.imp.settings.NumberSetting;

import java.util.ArrayList;

/**
 * @author Hoosiers
 * @since 03-15-2026
 */

public final class Settings implements Command {

    @Override
    public String name() {
        return "Settings";
    }

    @Override
    public String[] aliases() {
        return new String[]{
                this.name(),
                "Setting",
                "SettingsList",
                "SettingList"
        };
    }

    @Override
    public String description() {
        return "Returns the list of toggleable settings.";
    }

    @Override
    public void onCommand(String[] args) {
        this.sendClientMessageWithPrefix("List of Settings:");

        this.getHackManager().HACKS.forEach(hack -> {
            ArrayList<Setting<?>> settings = this.getSettingManager().getSettingsForHack(hack);

            if (!settings.isEmpty()) {
                settings.forEach(setting -> {

                    if (setting instanceof BooleanSetting) {

                        BooleanSetting booleanSetting = (BooleanSetting) setting;

                        this.sendClientMessageWithPrefix("Module: §b" + hack.NAME + "§f: Setting: §e" + booleanSetting.getName() + "§f; value = " + (booleanSetting.getValue() ? "§a" : "§c") + booleanSetting.getValue() + "§f, default = " + (booleanSetting.getDefaultValue() ? "§a" : "§c") + booleanSetting.getDefaultValue() + "§f.");

                    } else if (setting instanceof NumberSetting) {

                        NumberSetting numberSetting = (NumberSetting) setting;

                        this.sendClientMessageWithPrefix("Module: §b" + hack.NAME + "§f: Setting: §e" + numberSetting.getName() + "§f; value = §a" + numberSetting.getValue() + "§f, default = §b" + numberSetting.getDefaultValue() + "§f, min = §b" + numberSetting.getMin() + "§f, max = §b" + numberSetting.getMax() + "§f, decimal = §b" + numberSetting.getDecimal() + "§f.");

                    } else if (setting instanceof EnumSetting) {

                        EnumSetting enumSetting = (EnumSetting) setting;

                        this.sendClientMessageWithPrefix("Module: §b" + hack.NAME + "§f: Setting: §e" + enumSetting.getName() + "§f; value = §a" + enumSetting.getValue() + "§f, default = §b" + enumSetting.getDefaultValue() + "§f.");

                    } else if (setting instanceof ColorSetting) {

                        ColorSetting colorSetting = (ColorSetting) setting;

                        this.sendClientMessageWithPrefix("Module: §b" + hack.NAME + "§f: Setting: §e" + colorSetting.getName() + "§f; value = §a" + colorSetting.getValue().toString().replace("java.awt.Color", "") + "§f, default = §b" + colorSetting.getDefaultValue().toString().replace("java.awt.Color", "") + "§f.");
                    }

                });
            }
        });
    }
}
