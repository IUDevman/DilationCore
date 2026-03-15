package dev.hoosiers.dilation.imp;

import java.awt.*;

/**
 * @author Hoosiers
 * @since 03-14-2026
 */

public final class TabGUIEntry implements LinkedMethods {

    public final String entry;
    public final Color color;

    public TabGUIEntry(String entry, Color color) {
        this.entry = entry;
        this.color = color;
    }
}
