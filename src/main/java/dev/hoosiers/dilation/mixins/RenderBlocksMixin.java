package dev.hoosiers.dilation.mixins;

import dev.hoosiers.dilation.DilationCore;
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
public final class RenderBlocksMixin {

    @Shadow private boolean renderAllFaces;

    @Inject(method = "renderBlockByRenderType", at = @At("HEAD"), cancellable = true)
    public void renderBlockByRenderType(Block block, int x, int y, int z, CallbackInfoReturnable<Boolean> cir)  {
        DilationCore dilationCore = DilationCore.getInstance();

        if (dilationCore == null || !dilationCore.shouldXray() || dilationCore.failsNullCheck()) {
            return;
        }

        ArrayList<Block> xrayBlocks = XrayBlocks.getXrayBlocks(dilationCore.isDiamondsOnly());

        this.renderAllFaces = true;

        if (!xrayBlocks.contains(block)) {

            String blockName = block.getBlockName().toLowerCase();

            //too many fucking types!
            if (!dilationCore.isDiamondsOnly() && (blockName.contains("ladder")
                    || blockName.contains("door")
                    || blockName.contains("button")
                    || blockName.contains("skull")
                    || blockName.contains("fence")
                    || blockName.contains("plate")
                    || blockName.contains("chain")
                    || blockName.contains("carved")
                    || blockName.contains("jack")
                    || blockName.contains("item frame")
                    || blockName.contains("plushie")
                    || blockName.contains("lamp")
                    || blockName.contains("cactus"))) {
                return;
            }

            cir.setReturnValue(false);
        }
    }
}
