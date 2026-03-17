package dev.hoosiers.dilation.backend.mixins;

import dev.hoosiers.dilation.backend.events.LightValueEvent;
import dev.hoosiers.dilation.backend.events.SmokeEmoteEvent;
import dev.hoosiers.dilation.imp.LinkedMethods;
import net.minecraft.common.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Hoosiers
 * @since 02-20-2026
 */

@Mixin(value = World.class, priority = 6969)
public final class WorldMixin implements LinkedMethods {

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

    @Inject(method = "playAuxSFX", at = @At("HEAD"), cancellable = true)
    public void playAusSFX(int type, double x, double y, double z, int payload, CallbackInfo ci) {
        if (this.failsNullCheck()) {
            return;
        }

        if (payload == 6) {
            SmokeEmoteEvent smokeEmoteEvent = new SmokeEmoteEvent(x, y, z);
            this.getEventHandler().call(smokeEmoteEvent);

            if (smokeEmoteEvent.isCancelled()) {
                ci.cancel();
            }
        }
    }
}
