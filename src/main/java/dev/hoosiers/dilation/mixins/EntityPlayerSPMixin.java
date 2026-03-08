package dev.hoosiers.dilation.mixins;

import dev.hoosiers.dilation.DilationCore;
import dev.hoosiers.dilation.utils.ChatMessages;
import dev.hoosiers.dilation.utils.Globals;
import net.minecraft.client.player.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Hoosiers
 * @since 03-02-2026
 */

@Mixin(value = EntityPlayerSP.class, priority = 6969)
public final class EntityPlayerSPMixin implements Globals {

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    public void sendChatMessage(String arg1, CallbackInfo ci) {
        if (this.failsNullCheck() || arg1 == null || this.getWorld().isRemote) {
            return;
        }

        DilationCore dilationCore = this.getDilationCore();

        boolean cancelPacket = ChatMessages.handleCommandChatMessage(dilationCore, arg1);

        if (cancelPacket) {
            ci.cancel();
        }
    }

    //Velocity - pushed by blocks
    @Inject(method = "pushOutOfBlocks", at = @At("HEAD"), cancellable = true)
    public void pushOutofBlocks(double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
        if (this.failsNullCheck()) {
            return;
        }

        DilationCore dilationCore = this.getDilationCore();

        if (!dilationCore.shouldVelocity()) {
            return;
        }

        cir.setReturnValue(false);
    }
}
