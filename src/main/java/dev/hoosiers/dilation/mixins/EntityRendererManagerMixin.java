package dev.hoosiers.dilation.mixins;

import com.indigo3d.util.RenderSystem;
import dev.hoosiers.dilation.DilationCore;
import net.minecraft.client.player.EntityClientPlayerMP;
import net.minecraft.client.player.EntityOtherPlayerMP;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.world.Tessellator;
import net.minecraft.common.entity.Entity;
import net.minecraft.common.entity.animals.EntityAnimal;
import net.minecraft.common.entity.monsters.EntityMonster;
import net.minecraft.common.entity.monsters.EntitySlime;
import net.minecraft.common.entity.other.EntityAreaEffectCloud;
import net.minecraft.common.entity.other.EntityItem;
import net.minecraft.common.entity.other.EntityItemFireResistant;
import net.minecraft.common.util.math.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
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
public final class EntityRendererManagerMixin {

    //ESP main render method.
    //Set to tail or else the mob texture draws over the box.
    @Inject(method = "renderEntityWithPosYaw", at = @At("TAIL"))
    public <T extends Entity> void renderEntityWithPosYaw(T entity, double x, double y, double z, float yaw, float deltaTicks, boolean render_shadows, CallbackInfo ci) {
        DilationCore dilationCore = DilationCore.getInstance();

        if (dilationCore == null || dilationCore.failsNullCheck()) {
            return;
        }

        if (!dilationCore$shouldRenderESP(entity)) {
            return;
        }

        if (dilationCore.shouldESP()) {
            dilationCore$renderBoundingBox(entity, x, y, z);
        }

        if (dilationCore.shouldTracers() && entity instanceof EntityOtherPlayerMP) {
            dilationCore$drawTracerLine(x, y, z, Color.CYAN, 1F);
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

        return true;
    }

    //Renders bounding box... Copied from other MC methods but shows through blocks.
    @Unique
    private <T extends Entity> void dilationCore$renderBoundingBox(T entity, double x, double y, double z) {
        RenderSystem.disableDepthMask();
        RenderSystem.disableDepthTest();
        RenderSystem.disableTexture2D();
        RenderSystem.disableLighting();
        RenderSystem.disableCullFace();
        RenderSystem.disableBlend();

        boolean isFogEnabled = RenderSystem.isFogEnabled();

        RenderSystem.disableFog();

        GL11.glPushMatrix();

        AxisAlignedBB bb = entity.boundingBox;

        Tessellator.drawOutlinedBoundingBoxStatic(AxisAlignedBB.getAABBPool().getAABB(bb.minX - entity.posX + x, bb.minY - entity.posY + y, bb.minZ - entity.posZ + z, bb.maxX - entity.posX + x, bb.maxY - entity.posY + y, bb.maxZ - entity.posZ + z), dilationCore$getEntityColor(entity), true);

        GL11.glPopMatrix();

        if (isFogEnabled) {
            RenderSystem.enableFog();
        }

        RenderSystem.enableTexture2D();
        RenderSystem.enableLighting();
        RenderSystem.enableCullFace();
        RenderSystem.enableBlend();
        RenderSystem.enableDepthMask();
        RenderSystem.enableDepthTest();
    }

    //Returns pre-defined colors of entity types.
    @Unique
    private int dilationCore$getEntityColor(Entity entity) {

        if (entity instanceof EntityOtherPlayerMP) {
            return Color.CYAN.getRGB();
        }

        if (entity instanceof EntityAnimal) {
            return Color.GREEN.getRGB();
        }

        if (entity instanceof EntityMonster || entity instanceof EntitySlime) {
            return Color.RED.getRGB();
        }

        if (entity instanceof EntityItem || entity instanceof EntityItemFireResistant) {
            return Color.YELLOW.getRGB();
        }

        return Color.WHITE.getRGB();
    }

    //Draws a tracer from an entity to the center of the screen.
    //Shamelessly skidded from Osiris:
    //https://github.com/qe7/Osiris/blob/main/src/main/java/io/github/qe7/utils/render/OpenGLRenderUtil.java
    @Unique
    private void dilationCore$drawTracerLine(double x, double y, double z, Color color, float lineWidth) {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND); // 3042
        GL11.glEnable(GL11.GL_LINE_SMOOTH); // 2848
        GL11.glDisable(GL11.GL_DEPTH_TEST); // 2929
        GL11.glDisable(GL11.GL_TEXTURE_2D); // 3553
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND); // 3042
        GL11.glLineWidth(lineWidth);
        GL11.glColor4f((float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255, 1.0F);
        GL11.glBegin(2);
        GL11.glVertex3d(0, 0, 0);
        GL11.glVertex3d(x, y, z);
        GL11.glEnd();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }
}
