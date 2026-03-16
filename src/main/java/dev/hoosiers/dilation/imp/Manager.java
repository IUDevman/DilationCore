package dev.hoosiers.dilation.imp;

import java.util.logging.Logger;

/**
 * @author Hoosiers
 * @since 03-10-2026
 */

public interface Manager extends LinkedMethods {

    String name();

    void load(Logger logger);
}
