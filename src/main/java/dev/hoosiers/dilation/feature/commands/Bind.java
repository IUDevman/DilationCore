package dev.hoosiers.dilation.feature.commands;

import dev.hoosiers.dilation.imp.Command;
import dev.hoosiers.dilation.imp.Hack;
import org.lwjgl.input.Keyboard;

/**
 * @author Hoosiers
 * @since 03-14-2026
 */

public final class Bind implements Command {
    @Override
    public String name() {
        return "Bind";
    }

    @Override
    public String[] aliases() {
        return new String[]{
                this.name(),
                "SetBind",
                "Key",
                "Keybind"
        };
    }

    @Override
    public String description() {
        return "[HACK] [KEY]; Sets the bind to toggle the specified hack.";
    }

    @Override
    public void onCommand(String[] args) {

        if (args.length <= 1) {
            this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to apply bind to hack (no name inputted)!");
            return;
        }

        String hackName = args[1];

        if (hackName == null) {
            this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to apply bind to hack (no name inputted)!");
            return;
        }

        Hack hack = this.getHackManager().getHack(hackName);

        if (hack == null) {
            this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to apply bind to hack (no hack found for name)!");
            return;
        }

        String newBind = args[2];

        if (newBind == null) {
            this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to apply bind to hack (no new bind inputted)!");
            return;
        }

        if (newBind.equalsIgnoreCase("None") || newBind.equalsIgnoreCase("N/A")) {
            hack.setBind(Keyboard.KEY_NONE);
            this.sendClientMessageWithPrefix("Hack:" + " " + hack.NAME + " bind set to [§bN/A§f]!!");
            return;
        }

        int bind = Keyboard.getKeyIndex(newBind.toUpperCase());

        if (bind != Keyboard.KEY_NONE) {

            Hack alreadyBoundHack = this.getHackManager().HACKS.stream().filter(hack2 -> hack2.getBind().keyCode == bind).findFirst().orElse(null);

            if (alreadyBoundHack != null) {
                this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to apply bind to hack (key already bound to " + alreadyBoundHack.NAME + ")!");
                return;
            }
        }

        hack.setBind(bind);
        this.sendClientMessageWithPrefix("Hack:" + " " + hack.NAME + " bind set to [§b" + Keyboard.getKeyName(hack.getBind().keyCode)+ "§f]!!");

    }
}
