package dev.hoosiers.dilation.mixins;

import dev.hoosiers.dilation.DilationCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

/**
 * @author Hoosiers
 * @since 02-20-2026
 */

@Mixin(value = GuiIngame.class, priority = 6969)
public final class GuiIngameMixin {

    //render DilationCore HUD and module list
    @Inject(method = "renderHUD", at = @At("HEAD"))
    public void renderHUD(int x, int y, float deltaTicks, CallbackInfo ci) {

        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft == null) {
            return;
        }

        GuiIngame guiIngame = minecraft.ingameGUI;

        if (guiIngame == null) {
            return;
        }

        DilationCore dilationCore = DilationCore.getInstance();

        if (dilationCore == null) {
            return;
        }

        String dilationCoreMessage = "DilationCore " + dilationCore.getVersion() +  " >>> By Hoosiers :)";

        int minX = 0;
        int maxX = minecraft.fontRenderer.getStringWidth(dilationCoreMessage) + 3;
        int minY = 0;
        int maxY = 65;

        int outlineColor = new Color(255, 255, 0, 240).getRGB();
        int fillColor = new Color(50, 50, 50, 220).getRGB();
        int textColor = Color.WHITE.getRGB();

        Gui.drawRect(minX + 1, minY, maxX, maxY, fillColor);
        Gui.drawRect(minX, minY, maxX + 1, 1, outlineColor);
        Gui.drawRect(minX, minY, minX + 1, maxY + 1, outlineColor);
        Gui.drawRect(minX, maxY, maxX + 1, maxY + 1, outlineColor);
        Gui.drawRect(maxX, minY, maxX + 1, maxY + 1, outlineColor);

        guiIngame.drawString(minecraft.fontRenderer, dilationCoreMessage, 2, 2, textColor);

        String pageString = "";

        if (dilationCore.getGuiPage() == 1) {
            guiIngame.drawString(minecraft.fontRenderer, "> ESP (-e) [M]", minX +  2, minY + 14, dilationCore.shouldESP() ? Color.GREEN.getRGB() : Color.RED.getRGB());
            guiIngame.drawString(minecraft.fontRenderer, ">> FastBreak (-fab) [H]", minX + 2, minY + 24, dilationCore.shouldFastBreak() ? Color.GREEN.getRGB() : Color.RED.getRGB());
            guiIngame.drawString(minecraft.fontRenderer, "> Fly (-f) [G]", minX +  2, minY + 34, dilationCore.shouldFly() ? Color.GREEN.getRGB() : Color.RED.getRGB());
            guiIngame.drawString(minecraft.fontRenderer, ">> Fullbright (-fb) [B]", minX +  2, minY + 44, dilationCore.shouldFullbright() ? Color.GREEN.getRGB() : Color.RED.getRGB());
            guiIngame.drawString(minecraft.fontRenderer, "> Jesus (-j) [J]", minX +  2, minY + 54, dilationCore.shouldJesus() ? Color.GREEN.getRGB() : Color.RED.getRGB());

            pageString = "(1/4)";
        }

        if (dilationCore.getGuiPage() == 2) {
            guiIngame.drawString(minecraft.fontRenderer, "> KillAura (-ka) [R]", minX + 2, minY + 14, dilationCore.shouldKillAura() ? Color.GREEN.getRGB() : Color.RED.getRGB());
            guiIngame.drawString(minecraft.fontRenderer, ">> NoExhaustion (-ne) [K]", minX + 2, minY + 24, dilationCore.shouldNoExhaustion() ? Color.GREEN.getRGB() : Color.RED.getRGB());
            guiIngame.drawString(minecraft.fontRenderer, "> NoFall (-nf) [L]", minX + 2, minY + 34, dilationCore.shouldNoFall() ? Color.GREEN.getRGB() : Color.RED.getRGB());
            guiIngame.drawString(minecraft.fontRenderer, ">> NoWeather (-nw) [N]", minX + 2, minY + 44, dilationCore.shouldNoWeather() ? Color.GREEN.getRGB() : Color.RED.getRGB());
            guiIngame.drawString(minecraft.fontRenderer, "> Tracers (-t) [COMMA]", minX + 2, minY + 54, dilationCore.shouldTracers() ? Color.GREEN.getRGB() : Color.RED.getRGB());

            pageString = "(2/4)";
        }

        if (dilationCore.getGuiPage() == 3) {
            guiIngame.drawString(minecraft.fontRenderer, "> Xray (-x) [X]", minX + 2, minY + 14, dilationCore.shouldXray() ? Color.GREEN.getRGB() : Color.RED.getRGB());
            guiIngame.drawString(minecraft.fontRenderer, "-flight [speed] (3-20)", minX + 2, minY + 24, Color.CYAN.getRGB());
            guiIngame.drawString(minecraft.fontRenderer, "-killaura [range] (0-10)", minX + 2, minY + 34, Color.CYAN.getRGB());
            guiIngame.drawString(minecraft.fontRenderer, "-killaura [p/h/a] (Pas/Hos/Ani)", minX + 2, minY + 44, Color.CYAN.getRGB());
            guiIngame.drawString(minecraft.fontRenderer, "-xdo (Xray Diamonds Only)", minX + 2, minY + 54, Color.CYAN.getRGB());

            pageString = "(3/4)";
        }

        if (dilationCore.getGuiPage() == 4) {
            guiIngame.drawString(minecraft.fontRenderer, "-t portals (Tracer Portals)", minX + 2, minY + 14, Color.CYAN.getRGB());
            guiIngame.drawString(minecraft.fontRenderer, "-tm (Toggle Module MSGs)", minX + 2, minY + 24, Color.CYAN.getRGB());

            pageString = "(4/4)";
        }

        int pageX = minecraft.fontRenderer.getStringWidth(dilationCoreMessage) - minecraft.fontRenderer.getStringWidth(pageString);
        guiIngame.drawString(minecraft.fontRenderer, pageString, pageX + 2, maxY - 9, textColor);
    }
}
