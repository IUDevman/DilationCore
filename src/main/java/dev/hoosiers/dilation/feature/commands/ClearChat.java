package dev.hoosiers.dilation.feature.commands;

import dev.hoosiers.dilation.imp.Command;

/**
 * @author Hoosiers
 * @since 03-16-2026
 */

public final class ClearChat implements Command {

    @Override
    public String name() {
        return "ClearChat";
    }

    @Override
    public String[] aliases() {
        return new String[]{
                this.name(),
                "Clear"
        };
    }

    @Override
    public String description() {
        return "Clears the chat.";
    }

    @Override
    public void onCommand(String[] args) {
        this.getMinecraft().ingameGUI.clearChatMessages();
    }
}
