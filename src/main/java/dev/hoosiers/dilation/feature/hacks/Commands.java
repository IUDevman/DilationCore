package dev.hoosiers.dilation.feature.hacks;

import dev.hoosiers.dilation.imp.Category;
import dev.hoosiers.dilation.imp.Hack;
import org.lwjgl.input.Keyboard;

/**
 * @author Hoosiers
 * @since 03-10-2026
 */

public final class Commands extends Hack {

    public Commands() {
        super("Commands", Category.Client, Keyboard.KEY_NONE, true, false, true);
    }
}
