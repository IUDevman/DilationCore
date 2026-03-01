package dev.hoosiers.dilation.mixins;

import com.indigo3d.util.DisplayLists;
import dev.hoosiers.dilation.DilationCore;
import dev.hoosiers.dilation.misc.TileEntityDummy;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.world.RenderBlocks;
import net.minecraft.client.renderer.world.Tessellator;
import net.minecraft.client.renderer.world.TessellatorVertexState;
import net.minecraft.client.renderer.world.WorldRenderer;
import net.minecraft.common.block.Block;
import net.minecraft.common.block.Blocks;
import net.minecraft.common.block.tileentity.TileEntity;
import net.minecraft.common.entity.EntityLiving;
import net.minecraft.common.util.math.Vec3D;
import net.minecraft.common.world.World;
import net.minecraft.common.world.chunk.Chunk;
import net.minecraft.common.world.chunk.ChunkCache;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.List;

/**
 * @author Hoosiers
 * @since 02-27-2026
 */

@Mixin(value = WorldRenderer.class, priority = 6969)
public abstract class WorldRendererMixin {

    @Shadow public boolean needsUpdate;

    @Shadow public static int chunksUpdated;

    @Shadow private boolean canRender;

    @Shadow private boolean needsBoxUpdate;

    @Shadow @Final private DisplayLists displayLists;

    @Shadow @Final private int displayListsIndex;

    @Shadow public int posXClip;

    @Shadow public int posYClip;

    @Shadow public int posZClip;

    @Shadow public int sizeWidth;

    @Shadow public int sizeHeight;

    @Shadow public int sizeDepth;

    @Shadow public boolean isVisible;

    @Shadow public boolean isVisibleFromPosition;

    @Shadow public int posX;

    @Shadow public int posY;

    @Shadow public int posZ;

    @Shadow private static ChunkCache chunkCache;

    @Shadow public World worldObj;

    @Shadow public abstract void clearRender();

    @Shadow @Final public boolean[] skipRenderPass;

    @Shadow @Final public List<TileEntity> tileEntityRenderers;

    @Shadow private static RenderBlocks rblocks;

    @Shadow private TessellatorVertexState vertexState;

    @Shadow protected abstract void preRenderBlocks(int pass);

    @Shadow @Final private static Tessellator tessellator;

    @Shadow protected abstract void postRenderBlocks(int pass, EntityLiving player);

    @Shadow private double rendererMinX;

    @Shadow private double rendererMaxX;

    @Shadow private double rendererMinY;

    @Shadow private double rendererMaxY;

    @Shadow private double rendererMinZ;

    @Shadow private double rendererMaxZ;

    @Shadow @Final private List<TileEntity> tileEntities;

    @Shadow public boolean isChunkSkyLit;

    @Shadow private boolean isInitialized;

