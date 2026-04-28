package dev.hoosiers.dilation.backend.mixins;

import dev.hoosiers.dilation.backend.events.MovementToPlayerEvent;
import dev.hoosiers.dilation.backend.events.SignEvent;
import dev.hoosiers.dilation.feature.managers.CommandManager;
import dev.hoosiers.dilation.imp.LinkedMethods;
import net.minecraft.client.player.EntityPlayerSP;
import net.minecraft.common.block.tileentity.TileEntitySign;
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
public final class EntityPlayerSPMixin implements LinkedMethods {

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    public void sendChatMessage(String arg1, CallbackInfo ci) {
        if (this.failsNullCheck() || arg1 == null || this.getWorld().isRemote) {
            return;
        }

        CommandManager commandManager = this.getCommandManager();

        boolean cancelPacket = commandManager.dispatchCommands(arg1);

        if (cancelPacket) {
            ci.cancel();
        }
    }

    @Inject(method = "pushOutOfBlocks", at = @At("HEAD"), cancellable = true)
    public void pushOutofBlocks(double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
        if (this.failsNullCheck()) {
            return;
        }

        MovementToPlayerEvent movementToPlayerEvent = new MovementToPlayerEvent(MovementToPlayerEvent.Type.Block);
        this.getEventHandler().call(movementToPlayerEvent);

        if (movementToPlayerEvent.isCancelled()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "displayGUIEditSign", at = @At("HEAD"), cancellable = true)
    public void displayGUIEditSign(TileEntitySign sign, CallbackInfo ci) {
        if (this.failsNullCheck()) {
            return;
        }

        if (sign == null) {
            return;
        }

        SignEvent signEvent = new SignEvent(sign);
        this.getEventHandler().call(signEvent);

        if (signEvent.isCancelled()) {
            ci.cancel();
        }
    }
}
