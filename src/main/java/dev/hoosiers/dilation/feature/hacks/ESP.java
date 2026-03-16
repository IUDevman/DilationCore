package dev.hoosiers.dilation.feature.hacks;

import com.indigo3d.util.RenderSystem;
import dev.hoosiers.dilation.backend.events.RenderEntityEvent;
import dev.hoosiers.dilation.backend.events.RenderTileEntityEvent;
import dev.hoosiers.dilation.imp.Category;
import dev.hoosiers.dilation.imp.Hack;
import dev.hoosiers.dilation.imp.TileEntityDummy;
import dev.hoosiers.dilation.imp.event.EventTarget;
import dev.hoosiers.dilation.imp.settings.BooleanSetting;
import net.minecraft.client.player.EntityClientPlayerMP;
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
import net.minecraft.common.entity.other.EntityAreaEffectCloud;
import net.minecraft.common.entity.other.EntityItem;
import net.minecraft.common.entity.other.EntityItemFireResistant;
import net.minecraft.common.entity.projectile.EntityFireballWyvern;
import net.minecraft.common.util.math.AxisAlignedBB;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author Hoosiers
 * @since 03-14-2026
 */

public final class ESP extends Hack {

    public final BooleanSetting players = new BooleanSetting("Players", true);
    public final BooleanSetting hostiles = new BooleanSetting("Hostiles", true);
    public final BooleanSetting animals = new BooleanSetting("Animals", true);
    public final BooleanSetting items = new BooleanSetting("Items", true);
    public final BooleanSetting miscEntities = new BooleanSetting("Misc Entities", true);
    public final BooleanSetting containers = new BooleanSetting("Containers", true);
    public final BooleanSetting portals = new BooleanSetting("Portals", true);
    public final BooleanSetting miscTileEntities = new BooleanSetting("Misc Tile Entities", true);

    public ESP() {
        super("ESP", Category.Render, Keyboard.KEY_M, false, true, true);
    }

    @SuppressWarnings("unused")
    @EventTarget
    public void onRenderEntityEvent(RenderEntityEvent renderEntityEvent) {

        Entity entity = renderEntityEvent.entity;

        if (this.shouldRenderESP(entity)) {
            this.renderBoundingBoxFromCoordsForEntity(entity, renderEntityEvent.x, renderEntityEvent.y, renderEntityEvent.z);
        }
    }

    @SuppressWarnings("unused")
    @EventTarget
    public void onRenderTileEntityEvent(RenderTileEntityEvent renderTileEntityEvent) {
        if (this.shouldRenderESP(renderTileEntityEvent.tileEntity)) {
            this.renderBoundingBoxFromCoordsForTileEntity(renderTileEntityEvent.tileEntity, renderTileEntityEvent.x, renderTileEntityEvent.y, renderTileEntityEvent.z);
        }
    }

    private boolean shouldRenderESP(Entity entity) {
        if (entity instanceof EntityAreaEffectCloud || entity instanceof EntityClientPlayerMP || !entity.isEntityAlive() || entity.ticksExisted <= 2) {
            return false;
        }

        if (entity instanceof EntityOtherPlayerMP) {
            return this.players.getValue();
        }

        if (entity instanceof EntityAnimal) {
            return this.animals.getValue();
        }

        if (entity instanceof EntityMonster || entity instanceof EntitySlime) {
            return this.hostiles.getValue();
        }

        if (entity instanceof EntityWyvern || entity instanceof EntityBloodWyvern || entity instanceof EntityFireballWyvern) {
            return this.hostiles.getValue();
        }

        if (entity instanceof EntityItem || entity instanceof EntityItemFireResistant) {
            return this.items.getValue();
        }

        return this.miscEntities.getValue();
    }

    private boolean shouldRenderESP(TileEntity tileEntity) {

        if (tileEntity instanceof TileEntityChest || tileEntity instanceof TileEntityCrate || (tileEntity instanceof TileEntityDummy && ((TileEntityDummy) tileEntity).getBlockID() == Blocks.DIMENSIONAL_CHEST.blockID) || tileEntity instanceof TileEntityDrawer) {
            return this.containers.getValue();
        }

        if ((tileEntity instanceof TileEntityDummy && ((TileEntityDummy) tileEntity).getBlockID() == Blocks.PORTAL.blockID)) {
            return this.portals.getValue();
        }

        return this.miscTileEntities.getValue();
    }

    private <T extends Entity> void renderBoundingBoxFromCoordsForEntity(T entity, double x, double y, double z) {

        AxisAlignedBB entityAxisAlignedBB = entity.boundingBox;

        AxisAlignedBB renderAxisAlignedBB = AxisAlignedBB.getAABBPool().getAABB(entityAxisAlignedBB.minX - entity.posX + x, entityAxisAlignedBB.minY - entity.posY + y, entityAxisAlignedBB.minZ - entity.posZ + z, entityAxisAlignedBB.maxX - entity.posX + x, entityAxisAlignedBB.maxY - entity.posY + y, entityAxisAlignedBB.maxZ - entity.posZ + z);

        int color = getEntityColor(entity);

        renderBoundingBoxFromAABB(renderAxisAlignedBB, color);
    }

    private <T extends TileEntity> void renderBoundingBoxFromCoordsForTileEntity(T tileEntity, double x, double y, double z) {

        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);

        int color = getColorForTileEntity(tileEntity);

        renderBoundingBoxFromAABB(axisAlignedBB, color);
    }

    private void renderBoundingBoxFromAABB(AxisAlignedBB axisAlignedBB, int color) {
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

    private int getEntityColor(Entity entity) {

        if (entity instanceof EntityOtherPlayerMP) {
            return Color.CYAN.getRGB();
        }

        if (entity instanceof EntityAnimal) {
            return Color.GREEN.getRGB();
        }

        if (entity instanceof EntityMonster || entity instanceof EntitySlime) {
            return Color.RED.getRGB();
        }

        if (entity instanceof EntityWyvern || entity instanceof EntityBloodWyvern || entity instanceof EntityFireballWyvern) {
            return Color.RED.getRGB();
        }

        if (entity instanceof EntityItem || entity instanceof EntityItemFireResistant) {
            return Color.YELLOW.getRGB();
        }

        return Color.WHITE.getRGB();
    }

    private int getColorForTileEntity(TileEntity tileEntity) {

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
