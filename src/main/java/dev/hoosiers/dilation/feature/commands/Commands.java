package dev.hoosiers.dilation.feature.commands;

import dev.hoosiers.dilation.imp.Command;

/**
 * @author Hoosiers
 * @since 03-10-2026
 */

public final class Commands implements Command {

    @Override
    public String name() {
        return "Commands";
    }

    @Override
    public String[] aliases() {
        return new String[]{
                this.name(),
                "CommandsList",
                "CommandList",
                "Command"
        };
    }

    @Override
    public String description() {
        return "Returns the list of available commands.";
    }

    @Override
    public void onCommand(String[] args) {

        this.sendClientMessageWithPrefix("List of Commands:");

        this.getCommandManager().COMMANDS.forEach(command -> {
            String commandMessage = command.name() + ": " + this.description();

            this.sendClientMessageWithPrefix(commandMessage);
        });
    }
}
