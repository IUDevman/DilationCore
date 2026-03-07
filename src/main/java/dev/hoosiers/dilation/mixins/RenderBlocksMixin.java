package dev.hoosiers.dilation.mixins;

import dev.hoosiers.dilation.DilationCore;
import dev.hoosiers.dilation.utils.Globals;
import dev.hoosiers.dilation.utils.XrayBlocks;
import net.minecraft.client.renderer.world.RenderBlocks;
import net.minecraft.common.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

/**
 * @author Hoosiers
 * @since 02-26-2026
 */

@Mixin(value = RenderBlocks.class, priority = 6969)
public final class RenderBlocksMixin implements Globals {

    @Shadow private boolean renderAllFaces;

    @Inject(method = "renderBlockByRenderType", at = @At("HEAD"), cancellable = true)
    public void renderBlockByRenderType(Block block, int x, int y, int z, CallbackInfoReturnable<Boolean> cir)  {
        if (this.failsNullCheck()) {
            return;
        }

        DilationCore dilationCore = this.getDilationCore();

        if (!dilationCore.shouldXray()) {
            return;
        }

        ArrayList<Block> xrayBlocks = XrayBlocks.getXrayBlocks(dilationCore.isDiamondsOnly());

        this.renderAllFaces = true;

        if (!xrayBlocks.contains(block)) {

            String blockName = block.getBlockName().toLowerCase();

            if (!dilationCore.isDiamondsOnly() && XrayBlocks.isXrayBlockByName(blockName)) {
                return;
            }

            cir.setReturnValue(false);
        }
    }
}
