package dev.hoosiers.dilation.backend.mixins;

import dev.hoosiers.dilation.backend.events.LightValueEvent;
import dev.hoosiers.dilation.imp.LinkedMethods;
import net.minecraft.common.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Hoosiers
 * @since 02-20-2026
 */

@Mixin(value = World.class, priority = 6969)
public final class WorldMixin implements LinkedMethods {

    //renders entity brightness
    @Inject(method = "getLightBrightness", at = @At("HEAD"), cancellable = true)
    public void getLightBrightness(int x, int y, int z, CallbackInfoReturnable<Float> cir) {
        if (this.failsNullCheck()) {
            return;
        }

        LightValueEvent lightValueEvent = new LightValueEvent();
        this.getEventHandler().call(lightValueEvent);

        if (lightValueEvent.isCancelled()) {
            cir.setReturnValue(1000f);
        }
    }
}
