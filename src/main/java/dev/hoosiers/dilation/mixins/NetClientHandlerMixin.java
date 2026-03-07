package dev.hoosiers.dilation.mixins;


import dev.hoosiers.dilation.DilationCore;
import dev.hoosiers.dilation.utils.ChatMessages;
import dev.hoosiers.dilation.utils.Globals;
import net.minecraft.client.networking.NetClientHandler;
import net.minecraft.common.networking.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Hoosiers
 * @since 02-20-2026
 */

@Mixin(value = NetClientHandler.class, priority = 6969)
public final class NetClientHandlerMixin implements Globals {

    //use this inject to modify and examine sent packets
    @Inject(method = "addToSendQueue", at = @At("HEAD"), cancellable = true)
    public void addToSendQueue(Packet packet, CallbackInfo ci) {
        if (packet == null || this.failsNullCheck()) {
            return;
        }

        DilationCore dilationCore = this.getDilationCore();

        if (dilationCore.shouldNoFall() && packet instanceof Packet10Flying) {
            Packet10Flying packet10Flying = (Packet10Flying) packet;

            packet10Flying.onGround = true;
        }

        if (packet instanceof Packet3Chat) {
            Packet3Chat packet3Chat = (Packet3Chat) packet;

            if (packet3Chat.message == null) {
                return;
            }

            boolean cancelPacket = ChatMessages.handleCommandChatMessage(dilationCore, packet3Chat.message.toLowerCase());

            if (cancelPacket) {
                ci.cancel();
            }
        }
    }
}
