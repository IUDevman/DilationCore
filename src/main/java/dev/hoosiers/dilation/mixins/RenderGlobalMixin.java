package dev.hoosiers.dilation.mixins;

import dev.hoosiers.dilation.DilationCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.DestroyBlockProgress;
import net.minecraft.client.renderer.block.tileentity.TileEntityRenderManager;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.world.Frustrum;
import net.minecraft.client.renderer.world.RenderEngine;
import net.minecraft.client.renderer.world.RenderGlobal;
import net.minecraft.common.block.tileentity.TileEntity;
import net.minecraft.common.entity.Entity;
import net.minecraft.common.entity.EntityLiving;
import net.minecraft.common.util.math.MathHelper;
import net.minecraft.common.util.math.Vec3D;
import net.minecraft.common.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Hoosiers
 * @since 02-24-2026
 */

@Mixin(value = RenderGlobal.class, priority = 6969)
public final class RenderGlobalMixin {

    @Shadow private int renderEntitiesStartupCounter;

    @Shadow private World worldObj;

    @Shadow @Final private RenderEngine renderEngine;

    @Shadow @Final private Minecraft mc;

    @Shadow private int countEntitiesTotal;

    @Shadow private int countEntitiesRendered;

    @Shadow private int countEntitiesHidden;

    @Shadow @Final public List<TileEntity> tileEntities;

    @Shadow @Final private Map<Integer, DestroyBlockProgress> damagedBlocks;

    //Copied renderEntities method but removed distance check so all mobs are rendered.
    @Inject(method = "renderEntities", at = @At("HEAD"), cancellable = true)
    public void renderEntitites(Vec3D vec3D, Frustrum frustum, float deltaTicks, CallbackInfo ci) {
        DilationCore dilationCore = DilationCore.getInstance();

        if (dilationCore == null || dilationCore.failsNullCheck() || !dilationCore.shouldESP()) {
            return;
        }

        if (this.renderEntitiesStartupCounter > 0) {
            this.renderEntitiesStartupCounter--;
        } else {
            TileEntityRenderManager.instance.cacheActiveRenderInfo(this.worldObj, this.renderEngine, this.mc.fontRenderer, this.mc.renderViewEntity, deltaTicks);

            EntityRendererManager.instance.cacheActiveRenderInfo(this.worldObj, this.renderEngine, this.mc.fontRenderer, this.mc.renderViewEntity, this.mc.gameSettings, deltaTicks);

            this.countEntitiesTotal = 0;
            this.countEntitiesRendered = 0;
            this.countEntitiesHidden = 0;

            EntityLiving player = this.mc.renderViewEntity;

            EntityRendererManager.renderPosX = MathHelper.lerp_d(deltaTicks, player.lastTickPosX, player.posX);
            EntityRendererManager.renderPosY = MathHelper.lerp_d(deltaTicks, player.lastTickPosY, player.posY);
            EntityRendererManager.renderPosZ = MathHelper.lerp_d(deltaTicks, player.lastTickPosZ, player.posZ);

            TileEntityRenderManager.staticPlayerX = MathHelper.lerp_d(deltaTicks, player.lastTickPosX, player.posX);
            TileEntityRenderManager.staticPlayerY = MathHelper.lerp_d(deltaTicks, player.lastTickPosY, player.posY);
            TileEntityRenderManager.staticPlayerZ = MathHelper.lerp_d(deltaTicks, player.lastTickPosZ, player.posZ);

            List<Entity> entlist = this.worldObj.getLoadedEntityList();

            this.countEntitiesTotal = entlist.size();

            for (int iter = 0; iter < this.worldObj.weatherEffects.size(); iter++) {
                Entity progress;

                if ((progress = this.worldObj.weatherEffects.get(iter)).isInRangeToRenderVec3D(vec3D)) {
                    this.countEntitiesRendered++;
                    EntityRendererManager.instance.renderEntity(progress, deltaTicks);
                }
            }

            //changed this place
            for (int i = 0; i < entlist.size(); i++) {
                Entity var15 = entlist.get(i);

                //Note: I've added this to try to prevent a weird rendering glitch if the player dies with ESP on.
                //Without it, entity "shadows" will still be rendered and are stuck there unless relogged.
                if (Minecraft.getInstance().thePlayer.isDead) {
                    continue;
                }

                if (var15 == mc.renderViewEntity && this.mc.gameSettings.thirdPersonView == 0) {
                    continue;
                }

                    this.countEntitiesRendered++;
                    EntityRendererManager.instance.renderEntity(var15, deltaTicks);
            }

            //changed this place
            Iterator var13 = this.tileEntities.iterator();

            while (var13.hasNext()) {
                TileEntity var16 = (TileEntity) var13.next();

                TileEntityRenderManager.instance.renderTileEntity(var16, deltaTicks);
            }

            var13 = this.damagedBlocks.values().iterator();

            while (var13.hasNext()) {
                DestroyBlockProgress var17;

                int x = (var17 = (DestroyBlockProgress)var13.next()).getPartialBlockX();
                int y = var17.getPartialBlockY();
                int z = var17.getPartialBlockZ();

                TileEntity te;

                if ((te = this.worldObj.getBlockTileEntity(x, y, z)) != null && te.renderBlockDamage) {
                    TileEntityRenderManager.instance.renderTileEntityWithDamageProgress(te, x, y, z, deltaTicks, var17.getPartialBlockDamage());
                }
            }
        }

        ci.cancel();
    }
}
