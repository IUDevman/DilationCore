package dev.hoosiers.dilation.imp;

/**
 * @author Hoosiers
 * @since 03-10-2026
 */

public interface Command extends LinkedMethods {

    String name();

    String[] aliases();

    String description();

    void onCommand(String[] args);
}
