package dev.hoosiers.dilation.utils;

import dev.hoosiers.dilation.DilationCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.player.EntityPlayerSP;
import net.minecraft.common.networking.Packet;
import net.minecraft.common.world.World;

/**
 * @author Hoosiers
 * @since 03-06-2026
 */

public interface Globals {

    default Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }

    default World getWorld() {
        return getMinecraft().theWorld;
    }

    default EntityPlayerSP getPlayer() {
        return this.getMinecraft().thePlayer;
    }

    default GuiScreen getCurrentScreen() {
        return this.getMinecraft().currentScreen;
    }

    default GuiIngame getGuiIngame() {
        return this.getMinecraft().ingameGUI;
    }

    default FontRenderer getFontRenderer() {
        return getMinecraft().fontRenderer;
    }

    default void sendPacket(Packet packet) {
        getMinecraft().getSendQueue().addToSendQueue(packet);
    }

    //checks to see if the player or world is not loaded.
    //very important to not load things if their dependencies are not loaded.
    default boolean failsNullCheck() {

        return getMinecraft() == null || getPlayer() == null || getWorld() == null;
    }

    default DilationCore getDilationCore() {
        return DilationCore.getInstance();
    }
}
