package dev.hoosiers.dilation.mixins;

import dev.hoosiers.dilation.DilationCore;
import dev.hoosiers.dilation.utils.Globals;
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
public final class EntityPlayerMixin implements Globals {

    @Shadow @Final public InventoryPlayer inventory;

    //prevents sprint slowdown
    @Inject(method = "isExhausted", at = @At("HEAD"), cancellable = true)
    public void isExausted(CallbackInfoReturnable<Boolean> cir) {
        if (this.failsNullCheck()) {
            return;
        }

        DilationCore dilationCore = this.getDilationCore();

        if (dilationCore.shouldNoExhaustion()) {
            cir.setReturnValue(false);
        }
    }

    //FastBreak
    @Inject(method = "getCurrentPlayerStrVsBlock", at = @At("HEAD"), cancellable = true)
    public void getCurrentPlayerStrVsBlock(Block block, CallbackInfoReturnable<Float> cir) {
        if (this.failsNullCheck()) {
            return;
        }

        DilationCore dilationCore = this.getDilationCore();

        if (!dilationCore.shouldFastBreak()) {
            return;
        }

        float strength = this.inventory.getStrVsBlock(block);

        //We don't want hand/incompatible hits entering the multiplier because of haste, so we check for it with this
        float oldStrength = strength;

        if (this.getPlayer().hasEffect(Effects.HASTE)) {
            strength += this.getPlayer().potionEffects.get(Effects.HASTE.effectID).getLevel() + 1 << 1;
        }

        //diamond tools
        if (oldStrength > 1 && (strength >= 8 && strength < 14)) {
            strength = 14;
        }

        //iron tools
        if (oldStrength > 1 && (strength >= 6 && strength < 8)) {
            strength = 10;
        }

        //Stone tools
        if (oldStrength > 1 && (strength >= 4 && strength < 6)) {
            strength = 8;
        }

        //Wooden tools
        if (oldStrength > 1 && strength < 4) {
            strength = 6;
        }

        //Hand/incompatible tool
        if (strength == 1) {
            strength = 1.25F;
        }

        //Hard limit for underwater breaking
        //can break a little faster with hand, but not desired tool
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
