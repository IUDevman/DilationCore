package dev.hoosiers.dilation.mixins;

import dev.hoosiers.dilation.DilationCore;
import dev.hoosiers.dilation.utils.Globals;
import dev.hoosiers.dilation.utils.RenderMethods;
import net.minecraft.client.player.EntityClientPlayerMP;
import net.minecraft.client.player.EntityOtherPlayerMP;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.common.entity.Entity;
import net.minecraft.common.entity.other.EntityAreaEffectCloud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

/**
 * @author Hoosiers
 * @since 02-24-2026
 */

@Mixin(value = EntityRendererManager.class, priority = 6969)
public final class EntityRendererManagerMixin implements Globals {

    //ESP main render method.
    //Set to tail or else the mob texture draws over the box.
    @Inject(method = "renderEntityWithPosYaw", at = @At("TAIL"))
    public <T extends Entity> void renderEntityWithPosYaw(T entity, double x, double y, double z, float yaw, float deltaTicks, boolean render_shadows, CallbackInfo ci) {
        if (this.failsNullCheck()) {
            return;
        }

        DilationCore dilationCore = this.getDilationCore();

        if (!dilationCore$shouldRenderESP(entity)) {
            return;
        }

        if (dilationCore.shouldESP()) {
            RenderMethods.renderBoundingBoxFromCoordsForEntity(entity, x, y, z);
        }

        if (dilationCore.shouldTracers() && entity instanceof EntityOtherPlayerMP) {
            RenderMethods.drawTracerLine(x, y, z, Color.CYAN, 1F);
        }
    }

    //Checks to make sure no undesired entities are rendered in ESP... Reindev has a lot of "utility" entities that we don't want boxes for.
    @Unique
    private boolean dilationCore$shouldRenderESP(Entity entity) {

        if (entity instanceof EntityAreaEffectCloud) {
            return false;
        }

        if (entity instanceof EntityClientPlayerMP) {
            return false;
        }

        if (!entity.isEntityAlive()) {
            return false;
        }

        if (entity.ticksExisted <= 2) {
            return false;
        }

        return true;
    }
}
