package dev.hoosiers.dilation.feature.hacks;

import dev.hoosiers.dilation.backend.events.GuiChatEvent;
import dev.hoosiers.dilation.imp.Category;
import dev.hoosiers.dilation.imp.Command;
import dev.hoosiers.dilation.imp.Hack;
import dev.hoosiers.dilation.imp.event.EventTarget;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author Hoosiers
 * @since 09-03-2021
 */

public final class CommandPreview extends Hack {

    public CommandPreview() {
        super("CommandPreview", Category.Client, Keyboard.KEY_NONE, true, false, true);
    }

    @SuppressWarnings("unused")
    @EventTarget
    public void onGuiChatEvent(GuiChatEvent guiChatEvent) {
        this.render(guiChatEvent.message, guiChatEvent.x1, guiChatEvent.x2, guiChatEvent.y1, guiChatEvent.y2);
    }

    private final int outlineColor = new Color(255, 255, 0, 240).getRGB();
    private final int fillColor = new Color(50, 50, 50, 220).getRGB();
    private final int textColor = Color.WHITE.getRGB();

    private void render(String message, int x1, int x2, int y1, int y2) {

        Gui.drawRect(x1, y1 -1, x2, y1, this.outlineColor);
        Gui.drawRect(x1, y2, x2, y2 + 1, this.outlineColor);
        Gui.drawRect(x1 -1, y1 -1, x1, y2 + 1, this.outlineColor);
        Gui.drawRect(x2, y1 -1, x2 + 1, y2 + 1, outlineColor);

        y1 -= 22;
        y2 -= 22;

        String parsedMessage = message.replaceFirst(this.getCommandManager().PREFIX, "");

        if (parsedMessage.equalsIgnoreCase("")) return;

        String[] words = parsedMessage.split(" ");

        if (words.length < 1) return;

        Command command = this.getCommandManager().getCommand(words[0]);

        if (words.length == 1 && command == null) {
            ArrayList<Command> parsedCommands = this.getCommandManager().COMMANDS.stream().filter(command1 -> command1.name().toLowerCase().startsWith(parsedMessage)).collect(Collectors.toCollection(ArrayList::new));

            if (parsedCommands.isEmpty()) return;

            AtomicReference<String> commandList = new AtomicReference<>("");

            parsedCommands.forEach(command1 -> commandList.getAndSet(commandList.get() + command1.name() + ", "));

            if (commandList.get().length() > 2) {
                commandList.getAndSet(commandList.get().substring(0, commandList.get().length() - 2));
            }

            int startX = x1 + this.getFontRenderer().getStringWidth(this.getCommandManager().PREFIX);
            int startY = y1 - this.getFontRenderer().FONT_HEIGHT - 3;

            Gui.drawRect(startX - 2, startY - 2, startX + this.getFontRenderer().getStringWidth(commandList.get()) + 2, y1 - 1, this.fillColor);
            Gui.drawRect(startX - 3, startY - 3, startX + this.getFontRenderer().getStringWidth(commandList.get()) + 3, startY - 2, this.outlineColor);
            Gui.drawRect(startX - 3, startY + this.getFontRenderer().FONT_HEIGHT + 1, startX + this.getFontRenderer().getStringWidth(commandList.get()) + 3, startY + this.getFontRenderer().FONT_HEIGHT + 2, this.outlineColor);
            Gui.drawRect(startX - 3, startY - 2, startX - 2, y1 - 1, this.outlineColor);
            Gui.drawRect(startX + this.getFontRenderer().getStringWidth(commandList.get()) + 2, startY - 2, startX + this.getFontRenderer().getStringWidth(commandList.get()) + 3, y1 - 1, this.outlineColor);

            this.getGuiIngame().drawString(this.getFontRenderer(), commandList.get(), startX, startY, this.textColor);

        } else if (command != null) {

            int startX = x1 + this.getFontRenderer().getStringWidth(this.getCommandManager().PREFIX);
            int startY = y1 - this.getFontRenderer().FONT_HEIGHT - 3;

            Gui.drawRect(startX - 2, startY - 2, startX + this.getFontRenderer().getStringWidth(command.description()) + 2, y1 - 1, this.fillColor);
            Gui.drawRect(startX - 3, startY - 3, startX + this.getFontRenderer().getStringWidth(command.description()) + 3, startY - 2, this.outlineColor);
            Gui.drawRect(startX - 3, startY + this.getFontRenderer().FONT_HEIGHT + 1, startX + this.getFontRenderer().getStringWidth(command.description()) + 3, startY + this.getFontRenderer().FONT_HEIGHT + 2, this.outlineColor);
            Gui.drawRect(startX - 3, startY - 2, startX - 2, y1 - 1, this.outlineColor);
            Gui.drawRect(startX + this.getFontRenderer().getStringWidth(command.description()) + 2, startY - 2, startX + this.getFontRenderer().getStringWidth(command.description()) + 3, y1 - 1, this.outlineColor);

            this.getGuiIngame().drawString(this.getFontRenderer(), command.description(), startX, startY, this.textColor);
        }
    }
}
