package dev.hoosiers.dilation.mixins;

import dev.hoosiers.dilation.DilationCore;
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
public final class WorldInfoMixin {

    //NoWeather - Regular Rain
    @Inject(method = "getRaining", at = @At("HEAD"), cancellable = true)
    public void getRaining(CallbackInfoReturnable<Boolean> cir) {
        DilationCore dilationCore = DilationCore.getInstance();

        if (dilationCore == null) {
            return;
        }

        if (dilationCore.shouldNoWeather()) {
            cir.setReturnValue(false);
        }
    }

    //NoWeather - Thunderstorm
    @Inject(method = "getThundering", at = @At("HEAD"), cancellable = true)
    public void getThundering(CallbackInfoReturnable<Boolean> cir) {
        DilationCore dilationCore = DilationCore.getInstance();

        if (dilationCore == null) {
            return;
        }

        if (dilationCore.shouldNoWeather()) {
            cir.setReturnValue(false);
        }
    }
}
