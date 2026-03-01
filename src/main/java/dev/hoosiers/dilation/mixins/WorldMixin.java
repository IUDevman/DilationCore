package dev.hoosiers.dilation.mixins;

import dev.hoosiers.dilation.DilationCore;
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
public final class WorldMixin {

    //renders entity brightness
    @Inject(method = "getLightBrightness", at = @At("HEAD"), cancellable = true)
    public void getLightBrightness(int x, int y, int z, CallbackInfoReturnable<Float> cir) {
        DilationCore dilationCore = DilationCore.getInstance();

        if (dilationCore == null || dilationCore.failsNullCheck()) {
            return;
        }

        if (dilationCore.shouldFullbright() || dilationCore.shouldXray()) {
            cir.setReturnValue(1000f);
        }
    }
}
