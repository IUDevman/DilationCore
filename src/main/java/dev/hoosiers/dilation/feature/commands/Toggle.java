package dev.hoosiers.dilation.feature.commands;

import dev.hoosiers.dilation.imp.Command;
import dev.hoosiers.dilation.imp.Hack;

/**
 * @author Hoosiers
 * @since 03-10-2026
 */

public class Toggle implements Command {

    @Override
    public String name() {
        return "Toggle";
    }

    @Override
    public String[] aliases() {
        return new String[]{
                this.name(),
                "ToggleHacks",
                "ToggleHack",
                "enable",
                "disable"
        };
    }

    @Override
    public String description() {
        return "[HACK]; Toggles indicated hack.";
    }

    @Override
    public void onCommand(String[] args) {

        if (args.length <= 1) {
            this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to toggle hack (no name inputted)!");
            return;
        }

        String hackName = args[1];

        if (hackName == null) {
            this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to toggle hack (hack not found)!");
            return;
        }

        Hack hack = this.getHackManager().getHack(hackName);

        if (hack != null) {

            if (hack.NAME.equalsIgnoreCase("Commands")) {
                this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to toggle Commands hack (cannot execute)!");
                return;
            }

            hack.toggle();
            return;
        }

        this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to toggle hack (no hack found for name)!");
    }
}
