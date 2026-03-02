package dev.hoosiers.dilation.mixins;


import dev.hoosiers.dilation.DilationCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.networking.NetClientHandler;
import net.minecraft.common.networking.Packet;
import net.minecraft.common.networking.Packet10Flying;
import net.minecraft.common.networking.Packet3Chat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Hoosiers
 * @since 02-20-2026
 */

@Mixin(value = NetClientHandler.class, priority = 6969)
public final class NetClientHandlerMixin {

    //use this inject to modify and examine sent packets
    @Inject(method = "addToSendQueue", at = @At("HEAD"), cancellable = true)
    public void addToSendQueue(Packet packet, CallbackInfo ci) {
        DilationCore dilationCore = DilationCore.getInstance();

        if (dilationCore == null || packet == null || dilationCore.failsNullCheck()) {
            return;
        }

        if (dilationCore.shouldNoFall() && packet instanceof Packet10Flying) {
            Packet10Flying packet10Flying = (Packet10Flying) packet;

            packet10Flying.onGround = true;
        }

        if (packet instanceof Packet3Chat) {
            Packet3Chat packet3Chat = (Packet3Chat) packet;

            if (packet3Chat.message == null) {
                return;
            }

            String message = packet3Chat.message.toLowerCase();

            //toggle module messages
            if (message.equals("-tm")) {
                dilationCore.setShouldSendToggleMessages(!dilationCore.shouldSendToggleMessages());
                dilationCore$sendToggleMessages(dilationCore.shouldSendToggleMessages());
                ci.cancel();
                return;
            }

            //secret diamonds only mode for Xray
            if (message.equals("-xdo")) {
                dilationCore.setDiamondsOnly(!dilationCore.isDiamondsOnly());
                dilationCore$sendXrayDiamondToggleMessage(dilationCore.isDiamondsOnly());
                ci.cancel();
                return;
            }

            if (message.equals("-nofall") || message.equals("-nf")) {
                dilationCore.toggleNoFall();
                ci.cancel();
                return;
            }

            if (message.equals("-fullbright") || message.equals("-fb") || message.equals("-bright")) {
                dilationCore.toggleFullbright();
                ci.cancel();
                return;
            }

            if (message.equals("-noexhaustion") || message.equals("-noex") || message.equals("-ne")) {
                dilationCore.toggleNoExhaustion();
                ci.cancel();
                return;
            }

            if (message.equals("-jesus") || message.equals("-j")) {
                Minecraft.getInstance().thePlayer.addChatMessage("Jesus Test");
                dilationCore.toggleJesus();
                ci.cancel();
                return;
            }

            if (message.equals("-noweather") || message.equals("-nw")) {
                dilationCore.toggleNoWeather();
                ci.cancel();
                return;
            }

            if (message.equals("-esp") || message.equals("-e")) {
                dilationCore.toggleESP();
                ci.cancel();
                return;
            }

            if (message.equals("-fastbreak") || message.equals("-fab") || message.equals("-break")) {
                dilationCore.toggleFastBreak();
                ci.cancel();
                return;
            }

            if (message.equals("-xray") || message.equals("-x")) {
                dilationCore.toggleXray();
                ci.cancel();
                return;
            }

            //put this last as it has statements that can override fullbright.
            String[] messages = message.split(" ");

            if (messages[0].equals("-tracer") || messages[0].equals("-tracers") || messages[0].equals("-t")) {

                if (messages.length == 1) {
                    dilationCore.toggleTracers();
                    ci.cancel();
                    return;
                }

                String value = messages[1].toLowerCase();

                if (value.equals("portals") || value.equals("portal")) {
                    boolean shouldTracersPortalsNew = !dilationCore.shouldTracersPortals();

                    dilationCore.setShouldTracersPortals(shouldTracersPortalsNew);
                    dilationCore$sendTracersToggleMessage(shouldTracersPortalsNew);
                    ci.cancel();
                    return;
                }
            }

            if (messages[0].equals("-killaura") || messages[0].equals("-aura") || messages[0].equals("-ka") || messages[0].equals("-k")) {

                if (messages.length == 1) {
                    dilationCore.toggleKillAura();
                    ci.cancel();
                    return;
                }


                String value = messages[1];

                if (value == null) {
                    ci.cancel();
                    return;
                }

                try {

                    int value1 = Integer.parseInt(value);

                    dilationCore.setAuraRange(value1);
                    dilationCore$sendKillAuraRangeToggleMessage(dilationCore.getAuraRange(), 0, 10);

                    ci.cancel();
                    return;

                } catch (Exception ignored) {

                }

                if (value.toLowerCase().contains("p")) {
                    dilationCore.setAttackPlayers(!dilationCore.shouldAttackPlayers());
                    dilationCore$sendKillAuraToggleMessage("Players", dilationCore.shouldAttackPlayers(), dilationCore.shouldAttackPlayers(), dilationCore.shouldAttackHostiles(), dilationCore.shouldAttackAnimals());
                }

                if (value.toLowerCase().contains("h")) {
                    dilationCore.setAttackHostiles(!dilationCore.shouldAttackHostiles());
                    dilationCore$sendKillAuraToggleMessage("Hostiles", dilationCore.shouldAttackHostiles(), dilationCore.shouldAttackPlayers(), dilationCore.shouldAttackHostiles(), dilationCore.shouldAttackAnimals());
                }

                if (value.toLowerCase().contains("a")) {
                    dilationCore.setAttackAnimals(!dilationCore.shouldAttackAnimals());
                    dilationCore$sendKillAuraToggleMessage("Animals", dilationCore.shouldAttackAnimals(), dilationCore.shouldAttackPlayers(), dilationCore.shouldAttackHostiles(), dilationCore.shouldAttackAnimals());
                }

                ci.cancel();
                return;
            }

            if (messages[0].equals("-fly") || messages[0].equals("-flight") || messages[0].equals("-f")) {

                if (messages.length > 1) {

                    try {
                        String value = messages[1];

                        if (value == null) {
                            ci.cancel();
                            return;
                        }

                        int value1 = Integer.parseInt(value);

                        dilationCore.setFlightSpeed(value1);
                        dilationCore$sendFlightSpeedToggleMessage(dilationCore.getFlightSpeed(), 3, 20);

                        ci.cancel();
                        return;

                    } catch (Exception ignored) {
                        ci.cancel();
                        return;
                    }
                }

                dilationCore.toggleFly();
                ci.cancel();
            }
        }
    }

