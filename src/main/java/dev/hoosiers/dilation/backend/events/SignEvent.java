package dev.hoosiers.dilation.backend.events;

import dev.hoosiers.dilation.imp.event.imp.EventCancellable;
import net.minecraft.common.block.tileentity.TileEntitySign;

/**
 * @author Hoosiers
 * @since 04-28-2026
 */

public final class SignEvent extends EventCancellable {

    public final TileEntitySign tileEntitySign;

    public SignEvent(TileEntitySign tileEntitySign) {
        this.tileEntitySign = tileEntitySign;
    }
}
