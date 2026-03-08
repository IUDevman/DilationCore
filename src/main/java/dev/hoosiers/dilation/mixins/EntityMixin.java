package dev.hoosiers.dilation.mixins;

import dev.hoosiers.dilation.DilationCore;
import dev.hoosiers.dilation.utils.Globals;
import net.minecraft.common.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Hoosiers
 * @since 03-07-2026
 */

@Mixin(value = Entity.class, priority = 6969)
public final class EntityMixin implements Globals {

    //Velocity = pushed by entities
    @Inject(method = "applyEntityCollision", at = @At("HEAD"), cancellable = true)
    public void applyEntityCollision(Entity entity, CallbackInfo ci) {
        if (this.failsNullCheck()) {
            return;
        }

        DilationCore dilationCore = this.getDilationCore();

        if (!dilationCore.shouldVelocity()) {
            return;
        }

        ci.cancel();
    }

    //Prevent water push for flight and velocity
    @Inject(method = "handleWaterMovement", at = @At("HEAD"), cancellable = true)
    public void handleWaterMovement(CallbackInfoReturnable<Boolean> cir) {
        if (this.failsNullCheck()) {
            return;
        }
        DilationCore dilationCore = this.getDilationCore();

        if (dilationCore.shouldFly() || dilationCore.shouldVelocity()) {
            cir.setReturnValue(false);
        }
    }

    //Prevent liquid interactions while flying
    @Inject(method = "handleLiquidMovement", at = @At("HEAD"), cancellable = true)
    public void handleLiquidMovement(CallbackInfoReturnable<Boolean> cir) {
        if (this.failsNullCheck()) {
            return;
        }
        DilationCore dilationCore = this.getDilationCore();

        if (dilationCore.shouldFly()) {
            cir.setReturnValue(false);
        }
    }

    //Prevent liquid interactions while flying (lava)
    @Inject(method = "handleLavaMovement", at = @At("HEAD"), cancellable = true)
    public void handleLavaMovement(CallbackInfoReturnable<Boolean> cir) {
        if (this.failsNullCheck()) {
            return;
        }
        DilationCore dilationCore = this.getDilationCore();

        if (dilationCore.shouldFly()) {
            cir.setReturnValue(false);
        }
    }
}
