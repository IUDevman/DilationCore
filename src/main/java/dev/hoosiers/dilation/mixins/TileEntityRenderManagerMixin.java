package dev.hoosiers.dilation.mixins;

import com.indigo3d.util.RenderSystem;
import dev.hoosiers.dilation.DilationCore;
import dev.hoosiers.dilation.misc.TileEntityDummy;
import net.minecraft.client.renderer.block.tileentity.TileEntityRenderManager;
import net.minecraft.client.renderer.world.Tessellator;
import net.minecraft.common.block.tileentity.*;
import net.minecraft.common.util.math.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

/**
 * @author Hoosiers
 * @since 02-25-2026
 */

@Mixin(value = TileEntityRenderManager.class, priority = 6969)
public final class TileEntityRenderManagerMixin {

    //TileEntities for ESP (Chairs, Beacons, etc.).
    @Inject(method = "renderTileEntityAt", at = @At("TAIL"))
    public <T extends TileEntity> void renderTileEntityAt(T te, double x, double y, double z, float deltaTicks, int progress, CallbackInfo ci) {
        DilationCore dilationCore = DilationCore.getInstance();

        if (dilationCore == null || dilationCore.failsNullCheck() || !dilationCore.shouldESP()) {
            return;
        }

        dilationCore$renderBoundingBox(x, y, z, dilationCore$getColorForTileEntity(te));
    }

    @Unique
    private Color dilationCore$getColorForTileEntity(TileEntity tileEntity) {

        if (tileEntity instanceof TileEntityChest || tileEntity instanceof TileEntityCrate || tileEntity instanceof TileEntityDummy || tileEntity instanceof TileEntityDrawer) {
            return Color.ORANGE;
        }

        if (tileEntity instanceof TileEntityMobSpawner || tileEntity instanceof TileEntityBeacon) {
            return Color.MAGENTA;
        }

        return Color.PINK;
    }

    //Renders bounding box... Copied from other MC methods but shows through blocks.
    //All TileEntities are set to pink color for now.
    @Unique
    private void dilationCore$renderBoundingBox(double x, double y, double z, Color color) {
        RenderSystem.disableDepthMask();
        RenderSystem.disableDepthTest();
        RenderSystem.disableTexture2D();
        RenderSystem.disableLighting();
        RenderSystem.disableCullFace();
        RenderSystem.disableBlend();

        boolean isFogEnabled = RenderSystem.isFogEnabled();

        RenderSystem.disableFog();

        GL11.glPushMatrix();

        AxisAlignedBB bb = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);

        Tessellator.drawOutlinedBoundingBoxStatic(AxisAlignedBB.getAABBPool().getAABB(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ), color.getRGB(), true);

        GL11.glPopMatrix();

        if (isFogEnabled) {
            RenderSystem.enableFog();
        }

        RenderSystem.enableTexture2D();
        RenderSystem.enableLighting();
        RenderSystem.enableCullFace();
        RenderSystem.enableBlend();
        RenderSystem.enableDepthMask();
        RenderSystem.enableDepthTest();
    }
}
