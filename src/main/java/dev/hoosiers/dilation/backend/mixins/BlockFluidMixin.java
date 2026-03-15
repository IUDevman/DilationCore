package dev.hoosiers.dilation.backend.mixins;

import dev.hoosiers.dilation.backend.events.FluidCollisionEvent;
import dev.hoosiers.dilation.imp.LinkedMethods;
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
public final class BlockFluidMixin implements LinkedMethods {

    @Inject(method = "getCollisionBoundingBoxFromPool", at = @At("HEAD"), cancellable = true)
    public void getCollisionBoundingBoxFromPool(World world, int x, int y, int z, CallbackInfoReturnable<AxisAlignedBB> cir) {
        if (this.failsNullCheck()) {
            return;
        }

        FluidCollisionEvent fluidCollisionEvent = new FluidCollisionEvent(x, y, z);
        this.getEventHandler().call(fluidCollisionEvent);

        if (fluidCollisionEvent.isCancelled()) {
            cir.setReturnValue(AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1));
        }
    }
}
