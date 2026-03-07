package dev.hoosiers.dilation.mixins;

import dev.hoosiers.dilation.DilationCore;
import dev.hoosiers.dilation.utils.Globals;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

/**
 * @author Hoosiers
 * @since 02-20-2026
 */

@Mixin(value = GuiIngame.class, priority = 6969)
public final class GuiIngameMixin implements Globals {

    //render DilationCore HUD and module list
    @Inject(method = "renderHUD", at = @At("HEAD"))
    public void renderHUD(int x, int y, float deltaTicks, CallbackInfo ci) {

        if (this.failsNullCheck() || this.getGuiIngame() == null) {
            return;
        }

        DilationCore dilationCore = this.getDilationCore();

        String dilationCoreMessage = "DilationCore " + dilationCore.getVersion() +  " >>> By Hoosiers :)";

        int minX = 0;
        int maxX = this.getFontRenderer().getStringWidth(dilationCoreMessage) + 3;
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

        this.getGuiIngame().drawString(this.getFontRenderer(), dilationCoreMessage, 2, 2, textColor);

        String pageString = "";

        if (dilationCore.getGuiPage() == 1) {
            this.getGuiIngame().drawString(this.getFontRenderer(), "> ESP (-e) [M]", minX +  2, minY + 14, dilationCore.shouldESP() ? Color.GREEN.getRGB() : Color.RED.getRGB());
            this.getGuiIngame().drawString(this.getFontRenderer(), ">> FastBreak (-fab) [H]", minX + 2, minY + 24, dilationCore.shouldFastBreak() ? Color.GREEN.getRGB() : Color.RED.getRGB());
            this.getGuiIngame().drawString(this.getFontRenderer(), "> Fly (-f) [G]", minX +  2, minY + 34, dilationCore.shouldFly() ? Color.GREEN.getRGB() : Color.RED.getRGB());
            this.getGuiIngame().drawString(this.getFontRenderer(), ">> Fullbright (-fb) [B]", minX +  2, minY + 44, dilationCore.shouldFullbright() ? Color.GREEN.getRGB() : Color.RED.getRGB());
            this.getGuiIngame().drawString(this.getFontRenderer(), "> Jesus (-j) [J]", minX +  2, minY + 54, dilationCore.shouldJesus() ? Color.GREEN.getRGB() : Color.RED.getRGB());

            pageString = "(1/4)";
        }

        if (dilationCore.getGuiPage() == 2) {
            this.getGuiIngame().drawString(this.getFontRenderer(), "> KillAura (-ka) [R]", minX + 2, minY + 14, dilationCore.shouldKillAura() ? Color.GREEN.getRGB() : Color.RED.getRGB());
            this.getGuiIngame().drawString(this.getFontRenderer(), ">> NoExhaustion (-ne) [K]", minX + 2, minY + 24, dilationCore.shouldNoExhaustion() ? Color.GREEN.getRGB() : Color.RED.getRGB());
            this.getGuiIngame().drawString(this.getFontRenderer(), "> NoFall (-nf) [L]", minX + 2, minY + 34, dilationCore.shouldNoFall() ? Color.GREEN.getRGB() : Color.RED.getRGB());
            this.getGuiIngame().drawString(this.getFontRenderer(), ">> NoWeather (-nw) [N]", minX + 2, minY + 44, dilationCore.shouldNoWeather() ? Color.GREEN.getRGB() : Color.RED.getRGB());
            this.getGuiIngame().drawString(this.getFontRenderer(), "> Tracers (-t) [COMMA]", minX + 2, minY + 54, dilationCore.shouldTracers() ? Color.GREEN.getRGB() : Color.RED.getRGB());

            pageString = "(2/4)";
        }

        if (dilationCore.getGuiPage() == 3) {
            this.getGuiIngame().drawString(this.getFontRenderer(), "> TorchNuker (-tn) [U]", minX + 2, minY + 14, dilationCore.shouldTorchNuker() ? Color.GREEN.getRGB() : Color.RED.getRGB());
            this.getGuiIngame().drawString(this.getFontRenderer(), ">> Xray (-x) [X]", minX + 2, minY + 24, dilationCore.shouldXray() ? Color.GREEN.getRGB() : Color.RED.getRGB());
            this.getGuiIngame().drawString(this.getFontRenderer(), "-tm (Toggle Module MSGs)", minX + 2, minY + 34, Color.CYAN.getRGB());
            this.getGuiIngame().drawString(this.getFontRenderer(), "-f [speed] (3-20)", minX + 2, minY + 44, Color.CYAN.getRGB());
            this.getGuiIngame().drawString(this.getFontRenderer(), "-ka [range] (0-10)", minX + 2, minY + 54, Color.CYAN.getRGB());

            pageString = "(3/4)";
        }

        if (dilationCore.getGuiPage() == 4) {
            this.getGuiIngame().drawString(this.getFontRenderer(), "-ka [p/h/a] (Pas/Hos/Ani)", minX + 2, minY + 14, Color.CYAN.getRGB());
            this.getGuiIngame().drawString(this.getFontRenderer(), "-t portals (Tracer Portals)", minX + 2, minY + 24, Color.CYAN.getRGB());
            this.getGuiIngame().drawString(this.getFontRenderer(), "-tn [range] (1-10)", minX + 2, minY + 34, Color.CYAN.getRGB());
            this.getGuiIngame().drawString(this.getFontRenderer(), "-xdo (Xray Diamonds Only)", minX + 2, minY + 44, Color.CYAN.getRGB());

            pageString = "(4/4)";
        }

        int pageX =this.getFontRenderer().getStringWidth(dilationCoreMessage) - this.getFontRenderer().getStringWidth(pageString);
        this.getGuiIngame().drawString(this.getFontRenderer(), pageString, pageX + 2, maxY - 9, textColor);

        //Coordinates
        int posX = (int) this.getPlayer().posX;
        int posY = (int) this.getPlayer().posY - 1; //Why?
        int posZ = (int) this.getPlayer().posZ;

        if (this.getWorld().worldProvider.isHellWorld) {
            posX *= 8;
            posZ *= 8;
        }

        this.getGuiIngame().drawCenteredString(this.getFontRenderer(), dilationCore$getCoordsString(posX, posY, posZ, false), (float) maxX / 2, maxY + 2, Color.WHITE.getRGB());
        this.getGuiIngame().drawCenteredString(this.getFontRenderer(), dilationCore$getCoordsString(posX, posY, posZ, true), (float) maxX / 2, maxY + 12, Color.RED.getRGB());
    }

    //Returns string for player coordinates
    @Unique
    private String dilationCore$getCoordsString(int x, int y, int z, boolean nether) {
        if (nether) {
            x /= 8;
            z /= 8;
        }

        return "(" + x + ", " + y + ", " + z + ")";
    }
}
