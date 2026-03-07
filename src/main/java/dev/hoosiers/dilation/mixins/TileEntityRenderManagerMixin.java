package dev.hoosiers.dilation.mixins;

import dev.hoosiers.dilation.DilationCore;
import dev.hoosiers.dilation.utils.Globals;
import dev.hoosiers.dilation.utils.RenderMethods;
import dev.hoosiers.dilation.utils.TileEntityDummy;
import net.minecraft.client.renderer.block.tileentity.TileEntityRenderManager;
import net.minecraft.common.block.Blocks;
import net.minecraft.common.block.tileentity.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

/**
 * @author Hoosiers
 * @since 02-25-2026
 */

@Mixin(value = TileEntityRenderManager.class, priority = 6969)
public final class TileEntityRenderManagerMixin implements Globals {

    //TileEntities for ESP (Chairs, Beacons, etc.).
    //Also portal tracers.
    @Inject(method = "renderTileEntityAt", at = @At("TAIL"))
    public <T extends TileEntity> void renderTileEntityAt(T te, double x, double y, double z, float deltaTicks, int progress, CallbackInfo ci) {
        if (this.failsNullCheck()) {
            return;
        }

        DilationCore dilationCore = this.getDilationCore();

        if (dilationCore.shouldESP()) {
            RenderMethods.renderBoundingBoxFromCoords(te, x, y, z);
        }

        if (dilationCore.shouldTracers() && dilationCore.shouldTracersPortals() && te instanceof TileEntityDummy && ((TileEntityDummy) te).getBlockID() == Blocks.PORTAL.blockID) {

            RenderMethods.drawTracerLine(x, y, z, new Color(128, 0, 128, 255), 1F);
        }
    }
}
