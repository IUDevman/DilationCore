package dev.hoosiers.dilation.feature.managers;

import dev.hoosiers.dilation.DilationCore;
import dev.hoosiers.dilation.imp.Hack;
import dev.hoosiers.dilation.imp.Manager;
import dev.hoosiers.dilation.imp.Setting;
import dev.hoosiers.dilation.imp.settings.BooleanSetting;
import dev.hoosiers.dilation.imp.settings.ColorSetting;
import dev.hoosiers.dilation.imp.settings.EnumSetting;
import dev.hoosiers.dilation.imp.settings.NumberSetting;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * @author Hoosiers
 * @since 03-14-2026
 */

public final class ConfigManager implements Manager {

    private Logger logger;

    @Override
    public String name() {
        return "ConfigManager";
    }

    @Override
    public void load(Logger logger) {
        logger.info(this::name);
        this.logger = logger;

        File directory = this.returnDilationCoreDirectory();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(directory));

            String bufferedEntry;

            while ((bufferedEntry = bufferedReader.readLine()) != null) {
                try {

                    String[] entries = bufferedEntry.split(":");

                    String firstEntry = entries[0];
                    String secondEntry = entries[1];

                    if (firstEntry.equalsIgnoreCase("§CommandManager")) {

                        this.getCommandManager().PREFIX = secondEntry;
                        continue;
                    }

                    Hack hack = this.getHackManager().HACKS.stream().filter(hack1 -> hack1.NAME.equalsIgnoreCase(firstEntry)).findFirst().orElse(null);

                    if (hack != null) {

                        String thirdEntry = entries[2];

                        switch (secondEntry) {
                            case "§Enabled":
                                hack.setEnabled(Boolean.parseBoolean(thirdEntry));
                                break;
                            case "§KeyCode":
                                hack.setBind(Integer.parseInt(thirdEntry));
                                break;
                            case "§Drawn":
                                hack.DRAWN = Boolean.parseBoolean(thirdEntry);
                                break;
                            case "§Messages":
                                hack.MESSAGES = Boolean.parseBoolean(thirdEntry);
                                break;
                            default:
                                this.loadHackWithSettings(hack, secondEntry, thirdEntry);
                        }
                    }

                } catch (Exception ignored) {
                    this.logger.info("Failed to load " + DilationCore.MOD_NAME + " (" + DilationCore.MOD_VERSION + ") config [" + bufferedEntry + "]!");
                }
            }

            bufferedReader.close();

        } catch (Exception ignored) {
            this.logger.info("Failed to load " + DilationCore.MOD_NAME + " (" + DilationCore.MOD_VERSION + ") config!");
        }
    }

    private void loadHackWithSettings(Hack hack, String secondEntry, String thirdEntry) {

        ArrayList<Setting<?>> settings = this.getSettingManager().getSettingsForHack(hack);

        if (!settings.isEmpty()) {

            settings.forEach(setting -> {
                if (setting.getName().equalsIgnoreCase(secondEntry)) {

                    if (setting instanceof BooleanSetting) {

                        ((BooleanSetting) setting).setValue(Boolean.parseBoolean(thirdEntry));

                    } else if (setting instanceof NumberSetting) {

                        ((NumberSetting) setting).setValue(Double.parseDouble(thirdEntry));

                    } else if (setting instanceof EnumSetting) {
                        Enum<?>[] array = ((EnumSetting) setting).getValue().getDeclaringClass().getEnumConstants();

                        Arrays.stream(array).forEach(anEnum -> {
                            if (anEnum.name().equalsIgnoreCase(thirdEntry)) {
                                ((EnumSetting) setting).setValue(anEnum);
                            }
                        });

                    } else if (setting instanceof ColorSetting) {

                        ((ColorSetting) setting).setValue(new Color(Integer.parseInt(thirdEntry)));
                    }
                }
            });
        }
    }

    public void save() {
        File directory = this.returnDilationCoreDirectory();

        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(directory));

            printWriter.println("§CommandManager:" + this.getCommandManager().PREFIX);

            this.getHackManager().HACKS.forEach(hack -> {
                printWriter.println(hack.NAME + ":§Enabled:" + hack.isEnabled());
                printWriter.println(hack.NAME + ":§KeyCode:" + hack.getBind().keyCode);
                printWriter.println(hack.NAME + ":§Drawn:" + hack.DRAWN);
                printWriter.println(hack.NAME + ":§Messages:" + hack.MESSAGES);

                this.getSettingManager().getSettingsForHack(hack).forEach(setting -> {
                    if (setting instanceof ColorSetting) {

                        ColorSetting colorSetting = (ColorSetting) setting;
                        printWriter.println(hack.NAME + colorSetting.getName() + ":" + colorSetting.getValue().getRGB());

                    } else {
                        printWriter.println(hack.NAME + ":" + setting.getName() + ":" + setting.getValue());
                    }
                });
            });

            printWriter.close();

        } catch (Exception ignored) {
            this.logger.info("Failed to save " + DilationCore.MOD_NAME + " (" + DilationCore.MOD_VERSION + ") config!");
        }
    }

    private File returnDilationCoreDirectory() {
        File minecraftDirectory = this.getMinecraft().getMinecraftDir();

        return new File(minecraftDirectory, DilationCore.MOD_NAME + "_Config.txt");
    }

    //Shit to save/load:
    // Modules -
    // - Enabled
    // - Bind
    // - Drawn
    // - Messages
    // CommandManager prefix

    // ModuleName : [§Enabled/§Bind/§Drawn/§Messages or SettingName] : [blank or setting value]
    // §CommandManager : Prefix : blank
}
