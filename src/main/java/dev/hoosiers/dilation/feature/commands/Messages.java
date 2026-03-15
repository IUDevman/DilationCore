package dev.hoosiers.dilation.feature.commands;

import dev.hoosiers.dilation.imp.Command;
import dev.hoosiers.dilation.imp.Hack;

/**
 * @author Hoosiers
 * @since 03-14-2026
 */

public final class Messages implements Command {

    @Override
    public String name() {
        return "Messages";
    }

    @Override
    public String[] aliases() {
        return new String[]{
                this.name(),
                "Message",
                "ShowMessages",
                "ToggleMessages"
        };
    }

    @Override
    public String description() {
        return "[Module]; Toggles if a message will be sent in chat when a module is toggled.";
    }

    @Override
    public void onCommand(String[] args) {

        if (args.length <= 1) {
            this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to toggle messages for hack (no name inputted)!");
            return;
        }

        String hackName = args[1];

        if (hackName == null) {
            this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to toggle messages for hack (no name inputted)!");
            return;
        }

        Hack hack = this.getHackManager().getHack(hackName);

        if (hack != null) {
            hack.MESSAGES = !hack.MESSAGES;
            this.sendClientMessageWithPrefix("Hack:" + " " + hack.NAME + " messages set to [§b" + (hack.MESSAGES ? "§aTrue" : "§cFalse") + "§f]!!");
            return;
        }

        this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to toggle messages for hack (no hack found for name)!");
    }
}
