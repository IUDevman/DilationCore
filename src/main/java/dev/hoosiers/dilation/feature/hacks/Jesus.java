package dev.hoosiers.dilation.feature.hacks;

import dev.hoosiers.dilation.backend.events.FluidCollisionEvent;
import dev.hoosiers.dilation.imp.Category;
import dev.hoosiers.dilation.imp.Hack;
import dev.hoosiers.dilation.imp.event.EventTarget;
import net.minecraft.common.block.data.Material;
import net.minecraft.common.block.data.Materials;
import org.lwjgl.input.Keyboard;

/**
 * @author Hoosiers
 * @since 03-14-2026
 */

public final class Jesus extends Hack {

    public Jesus() {
        super("Jesus", Category.Movement, Keyboard.KEY_J, false, true, true);
    }

    @Override
    public void onTick() {
        boolean isInLava = this.getPlayer().isInsideOfMaterial(Materials.LAVA);
        boolean isInAcid = this.getPlayer().isInsideOfMaterial(Materials.ACID);
        boolean isInSanguis = this.getPlayer().isInsideOfMaterial(Materials.SANGUIS);

        if (this.getPlayer().isInWater() || isInLava || isInSanguis || isInAcid) {

            if (this.getCurrentScreen() == null && Keyboard.isKeyDown(this.getMinecraft().gameSettings.keyBindSneak.keyCode)) {
                return;
            }

            this.getPlayer().motionY = (isInLava || isInAcid || isInSanguis) ? 1.2d : 0.30d;
        }
    }

    @SuppressWarnings("unused")
    @EventTarget
    public void onFluidCollisionEvent(FluidCollisionEvent fluidCollisionEvent) {
        if (this.getPlayer().isInWater()
                || this.getPlayer().isInsideOfMaterial(Materials.LAVA)
                || this.getPlayer().isInsideOfMaterial(Materials.ACID)
                ||  this.getPlayer().isInsideOfMaterial(Materials.SANGUIS)) {
            return;
        }

        Material material = this.getWorld().getBlockMaterial(fluidCollisionEvent.x, fluidCollisionEvent.y, fluidCollisionEvent.z);

        if (material == Materials.LAVA || material == Materials.ACID || material == Materials.SANGUIS) {
            fluidCollisionEvent.setCancelled(true);
            return;
        }

        if (this.getCurrentScreen() == null && Keyboard.isKeyDown(this.getMinecraft().gameSettings.keyBindSneak.keyCode)) {
            return;
        }

        if (!(this.getPlayer().fallDistance >= 3)) {
            fluidCollisionEvent.setCancelled(true);
        }
    }
}
