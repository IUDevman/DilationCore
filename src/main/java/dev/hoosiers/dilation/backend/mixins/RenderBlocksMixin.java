package dev.hoosiers.dilation.backend.mixins;

import dev.hoosiers.dilation.backend.events.RenderBlockEvent;
import dev.hoosiers.dilation.feature.hacks.Xray;
import dev.hoosiers.dilation.imp.LinkedMethods;
import net.minecraft.client.renderer.world.RenderBlocks;
import net.minecraft.common.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Hoosiers
 * @since 02-26-2026
 */

@Mixin(value = RenderBlocks.class, priority = 6969)
public final class RenderBlocksMixin implements LinkedMethods {

    @Shadow private boolean renderAllFaces;

    @Inject(method = "renderBlockByRenderType", at = @At("HEAD"), cancellable = true)
    public void renderBlockByRenderType(Block block, int x, int y, int z, CallbackInfoReturnable<Boolean> cir)  {
        if (this.failsNullCheck()) {
            return;
        }

        //This is going to be the heaviest thing on performance, so we'll add this check.
        if (!this.getHackManager().getHack(Xray.class).isEnabled()) {
            return;
        }

        this.renderAllFaces = true;

        RenderBlockEvent renderBlockEvent = new RenderBlockEvent(block);
        this.getEventHandler().call(renderBlockEvent);

        if (renderBlockEvent.isCancelled()) {
            cir.setReturnValue(false);
        }
    }
}
