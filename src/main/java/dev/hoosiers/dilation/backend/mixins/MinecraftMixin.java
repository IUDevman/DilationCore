package dev.hoosiers.dilation.backend.mixins;

import dev.hoosiers.dilation.backend.events.ClientTickEvent;
import dev.hoosiers.dilation.imp.LinkedMethods;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Hoosiers
 * @since 02-20-2026
 */

@Mixin(value = Minecraft.class, priority = 6969)
public final class MinecraftMixin implements LinkedMethods {

    //runs main tick method in DilationCore
    @Inject(method = "runTick", at = @At("TAIL"))
    public void runTick(CallbackInfo ci) {
        if (this.failsNullCheck()) {
            return;
        }

        ClientTickEvent clientTickEvent = new ClientTickEvent();
        this.getEventHandler().call(clientTickEvent);
    }

    @Inject(method = "shutdown", at = @At("HEAD"))
    public void shutdown(CallbackInfo ci) {
        this.getConfigManager().save();
    }
}
