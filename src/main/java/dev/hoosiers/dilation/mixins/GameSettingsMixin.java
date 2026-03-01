package dev.hoosiers.dilation.mixins;

import com.fox2code.foxloader.client.KeyBindingAPI;
import dev.hoosiers.dilation.DilationCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.GameSettings;
import net.minecraft.client.util.KeyBinding;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Hoosiers
 * @since 02-22-2026
 */

@Mixin(value = GameSettings.class, priority = 6969)
public final class GameSettingsMixin {

    @Shadow public KeyBinding[] keyBindings;

    @Shadow @Final public KeyBinding keyBindAttack;

    @Shadow @Final public KeyBinding keyBindUseItem;

    @Shadow @Final public KeyBinding keyBindPickBlock;

    @Shadow @Final public KeyBinding keyBindForward;

    @Shadow @Final public KeyBinding keyBindLeft;

    @Shadow @Final public KeyBinding keyBindBack;

    @Shadow @Final public KeyBinding keyBindRight;

    @Shadow @Final public KeyBinding keyBindJump;

    @Shadow @Final public KeyBinding keyBindSneak;

    @Shadow @Final public KeyBinding keyBindSprint;

    @Shadow @Final public KeyBinding keyBindDrop;

    @Shadow @Final public KeyBinding keyBindInventory;

    @Shadow @Final public KeyBinding keyBindChat;

    @Shadow @Final public KeyBinding keyBindToggleFog;

    @Shadow @Final public KeyBinding keyBindZoom;

    @Shadow @Final public KeyBinding keyBindPlayerList;

    @Shadow @Final public KeyBinding keyBindNoClip;

    @Shadow @Final public KeyBinding keyBindEmotes;

    @Shadow @Final public KeyBinding keyBindPhotoMode;

    @Shadow @Final public KeyBinding keyBindCommand;

    @Shadow @Final public KeyBinding keyBindThirdPerson;

    @Shadow @Final public KeyBinding keyBindCinematicCamera;

    @Shadow @Final public KeyBinding keyBindToggleGamemode;

    //yes I know this is jank but fuck it.
    @Inject(method = "init", at = @At("TAIL"))
    public void init(Minecraft minecraft, CallbackInfo ci) {
        DilationCore dilationCore = DilationCore.getInstance();

        if (dilationCore == null) {
            return;
        }

        this.keyBindings = new KeyBinding[]{
                this.keyBindAttack,
                this.keyBindUseItem,
                this.keyBindPickBlock,
                this.keyBindForward,
                this.keyBindLeft,
                this.keyBindBack,
                this.keyBindRight,
                this.keyBindJump,
                this.keyBindSneak,
                this.keyBindSprint,
                this.keyBindDrop,
                this.keyBindInventory,
                this.keyBindChat,
                this.keyBindToggleFog,
                this.keyBindZoom,
                this.keyBindPlayerList,
                this.keyBindNoClip,
                this.keyBindEmotes,
                this.keyBindPhotoMode,
                this.keyBindCommand,
                this.keyBindThirdPerson,
                this.keyBindCinematicCamera,
                this.keyBindToggleGamemode,
                dilationCore.keyBindingFly,
                dilationCore.keyBindingJesus,
                dilationCore.keyBindingNoExhaustion,
                dilationCore.keyBindingFullbright,
                dilationCore.keyBindingNoFall,
                dilationCore.keyBindingESP,
                dilationCore.keyBindingNoWeather,
                dilationCore.keyBindingFastBreak,
                dilationCore.keyBindingXray,
                dilationCore.keyBindingTracers,
                dilationCore.keyBindingKillAura,
                dilationCore.keyBindingPageLeft,
                dilationCore.keyBindingPageRight
        };

        this.keyBindings = KeyBindingAPI.Internal.inject(this.keyBindings);
    }
}
