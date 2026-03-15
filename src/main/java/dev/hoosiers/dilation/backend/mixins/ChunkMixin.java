package dev.hoosiers.dilation.backend.mixins;

import dev.hoosiers.dilation.backend.events.LightValueEvent;
import dev.hoosiers.dilation.imp.LinkedMethods;
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
public final class ChunkMixin implements LinkedMethods {

    @Inject(method = "getBlockLightValue", at = @At("HEAD"), cancellable = true)
    public void getBlockLightValue(int x, int y, int z, int lightmodifier, CallbackInfoReturnable<Integer> cir) {
        if (this.failsNullCheck()) {
            return;
        }

        LightValueEvent lightValueEvent = new LightValueEvent();
        this.getEventHandler().call(lightValueEvent);

        if (lightValueEvent.isCancelled()) {
            cir.setReturnValue(15);
        }
    }
}
