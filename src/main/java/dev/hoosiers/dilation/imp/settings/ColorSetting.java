package dev.hoosiers.dilation.imp.settings;

import dev.hoosiers.dilation.imp.Setting;

import java.awt.*;

/**
 * @author Hoosiers
 * @since 03-03-2026
 */

//Not used yet but adding just in case
public class ColorSetting implements Setting<Color> {

    private final String name;
    private final Color defaultValue;
    private Color value;

    public ColorSetting(String name, Color value) {
        this.name = name;
        this.defaultValue = value;
        this.value = value;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Color getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public Color getValue() {
        return this.value;
    }

    @Override
    public void setValue(Color value) {
        this.value = value;
    }
}
