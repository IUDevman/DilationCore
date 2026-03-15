package dev.hoosiers.dilation.backend.mixins;

import dev.hoosiers.dilation.backend.events.MovementToPlayerEvent;
import dev.hoosiers.dilation.imp.LinkedMethods;
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
public final class EntityMixin implements LinkedMethods {

    @Inject(method = "applyEntityCollision", at = @At("HEAD"), cancellable = true)
    public void applyEntityCollision(Entity entity, CallbackInfo ci) {
        if (this.failsNullCheck()) {
            return;
        }

        MovementToPlayerEvent movementToPlayerEvent = new MovementToPlayerEvent(MovementToPlayerEvent.Type.Entity);
        this.getEventHandler().call(movementToPlayerEvent);

        if (movementToPlayerEvent.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(method = "handleLiquidMovement", at = @At("HEAD"), cancellable = true)
    public void handleLiquidMovement(CallbackInfoReturnable<Boolean> cir) {
        if (this.failsNullCheck()) {
            return;
        }

        MovementToPlayerEvent movementToPlayerEvent = new MovementToPlayerEvent(MovementToPlayerEvent.Type.Liquid);
        this.getEventHandler().call(movementToPlayerEvent);

        if (movementToPlayerEvent.isCancelled()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "handleWaterMovement", at = @At("HEAD"), cancellable = true)
    public void handleWaterMovement(CallbackInfoReturnable<Boolean> cir) {
        if (this.failsNullCheck()) {
            return;
        }

        MovementToPlayerEvent movementToPlayerEvent = new MovementToPlayerEvent(MovementToPlayerEvent.Type.Water);
        this.getEventHandler().call(movementToPlayerEvent);

        if (movementToPlayerEvent.isCancelled()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "handleLavaMovement", at = @At("HEAD"), cancellable = true)
    public void handleLavaMovement(CallbackInfoReturnable<Boolean> cir) {
        if (this.failsNullCheck()) {
            return;
        }

        MovementToPlayerEvent movementToPlayerEvent = new MovementToPlayerEvent(MovementToPlayerEvent.Type.Lava);
        this.getEventHandler().call(movementToPlayerEvent);

        if (movementToPlayerEvent.isCancelled()) {
            cir.setReturnValue(false);
        }
    }
}
