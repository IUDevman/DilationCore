package dev.hoosiers.dilation.backend.events;

import dev.hoosiers.dilation.imp.event.imp.EventCancellable;
import net.minecraft.common.block.tileentity.TileEntity;

/**
 * @author Hoosiers
 * @since 03-14-2026
 */

public final class RenderTileEntityEvent extends EventCancellable {

    public final TileEntity tileEntity;
    public final double x;
    public final double y;
    public final double z;

    public RenderTileEntityEvent(TileEntity tileEntity, double x, double y, double z) {
        this.tileEntity = tileEntity;
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
