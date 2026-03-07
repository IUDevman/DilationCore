package dev.hoosiers.dilation.mixins;

import dev.hoosiers.dilation.DilationCore;
import dev.hoosiers.dilation.utils.ChatMessages;
import dev.hoosiers.dilation.utils.Globals;
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
public final class EntityPlayerSPMixin implements Globals {

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    public void sendChatMessage(String arg1, CallbackInfo ci) {
        DilationCore dilationCore = this.getDilationCore();

        if (dilationCore.failsNullCheck() || arg1 == null || this.getWorld().isRemote) {
            return;
        }

        boolean cancelPacket = ChatMessages.handleCommandChatMessage(dilationCore, arg1);

        if (cancelPacket) {
            ci.cancel();
        }
    }
}
