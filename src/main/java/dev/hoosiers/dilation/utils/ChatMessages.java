package dev.hoosiers.dilation.utils;

import dev.hoosiers.dilation.DilationCore;
import net.minecraft.client.Minecraft;

/**
 * @author Hoosiers
 * @since 03-02-2026
 */

public final class ChatMessages {

    private static final String clientPrefix = "[§bDilation§9Core§f]";

    //sends a chat message when modules are toggled
    public static void sendChatToggleMessage(DilationCore dilationCore, String module, boolean enabled) {
        if (dilationCore.failsNullCheck()) {
            return;
        }

        if (!dilationCore.shouldSendToggleMessages()) {
            return;
        }

        String message = "";

        if (enabled) {
            message = clientPrefix + " Module " + module + " §aEnabled§f!";
        } else {
            message = clientPrefix + " Module " + module + " §cDisabled§f!";
        }

        Minecraft.getInstance().thePlayer.addChatMessage(message);
    }

    //sends a chat message when commands are sent
    public static boolean handleCommandChatMessage(DilationCore dilationCore, String message) {

        //toggle module messages
        if (message.equals("-tm")) {
            dilationCore.setShouldSendToggleMessages(!dilationCore.shouldSendToggleMessages());
            sendToggleMessages(dilationCore.shouldSendToggleMessages());
            return true;
        }

        //secret diamonds only mode for Xray
        if (message.equals("-xdo")) {
            dilationCore.setDiamondsOnly(!dilationCore.isDiamondsOnly());
            sendXrayDiamondToggleMessage(dilationCore.isDiamondsOnly());
            return true;
        }

        if (message.equals("-nofall") || message.equals("-nf")) {
            dilationCore.toggleNoFall();
            return true;
        }

        if (message.equals("-fullbright") || message.equals("-fb") || message.equals("-bright")) {
            dilationCore.toggleFullbright();
            return true;
        }

        if (message.equals("-noexhaustion") || message.equals("-noex") || message.equals("-ne")) {
            dilationCore.toggleNoExhaustion();
            return true;
        }

        if (message.equals("-jesus") || message.equals("-j")) {
            Minecraft.getInstance().thePlayer.addChatMessage("Jesus Test");
            dilationCore.toggleJesus();
            return true;
        }

        if (message.equals("-noweather") || message.equals("-nw")) {
            dilationCore.toggleNoWeather();
            return true;
        }

        if (message.equals("-esp") || message.equals("-e")) {
            dilationCore.toggleESP();
            return true;
        }

        if (message.equals("-fastbreak") || message.equals("-fab") || message.equals("-break")) {
            dilationCore.toggleFastBreak();
            return true;
        }

        if (message.equals("-xray") || message.equals("-x")) {
            dilationCore.toggleXray();
            return true;
        }

        //put this last as it has statements that can override fullbright.
        String[] messages = message.split(" ");

        messages[0] = messages[0].toLowerCase();

        if (messages[0].equals("-torchnuker") || messages[0].equals("-tn")) {

            if (messages.length == 1) {
                dilationCore.toggleTorchNuker();
                return true;
            }

            String value = messages[1];

            if (value == null) {
                return true;
            }

            try {

                int value1 = Integer.parseInt(value);

                dilationCore.setTorchNukerRange(value1);
                sendTorchNukerRangeToggleMessage(dilationCore.getTorchNukerRange(), 1, 10);

                return true;

            } catch (Exception ignored) {
                return true;
            }
        }

        if (messages[0].equals("-tracer") || messages[0].equals("-tracers") || messages[0].equals("-t")) {

            if (messages.length == 1) {
                dilationCore.toggleTracers();
                return true;
            }

            String value = messages[1].toLowerCase();

            if (value.equals("portals") || value.equals("portal")) {
                boolean shouldTracersPortalsNew = !dilationCore.shouldTracersPortals();

                dilationCore.setShouldTracersPortals(shouldTracersPortalsNew);
                sendTracersToggleMessage(shouldTracersPortalsNew);
                return true;
            }
        }

        if (messages[0].equals("-killaura") || messages[0].equals("-aura") || messages[0].equals("-ka") || messages[0].equals("-k")) {

            if (messages.length == 1) {
                dilationCore.toggleKillAura();
                return true;
            }


            String value = messages[1].toLowerCase();

            try {

                int value1 = Integer.parseInt(value);

                dilationCore.setAuraRange(value1);
                sendKillAuraRangeToggleMessage(dilationCore.getAuraRange(), 0, 10);

                return true;

            } catch (Exception ignored) {

            }

            if (value.contains("p")) {
                dilationCore.setAttackPlayers(!dilationCore.shouldAttackPlayers());
                sendKillAuraToggleMessage("Players", dilationCore.shouldAttackPlayers(), dilationCore.shouldAttackPlayers(), dilationCore.shouldAttackHostiles(), dilationCore.shouldAttackAnimals());
            }

            if (value.contains("h")) {
                dilationCore.setAttackHostiles(!dilationCore.shouldAttackHostiles());
                sendKillAuraToggleMessage("Hostiles", dilationCore.shouldAttackHostiles(), dilationCore.shouldAttackPlayers(), dilationCore.shouldAttackHostiles(), dilationCore.shouldAttackAnimals());
            }

            if (value.contains("a")) {
                dilationCore.setAttackAnimals(!dilationCore.shouldAttackAnimals());
                sendKillAuraToggleMessage("Animals", dilationCore.shouldAttackAnimals(), dilationCore.shouldAttackPlayers(), dilationCore.shouldAttackHostiles(), dilationCore.shouldAttackAnimals());
            }

            return true;
        }

        if (messages[0].equals("-fly") || messages[0].equals("-flight") || messages[0].equals("-f")) {

            if (messages.length > 1) {

                try {

                    String value = messages[1];

                    int value1 = Integer.parseInt(value);

                    dilationCore.setFlightSpeed(value1);
                    sendFlightSpeedToggleMessage(dilationCore.getFlightSpeed(), 3, 20);

                    return true;

                } catch (Exception ignored) {
                    return true;
                }
            }

            dilationCore.toggleFly();
            return true;
        }

        return false;
    }

