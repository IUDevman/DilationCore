package dev.hoosiers.dilation.mixins;


import dev.hoosiers.dilation.DilationCore;
import dev.hoosiers.dilation.utils.ChatMessages;
import dev.hoosiers.dilation.utils.Globals;
import net.minecraft.client.Minecraft;
import net.minecraft.client.networking.NetClientHandler;
import net.minecraft.common.networking.*;
import net.minecraft.common.world.Explosion;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Hoosiers
 * @since 02-20-2026
 */

@Mixin(value = NetClientHandler.class, priority = 6969)
public final class NetClientHandlerMixin implements Globals {

    @Shadow @Final private Minecraft mc;

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

    //Velocity - from entity damage
    @Inject(method = "handleEntityVelocity", at = @At("HEAD"), cancellable = true)
    public void handleEntityVelocity(Packet28EntityVelocity packet28, CallbackInfo ci) {
        if (this.failsNullCheck()) {
            return;
        }

        DilationCore dilationCore = this.getDilationCore();

        if (!dilationCore.shouldVelocity()) {
            return;
        }

        if (packet28.entityId == this.getPlayer().entityId) {
            ci.cancel();
        }
    }

    //Velocity - pushed by explosion
    @Inject(method = "handleExplosion", at = @At("HEAD"), cancellable = true)
    public void handleExplosion(Packet60Explosion packet60, CallbackInfo ci) {
        if (this.failsNullCheck()) {
            return;
        }

        DilationCore dilationCore = this.getDilationCore();

        if (!dilationCore.shouldVelocity()) {
            return;
        }

        Explosion explosion;
        (explosion = new Explosion(
                this.mc.theWorld,
                null,
                packet60.explosionX,
                packet60.explosionY,
                packet60.explosionZ,
                packet60.explosionSize
        ))
                .destroyedBlockPositions = packet60.destroyedBlockPositions;
        explosion.isFlaming = packet60.isFlaming;
        explosion.doExplosionB(packet60.spawnParticles, packet60.isDynamite);

        ci.cancel();
    }
}
