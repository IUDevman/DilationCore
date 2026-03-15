package dev.hoosiers.dilation.feature.commands;

import dev.hoosiers.dilation.imp.Command;

import java.util.ArrayList;

/**
 * @author Hoosiers
 * @since 03-15-2026
 */

public final class Hacks implements Command {

    @Override
    public String name() {
        return "Hacks";
    }

    @Override
    public String[] aliases() {
        return new String[]{
                this.name(),
                "Hack",
                "HackList",
                "HacksList"
        };
    }

    @Override
    public String description() {
        return "Returns the list of available hacks (§aEnabled§f/§cDisabled§f).";
    }

    @Override
    public void onCommand(String[] args) {

        String hackListString = "Hacks [§b" + this.getHackManager().HACKS.size() + "§f]: ";

        ArrayList<String> hacks = new ArrayList<>();

        this.getHackManager().HACKS.forEach(hack -> hacks.add((hack.isEnabled() ? "§a" : "§c") + hack.NAME + "§f"));

        this.sendClientMessageWithPrefix(hackListString + hacks);
    }
}
