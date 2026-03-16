package dev.hoosiers.dilation.feature.commands;

import dev.hoosiers.dilation.imp.Command;

/**
 * @author Hoosiers
 * @since 03-14-2026
 */

public final class Prefix implements Command {

    @Override
    public String name() {
        return "Prefix";
    }

    @Override
    public String[] aliases() {
        return new String[]{
                this.name(),
                "SetPrefix",
        };
    }

    @Override
    public String description() {
        return "[CHAR]; Sets command prefix. Current: [§b" + this.getCommandManager().PREFIX + "§f].";
    }

    @Override
    public void onCommand(String[] args) {

        if (args.length <= 1) {
            this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to apply command prefix (no prefix inputted)!");
            return;
        }

        String newPrefix = args[1];

        if (newPrefix == null) {
            this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to apply command prefix (no prefix inputted)!");
            return;
        }

        if (newPrefix.length() > 1) {
            this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to apply command prefix (prefix longer than 1 character)!");
            return;
        }

        if (newPrefix.equalsIgnoreCase("") || newPrefix.equalsIgnoreCase(" ")) {
            this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to apply command prefix (invalid)!");
            return;
        }

        this.getCommandManager().PREFIX = newPrefix.toLowerCase();
        this.sendClientMessageWithPrefix("Set command prefix to [§b" + newPrefix.toLowerCase() + "§f].");
    }
}
