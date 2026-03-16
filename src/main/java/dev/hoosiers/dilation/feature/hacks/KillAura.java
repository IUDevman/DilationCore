package dev.hoosiers.dilation.feature.hacks;

import dev.hoosiers.dilation.imp.Category;
import dev.hoosiers.dilation.imp.Hack;
import dev.hoosiers.dilation.imp.settings.BooleanSetting;
import dev.hoosiers.dilation.imp.settings.NumberSetting;
import net.minecraft.client.player.EntityOtherPlayerMP;
import net.minecraft.common.entity.Entity;
import net.minecraft.common.entity.animals.EntityAnimal;
import net.minecraft.common.entity.monsters.EntityMonster;
import net.minecraft.common.entity.monsters.EntitySlime;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hoosiers
 * @since 03-14-2026
 */

public final class KillAura extends Hack {

    public final NumberSetting range = new NumberSetting("Range", 7.0, 1.0, 10.0, 1);
    public final BooleanSetting players = new BooleanSetting("Players", true);
    public final BooleanSetting hostiles = new BooleanSetting("Hostiles", true);
    public final BooleanSetting animals = new BooleanSetting("Animals", true);

    public KillAura() {
        super("KillAura", Category.Player, Keyboard.KEY_R, false, true, true);
    }

    @Override
    public void onTick() {
        LinkedHashMap<Entity, Double> entitiesToKill = new LinkedHashMap<>();

        for (int i = 0; i < this.getWorld().loadedEntityList.size(); i++) {
            Entity entity = this.getWorld().loadedEntityList.get(i);

            //I don't know if this is needed
            if (entity == null) {
                continue;
            }

            //Don't hit us!
            if (entity == this.getPlayer()) {
                continue;
            }

            //should be the least performance intensive operation
            if (this.shouldAttackEntity(entity)) {

                double distance = this.getPlayer().getDistanceToEntity(entity);

                //confirm that the entity is in range
                if (distance <= this.range.getValue()) {
                    entitiesToKill.put(entity, distance);
                }
            }
        }

        //sort entities by range
        if (!entitiesToKill.isEmpty()) {

            List<Map.Entry<Entity, Double>> entryList = new ArrayList<>(entitiesToKill.entrySet());

            entryList.sort(Map.Entry.comparingByValue());

            Map.Entry<Entity, Double> result = entryList.stream().findFirst().orElse(null);

            if (result != null) {
                this.getMinecraft().playerController.attackEntity(this.getPlayer(), result.getKey());
                this.getPlayer().swingItem();
            }
        }
    }

    private boolean shouldAttackEntity(Entity entity) {

        //Do not attack dead entities
        if (!entity.isEntityAlive()) {
            return false;
        }

        if (this.players.getValue() && entity instanceof EntityOtherPlayerMP) {
            return true;
        }

        if (this.hostiles.getValue() && (entity instanceof EntityMonster || entity instanceof EntitySlime)) {
            return true;
        }

        if (this.animals.getValue() && entity instanceof EntityAnimal) {
            return true;
        }

        return false;
    }
}
