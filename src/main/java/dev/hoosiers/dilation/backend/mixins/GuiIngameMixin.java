package dev.hoosiers.dilation.backend.mixins;

import dev.hoosiers.dilation.backend.events.DrawGuiScreenEvent;
import dev.hoosiers.dilation.imp.LinkedMethods;
import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Hoosiers
 * @since 02-20-2026
 */

@Mixin(value = GuiIngame.class, priority = 6969)
public final class GuiIngameMixin implements LinkedMethods {

    @Inject(method = "renderHUD", at = @At("HEAD"))
    public void renderHUD(int x, int y, float deltaTicks, CallbackInfo ci) {
        if (this.failsNullCheck() || this.getGuiIngame() == null) {
            return;
        }

        DrawGuiScreenEvent drawGuiScreenEvent = new DrawGuiScreenEvent();
        this.getEventHandler().call(drawGuiScreenEvent);
    }
}
