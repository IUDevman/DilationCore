package dev.hoosiers.dilation.feature.hacks;

import dev.hoosiers.dilation.imp.Category;
import dev.hoosiers.dilation.imp.Hack;
import dev.hoosiers.dilation.imp.settings.NumberSetting;
import net.minecraft.common.block.Blocks;
import net.minecraft.common.networking.Packet14BlockDig;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector4f;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author Hoosiers
 * @since 03-14-2026
 */

public final class TorchNuker extends Hack {

    public final NumberSetting range = new NumberSetting("Range", 5, 1, 10, 0);
    public final NumberSetting delay = new NumberSetting("Delay", 3, 0, 20, 0);

    public TorchNuker() {
        super("TorchNuker", Category.Render, Keyboard.KEY_U, false, true, true);
    }

    private int torchNukerDelay = 0;

    @Override
    public void onDisable() {
        this.torchNukerDelay = 0;
    }

    @Override
    public void onTick() {

        if (this.delay.getValue() != 0 && this.torchNukerDelay < this.delay.getValue()) {
            torchNukerDelay++;
            return;
        }

        this.torchNukerDelay = 0;

        int eX = (int) this.getPlayer().posX;
        int eY = (int) this.getPlayer().posY;
        int eZ = (int) this.getPlayer().posZ;

        double torchNukerRangeSquared = Math.pow(this.range.getValue(), 2);

        ArrayList<Vector4f> torchCoordinates = new ArrayList<>();

        ArrayList<Integer> torchTypes = this.getTorchTypes();

        for (int x = (int) -this.range.getValue(); x <= this.range.getValue(); x++) {
            for (int y = (int) -this.range.getValue(); y <= this.range.getValue(); y++) {
                for (int z = (int) -this.range.getValue(); z<= this.range.getValue(); z++) {

                    double distanceSquared = Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2);

                    if (torchNukerRangeSquared < distanceSquared) {
                        continue;
                    }

                    int newX = eX + x;
                    int newY = eY + y;
                    int newZ = eZ + z;

                    int blockID = this.getWorld().getBlockId(newX, newY, newZ);

                    if (torchTypes.contains(blockID)) {

                        Vector4f vector4f = new Vector4f(newX, newY, newZ, (float) distanceSquared);

                        torchCoordinates.add(vector4f);
                    }
                }
            }
        }

        if (!torchCoordinates.isEmpty()) {

            Vector4f vector4fX = torchCoordinates.stream().min(Comparator.comparing(vector4f -> vector4f.w)).orElse(null);

            if (!this.getWorld().isRemote) {
                return;
            }

            this.sendPacket(new Packet14BlockDig(0, (int) vector4fX.x, (int) vector4fX.y, (int) vector4fX.z, 0));
            this.sendPacket(new Packet14BlockDig(2, (int) vector4fX.x, (int) vector4fX.y, (int) vector4fX.z, 0));
        }
    }

    private ArrayList<Integer> getTorchTypes() {
        ArrayList<Integer> torchTypes = new ArrayList<>();

        torchTypes.add(Blocks.TORCH.blockID);
        torchTypes.add(Blocks.JET_TORCH.blockID);
        torchTypes.add(Blocks.STICKY_TORCH.blockID);
        torchTypes.add(Blocks.QUARTZ_TORCH.blockID);
        torchTypes.add(Blocks.MYTHRIL_TORCH.blockID);
        torchTypes.add(Blocks.CITRINE_TORCH.blockID);
        torchTypes.add(Blocks.REDSTONE_TORCH_ACTIVE.blockID);
        torchTypes.add(Blocks.REDSTONE_TORCH_IDLE.blockID);

        return torchTypes;
    }
}
