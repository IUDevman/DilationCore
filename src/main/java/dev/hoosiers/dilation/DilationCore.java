package dev.hoosiers.dilation;

import com.fox2code.foxloader.loader.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.EntityOtherPlayerMP;
import net.minecraft.client.player.EntityPlayerSP;
import net.minecraft.client.util.KeyBinding;
import net.minecraft.common.block.Block;
import net.minecraft.common.block.Blocks;
import net.minecraft.common.block.data.Materials;
import net.minecraft.common.entity.Entity;
import net.minecraft.common.entity.animals.EntityAnimal;
import net.minecraft.common.entity.monsters.EntityMonster;
import net.minecraft.common.entity.monsters.EntitySlime;
import net.minecraft.common.world.World;
import org.lwjgl.input.Keyboard;

import java.io.*;
import java.util.*;

/**
 * @author Hoosiers
 * @since 02-20-2026
 */

public final class DilationCore extends Mod {

    //mod version
    private final String version = "r0.3.0";

    public String getVersion() {
        return this.version;
    }

    //mod instance
    private static DilationCore INSTANCE;

    public DilationCore() {
        INSTANCE = this;
    }

    //get mod instance
    public static DilationCore getInstance() {
        return INSTANCE;
    }

    //load config
    @Override
    public void onPostInit() {

        loadConfiguration();
    }

    //shittiest way to do it, but I can't be damned to spend time making a griefing client instead of just griefing.
    private boolean shouldESP = false;
    private boolean shouldFastBreak = false;
    private boolean shouldFly = false;
    private boolean shouldFullbright = false;
    private boolean shouldJesus = false;
    private boolean shouldKillAura = false;
    private boolean shouldNoExhaustion = false;
    private boolean shouldNoFall = false;
    private boolean shouldNoWeather = false;
    private boolean shouldTracers = false;
    private boolean shouldXray = false;

    private int flightSpeed = 5;
    private int auraRange = 7;

    public boolean shouldESP() {
        return this.shouldESP;
    }

    public boolean shouldFastBreak() {
        return this.shouldFastBreak;
    }

    public boolean shouldFly() {
        return this.shouldFly;
    }

    public boolean shouldFullbright() {
        return this.shouldFullbright;
    }

    public boolean shouldJesus() {
        return this.shouldJesus;
    }

    public boolean shouldKillAura() {
        return this.shouldKillAura;
    }

    public boolean shouldNoExhaustion() {
        return this.shouldNoExhaustion;
    }

    public boolean shouldNoFall() {
        return this.shouldNoFall;
    }

    public boolean shouldNoWeather() {
        return this.shouldNoWeather;
    }

    public boolean shouldTracers() {
        return this.shouldTracers;
    }

    public boolean shouldXray() {
        return this.shouldXray;
    }

    public void toggleESP() {
        this.shouldESP = !this.shouldESP();
        this.sendChatToggleMessage("ESP", this.shouldESP());
    }

    public void toggleFastBreak() {
        this.shouldFastBreak = !this.shouldFastBreak();
        this.sendChatToggleMessage("FastBreak", this.shouldFastBreak());
    }

    public void toggleFly() {
        this.shouldFly = !this.shouldFly();
        this.sendChatToggleMessage("Fly", this.shouldFly());
    }

    public void toggleFullbright() {
        this.shouldFullbright = !this.shouldFullbright();
        this.fullbrightCleanUp();
        this.sendChatToggleMessage("Fullbright", this.shouldFullbright());

    }

    public void toggleJesus() {
        this.shouldJesus = !this.shouldJesus();
        this.sendChatToggleMessage("Jesus", this.shouldJesus());
    }

    public void toggleKillAura() {
        this.shouldKillAura = !this.shouldKillAura();
        this.sendChatToggleMessage("KillAura", this.shouldKillAura());
    }

    public void toggleNoExhaustion()  {
        this.shouldNoExhaustion = !this.shouldNoExhaustion();
        this.sendChatToggleMessage("NoExhaustion", this.shouldNoExhaustion());
    }

    public void toggleNoFall() {
        this.shouldNoFall = !this.shouldNoFall();
        this.sendChatToggleMessage("NoFall", this.shouldNoFall());
    }

    public void toggleNoWeather() {
        this.shouldNoWeather = !this.shouldNoWeather();
        this.sendChatToggleMessage("NoWeather", this.shouldNoWeather());
    }

