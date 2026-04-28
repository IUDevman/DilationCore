package dev.hoosiers.dilation.feature.hacks;

import dev.hoosiers.dilation.backend.events.SignEvent;
import dev.hoosiers.dilation.imp.Category;
import dev.hoosiers.dilation.imp.Hack;
import dev.hoosiers.dilation.imp.event.EventTarget;
import net.minecraft.common.networking.Packet130UpdateSign;
import org.lwjgl.input.Keyboard;

/**
 * @author Hoosiers
 * @since 04-28-2026
 */

public final class AutoSign extends Hack {

    public AutoSign() {
        super("AutoSign", Category.Player, Keyboard.KEY_NONE, false, true, true);
    }

    @SuppressWarnings("unused")
    @EventTarget
    public void onSignEvent(SignEvent signEvent) {
        signEvent.tileEntitySign.signText[0] = this.toAdd[0];
        signEvent.tileEntitySign.signText[1] = this.toAdd[1];
        signEvent.tileEntitySign.signText[2] = this.toAdd[2];
        signEvent.tileEntitySign.signText[3] = this.toAdd[3];

        signEvent.tileEntitySign.updateTileEntityIThink();

        if (this.getWorld().isRemote) {
            this.sendPacket(new Packet130UpdateSign(signEvent.tileEntitySign.xCoord, signEvent.tileEntitySign.yCoord, signEvent.tileEntitySign.zCoord, signEvent.tileEntitySign.signText));
        }

        signEvent.setCancelled(true);
    }

    private final String[] toAdd = new String[]{
            "Griefed with",
            "DilationCore!!!",
            "So eZZZZZZZZ",
            "MY PENIS LONG"
    };
}
