package dev.hoosiers.dilation.feature.hacks;

import dev.hoosiers.dilation.DilationCore;
import dev.hoosiers.dilation.backend.events.DrawGuiScreenEvent;
import dev.hoosiers.dilation.imp.Category;
import dev.hoosiers.dilation.imp.Hack;
import dev.hoosiers.dilation.imp.TabGUIEntry;
import dev.hoosiers.dilation.imp.event.EventTarget;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.util.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author Hoosiers
 * @since 03-15-2026
 */

public final class TabGUI extends Hack {

    public TabGUI() {
        super("TabGUI", Category.Client, Keyboard.KEY_NONE, true, false, true);
    }

    public final KeyBinding tabLeft = new KeyBinding("key.tabLeft", Keyboard.KEY_LEFT);
    public final KeyBinding tabRight = new KeyBinding("key.tabRight", Keyboard.KEY_RIGHT);

    private int page = 1;

    private final int minPage = 1;
    private int maxPage = 1;

    private void setPage(int page) {
        if (page >  maxPage) {
            page = minPage;
        } else if (page < minPage) {
            page = maxPage;
        }

        this.page = page;
    }

    @Override
    public void onTick() {
        if (this.getCurrentScreen() != null) {
            return;
        }

        if (this.tabLeft.isPressed()) {
            this.setPage(this.page - 1);
        } else if (this.tabRight.isPressed()) {
            this.setPage(this.page + 1);
        }
    }

    private final String dilationCoreMessage = DilationCore.MOD_NAME + " " + DilationCore.MOD_VERSION +  " >>> By Hoosiers :)";

    private final int minX = 0;
    private final int maxX = this.getFontRenderer().getStringWidth(dilationCoreMessage) + 3;
    private final int minY = 0;
    private final int maxY = 65;

    @SuppressWarnings("unused")
    @EventTarget
    public void onDrawGuiScreenEvent(DrawGuiScreenEvent drawGuiScreenEvent) {

        this.renderTabGui(this.minX, this.maxX, this.minY, this.maxY);

        this.renderCoordinates(this.maxX, this.maxY);
    }

    private final int outlineColor = new Color(255, 255, 0, 240).getRGB();
    private final int fillColor = new Color(50, 50, 50, 220).getRGB();
    private final int textColor = Color.WHITE.getRGB();

    private void renderTabGui(int minX, int maxX, int minY, int maxY) {
        Gui.drawRect(minX + 1, minY, maxX, maxY, this.fillColor);
        Gui.drawRect(minX, minY, maxX + 1, 1, this.outlineColor);
        Gui.drawRect(minX, minY, minX + 1, maxY + 1, this.outlineColor);
        Gui.drawRect(minX, maxY, maxX + 1, maxY + 1, this.outlineColor);
        Gui.drawRect(maxX, minY, maxX + 1, maxY + 1, this.outlineColor);

        this.getGuiIngame().drawString(this.getFontRenderer(), dilationCoreMessage, 2, 2, textColor);

        ArrayList<TabGUIEntry> tabGUIEntries = new ArrayList<>();

        tabGUIEntries.add(new TabGUIEntry("[" + this.getCommandManager().PREFIX + "Commands] List", Color.CYAN));
        tabGUIEntries.add(new TabGUIEntry("Test", Color.WHITE));

        this.getHackManager().HACKS.forEach(hack -> {

            if (!this.shouldIgnoreHack(hack.NAME)) {
                tabGUIEntries.add(new TabGUIEntry(hack.NAME + " [" + hack.getBind().keyCode + "]", hack.isEnabled() ? Color.GREEN : Color.RED));
            }
        });

        double maxPage = tabGUIEntries.size() / 5d;

        this.maxPage = (int) Math.ceil(maxPage);

        String pageString = "(" + this.page + "/" + this.maxPage + ")";

        int pageX = this.getFontRenderer().getStringWidth(this.dilationCoreMessage) - this.getFontRenderer().getStringWidth(pageString);

        this.getGuiIngame().drawString(this.getFontRenderer(), pageString, pageX + 2, maxY - 9, this.textColor);

        for (int i = 0; i <= 4; i++) {

            String dumbassWayOfDoingThis;

            if (i == 0 || i == 2 || i == 4) {
                dumbassWayOfDoingThis = "> ";
            } else {
                dumbassWayOfDoingThis = ">> ";
            }

            int pageI = i + ((page - 1) * 5);

            if (tabGUIEntries.size() - 1 >= pageI) {
                this.getGuiIngame().drawString(this.getFontRenderer(), dumbassWayOfDoingThis + tabGUIEntries.get(pageI).entry, minX +  2,  minY + 4 + (10 * (i + 1)), tabGUIEntries.get(pageI).color.getRGB());
            }
        }
    }

    private boolean shouldIgnoreHack(String name) {
        if (name == "Commands") {
            return true;
        } else if (name == "TabGUI") {
            return true;
        }

        return false;
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