    //sends message when module toggle messages is changed
    private static void sendToggleMessages(boolean toggleMessages) {
        String message = "";

        if (toggleMessages) {
            message = clientPrefix + " Set Module Toggle Messages To §aTrue§f!";
        } else {
            message = clientPrefix + " Set Module Toggle Messages To §cFalse§f!";
        }

        Minecraft.getInstance().thePlayer.addChatMessage(message);
    }

    //sends Xray Diamonds Only toggle message
    private static void sendXrayDiamondToggleMessage(boolean diamondsOnly) {
        String message = "";

        if (diamondsOnly) {
            message = clientPrefix + " Set Xray Diamonds Only Setting To §aTrue§f!";
        } else {
            message = clientPrefix + " Set Xray Diamonds Only Setting To §cFalse§f!";
        }

        Minecraft.getInstance().thePlayer.addChatMessage(message);
    }

    //sends TorchNuker message
    private static void sendTorchNukerRangeToggleMessage(int range, int min, int max) {
        String message = clientPrefix + " Set TorchNuker Range Setting To §c" + range + " §f(min = " + min + ", max = " + max + ").";

        Minecraft.getInstance().thePlayer.addChatMessage(message);
    }

    //sends Tracers message
    private static void sendTracersToggleMessage(boolean portals) {
        String message = "";

        if (portals) {
            message = clientPrefix + " Set Tracers Draw to Portals Setting To §aTrue§f!";
        } else {
            message = clientPrefix + " Set Tracers Draw to Portals Setting To §cFalse§f!";
        }

        Minecraft.getInstance().thePlayer.addChatMessage(message);
    }

    //sends KillAura toggle message
    private static void sendKillAuraToggleMessage(String param, boolean paramValue, boolean players, boolean hostiles, boolean passives) {
        String player = players ? "§aPlayers" : "§cPlayers";
        String hostile = hostiles ? "§aHostiles" : "§cHostiles";
        String passive = passives ? "§aPassive" : "§cPassive";

        String message = clientPrefix + " Set Killaura Setting " + param + " to §c" + paramValue + "§f(" + player + "§f/" + hostile + "§f/" + passive + "§f).";

        Minecraft.getInstance().thePlayer.addChatMessage(message);
    }

    //sends KillAura range toggle message
    private static void sendKillAuraRangeToggleMessage(int range, int min, int max) {
        String message = clientPrefix + " Set KillAura Range Setting To §c" + range + " §f(min = " + min + ", max = " + max + ").";

        Minecraft.getInstance().thePlayer.addChatMessage(message);
    }

    //sends Flight range toggle message
    private static void sendFlightSpeedToggleMessage(int speed, int min, int max) {
        String message = clientPrefix + " Set Flight Speed Setting To §c" + speed + " §f(min = " + min + ", max = " + max + ").";

        Minecraft.getInstance().thePlayer.addChatMessage(message);
    }
}
