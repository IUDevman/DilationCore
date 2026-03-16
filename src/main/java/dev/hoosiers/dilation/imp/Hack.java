package dev.hoosiers.dilation.imp;

import net.minecraft.client.util.KeyBinding;

/**
 * @author Hoosiers
 * @since 03-10-2026
 */

public abstract class Hack implements LinkedMethods {

    public final String NAME;
    public final Category CATEGORY;

    private boolean ENABLED;
    public boolean DRAWN;
    public boolean MESSAGES;

    private KeyBinding BIND_KEY;

    public Hack(String NAME, Category CATEGORY, int BIND, boolean ENABLED, boolean DRAWN, boolean MESSAGES) {
        this.NAME = NAME;
        this.CATEGORY = CATEGORY;

        this.BIND_KEY = new KeyBinding("key." + this.NAME.toLowerCase(), BIND);

        this.DRAWN = DRAWN;
        this.MESSAGES = MESSAGES;

        this.setEnabled(ENABLED);
    }

    public KeyBinding getBind() {
        return this.BIND_KEY;
    }

    public void setBind(int newBind) {
        this.BIND_KEY = new KeyBinding("key." + this.NAME.toLowerCase(), newBind);
    }

    public void setEnabled(boolean ENABLED) {
        if (ENABLED)  {
            this.enable();
            return;
        }

        this.disable();
    }

    public boolean isEnabled() {
        return this.ENABLED;
    }

    public void toggle() {
        setEnabled(!this.ENABLED);
    }

    protected void enable() {
        this.ENABLED = true;
        this.getEventHandler().register(this);

        if (!this.failsNullCheck()) {
            onEnable();
            if (this.MESSAGES) {
                this.sendClientMessageWithPrefix("Hack:" +
                        " " + this.NAME + " §aEnabled§f!");
            }
        }
    }

    protected void disable() {
        this.ENABLED = false;
        this.getEventHandler().unregister(this);

        if (!this.failsNullCheck()) {
            onDisable();

            if (this.MESSAGES) {
                this.sendClientMessageWithPrefix("Hack: " + this.NAME + " §cDisabled§f!");
            }
        }
    }

    protected void onEnable() {

    }

    protected void onDisable() {

    }

    public void onTick() {

    }
}
