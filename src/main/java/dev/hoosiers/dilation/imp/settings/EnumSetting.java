package dev.hoosiers.dilation.imp.settings;

import dev.hoosiers.dilation.imp.Setting;

/**
 * @author Hoosiers
 * @since 03-03-2026
 */

public class EnumSetting implements Setting<Enum<?>> {

    private final String name;
    private final Enum<?> defaultValue;
    private Enum<?> value;

    public EnumSetting(String name, Enum<?> value) {
        this.name = name;
        this.defaultValue = value;
        this.value = value;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Enum<?> getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public Enum<?> getValue() {
        return this.value;
    }

    @Override
    public void setValue(Enum<?> value) {
        this.value = value;
    }

    public void increment() {
        Enum<?>[] array = this.getValue().getDeclaringClass().getEnumConstants();
        int index = this.getValue().ordinal() + 1;

        if (index >= array.length) {
            index = 0;
        }

        this.setValue(array[index]);
    }
}
