package dev.hoosiers.dilation.backend.mixins;

import dev.hoosiers.dilation.backend.events.RenderTileEntityEvent;
import dev.hoosiers.dilation.imp.LinkedMethods;
import net.minecraft.client.renderer.block.tileentity.TileEntityRenderManager;
import net.minecraft.common.block.tileentity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Hoosiers
 * @since 02-25-2026
 */

@Mixin(value = TileEntityRenderManager.class, priority = 6969)
public final class TileEntityRenderManagerMixin implements LinkedMethods {

    @Inject(method = "renderTileEntityAt", at = @At("TAIL"))
    public <T extends TileEntity> void renderTileEntityAt(T te, double x, double y, double z, float deltaTicks, int progress, CallbackInfo ci) {
        if (this.failsNullCheck()) {
            return;
        }

        RenderTileEntityEvent renderTileEntityEvent = new RenderTileEntityEvent(te, x, y, z);
        this.getEventHandler().call(renderTileEntityEvent);
    }
}
