package dev.hoosiers.dilation.feature.managers;

import dev.hoosiers.dilation.feature.commands.*;
import dev.hoosiers.dilation.imp.Command;
import dev.hoosiers.dilation.imp.Manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

/**
 * @author Hoosiers
 * @since 03-10-2026
 */

public final class CommandManager implements Manager {

    public final ArrayList<Command> COMMANDS = new ArrayList<>();

    public String PREFIX = "-";

    @Override
    public String name() {
        return "CommandManager";
    }

    @Override
    public void load(Logger logger) {
        logger.info(this::name);

        this.COMMANDS.add(new Bind());
        this.COMMANDS.add(new ClearChat());
        this.COMMANDS.add(new Commands());
        this.COMMANDS.add(new Drawn());
        this.COMMANDS.add(new Hacks());
        this.COMMANDS.add(new Messages());
        this.COMMANDS.add(new Prefix());
        this.COMMANDS.add(new Set());
        this.COMMANDS.add(new Settings());
        this.COMMANDS.add(new Spammer());
        this.COMMANDS.add(new Toggle());
    }

    public Command getCommand(String name) {
        return this.COMMANDS.stream().filter(command -> command.name().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public boolean dispatchCommands(String args) {

        String[] message = args.split(" ");

        String commandMessage = message[0];

        if (commandMessage == null) {

            return false;
        }

        if (commandMessage == this.PREFIX) {
            if (this.getHackManager().getHack(dev.hoosiers.dilation.feature.hacks.Commands.class).isEnabled()) {
                this.sendClientMessageWithPrefix("§c" + this.name() + ": Unknown command!");
            }

            return true;
        }

        if (commandMessage.startsWith(this.PREFIX)) {

            AtomicBoolean hasDispatchedMessage = new AtomicBoolean(false);

            if (this.getHackManager().getHack(dev.hoosiers.dilation.feature.hacks.Commands.class).isEnabled()) {

                this.COMMANDS.forEach(command -> Arrays.stream(command.aliases()).forEach(alias -> {
                    if (commandMessage.equalsIgnoreCase(this.PREFIX + alias)) {
                        command.onCommand(message);
                        hasDispatchedMessage.set(true);
                    }
                }));

                if (!hasDispatchedMessage.get()) {
                    this.sendClientMessageWithPrefix("§c" + this.name() + ": Unknown command!");
                }
            }

            return true;
        }

        return false;
    }
}
