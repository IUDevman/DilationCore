package dev.hoosiers.dilation.backend.mixins;

import dev.hoosiers.dilation.backend.events.MovementToPlayerEvent;
import dev.hoosiers.dilation.backend.events.Packet10FlyingEvent;
import dev.hoosiers.dilation.imp.LinkedMethods;
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
public final class NetClientHandlerMixin implements LinkedMethods {

    @Shadow @Final private Minecraft mc;

    @Inject(method = "addToSendQueue", at = @At("HEAD"), cancellable = true)
    public void addToSendQueue(Packet packet, CallbackInfo ci) {
        if (packet == null || this.failsNullCheck()) {
            return;
        }

        if (packet instanceof Packet3Chat) {
            Packet3Chat packet3Chat = (Packet3Chat) packet;

            if (packet3Chat.message == null) {
                return;
            }

            boolean cancelPacket = this.getCommandManager().dispatchCommands(packet3Chat.message);

            if (cancelPacket) {
                ci.cancel();
                return;
            }
        }

        if (packet instanceof Packet10Flying) {

            Packet10FlyingEvent packet10FlyingEvent = new Packet10FlyingEvent();
            this.getEventHandler().call(packet10FlyingEvent);

            if (packet10FlyingEvent.isCancelled()) {
                Packet10Flying packet10Flying = (Packet10Flying) packet;

                packet10Flying.onGround = true;
            }
        }
    }

    @Inject(method = "handleEntityVelocity", at = @At("HEAD"), cancellable = true)
    public void handleEntityVelocity(Packet28EntityVelocity packet28, CallbackInfo ci) {
        if (this.failsNullCheck()) {
            return;
        }

        if (packet28.entityId == this.getPlayer().entityId) {
            MovementToPlayerEvent movementToPlayerEvent = new MovementToPlayerEvent(MovementToPlayerEvent.Type.Entity);
            this.getEventHandler().call(movementToPlayerEvent);

            if (movementToPlayerEvent.isCancelled()) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "handleExplosion", at = @At("HEAD"), cancellable = true)
    public void handleExplosion(Packet60Explosion packet60, CallbackInfo ci) {
        if (this.failsNullCheck()) {
            return;
        }

        MovementToPlayerEvent movementToPlayerEvent = new MovementToPlayerEvent(MovementToPlayerEvent.Type.Explosion);
        this.getEventHandler().call(movementToPlayerEvent);

        if (movementToPlayerEvent.isCancelled()) {
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
}
