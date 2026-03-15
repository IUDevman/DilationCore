package dev.hoosiers.dilation.old;

/**
 * @author Hoosiers
 * @since 02-20-2026
 */

public final class DilationCore {

    /*

    public final KeyBinding keyBindingPageLeft = new KeyBinding("key.pageLeft", Keyboard.KEY_LEFT);
    public final KeyBinding keyBindingPageRight = new KeyBinding("key.pageRight", Keyboard.KEY_RIGHT);

    // toggle modules from keybind
    private void triggerModuleFromKey() {


        //GUI Page
        if (this.keyBindingPageLeft.isPressed()) {
            this.setGuiPage(this.getGuiPage() - 1);
        } else if (this.keyBindingPageRight.isPressed()) {
            this.setGuiPage(this.getGuiPage() + 1);
        }
    }

    //returns config file.
    private File returnDilationCoreDirectory() {
        File minecraftDirectory = this.getMinecraft().getMinecraftDir();

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

                    if (entry0.equals("RangeKA")) {
                        this.setAuraRange(Integer.parseInt(entry1));
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

                    if (entry0.equals("Sneak") && Boolean.parseBoolean(entry1)) {
                        this.toggleSneak();
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

                    if (entry0.equals("Velocity") && Boolean.parseBoolean(entry1)) {
                        this.toggleVelocity();
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
            printWriter.println("RangeKA:" + this.getAuraRange());
            printWriter.println("PlayersKA:" + this.shouldAttackPlayers());
            printWriter.println("HostilesKA:" + this.shouldAttackHostiles());
            printWriter.println("AnimalsKA:" + this.shouldAttackAnimals());
            printWriter.println("NoExhaustion:" + this.shouldNoExhaustion());
            printWriter.println("NoFall:" + this.shouldNoFall());
            printWriter.println("NoWeather:" + this.shouldNoWeather());
            printWriter.println("Sneak:" + this.shouldSneak());
            printWriter.println("Tracers:" + this.shouldTracers());
            printWriter.println("PortalsT:" + this.shouldTracersPortals());
            printWriter.println("TorchNuker:" + this.shouldTorchNuker());
            printWriter.println("RangeTN:" + this.getTorchNukerRange());
            printWriter.println("Velocity:" + this.shouldVelocity());
            printWriter.println("Xray:" + this.shouldXray());
            printWriter.println("DiamondsOnlyX:" + this.isDiamondsOnly());
            printWriter.println("GUIPage:" + this.getGuiPage());
            printWriter.println("ModuleToggleMSGs:" + this.shouldSendToggleMessages());

            printWriter.close();

        } catch (Exception ignored) {

        }
    }

     */
}
