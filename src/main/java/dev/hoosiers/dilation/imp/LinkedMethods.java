package dev.hoosiers.dilation.imp;

import dev.hoosiers.dilation.DilationCore;
import dev.hoosiers.dilation.feature.managers.CommandManager;
import dev.hoosiers.dilation.feature.managers.ConfigManager;
import dev.hoosiers.dilation.feature.managers.HackManager;
import dev.hoosiers.dilation.feature.managers.SettingManager;
import dev.hoosiers.dilation.imp.event.EventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.player.EntityPlayerSP;
import net.minecraft.common.networking.Packet;
import net.minecraft.common.world.World;

/**
 * @author Hoosiers
 * @since 03-10-2026
 */

public interface LinkedMethods {

    default Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }

    default World getWorld() {
        return this.getMinecraft().theWorld;
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
        return this.getMinecraft().fontRenderer;
    }

    default boolean failsNullCheck() {
        return this.getMinecraft() == null || this.getPlayer() == null || this.getWorld() == null;
    }

    default void resetWorldRenders() {

        if (this.failsNullCheck()) {
            return;
        }

        this.getMinecraft().renderGlobal.updateRenderers(this.getPlayer(), Minecraft.getSystemTime());
        this.getMinecraft().renderGlobal.loadRenderers();
    }

    default String messagePrefix() {
        return "[§bDilation§9Core§f] ";
    }

    default void sendClientMessageWithPrefix(String message) {

        String prefixMessage = this.messagePrefix() + message;

        this.getPlayer().addChatMessage(prefixMessage);
    }

    default void sendPacket(Packet packet) {
        this.getMinecraft().getSendQueue().addToSendQueue(packet);
    }

    default DilationCore getDilationCore() {
        return DilationCore.getInstance();
    }

    default EventHandler getEventHandler() {
        return this.getDilationCore().EVENT_HANDLER;
    }

    default CommandManager getCommandManager()  {
        return this.getDilationCore().COMMAND_MANAGER;
    }

    default HackManager getHackManager() {
        return this.getDilationCore().HACK_MANAGER;
    }

    default SettingManager getSettingManager() {
        return this.getDilationCore().SETTING_MANAGER;
    }

    default ConfigManager getConfigManager() {
        return this.getDilationCore().CONFIG_MANAGER;
    }
}
