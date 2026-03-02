package dev.hoosiers.dilation;

import com.fox2code.foxloader.loader.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.EntityOtherPlayerMP;
import net.minecraft.client.player.EntityPlayerSP;
import net.minecraft.client.util.KeyBinding;
import net.minecraft.common.block.Blocks;
import net.minecraft.common.block.data.Material;
import net.minecraft.common.block.data.Materials;
import net.minecraft.common.entity.Entity;
import net.minecraft.common.entity.animals.EntityAnimal;
import net.minecraft.common.entity.monsters.EntityMonster;
import net.minecraft.common.entity.monsters.EntitySlime;
import net.minecraft.common.networking.Packet14BlockDig;
import net.minecraft.common.world.World;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector4f;

import java.io.*;
import java.util.*;

/**
 * @author Hoosiers
 * @since 02-20-2026
 */

public final class DilationCore extends Mod {

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

    //mod version
    private final String version = "d0.4.0";

    public String getVersion() {
        return this.version;
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
    private boolean shouldTorchNuker = false;
    private boolean shouldXray = false;

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

    public boolean shouldTorchNuker() {
        return this.shouldTorchNuker;
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

    public void toggleTorchNuker() {
        this.shouldTorchNuker = !this.shouldTorchNuker();
        this.sendChatToggleMessage("TorchNuker", this.shouldTorchNuker());
    }

    public void toggleXray() {
        this.shouldXray = !this.shouldXray();
        this.fullbrightCleanUp();
        this.sendChatToggleMessage("Xray", this.shouldXray());
    }

    private int flightSpeed = 5;

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

    private int auraRange = 7;

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

    private boolean shouldTracersPortals = false;

    public boolean shouldTracersPortals() {
        return this.shouldTracersPortals;
    }

    public void setShouldTracersPortals(boolean shouldTracersPortals) {
        this.shouldTracersPortals = shouldTracersPortals;
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

    //Check to see if we should attack an entity with KillAura.
    private boolean shouldAttackEntity(Entity entity) {

        //Do not attack dead entities
        if (!entity.isEntityAlive()) {
            return false;
        }

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

    //Diamonds only mode for Xray.
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

    private int torchNukerRange = 5;

    public int getTorchNukerRange() {
        return this.torchNukerRange;
    }

    public void setTorchNukerRange(int torchNukerRange) {
        if (torchNukerRange < 1) {
            torchNukerRange = 1;
        }

        if (torchNukerRange > 10) {
            torchNukerRange = 10;
        }

        this.torchNukerRange = torchNukerRange;
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

    //Pages for gui... it's getting pretty long
    //4 pages for now
    private int guiPage = 1;

    public int getGuiPage() {
        return this.guiPage;
    }

    public void setGuiPage(int newGuiPage) {
        if (newGuiPage < 1) {
            newGuiPage = 1;
        }

        if (newGuiPage > 4) {
            newGuiPage = 4;
        }

        this.guiPage = newGuiPage;
    }

    //@see GameSettingsMixin
    //Can change this eventually to a dedicated keybind manager... It doesn't look like we have to add these keybinds to the mixin for them to work.
    public final KeyBinding keyBindingESP = new KeyBinding("key.ESP", Keyboard.KEY_M);
    public final KeyBinding keyBindingFastBreak = new KeyBinding("key.fastBreak", Keyboard.KEY_H);
    public final KeyBinding keyBindingFly = new KeyBinding("key.fly", Keyboard.KEY_G);
    public final KeyBinding keyBindingNoFall = new KeyBinding("key.noFall", Keyboard.KEY_L);
    public final KeyBinding keyBindingNoExhaustion = new KeyBinding("key.noExhaustion", Keyboard.KEY_K);
    public final KeyBinding keyBindingJesus = new KeyBinding("key.jesus", Keyboard.KEY_J);
    public final KeyBinding keyBindingKillAura = new KeyBinding("key.killAura", Keyboard.KEY_R);
    public final KeyBinding keyBindingFullbright = new KeyBinding("key.fullbright", Keyboard.KEY_B);
    public final KeyBinding keyBindingNoWeather = new KeyBinding("key.noWeather", Keyboard.KEY_N);
    public final KeyBinding keyBindingTracers = new KeyBinding("key.tracers", Keyboard.KEY_COMMA);
    public final KeyBinding keyBindingTorchNuker = new KeyBinding("key.torchNuker", Keyboard.KEY_U);
    public final KeyBinding keyBindingXray = new KeyBinding("key.xray", Keyboard.KEY_X);
    public final KeyBinding keyBindingPageLeft = new KeyBinding("key.pageLeft", Keyboard.KEY_LEFT);
    public final KeyBinding keyBindingPageRight = new KeyBinding("key.pageRight", Keyboard.KEY_RIGHT);

    private int torchNukerDelay = 0;

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
            boolean isInAcid = Minecraft.getInstance().thePlayer.isInsideOfMaterial(Materials.ACID);
            boolean isInSanguis = Minecraft.getInstance().thePlayer.isInsideOfMaterial(Materials.SANGUIS);

            if (Minecraft.getInstance().thePlayer.isInWater() || isInLava || isInSanguis || isInAcid) {

                if (Minecraft.getInstance().currentScreen == null && Keyboard.isKeyDown(Minecraft.getInstance().gameSettings.keyBindSneak.keyCode)) {
                    return;
                }

                Minecraft.getInstance().thePlayer.motionY = (isInLava || isInAcid || isInSanguis) ? 1.2d : 0.30d;
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

        //TorchNuker
        if (this.shouldTorchNuker()) {

            //Have a delay of about 5 ticks
            if (this.torchNukerDelay < 5) {
                torchNukerDelay++;
                return;
            }

            //reset delay even if we don't break a torch to prevent this setting from getting stuck
            this.torchNukerDelay = 0;

            int eX = (int) Minecraft.getInstance().thePlayer.posX;
            int eY = (int) Minecraft.getInstance().thePlayer.posY;
            int eZ = (int) Minecraft.getInstance().thePlayer.posZ;

            double torchNukerRangeSquared = Math.pow(this.getTorchNukerRange(), this.getTorchNukerRange());

            ArrayList<Vector4f> torchCoordinates = new ArrayList<>();

            ArrayList<Integer> torchTypes = this.getTorchTypes();

            //get a list of coordinates within the range of the torch nuker.
            for (int x = -this.getTorchNukerRange(); x <= this.getTorchNukerRange(); x++) {
                for (int y = -this.getTorchNukerRange(); y <= this.getTorchNukerRange(); y++) {
                    for (int z = -this.getTorchNukerRange(); z<= this.getTorchNukerRange(); z++) {

                        double distanceSquared = Math.pow(x, x) + Math.pow(y, y) + Math.pow(z, z);

                        if (torchNukerRangeSquared < distanceSquared) {
                            continue;
                        }

                        int newX = eX + x;
                        int newY = eY + y;
                        int newZ = eZ + z;

                        int blockID = Minecraft.getInstance().theWorld.getBlockId(newX, newY, newZ);

                        if (torchTypes.contains(blockID)) {

                            //Save the squared distance for later to sort the list
                            Vector4f vector4f = new Vector4f(newX, newY, newZ, (float) distanceSquared);

                            torchCoordinates.add(vector4f);
                        }
                    }
                }
            }

            if (!torchCoordinates.isEmpty()) {

                //find the closest torch
                Vector4f vector4fX = torchCoordinates.stream().min(Comparator.comparing(vector4f -> vector4f.w)).orElse(null);

                //Crashes in singleplayer without this
                if (!Minecraft.getInstance().theWorld.isRemote) {
                    return;
                }

                //break torch
                Minecraft.getInstance().getSendQueue().addToSendQueue(new Packet14BlockDig(0, (int) vector4fX.x, (int) vector4fX.y, (int) vector4fX.z, 0));
                Minecraft.getInstance().getSendQueue().addToSendQueue(new Packet14BlockDig(2, (int) vector4fX.x, (int) vector4fX.y, (int) vector4fX.z, 0));
            }
        }
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

        if (entityPlayerSP.isInWater() || entityPlayerSP.isInsideOfMaterial(Materials.LAVA) || entityPlayerSP.isInsideOfMaterial(Materials.ACID) ||  entityPlayerSP.isInsideOfMaterial(Materials.SANGUIS)) {
            return false;
        }

        Material material = world.getBlockMaterial(x, y, z);

        if (material == Materials.LAVA || material == Materials.ACID || material == Materials.SANGUIS) {
            return true;
        }

        if (minecraft.currentScreen == null && Keyboard.isKeyDown(minecraft.gameSettings.keyBindSneak.keyCode)) {
            return false;
        }

        return !(entityPlayerSP.fallDistance >= 3);
    }

    //Returns all of the different types of torches in ReIndev.
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

        //TorchNuker
        if (keyBindingTorchNuker.isPressed()) {
            this.toggleTorchNuker();
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

                    if (entry0.equals("PortalsT")) {
                        this.setShouldTracersPortals(Boolean.parseBoolean(entry1));
                    }

                    if (entry0.equals("TorchNuker") && Boolean.parseBoolean(entry1)) {
                        this.toggleTorchNuker();
                    }

                    if (entry0.equals("RangeTN")) {
                        this.setTorchNukerRange(Integer.parseInt(entry1));
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
            printWriter.println("PortalsT:" + this.shouldTracersPortals());
            printWriter.println("TorchNuker:" + this.shouldTorchNuker());
            printWriter.println("RangeTN:" + this.getTorchNukerRange());
            printWriter.println("Xray:" + this.shouldXray());
            printWriter.println("DiamondsOnlyX:" + this.isDiamondsOnly());
            printWriter.println("GUIPage:" + this.getGuiPage());
            printWriter.println("ModuleToggleMSGs:" + this.shouldSendToggleMessages());

            printWriter.close();

        } catch (Exception ignored) {

        }
    }
}