    //OK So here is another CERTIFIED JANK implementation.
    //ReIndev for some reason has Chests not render as tile entities even though they are.
    //So, the TileEntityRenderer can't pick them up for ESP.
    //We add them back (no special render)...
    //Also, some things that should be tile entities are not (crafting table(s), dimensional chest).
    //So, we set these as well.
    @Inject(method = "updateRenderer", at = @At("HEAD"), cancellable = true)
    public void updateRenderer(EntityLiving player, CallbackInfo ci) {
        DilationCore dilationCore = DilationCore.getInstance();

        if (dilationCore == null || !dilationCore.shouldESP() || dilationCore.failsNullCheck()) {
            return;
        }

        if (this.needsUpdate) {
            chunksUpdated++;
            this.canRender = false;
            Vec3D.recycleCache();
            if (this.needsBoxUpdate) {
                float float2 = 0.0F;
                this.displayLists.newDisplayList(this.displayListsIndex + 2);
                RenderItem.renderAABB(
                        this.posXClip - float2,
                        this.posYClip - float2,
                        this.posZClip - float2,
                        this.posXClip + this.sizeWidth + float2,
                        this.posYClip + this.sizeHeight + float2,
                        this.posZClip + this.sizeDepth + float2
                );
                this.displayLists.endDisplayList();
                this.needsBoxUpdate = false;
            }

            this.isVisible = true;
            this.isVisibleFromPosition = false;
            int sx = this.posX;
            int sy = this.posY;
            int sz = this.posZ;
            int ex = this.posX + this.sizeWidth;
            int ey = this.posY + this.sizeHeight;
            int ez = this.posZ + this.sizeDepth;
            byte size = 1;
            if (chunkCache == null) {
                chunkCache = new ChunkCache(this.worldObj, sx - size, sy - size, sz - size, ex + size, ey + size, ez + size);
            } else {
                chunkCache.set(this.worldObj, sx - size, sy - size, sz - size, ex + size, ey + size, ez + size);
            }

            if (chunkCache.getChunkFromBlockPos(sx, sy, sz).quickHasNoBlocks()) {
                this.clearRender();
                return;
            }

            for (int rpeter = 0; rpeter < 2; rpeter++) {
                this.skipRenderPass[rpeter] = true;
            }

            Chunk.isSkyLit = false;
            HashSet<TileEntity> tileentityset = new HashSet<TileEntity>(this.tileEntityRenderers);
            this.tileEntityRenderers.clear();
            if (rblocks == null) {
                rblocks = new RenderBlocks(chunkCache);
            } else {
                rblocks.reset(chunkCache);
            }

            this.vertexState = null;
            int minRenderX = Integer.MAX_VALUE;
            int minRenderY = Integer.MAX_VALUE;
            int minRenderZ = Integer.MAX_VALUE;
            int maxRenderX = Integer.MIN_VALUE;
            int maxRenderY = Integer.MIN_VALUE;
            int maxRenderZ = Integer.MIN_VALUE;

            for (int pass = 0; pass < 2; pass++) {
                boolean donecorrectrenderpass = false;
                boolean renderedblockcorrectly = false;
                boolean isrenderingblocks = false;

                for (int x = sx; x < ex; x++) {
                    for (int y = sy; y < ey; y++) {
                        for (int z = sz; z < ez; z++) {
                            int blockid;
                            if ((blockid = chunkCache.getBlockId(x, y, z)) != 0) {

                                //Turn desired blocks into dummy tile entities.
                                if (dilationCore$shouldMakeDummyFor(this.worldObj.getBlockId(x, y, z))) {
                                    TileEntityDummy tileEntityDummy = new TileEntityDummy();

                                    tileEntityDummy.xCoord = x;
                                    tileEntityDummy.yCoord = y;
                                    tileEntityDummy.zCoord = z;

                                    this.tileEntityRenderers.add(tileEntityDummy);
                                }

                                //Make sure the special render doesn't block our ESP.
                                if (pass == 0 && Blocks.IS_BLOCK_CONTAINER.get(blockid)) {
                                    TileEntity tileentity = chunkCache.getBlockTileEntity(x, y, z);
                                    this.tileEntityRenderers.add(tileentity);
                                }

                                Block var29;
                                if ((var29 = Blocks.BLOCKS_LIST[blockid]).getRenderBlockPass() != pass) {
                                    donecorrectrenderpass = true;
                                } else {
                                    if (!isrenderingblocks) {
                                        isrenderingblocks = true;
                                        this.preRenderBlocks(pass);
                                    }

                                    if (rblocks.renderBlockByRenderType(var29, x, y, z)) {
                                        renderedblockcorrectly = true;
                                        minRenderX = Math.min(minRenderX, x);
                                        maxRenderX = Math.max(maxRenderX, x);
                                        minRenderY = Math.min(minRenderY, y);
                                        maxRenderY = Math.max(maxRenderY, y);
                                        minRenderZ = Math.min(minRenderZ, z);
                                        maxRenderZ = Math.max(maxRenderZ, z);
                                    }
                                }
                            }
                        }
                    }
                }

                if (renderedblockcorrectly && tessellator.gotVertex()) {
                    this.skipRenderPass[pass] = false;
                    this.canRender = true;
                }

                if (isrenderingblocks) {
                    this.postRenderBlocks(pass, player);
                }

                if (!donecorrectrenderpass) {
                    break;
                }
            }

            this.rendererMinX = minRenderX - 1.0F;
            this.rendererMaxX = maxRenderX + 1 + 1.0F;
            this.rendererMinY = minRenderY - 1.0F;
            this.rendererMaxY = maxRenderY + 1 + 1.0F;
            this.rendererMinZ = minRenderZ - 1.0F;
            this.rendererMaxZ = maxRenderZ + 1 + 1.0F;
            HashSet var28;
            (var28 = new HashSet<TileEntity>(this.tileEntityRenderers)).removeAll(tileentityset);
            this.tileEntities.addAll(var28);
            this.tileEntityRenderers.forEach(tileentityset::remove);
            this.tileEntities.removeAll(tileentityset);
            this.isChunkSkyLit = this.canRender && Chunk.isSkyLit;
            this.isInitialized = true;
        }
        ci.cancel();
    }

    //List of blocks to make a dummy for.
    @Unique
    private boolean dilationCore$shouldMakeDummyFor(int id) {

        if (id == Blocks.DIMENSIONAL_CHEST.blockID) {
            return true;
        }

        if (id == Blocks.CRAFTING_TABLE.blockID) {
            return true;
        }

        if (id == Blocks.CARPENTRY_TABLE.blockID) {
            return true;
        }

        if (id == Blocks.ARTIFICIAL_HIVE.blockID) {
            return true;
        }

        if (id == Blocks.CAULDRON.blockID) {
            return true;
        }

        if (id == Blocks.BED.blockID) {
            return true;
        }

        return false;
    }
}
