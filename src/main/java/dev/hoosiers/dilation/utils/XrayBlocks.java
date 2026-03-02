package dev.hoosiers.dilation.utils;

import net.minecraft.common.block.Block;
import net.minecraft.common.block.Blocks;

import java.util.ArrayList;

/**
 * @author Hoosiers
 * @since 03-01-2026
 */

public class XrayBlocks {

    //@see RenderBlocksMixin
    //returns list of blocks to >> NOT << hide when Xray is enabled.
    public static ArrayList<Block> getXrayBlocks(boolean diamondsOnly) {
        ArrayList<Block> xrayBlocks = new ArrayList<>();

        if (diamondsOnly) {
            xrayBlocks.add(Blocks.DIAMOND_BLOCK);
            xrayBlocks.add(Blocks.DIAMOND_ORE);
            xrayBlocks.add(Blocks.NETHER_DIAMOND_ORE);
            xrayBlocks.add(Blocks.LAVA_MOVING);
            xrayBlocks.add(Blocks.LAVA_STILL);
            xrayBlocks.add(Blocks.WATER_STILL);
            xrayBlocks.add(Blocks.WATER_MOVING);
            xrayBlocks.add(Blocks.ACID_MOVING);
            xrayBlocks.add(Blocks.ACID_STILL);
            xrayBlocks.add(Blocks.SANGUIS_MOVING);
            xrayBlocks.add(Blocks.SANGUIS_STILL);
            xrayBlocks.add(Blocks.QUICKSAND);

            return xrayBlocks;
        }

        xrayBlocks.add(Blocks.CRAFTING_TABLE);
        xrayBlocks.add(Blocks.FURNACE_IDLE);
        xrayBlocks.add(Blocks.FURNACE_ACTIVE);
        xrayBlocks.add(Blocks.CHEST);
        xrayBlocks.add(Blocks.COLORED_CHEST);
        xrayBlocks.add(Blocks.DIMENSIONAL_CHEST);
        xrayBlocks.add(Blocks.CRATE);
        xrayBlocks.add(Blocks.COLORED_CRATE);
        xrayBlocks.add(Blocks.BED);
        xrayBlocks.add(Blocks.TORCH);
        xrayBlocks.add(Blocks.JET_TORCH);
        xrayBlocks.add(Blocks.CITRINE_TORCH);
        xrayBlocks.add(Blocks.MYTHRIL_TORCH);
        xrayBlocks.add(Blocks.QUARTZ_TORCH);
        xrayBlocks.add(Blocks.STICKY_TORCH);
        xrayBlocks.add(Blocks.REDSTONE_TORCH_IDLE);
        xrayBlocks.add(Blocks.REDSTONE_TORCH_ACTIVE);
        xrayBlocks.add(Blocks.CARPENTRY_TABLE);
        xrayBlocks.add(Blocks.CAULDRON);
        xrayBlocks.add(Blocks.TILLED_FIELD);
        xrayBlocks.add(Blocks.CARROT_CROPS);
        xrayBlocks.add(Blocks.CORN_CROPS);
        xrayBlocks.add(Blocks.MINT_CROPS);
        xrayBlocks.add(Blocks.WHEAT_CROPS);
        xrayBlocks.add(Blocks.POTATO_CROPS);
        xrayBlocks.add(Blocks.CORN_COB_BLOCK);
        xrayBlocks.add(Blocks.POPCORN_BLOCK);
        xrayBlocks.add(Blocks.BUNDLE_OF_MINT);
        xrayBlocks.add(Blocks.CHEESE_WHEEL);
        xrayBlocks.add(Blocks.GLOWSTONE);
        xrayBlocks.add(Blocks.BEACON);
        xrayBlocks.add(Blocks.COAL_BLOCK);
        xrayBlocks.add(Blocks.COAL_ORE);
        xrayBlocks.add(Blocks.NETHER_COAL_ORE);
        xrayBlocks.add(Blocks.IRON_BLOCK);
        xrayBlocks.add(Blocks.IRON_ORE);
        xrayBlocks.add(Blocks.NETHER_IRON_ORE);
        xrayBlocks.add(Blocks.GOLD_BLOCK);
        xrayBlocks.add(Blocks.GOLD_ORE);
        xrayBlocks.add(Blocks.NETHER_GOLD_ORE);
        xrayBlocks.add(Blocks.DIAMOND_BLOCK);
        xrayBlocks.add(Blocks.DIAMOND_ORE);
        xrayBlocks.add(Blocks.NETHER_DIAMOND_ORE);
        xrayBlocks.add(Blocks.LAPIS_BLOCK);
        xrayBlocks.add(Blocks.AUGMENTITE_ORE);
        xrayBlocks.add(Blocks.AUGMENTITE_BLOCK);
        xrayBlocks.add(Blocks.GEAR);
        xrayBlocks.add(Blocks.GEAR_RELAY_ACTIVE);
        xrayBlocks.add(Blocks.GEAR_RELAY_IDLE);
        xrayBlocks.add(Blocks.GEAR_WAIT_ACTIVE);
        xrayBlocks.add(Blocks.GEAR_WAIT_IDLE);
        xrayBlocks.add(Blocks.GEAR_AND_GATE_ACTIVE);
        xrayBlocks.add(Blocks.GEAR_AND_GATE_IDLE);
        xrayBlocks.add(Blocks.GEAR_NOT_GATE_ACTIVE);
        xrayBlocks.add(Blocks.GEAR_NOT_GATE_IDLE);
        xrayBlocks.add(Blocks.GEAR_OR_GATE_ACTIVE);
        xrayBlocks.add(Blocks.GEAR_OR_GATE_IDLE);
        xrayBlocks.add(Blocks.GEAR_XOR_GATE_ACTIVE);
        xrayBlocks.add(Blocks.GEAR_XOR_GATE_IDLE);
        xrayBlocks.add(Blocks.COMBUSTOR_ACTIVE);
        xrayBlocks.add(Blocks.COMBUSTOR_IDLE);
        xrayBlocks.add(Blocks.HONEYCOMB_BLOCK);
        xrayBlocks.add(Blocks.FIRE);
        xrayBlocks.add(Blocks.LAVA_MOVING);
        xrayBlocks.add(Blocks.LAVA_STILL);
        xrayBlocks.add(Blocks.WATER_STILL);
        xrayBlocks.add(Blocks.WATER_MOVING);
        xrayBlocks.add(Blocks.ACID_MOVING);
        xrayBlocks.add(Blocks.ACID_STILL);
        xrayBlocks.add(Blocks.SANGUIS_MOVING);
        xrayBlocks.add(Blocks.SANGUIS_STILL);
        xrayBlocks.add(Blocks.QUICKSAND);
        xrayBlocks.add(Blocks.PUMPKIN);
        xrayBlocks.add(Blocks.PUMPKIN_STEM);
        xrayBlocks.add(Blocks.WATERLILY);
        xrayBlocks.add(Blocks.BLUE_WATERLILY);
        xrayBlocks.add(Blocks.YELLOW_WATERLILY);
        xrayBlocks.add(Blocks.LILYPAD);
        xrayBlocks.add(Blocks.WATERMELON);
        xrayBlocks.add(Blocks.WATERMELON_STEM);
        xrayBlocks.add(Blocks.QUARTZ);
        xrayBlocks.add(Blocks.LEVER);
        xrayBlocks.add(Blocks.MOB_SPAWNER);
        xrayBlocks.add(Blocks.TNT);
        xrayBlocks.add(Blocks.JUKEBOX);
        xrayBlocks.add(Blocks.PISTON_BASE);
        xrayBlocks.add(Blocks.PISTON_EXTENSION);
        xrayBlocks.add(Blocks.PISTON_MOVING);
        xrayBlocks.add(Blocks.PISTON_STICKY_BASE);
        xrayBlocks.add(Blocks.MOTOR_ACTIVE);
        xrayBlocks.add(Blocks.MOTOR_IDLE);
        xrayBlocks.add(Blocks.RAIL);
        xrayBlocks.add(Blocks.DETECTOR_RAIL);
        xrayBlocks.add(Blocks.POWERED_RAIL);
        xrayBlocks.add(Blocks.CONVEYOR_BELT_ACTIVE);
        xrayBlocks.add(Blocks.CONVEYOR_BELT_IDLE);
        xrayBlocks.add(Blocks.SUGAR_CANE);
        xrayBlocks.add(Blocks.SIGN_POST);
        xrayBlocks.add(Blocks.WALL_SIGN);
        xrayBlocks.add(Blocks.FORGE_ACTIVE);
        xrayBlocks.add(Blocks.FORGE_IDLE);
        xrayBlocks.add(Blocks.ARTIFICIAL_HIVE);
        xrayBlocks.add(Blocks.REFRIDGIFREEZER_ACTIVE);
        xrayBlocks.add(Blocks.REFRIDGIFREEZER_IDLE);
        xrayBlocks.add(Blocks.INCINERATOR);
        xrayBlocks.add(Blocks.TOMBSTONE);
        xrayBlocks.add(Blocks.CUCURBOO_TOMBSTONE);
        xrayBlocks.add(Blocks.PORTAL);

        return xrayBlocks;
    }
}
