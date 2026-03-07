package dev.hoosiers.dilation.mixins;

import dev.hoosiers.dilation.DilationCore;
import dev.hoosiers.dilation.utils.Globals;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.common.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Hoosiers
 * @since 02-20-2026
 */

@Mixin(value = EntityRenderer.class, priority = 6969)
public final class EntityRendererMixin implements Globals {

    //delete ugly entity shadows
    @Inject(method = "renderShadow", at = @At("HEAD"), cancellable = true)
    public <T extends Entity> void renderShadow(T entity, double x, double y, double z, float opacity, float deltaTicks, CallbackInfo ci) {
        if (this.failsNullCheck()) {
            return;
        }

        DilationCore dilationCore = this.getDilationCore();

        if (dilationCore.shouldFullbright() || dilationCore.shouldXray()) {
            ci.cancel();
        }
    }
}
