package dev.hoosiers.dilation.backend.events;

import dev.hoosiers.dilation.imp.event.imp.EventCancellable;
import net.minecraft.common.block.Block;

/**
 * @author Hoosiers
 * @since 03-14-2026
 */

public final class RenderBlockEvent extends EventCancellable {

    public final Block block;

    public RenderBlockEvent(Block block) {
        this.block = block;
    }
}
