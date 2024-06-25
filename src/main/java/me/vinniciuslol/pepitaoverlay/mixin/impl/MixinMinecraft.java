package me.vinniciuslol.pepitaoverlay.mixin.impl;

import me.vinniciuslol.pepitaoverlay.PepitaOverlay;
import me.vinniciuslol.pepitaoverlay.interfaces.IMinecraft;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft implements IMinecraft {

    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;dispatchKeypresses()V"))
    public void onRunTick(CallbackInfo ci) {
        if (mc.currentScreen != null)
            return;

        int k = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey();

        if (Keyboard.getEventKeyState()) {
            PepitaOverlay.MODULE_MANAGER.getModules().forEach(m -> {
                if (m.getKeybind() != 0 && k == m.getKeybind())
                    m.toggle();
            });
        }
    }
}
