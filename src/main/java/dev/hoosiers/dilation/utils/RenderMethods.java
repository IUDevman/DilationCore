package dev.hoosiers.dilation.utils;

import com.indigo3d.util.RenderSystem;
import net.minecraft.client.player.EntityOtherPlayerMP;
import net.minecraft.client.renderer.world.Tessellator;
import net.minecraft.common.block.Blocks;
import net.minecraft.common.block.tileentity.*;
import net.minecraft.common.entity.Entity;
import net.minecraft.common.entity.animals.EntityAnimal;
import net.minecraft.common.entity.monsters.EntityBloodWyvern;
import net.minecraft.common.entity.monsters.EntityMonster;
import net.minecraft.common.entity.monsters.EntitySlime;
import net.minecraft.common.entity.monsters.EntityWyvern;
import net.minecraft.common.entity.other.EntityItem;
import net.minecraft.common.entity.other.EntityItemFireResistant;
import net.minecraft.common.entity.projectile.EntityFireballWyvern;
import net.minecraft.common.util.math.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author Hoosiers
 * @since 03-01-2026
 */

public final class RenderMethods {

    //Draws an ESP bounding box for an Entity.
    public static  <T extends Entity> void renderBoundingBoxFromCoordsForEntity(T entity, double x, double y, double z) {

        AxisAlignedBB entityAxisAlignedBB = entity.boundingBox;

        AxisAlignedBB renderAxisAlignedBB = AxisAlignedBB.getAABBPool().getAABB(entityAxisAlignedBB.minX - entity.posX + x, entityAxisAlignedBB.minY - entity.posY + y, entityAxisAlignedBB.minZ - entity.posZ + z, entityAxisAlignedBB.maxX - entity.posX + x, entityAxisAlignedBB.maxY - entity.posY + y, entityAxisAlignedBB.maxZ - entity.posZ + z);

        int color = getEntityColor(entity);

        renderBoundingBoxFromAABB(renderAxisAlignedBB, color);
    }

    //Draws an ESP bounding box for a TileEntity.
    public static <T extends TileEntity> void renderBoundingBoxFromCoords(T tileEntity, double x, double y, double z) {

        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);

        int color = getColorForTileEntity(tileEntity);

        renderBoundingBoxFromAABB(axisAlignedBB, color);
    }

    //Draws a bounding box with the defined AxisAlignedBB parameters.
    private static void renderBoundingBoxFromAABB(AxisAlignedBB axisAlignedBB, int color) {
        RenderSystem.disableDepthMask();
        RenderSystem.disableDepthTest();
        RenderSystem.disableTexture2D();
        RenderSystem.disableLighting();
        RenderSystem.disableCullFace();
        RenderSystem.disableBlend();

        boolean isFogEnabled = RenderSystem.isFogEnabled();

        RenderSystem.disableFog();

        GL11.glPushMatrix();

        Tessellator.drawOutlinedBoundingBoxStatic(AxisAlignedBB.getAABBPool().getAABB(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ), color, true);

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

    //Draws a tracer from an entity to the center of the screen.
    //Shamelessly skidded from Osiris:
    //https://github.com/qe7/Osiris/blob/main/src/main/java/io/github/qe7/utils/render/OpenGLRenderUtil.java
    public static void drawTracerLine(double x, double y, double z, Color color, float lineWidth) {
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

    //Returns Entity ESP render color
    private static int getEntityColor(Entity entity) {

        if (entity instanceof EntityOtherPlayerMP) {
            return Color.CYAN.getRGB();
        }

        if (entity instanceof EntityAnimal) {
            return Color.GREEN.getRGB();
        }

        //Slimes are not listed as monsters for some reason
        if (entity instanceof EntityMonster || entity instanceof EntitySlime) {
            return Color.RED.getRGB();
        }

        //Neither are Wyverns
        if (entity instanceof EntityWyvern || entity instanceof EntityBloodWyvern || entity instanceof EntityFireballWyvern) {
            return Color.RED.getRGB();
        }

        if (entity instanceof EntityItem || entity instanceof EntityItemFireResistant) {
            return Color.YELLOW.getRGB();
        }

        return Color.WHITE.getRGB();
    }

    //Returns TileEntity ESP render color.
    private static int getColorForTileEntity(TileEntity tileEntity) {

        if (tileEntity instanceof TileEntityDummy && ((TileEntityDummy) tileEntity).getBlockID() == Blocks.PORTAL.blockID) {
            return new Color(128, 0, 128, 255).getRGB();
        }

        if (tileEntity instanceof TileEntityChest || tileEntity instanceof TileEntityCrate || tileEntity instanceof TileEntityDummy || tileEntity instanceof TileEntityDrawer) {
            return Color.ORANGE.getRGB();
        }

        if (tileEntity instanceof TileEntityMobSpawner || tileEntity instanceof TileEntityBeacon) {
            return Color.MAGENTA.getRGB();
        }

        return Color.PINK.getRGB();
    }
}
