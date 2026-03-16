package dev.hoosiers.dilation.feature.commands;

import dev.hoosiers.dilation.imp.Command;
import net.minecraft.common.networking.Packet3Chat;

/**
 * @author Hoosiers
 * @since 03-15-2026
 */

public final class Spammer implements Command {

    @Override
    public String name() {
        return "Spammer";
    }

    @Override
    public String[] aliases() {
        return new String[]{
                this.name(),
                "Spam"
        };
    }

    @Override
    public String description() {
        return "[INT_AMOUNT] [spam_message]; Spams the desired method into chat for the number of times.";
    }

    @Override
    public void onCommand(String[] args) {

        if (!this.getWorld().isRemote) {
            this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to send spam (in singleplayer)!");
            return;
        }

        if (args.length < 3) {

            if (args.length < 2) {
                this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to send spam (no amount inputted)!");
                return;
            }

            this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to send spam (no message inputted)!");
            return;
        }

        try {

            int amount = Integer.parseInt(args[1]);

            String message = args[2].replace("_", " ");

            for (int i = 0; i < amount; i++) {
                this.sendPacket(new Packet3Chat(message));
            }

        } catch (Exception ignored) {
            this.sendClientMessageWithPrefix("§c" + this.name() + ": Failed to send spam (unknown error)!");
        }
    }
}
