package dev.hoosiers.dilation.feature.hacks;

import dev.hoosiers.dilation.imp.Category;
import dev.hoosiers.dilation.imp.Hack;
import net.minecraft.common.networking.Packet19EntityAction;
import org.lwjgl.input.Keyboard;

/**
 * @author Hoosiers
 * @since 03-14-2026
 */

public final class Sneak extends Hack {

    public Sneak() {
        super("Sneak", Category.Movement, Keyboard.KEY_Z, false, true, true);
    }

    @Override
    public void onDisable() {
        if (this.getWorld().isRemote && !Keyboard.isKeyDown(this.getMinecraft().gameSettings.keyBindSneak.keyCode)) {
            this.sendPacket(new Packet19EntityAction(this.getPlayer(), 2));
        }
    }

    @Override
    public void onTick() {
        if (this.getWorld().isRemote) {
            this.sendPacket(new Packet19EntityAction(this.getPlayer(), 1));
        }
    }
}
