package dev.hoosiers.dilation.feature.hacks;

import dev.hoosiers.dilation.backend.events.DrawGuiScreenEvent;
import dev.hoosiers.dilation.imp.Category;
import dev.hoosiers.dilation.imp.Hack;
import dev.hoosiers.dilation.imp.event.EventTarget;
import org.lwjgl.input.Keyboard;

import java.awt.*;

/**
 * @author Hoosiers
 * @since 03-15-2026
 */

public final class Coordinates extends Hack {

    public Coordinates() {
        super("Coordinates", Category.Client, Keyboard.KEY_O, false, true, true);
    }

    @SuppressWarnings("unused")
    @EventTarget
    public void onDrawGuiScreenEvent(DrawGuiScreenEvent drawGuiScreenEvent) {

        TabGUI tabGUI = this.getHackManager().getHack(TabGUI.class);

        this.renderCoordinates(tabGUI.maxX, tabGUI.maxY);
    }

    private void renderCoordinates(int maxX, int maxY) {
        int posX = (int) this.getPlayer().posX;
        int posY = (int) this.getPlayer().posY - 1; //Why?
        int posZ = (int) this.getPlayer().posZ;

        if (this.getWorld().worldProvider.isHellWorld) {
            posX *= 8;
            posZ *= 8;
        }

        this.getGuiIngame().drawCenteredString(this.getFontRenderer(), getCoordsString(posX, posY, posZ, false), (float) maxX / 2, maxY + 2, Color.WHITE.getRGB());
        this.getGuiIngame().drawCenteredString(this.getFontRenderer(), getCoordsString(posX, posY, posZ, true), (float) maxX / 2, maxY + 12, Color.RED.getRGB());
    }

    private String getCoordsString(int x, int y, int z, boolean nether) {
        if (nether) {
            x /= 8;
            z /= 8;
        }

        return "(" + x + ", " + y + ", " + z + ")";
    }
}
