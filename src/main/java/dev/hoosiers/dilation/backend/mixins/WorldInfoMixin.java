package dev.hoosiers.dilation.backend.mixins;

import dev.hoosiers.dilation.backend.events.WeatherEvent;
import dev.hoosiers.dilation.imp.LinkedMethods;
import net.minecraft.common.world.WorldInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Hoosiers
 * @since 02-22-2026
 */

@Mixin(value = WorldInfo.class, priority = 6969)
public final class WorldInfoMixin implements LinkedMethods {

    @Inject(method = "getRaining", at = @At("HEAD"), cancellable = true)
    public void getRaining(CallbackInfoReturnable<Boolean> cir) {
        if (this.failsNullCheck()) {
            return;
        }

        WeatherEvent weatherEvent = new WeatherEvent();
        this.getEventHandler().call(weatherEvent);

        if (weatherEvent.isCancelled()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "getThundering", at = @At("HEAD"), cancellable = true)
    public void getThundering(CallbackInfoReturnable<Boolean> cir) {
        if (this.failsNullCheck()) {
            return;
        }

        WeatherEvent weatherEvent = new WeatherEvent();
        this.getEventHandler().call(weatherEvent);

        if (weatherEvent.isCancelled()) {
            cir.setReturnValue(false);
        }
    }
}
