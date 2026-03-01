package dev.hoosiers.dilation.mixins;

import dev.hoosiers.dilation.DilationCore;
import net.minecraft.common.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Hoosiers
 * @since 02-20-2026
 */

@Mixin(value = Chunk.class, priority = 6969)
public final class ChunkMixin {

    //sets block brightness
    @Inject(method = "getBlockLightValue", at = @At("HEAD"), cancellable = true)
    public void getBlockLightValue(int x, int y, int z, int lightmodifier, CallbackInfoReturnable<Integer> cir) {
        DilationCore dilationCore = DilationCore.getInstance();

        if (dilationCore == null || dilationCore.failsNullCheck()) {
            return;
        }

        if (dilationCore.shouldFullbright() || dilationCore.shouldXray()) {
            cir.setReturnValue(15);
        }
    }
}
