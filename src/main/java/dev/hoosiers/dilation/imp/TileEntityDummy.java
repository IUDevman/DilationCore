package dev.hoosiers.dilation.imp;

import net.minecraft.common.block.tileentity.TileEntity;

/**
 * @author Hoosiers
 * @since 02-27-2026
 */

public final class TileEntityDummy extends TileEntity {

    private final int blockID;

    public TileEntityDummy(int blockID) {
        this.blockID = blockID;
    }

    public int getBlockID() {
        return this.blockID;
    }

    //....... ▄▄ ▄▄
    //......▄▌▒▒▀▒▒▐▄
    //.... ▐▒▒▒▒▒▒▒▒▒▌
    //... ▐▒▒▒▒▒▒▒▒▒▒▒▌
    //....▐▒▒▒▒▒▒▒▒▒▒▒▌
    //....▐▀▄▄▄▄▄▄▄▄▄▀▌
    //....▐░░░░░░░░░░░▌
    //....▐░░░░░░░░░░░▌
    //....▐░░░░░░░░░░░▌
    //....▐░░░░░░░░░░░▌
    //....▐░░░░░░░░░░░▌
    //....▐░░░░░░░░░░░▌
    //....▐░░░░░░░░░░░▌
    //....▐░░░░░░░░░░░▌
    //....▐░░░░░░░░░░░▌
    //....▐░░░░░░░░░░░▌
    //....▐░░░░░░░░░░░▌
    //...▄█▓░░░░░░░░░▓█▄
    //..▄▀░░░░░░░░░░░░░ ▀▄
    //.▐░░░░░░░▀▄▒▄▀░░░░░░▌
    //▐░░░░░░░▒▒▐▒▒░░░░░░░▌
    //▐▒░░░░░▒▒▒▐▒▒▒░░░░░▒▌
    //.▀▄▒▒▒▒▒▄▀▒▀▄▒▒▒▒▒▄▀
    //.... ▀▀▀▀▀ ▀▀▀▀▀
}
