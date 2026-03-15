package dev.hoosiers.dilation.backend.mixins;

import dev.hoosiers.dilation.backend.events.RenderEntityEvent;
import dev.hoosiers.dilation.imp.LinkedMethods;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.common.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Hoosiers
 * @since 02-24-2026
 */

@Mixin(value = EntityRendererManager.class, priority = 6969)
public final class EntityRendererManagerMixin implements LinkedMethods {

    @Inject(method = "renderEntityWithPosYaw", at = @At("TAIL"))
    public <T extends Entity> void renderEntityWithPosYaw(T entity, double x, double y, double z, float yaw, float deltaTicks, boolean render_shadows, CallbackInfo ci) {
        if (this.failsNullCheck()) {
            return;
        }

        RenderEntityEvent renderEntityEvent = new RenderEntityEvent(entity, x, y, z);
        this.getEventHandler().call(renderEntityEvent);
    }
}
