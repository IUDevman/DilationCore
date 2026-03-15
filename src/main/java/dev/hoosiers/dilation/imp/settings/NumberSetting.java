package dev.hoosiers.dilation.imp.settings;

import dev.hoosiers.dilation.imp.Setting;

/**
 * @author Hoosiers
 * @since 03-03-2026
 */

public class NumberSetting implements Setting<Double> {

    private final String name;
    private final double defaultValue;
    private double value;
    private final double min;
    private final double max;
    private final int decimal;

    public NumberSetting(String name, double value, double min, double max, int decimal) {
        this.name = name;
        this.defaultValue = value;
        this.value = value;
        this.min = min;
        this.max = max;
        this.decimal = decimal;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Double getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public Double getValue() {
        return this.value;
    }

    @Override
    public void setValue(Double value) {
        this.value = value;
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }

    public int getDecimal() {
        return this.decimal;
    }
}
