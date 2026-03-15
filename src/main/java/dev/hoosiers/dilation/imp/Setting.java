package dev.hoosiers.dilation.imp;

/**
 * @author Hoosiers
 * @since 03-03-2026
 */

public interface Setting<T> {

    String getName();

    T getDefaultValue();

    T getValue();

    void setValue(T value);

    default void reset() {
        setValue(getDefaultValue());
    }
}
