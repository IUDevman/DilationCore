package dev.hoosiers.dilation.mixins;

import dev.hoosiers.dilation.DilationCore;
import dev.hoosiers.dilation.utils.Globals;
import net.minecraft.common.block.children.BlockFluid;
import net.minecraft.common.util.math.AxisAlignedBB;
import net.minecraft.common.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Hoosiers
 * @since 02-20-2025
 */

@Mixin(value = BlockFluid.class, priority = 6969)
public final class BlockFluidMixin implements Globals {

    //changes water collision box to full block
    @Inject(method = "getCollisionBoundingBoxFromPool", at = @At("HEAD"), cancellable = true)
    public void getCollisionBoundingBoxFromPool(World world, int x, int y, int z, CallbackInfoReturnable<AxisAlignedBB> cir) {
        if (this.failsNullCheck()) {
            return;
        }

        DilationCore dilationCore = this.getDilationCore();

        if (dilationCore.shouldJesus() && dilationCore.shouldJesus(x, y, z)) {
            cir.setReturnValue(AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1));
        }
    }
}
