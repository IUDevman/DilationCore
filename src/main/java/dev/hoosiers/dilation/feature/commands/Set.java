package dev.hoosiers.dilation.feature.commands;

import dev.hoosiers.dilation.imp.Command;
import dev.hoosiers.dilation.imp.Hack;
import dev.hoosiers.dilation.imp.Setting;
import dev.hoosiers.dilation.imp.settings.BooleanSetting;
import dev.hoosiers.dilation.imp.settings.ColorSetting;
import dev.hoosiers.dilation.imp.settings.EnumSetting;
import dev.hoosiers.dilation.imp.settings.NumberSetting;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Hoosiers
 * @since 03-15-2026
 */

public final class Set implements Command {

    @Override
    public String name() {
        return "Set";
    }

    @Override
    public String[] aliases() {
        return new String[]{
                this.name(),
                "SetValue"
        };
    }

    @Override
    public String description() {
        return "[hack] [setting_name] [value] (r_g_b for color settings); Changes setting values.";
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length <= 1) {
            this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to change setting (no hack name inputted)!");
            return;
        }

        String hackName = args[1];

        if (hackName == null) {
            this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to change setting (hack not found)!");
            return;
        }

        Hack hack = this.getHackManager().getHack(hackName);

        if (hack == null) {
            this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to change setting (no hack found for name)!");
            return;
        }

        if (args.length < 3) {

            ArrayList<String> settingNames = new ArrayList<>();

            this.getSettingManager().getSettingsForHack(hack).forEach(setting -> settingNames.add(setting.getName()));

            this.sendClientMessageWithPrefix("Hack:" + " '" + hack.NAME + "' settings: " + settingNames);
            return;
        }

        String settingName = args[2].replace("_", " ");

        Setting<?> setting = this.getSettingManager().getSettingsForHack(hack).stream().filter(setting1 -> setting1.getName().equalsIgnoreCase(settingName)).findFirst().orElse(null);

        if (setting == null) {
            this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to change setting for '" + hack.NAME + "' (no setting found for name)!");
            return;
        }

        if (args.length < 4) {
            this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to change setting '" + setting.getName() + "' (" + hack.NAME + ") (no value inputted)!");
            return;
        }

        this.setValueDetailed(hack, setting, args[3]);
    }

    private void setValueDetailed(Hack hack, Setting<?> setting, String value) {
        if (setting instanceof BooleanSetting) {
            if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")) {
                this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to change setting '" + setting.getName() + "' (" + hack.NAME + ") (invalid boolean)!");
                return;
            }

            BooleanSetting booleanSetting = (BooleanSetting) setting;

            booleanSetting.setValue(Boolean.parseBoolean(value));
            this.sendClientMessageWithPrefix("Hack: '" + hack.NAME +  "' Setting: '" + setting.getName() + "' boolean value set to " + (booleanSetting.getValue() ? "§a" : "§c") + booleanSetting.getValue() + "§f!");

        } else if (setting instanceof NumberSetting) {
            try {
                double value1 = Double.parseDouble(value);

                if (value1 > ((NumberSetting) setting).getMax()) value1 = ((NumberSetting) setting).getMax();
                else if (value1 < ((NumberSetting) setting).getMin()) value1 = ((NumberSetting) setting).getMin();

                String decimalFix = this.adjustForDecimals((NumberSetting) setting, value1);

                NumberSetting numberSetting = (NumberSetting) setting;

                numberSetting.setValue(Double.parseDouble(decimalFix));
                this.sendClientMessageWithPrefix("Hack: '" + hack.NAME +  "' Setting: '" + setting.getName() + "' number value set to §b" + numberSetting.getValue() + "§f!");


            } catch (NumberFormatException ignored) {
                this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to change setting '" + setting.getName() + "' (" + hack.NAME + ") (invalid number value)!");
            }
        } else if (setting instanceof EnumSetting) {
            Enum<?>[] array = ((EnumSetting) setting).getValue().getDeclaringClass().getEnumConstants();

            AtomicBoolean found = new AtomicBoolean(false);

            Arrays.stream(array).forEach(anEnum -> {
                if (!found.get() && anEnum.name().equalsIgnoreCase(value)) {

                    found.set(true);

                    EnumSetting enumSetting = (EnumSetting) setting;

                    enumSetting.setValue(anEnum);
                    this.sendClientMessageWithPrefix("Hack: '" + hack.NAME +  "' Setting: '" + setting.getName() + "' enum value set to §b" + enumSetting.getValue() + "§f!");
                }
            });

            if (!found.get()) {
                this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to change setting '" + setting.getName() + "' (" + hack.NAME + ") (invalid enum value)!");
            }
        } else if (setting instanceof ColorSetting) {
            String[] values = value.split("_");

            if (values.length < 3) {
                this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to change setting '" + setting.getName() + "' (" + hack.NAME + ") (invalid color)!");
                return;
            }

            try {
                int red = adjustForColor(Integer.parseInt(values[0]));
                int green = adjustForColor(Integer.parseInt(values[1]));
                int blue = adjustForColor(Integer.parseInt(values[2]));

                ColorSetting colorSetting = (ColorSetting) setting;

                colorSetting.setValue(new Color(red, green, blue));
                this.sendClientMessageWithPrefix("Hack: '" + hack.NAME +  "' Setting: '" + setting.getName() + "' color set to §b" + colorSetting.getValue().toString().replace("java.awt.Color", "") + "§f!");

            } catch (NumberFormatException ignored) {
                this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to change setting '" + setting.getName() + "' (" + hack.NAME + ") (invalid color)!");
            }
        }
    }

    private String adjustForDecimals(NumberSetting setting, double value) {
        return String.format("%." + setting.getDecimal() + "f", value);
    }

    private int adjustForColor(int value) {
        if (value > 255) return 255;
        else return Math.max(value, 0);
    }
}
