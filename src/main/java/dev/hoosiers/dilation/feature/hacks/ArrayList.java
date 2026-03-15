package dev.hoosiers.dilation.feature.hacks;

import dev.hoosiers.dilation.backend.events.DrawGuiScreenEvent;
import dev.hoosiers.dilation.imp.Category;
import dev.hoosiers.dilation.imp.Hack;
import dev.hoosiers.dilation.imp.event.EventTarget;
import dev.hoosiers.dilation.imp.settings.EnumSetting;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.awt.*;

/**
 * @author Hoosiers
 * @since 03-15-2026
 */

public final class ArrayList extends Hack {

    public EnumSetting direction = new EnumSetting("Direction", Direction.Right);

    public ArrayList() {
        super("ArrayList", Category.Client, Keyboard.KEY_P, true, false, true);
    }

    @SuppressWarnings("unused")
    @EventTarget
    public void onDrawGuiScreenEvent(DrawGuiScreenEvent drawGuiScreenEvent) {

        this.renderArrayList();
    }

    private void renderArrayList() {

        java.util.ArrayList<Hack> enabledHacks = new java.util.ArrayList<>();

        this.getHackManager().HACKS.forEach(hack -> {
            if (hack.DRAWN && hack.isEnabled()) {

                enabledHacks.add(hack);
            }
        });

        if (enabledHacks.isEmpty()) {
            return;
        }

        for (int i = 0; i < enabledHacks.size(); i++) {

            Hack hack =  enabledHacks.get(i);

            String hackName = hack.NAME;
            Color hackColor = this.getHackColorFromCategory(hack.CATEGORY);

            int directionAdjust = (direction.getValue().equals(Direction.Left) ? 60 : this.getFontRenderer().getStringWidth(hackName));

            this.getGuiIngame().drawString(this.getFontRenderer(), hackName, ScaledResolution.instance.getScaledWidth() - directionAdjust - 2, 2 + (10 * i), hackColor.getRGB());
        }
    }

    private Color getHackColorFromCategory(Category category) {
        switch (category)  {
            case Client:
                return Color.GREEN;
            case Player:
                return Color.CYAN;
            case Movement:
                return Color.YELLOW;
            case Render:
                return Color.RED;
            default:
                return Color.WHITE;
        }
    }

    private enum Direction {
        Left,
        Right
    }
}
