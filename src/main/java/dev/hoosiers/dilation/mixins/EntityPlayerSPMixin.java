package dev.hoosiers.dilation.mixins;

import dev.hoosiers.dilation.DilationCore;
import dev.hoosiers.dilation.utils.ChatMessages;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Hoosiers
 * @since 03-02-2026
 */

@Mixin(value = EntityPlayerSP.class, priority = 6969)
public final class EntityPlayerSPMixin {

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    public void sendChatMessage(String arg1, CallbackInfo ci) {
        DilationCore dilationCore = DilationCore.getInstance();

        if (dilationCore == null || arg1 == null || dilationCore.failsNullCheck() || Minecraft.getInstance().theWorld.isRemote) {
            return;
        }

        boolean cancelPacket = ChatMessages.handleCommandChatMessage(dilationCore, arg1);

        if (cancelPacket) {
            ci.cancel();
        }
    }
}