    @Unique
    private void dilationCore$sendToggleMessages(boolean toggleMessages) {
        String message = "";

        if (toggleMessages) {
            message = "[§bDilation§9Core§f] Set Module Toggle Messages To §aTrue§f!";
        } else {
            message = "[§bDilation§9Core§f] Set Module Toggle Messages To §cFalse§f!";
        }

        Minecraft.getInstance().thePlayer.addChatMessage(message);
    }

    @Unique
    private void dilationCore$sendXrayDiamondToggleMessage(boolean diamondsOnly) {
        String message = "";

        if (diamondsOnly) {
            message = "[§bDilation§9Core§f] Set Xray Diamonds Only Setting To §aTrue§f!";
        } else {
            message = "[§bDilation§9Core§f] Set Xray Diamonds Only Setting To §cFalse§f!";
        }

        Minecraft.getInstance().thePlayer.addChatMessage(message);
    }

    @Unique
    private void dilationCore$sendTracersToggleMessage(boolean portals) {
        String message = "";

        if (portals) {
            message = "[§bDilation§9Core§f] Set Tracers Draw to Portals Setting To §aTrue§f!";
        } else {
            message = "[§bDilation§9Core§f] Set Tracers Draw to Portals Setting To §cFalse§f!";
        }

        Minecraft.getInstance().thePlayer.addChatMessage(message);
    }

    @Unique
    private void dilationCore$sendKillAuraToggleMessage(String param, boolean paramValue, boolean players, boolean hostiles, boolean passives) {
        String player = players ? "§aPlayers" : "§cPlayers";
        String hostile = hostiles ? "§aHostiles" : "§cHostiles";
        String passive = passives ? "§aPassive" : "§cPassive";

        String message = "[§bDilation§9Core§f] Set Killaura Setting " + param + " to §c" + paramValue + "§f(" + player + "§f/" + hostile + "§f/" + passive + "§f).";

        Minecraft.getInstance().thePlayer.addChatMessage(message);
    }

    @Unique
    private void dilationCore$sendKillAuraRangeToggleMessage(int range, int min, int max) {
        String message = "[§bDilation§9Core§f] Set KillAura Range Setting To §c" + range + " §f(min = " + min + ", max = " + max + ").";

        Minecraft.getInstance().thePlayer.addChatMessage(message);
    }

    @Unique
    private void dilationCore$sendFlightSpeedToggleMessage(int speed, int min, int max) {
        String message = "[§bDilation§9Core§f] Set Flight Speed Setting To §c" + speed + " §f(min = " + min + ", max = " + max + ").";

        Minecraft.getInstance().thePlayer.addChatMessage(message);
    }
}
