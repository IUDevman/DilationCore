package dev.hoosiers.dilation.feature.hacks;

import dev.hoosiers.dilation.backend.events.RenderEntityEvent;
import dev.hoosiers.dilation.backend.events.RenderTileEntityEvent;
import dev.hoosiers.dilation.imp.Category;
import dev.hoosiers.dilation.imp.Hack;
import dev.hoosiers.dilation.imp.TileEntityDummy;
import dev.hoosiers.dilation.imp.event.EventTarget;
import dev.hoosiers.dilation.imp.settings.BooleanSetting;
import net.minecraft.client.player.EntityOtherPlayerMP;
import net.minecraft.common.block.Blocks;
import net.minecraft.common.block.tileentity.TileEntity;
import net.minecraft.common.entity.Entity;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author Hoosiers
 * @since 03-14-2026
 */

public final class Tracers extends Hack {

    public final BooleanSetting portals = new BooleanSetting("Portals", false);

    public Tracers() {
        super("Tracers", Category.Render, Keyboard.KEY_COMMA, false, true, true);
    }

    @SuppressWarnings("unused")
    @EventTarget
    public void onRenderEntityEvent(RenderEntityEvent renderEntityEvent) {

        if (this.shouldRenderTracers(renderEntityEvent.entity)) {
            this.drawTracerLine(renderEntityEvent.x, renderEntityEvent.y, renderEntityEvent.z, Color.CYAN, 1F);
        }
    }

    @SuppressWarnings("unused")
    @EventTarget
    public void onRenderTileEntityEvent(RenderTileEntityEvent renderTileEntityEvent) {

        TileEntity tileEntity = renderTileEntityEvent.tileEntity;

        if (portals.getValue() && tileEntity instanceof TileEntityDummy && ((TileEntityDummy) tileEntity).getBlockID() == Blocks.PORTAL.blockID) {
            this.drawTracerLine(renderTileEntityEvent.x, renderTileEntityEvent.y, renderTileEntityEvent.z, new Color(128, 0, 128, 255), 1F);
        }
    }

    private boolean shouldRenderTracers(Entity entity) {
        if (!entity.isEntityAlive()) {
            return false;
        }

        if (entity.ticksExisted <= 2) {
            return false;
        }

        return entity instanceof EntityOtherPlayerMP;
    }

    //Shamelessly skidded from Osiris:
    //https://github.com/qe7/Osiris/blob/main/src/main/java/io/github/qe7/utils/render/OpenGLRenderUtil.java
    private void drawTracerLine(double x, double y, double z, Color color, float lineWidth) {
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