    public void toggleTracers() {
        this.shouldTracers = !this.shouldTracers();
        this.sendChatToggleMessage("Tracers", this.shouldTracers());
    }

    public void toggleXray() {
        this.shouldXray = !this.shouldXray();
        this.fullbrightCleanUp();
        this.sendChatToggleMessage("Xray", this.shouldXray());
    }

    public int getFlightSpeed() {
        return this.flightSpeed;
    }

    //sets flight speed and makes sure it isn't too low or high.
    public void setFlightSpeed(int flightSpeed) {

        int newFlightSpeed = flightSpeed;

        if (newFlightSpeed < 3) {
            newFlightSpeed = 3;
        }

        if (newFlightSpeed > 20) {
            newFlightSpeed = 20;
        }

        this.flightSpeed = newFlightSpeed;
    }

    public int getAuraRange() {
        return this.auraRange;
    }

    public void setAuraRange(int auraRange) {

        int newAuraRange = auraRange;

        if (newAuraRange < 0) {
            newAuraRange = 0;
        }

        if (newAuraRange > 10) {
            newAuraRange = 10;
        }

        this.auraRange = newAuraRange;
    }

    //checks to see if the player or world is not loaded.
    //very important to not load things if their dependencies are not loaded.
    public boolean failsNullCheck() {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft == null || minecraft.thePlayer == null || minecraft.theWorld == null) {
            return true;
        }

