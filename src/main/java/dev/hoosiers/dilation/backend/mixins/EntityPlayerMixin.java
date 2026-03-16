package dev.hoosiers.dilation.backend.mixins;

import dev.hoosiers.dilation.backend.events.BlockStrengthEvent;
import dev.hoosiers.dilation.backend.events.ExhaustionEvent;
import dev.hoosiers.dilation.imp.LinkedMethods;
import net.minecraft.common.block.Block;
import net.minecraft.common.block.data.Materials;
import net.minecraft.common.effect.Effects;
import net.minecraft.common.entity.player.EntityPlayer;
import net.minecraft.common.entity.player.InventoryPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Hoosiers
 * @since 02-20-2026
 */

@Mixin(value = EntityPlayer.class, priority = 6969)
public final class EntityPlayerMixin implements LinkedMethods {

    @Shadow @Final public InventoryPlayer inventory;

    @Inject(method = "isExhausted", at = @At("HEAD"), cancellable = true)
    public void isExausted(CallbackInfoReturnable<Boolean> cir) {
        if (this.failsNullCheck()) {
            return;
        }

        ExhaustionEvent exhaustionEvent = new ExhaustionEvent();
        this.getEventHandler().call(exhaustionEvent);

        if (exhaustionEvent.isCancelled()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "getCurrentPlayerStrVsBlock", at = @At("HEAD"), cancellable = true)
    public void getCurrentPlayerStrVsBlock(Block block, CallbackInfoReturnable<Float> cir) {
        if (this.failsNullCheck()) {
            return;
        }

        BlockStrengthEvent blockStrengthEvent = new BlockStrengthEvent();
        this.getEventHandler().call(blockStrengthEvent);

        if (blockStrengthEvent.isCancelled()) {
            float strength = this.inventory.getStrVsBlock(block);

            float oldStrength = strength;

            if (this.getPlayer().hasEffect(Effects.HASTE)) {
                strength += this.getPlayer().potionEffects.get(Effects.HASTE.effectID).getLevel() + 1 << 1;
            }

            if (oldStrength > 1 && (strength >= 8 && strength < 14)) {
                strength = 14;
            } else  if (oldStrength > 1 && (strength >= 6 && strength < 8)) {
                strength = 10;
            } else if (oldStrength > 1 && (strength >= 4 && strength < 6)) {
                strength = 8;
            } else if (oldStrength > 1 && strength < 4) {
                strength = 6;
            }

            if (strength == 1) {
                strength = 1.25F;
            }

            if (this.getPlayer().isInsideOfMaterial(Materials.WATER) && !this.getPlayer().hasEffect(Effects.BREATHING)) {

                if (strength == 1.25){
                    strength /= 4.5F;
                } else {
                    strength /= 5;
                }
            }

            cir.setReturnValue(strength);
        }
    }
}
