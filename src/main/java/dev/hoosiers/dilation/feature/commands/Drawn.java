package dev.hoosiers.dilation.feature.commands;

import dev.hoosiers.dilation.imp.Command;
import dev.hoosiers.dilation.imp.Hack;

/**
 * @author Hoosiers
 * @since 03-14-2026
 */

public final class Drawn implements Command {

    @Override
    public String name() {
        return "Drawn";
    }

    @Override
    public String[] aliases() {
        return new String[]{
                this.name(),
                "Draw",
                "Shown",
                "Show",
                "ArrayList"
        };
    }

    @Override
    public String description() {
        return "[HACK]; Toggles if a hack name will be drawn in the ArrayList.";
    }

    @Override
    public void onCommand(String[] args) {

        if (args.length <= 1) {
            this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to toggle drawn for hack (no name inputted)!");
            return;
        }

        String hackName = args[1];

        if (hackName == null) {
            this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to toggle drawn for hack (no name inputted)!");
            return;
        }

        Hack hack = this.getHackManager().getHack(hackName);

        if (hack != null) {
            hack.DRAWN = !hack.DRAWN;
            this.sendClientMessageWithPrefix("Hack:" + " " + hack.NAME + " drawn set to [§b" + (hack.DRAWN ? "§aTrue" : "§cFalse") + "§f]!!");
            return;
        }

        this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to toggle drawn for hack (no hack found for name)!");
    }
}