        return false;
    }

    private boolean shouldSendToggleMessages = true;

    public boolean shouldSendToggleMessages() {
        return this.shouldSendToggleMessages;
    }

    public void setShouldSendToggleMessages(boolean shouldSendToggleMessages){
        this.shouldSendToggleMessages = shouldSendToggleMessages;
    }

    //sends a chat message when modules are toggled
    private void sendChatToggleMessage(String module, boolean enabled) {
        if (this.failsNullCheck()) {
            return;
        }

        if (!this.shouldSendToggleMessages()) {
            return;
        }

        String message = "";

        if (enabled) {
            message = "[§bDilation§9Core§f] Module " + module + " §aEnabled§f!";
        } else {
            message = "[§bDilation§9Core§f] Module " + module + " §cDisabled§f!";
        }

        Minecraft.getInstance().thePlayer.addChatMessage(message);

        this.incrementAndRemindToggleMessages();
    }

    private int messageCount = 0;

    private int getMessageCount() {
        return this.messageCount;
    }

    private void resetMessageCount() {
        this.messageCount = 0;
    }

    private void incrementAndRemindToggleMessages() {
        this.messageCount += 1;

        if (this.getMessageCount() == 20) {
            Minecraft.getInstance().thePlayer.addChatMessage("[§bDilation§9Core§f] Getting spammed too much? Type §c-tm§f to disable toggle messages!");
            this.resetMessageCount();
        }
    }

    //resets world render when changing brightness
    private void fullbrightCleanUp() {

        if (this.failsNullCheck()) {
            return;
        }

        Minecraft.getInstance().renderGlobal.updateRenderers(Minecraft.getInstance().thePlayer, Minecraft.getSystemTime());
        Minecraft.getInstance().renderGlobal.loadRenderers();
    }

    //checks to see if certain conditions are present that could harm the player if jesus is on.
    public boolean shouldJesus(int x, int y, int z) {

        Minecraft minecraft = Minecraft.getInstance();
        EntityPlayerSP entityPlayerSP = minecraft.thePlayer;
        World world = minecraft.theWorld;

        if (entityPlayerSP.isInWater() || entityPlayerSP.isInsideOfMaterial(Materials.LAVA)) {
            return false;
        }

        if (world.getBlockMaterial(x, y, z) == Materials.LAVA) {
            return true;
        }

        if (minecraft.currentScreen == null && Keyboard.isKeyDown(minecraft.gameSettings.keyBindSneak.keyCode)) {
            return false;
        }

        return !(entityPlayerSP.fallDistance >= 3);
    }

    //secret
    private boolean players = true;

    private boolean hostiles = true;

    private boolean animals = true;

    public boolean shouldAttackPlayers() {
        return this.players;
    }

    public boolean shouldAttackHostiles() {
        return this.hostiles;
    }

    public boolean shouldAttackAnimals() {
        return this.animals;
    }

    public void setAttackPlayers(boolean attackPlayers) {
        this.players = attackPlayers;
    }

    public void setAttackHostiles(boolean attackHostiles) {
        this.hostiles = attackHostiles;
    }

    public void setAttackAnimals(boolean attackPassive) {
        this.animals = attackPassive;
    }

    //check to see if we should attack an entity with KillAura.
    private boolean shouldAttackEntity(Entity entity) {

        if (this.shouldAttackPlayers() && entity instanceof EntityOtherPlayerMP) {
            return true;
        }

        if (this.shouldAttackHostiles() && (entity instanceof EntityMonster || entity instanceof EntitySlime)) {
            return true;
        }

        if (this.shouldAttackAnimals() && entity instanceof EntityAnimal) {
            return true;
        }

        return false;
    }

    //secret diamonds only mode for Xray.
    private boolean diamondsOnly = false;

    public boolean isDiamondsOnly()  {
        return this.diamondsOnly;
    }

    public void setDiamondsOnly(boolean diamondsOnly)  {
        this.diamondsOnly = diamondsOnly;

        if (this.shouldXray()) {
            this.fullbrightCleanUp();
        }
    }

    //@see RenderBlocksMixin
    //returns list of blocks to >> NOT << hide when Xray is enabled.
    public ArrayList<Block> getXrayBlocks() {
        ArrayList<Block> xrayBlocks = new ArrayList<>();

        if (this.isDiamondsOnly()) {
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

        return xrayBlocks;
    }

    //@see GameSettingsMixin
    //Can change this eventually to a dedicated keybind manager... It doesn't look like we have to add these keybinds to the mixin for them to work.
    public final KeyBinding keyBindingESP = new KeyBinding("key.ESP", Keyboard.KEY_K);
    public final KeyBinding keyBindingFastBreak = new KeyBinding("key.fastBreak", Keyboard.KEY_L);
    public final KeyBinding keyBindingFly = new KeyBinding("key.fly", Keyboard.KEY_G);
    public final KeyBinding keyBindingNoFall = new KeyBinding("key.noFall", Keyboard.KEY_N);
    public final KeyBinding keyBindingNoExhaustion = new KeyBinding("key.noExhaustion", Keyboard.KEY_M);
    public final KeyBinding keyBindingJesus = new KeyBinding("key.jesus", Keyboard.KEY_J);
    public final KeyBinding keyBindingKillAura = new KeyBinding("key.killAura", Keyboard.KEY_O);
    public final KeyBinding keyBindingFullbright = new KeyBinding("key.fullbright", Keyboard.KEY_B);
    public final KeyBinding keyBindingNoWeather = new KeyBinding("key.noWeather", Keyboard.KEY_H);
    public final KeyBinding keyBindingTracers = new KeyBinding("key.tracers", Keyboard.KEY_P);
    public final KeyBinding keyBindingXray = new KeyBinding("key.xray", Keyboard.KEY_X);
    public final KeyBinding keyBindingPageLeft = new KeyBinding("key.pageLeft", Keyboard.KEY_LEFT);
    public final KeyBinding keyBindingPageRight = new KeyBinding("key.pageRight", Keyboard.KEY_RIGHT);

    //Pages for gui... it's getting pretty long
    //2 pages for now
    private int guiPage = 1;

    public int getGuiPage() {
        return this.guiPage;
    }

    public void setGuiPage(int newGuiPage) {
        if (newGuiPage < 1) {
            newGuiPage = 1;
        }

        if (newGuiPage > 3) {
            newGuiPage = 3;
        }

        this.guiPage = newGuiPage;
    }

    //@see MinecraftMixin
    public void onTick() {

        //Activate modules
        if (Minecraft.getInstance().currentScreen == null) {
            triggerModuleFromKey();
        }

        //No fall
        if (this.shouldNoFall()) {
            Minecraft.getInstance().thePlayer.fallDistance = 0;
        }

        //Flight
        if (this.shouldFly()) {
            Minecraft.getInstance().thePlayer.motionX = 0;
            Minecraft.getInstance().thePlayer.motionY = 0;
            Minecraft.getInstance().thePlayer.motionZ = 0;

            Minecraft.getInstance().thePlayer.jumpMovementFactor = (float) (this.getFlightSpeed() / 3);

            //makes sure these don't run when in the chat
            if (Minecraft.getInstance().currentScreen == null) {
                if (Keyboard.isKeyDown(Minecraft.getInstance().gameSettings.keyBindJump.keyCode)) {
                    Minecraft.getInstance().thePlayer.motionY = Minecraft.getInstance().thePlayer.motionY + ((double) this.getFlightSpeed() / 4);
                }

                if (Keyboard.isKeyDown(Minecraft.getInstance().gameSettings.keyBindSneak.keyCode)) {
                    Minecraft.getInstance().thePlayer.motionY = Minecraft.getInstance().thePlayer.motionY - ((double) this.flightSpeed / 4);
                }
            }

        }

        //Jesus
        if (this.shouldJesus()) {
            boolean isInLava = Minecraft.getInstance().thePlayer.isInsideOfMaterial(Materials.LAVA);

            if (Minecraft.getInstance().thePlayer.isInWater() || isInLava) {

                if (Minecraft.getInstance().currentScreen == null && Keyboard.isKeyDown(Minecraft.getInstance().gameSettings.keyBindSneak.keyCode)) {
                    return;
                }

                Minecraft.getInstance().thePlayer.motionY = isInLava ? 1.2d : 0.30d;
            }
        }

        //KillAura
        if (this.shouldKillAura()) {

            LinkedHashMap<Entity, Double> entitiesToKill = new LinkedHashMap<>();

            for (int i = 0; i < Minecraft.getInstance().theWorld.loadedEntityList.size(); i++) {
                Entity entity = Minecraft.getInstance().theWorld.loadedEntityList.get(i);

                //I don't know if this is needed
                if (entity == null) {
                    continue;
                }

                //Don't hit us!
                if (entity == Minecraft.getInstance().thePlayer) {
                    continue;
                }

                //should be the least performance intensive operation
                if (this.shouldAttackEntity(entity)) {

                    double distance = Minecraft.getInstance().thePlayer.getDistanceToEntity(entity);

                    //confirm that the entity is in range
                    if (distance <= this.getAuraRange()) {
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
                    Minecraft.getInstance().playerController.attackEntity(Minecraft.getInstance().thePlayer, result.getKey());
                    Minecraft.getInstance().thePlayer.swingItem();
                }
            }
        }
    }

    // toggle modules from keybind
    private void triggerModuleFromKey() {

        //EntityESP
        if (keyBindingESP.isPressed()) {
            this.toggleESP();
        }

        //Fastbreak
        if (keyBindingFastBreak.isPressed()) {
            this.toggleFastBreak();
        }

        //Fullbright
        if (keyBindingFullbright.isPressed()) {
            this.toggleFullbright();
        }

        //NoFall
        if (keyBindingNoFall.isPressed()) {
            this.toggleNoFall();
        }

        //NoExhaustion
        if (keyBindingNoExhaustion.isPressed()) {
            this.toggleNoExhaustion();
        }

        //Jesus
        if (keyBindingJesus.isPressed()) {
            this.toggleJesus();
        }

        //KillAura
        if (keyBindingKillAura.isPressed()) {
            this.toggleKillAura();
        }

        //flight ~~~ ReinDev already has F bound to fog.
        if (keyBindingFly.isPressed()) {
            this.toggleFly();
        }

        //NoWeather
        if (keyBindingNoWeather.isPressed()) {
            this.toggleNoWeather();
        }

        //Tracers
        if (keyBindingTracers.isPressed()) {
            this.toggleTracers();
        }

        //Xray
        if (keyBindingXray.isPressed()) {
            this.toggleXray();
        }

        //GUI Page
        if (keyBindingPageLeft.isPressed()) {
            this.setGuiPage(this.getGuiPage() - 1);
        } else if (keyBindingPageRight.isPressed()) {
            this.setGuiPage(this.getGuiPage() + 1);
        }
    }

    //returns config file.
    private File returnDilationCoreDirectory() {
        File minecraftDirectory = Minecraft.getInstance().getMinecraftDir();

        File dilationCoreDirectory = new File(minecraftDirectory, "DilationCoreConfig.txt");

        return dilationCoreDirectory;
    }

    //load active modules and values.
    //should eventually fix this to allow for loading of modules that are enabled by default.
    private void loadConfiguration() {
        File dilationCoreDirectory = this.returnDilationCoreDirectory();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(dilationCoreDirectory));

            String bufferedEntry;

            while ((bufferedEntry = bufferedReader.readLine()) != null) {
                try {
                    String[] entry = bufferedEntry.split(":");

                    String entry0 = entry[0];
                    String entry1 = entry[1];

                    if (entry0.equals("ESP") && Boolean.parseBoolean(entry1)) {
                        this.toggleESP();
                    }

                    if (entry0.equals("FastBreak") && Boolean.parseBoolean(entry1)) {
                        this.toggleFastBreak();
                    }

                    if (entry0.equals("Fly") && Boolean.parseBoolean(entry1)) {
                        this.toggleFly();
                    }

                    if (entry0.equals("FlySpeed")) {
                        this.setFlightSpeed(Integer.parseInt(entry1));
                    }

                    if (entry0.equals("FullBright") && Boolean.parseBoolean(entry1)) {
                        this.toggleFullbright();
                    }

                    if (entry0.equals("Jesus") && Boolean.parseBoolean(entry1)) {
                        this.toggleJesus();
                    }

                    if (entry0.equals("KillAura") && Boolean.parseBoolean(entry1)) {
                        this.toggleKillAura();
                    }

                    if (entry0.equals("PlayersKA")) {
                        this.setAttackPlayers(Boolean.parseBoolean(entry1));
                    }

                    if (entry0.equals("HostilesKA")) {
                        this.setAttackHostiles(Boolean.parseBoolean(entry1));
                    }

                    if (entry0.equals("AnimalsKA")) {
                        this.setAttackAnimals(Boolean.parseBoolean(entry1));
                    }

                    if (entry0.equals("NoExhaustion") && Boolean.parseBoolean(entry1)) {
                        this.toggleNoExhaustion();
                    }

                    if (entry0.equals("NoFall") && Boolean.parseBoolean(entry1)) {
                        this.toggleNoFall();
                    }

                    if (entry0.equals("NoWeather") && Boolean.parseBoolean(entry1)) {
                        this.toggleNoWeather();
                    }

                    if (entry0.equals("Tracers") && Boolean.parseBoolean(entry1)) {
                        this.toggleTracers();
                    }

                    if (entry0.equals("Xray") && Boolean.parseBoolean(entry1)) {
                        this.toggleXray();
                    }

                    if (entry0.equals("DiamondsOnlyX")) {
                        this.setDiamondsOnly(Boolean.parseBoolean(entry1));
                    }

                    if (entry0.equals("GUIPage")) {
                        this.setGuiPage(Integer.parseInt(entry1));
                    }

                    if (entry0.equals("ModuleToggleMSGs")) {
                        this.setShouldSendToggleMessages(Boolean.parseBoolean(entry1));
                    }

                } catch (Exception ignored) {

                }
            }

            bufferedReader.close();

        } catch (Exception ignored) {

        }
    }

    //save active modules and values.
    //@see MinecraftMixin.
    public void saveConfiguration() {
        File dilationCoreDirectory = this.returnDilationCoreDirectory();

        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(dilationCoreDirectory));

            printWriter.println("ESP:" + this.shouldESP());
            printWriter.println("FastBreak:" + this.shouldFastBreak());
            printWriter.println("Fly:" + this.shouldFly());
            printWriter.println("FlySpeed:" + this.getFlightSpeed());
            printWriter.println("FullBright:" + this.shouldFullbright());
            printWriter.println("Jesus:" + this.shouldJesus());
            printWriter.println("KillAura:" + this.shouldKillAura());
            printWriter.println("PlayersKA:" + this.shouldAttackPlayers());
            printWriter.println("HostilesKA:" + this.shouldAttackHostiles());
            printWriter.println("AnimalsKA:" + this.shouldAttackAnimals());
            printWriter.println("NoExhaustion:" + this.shouldNoExhaustion());
            printWriter.println("NoFall:" + this.shouldNoFall());
            printWriter.println("NoWeather:" + this.shouldNoWeather());
            printWriter.println("Tracers:" + this.shouldTracers());
            printWriter.println("Xray:" + this.shouldXray());
            printWriter.println("DiamondsOnlyX:" + this.isDiamondsOnly());
            printWriter.println("GUIPage:" + this.getGuiPage());
            printWriter.println("ModuleToggleMSGs:" + this.shouldSendToggleMessages());

            printWriter.close();

        } catch (Exception ignored) {

        }
    }
}
