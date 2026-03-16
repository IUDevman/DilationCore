package dev.hoosiers.dilation.feature.hacks;

import dev.hoosiers.dilation.imp.Category;
import dev.hoosiers.dilation.imp.Hack;
import net.minecraft.common.networking.Packet92Emote;
import org.lwjgl.input.Keyboard;

/**
 * @author Hoosiers
 * @since 03-16-2026
 */

public final class Gassy extends Hack {

    public Gassy() {
        super("Gassy", Category.Render, Keyboard.KEY_NONE, false, true, true);
    }

    @Override
    public void onTick() {
        if (this.getWorld().isRemote) {
            this.sendPacket(new Packet92Emote(6));
        } else {
            this.getWorld().playAuxSFX(2050, this.getPlayer().posX, this.getPlayer().posY, this.getPlayer().posZ, 6);
        }
    }
}
