package dev.hoosiers.dilation.backend.mixins;

import dev.hoosiers.dilation.backend.events.GuiChatEvent;
import dev.hoosiers.dilation.imp.LinkedMethods;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Hoosiers
 * @since 03-15-2026
 */

@Mixin(value = GuiChat.class, priority = 6969)
public final class GuiChatMixin extends GuiScreen implements LinkedMethods {

    @Shadow @Final public GuiTextField chat;

    @Inject(method = "drawScreen", at = @At("HEAD"))
    public void drawScreen(float mouseX, float mouseY, float deltaTicks, CallbackInfo ci) {
        if (this.failsNullCheck()) {
            return;
        }

        boolean isCommandChat =  this.chat.text.startsWith(this.getCommandManager().PREFIX);

        if (isCommandChat) {
            GuiChatEvent guiChatEvent = new GuiChatEvent(this.chat.text, 2, this.width - 2, this.height - 14, this.height - 2);
            this.getEventHandler().call(guiChatEvent);
        }
    }
}
