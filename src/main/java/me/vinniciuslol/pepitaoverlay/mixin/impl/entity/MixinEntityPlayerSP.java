package me.vinniciuslol.pepitaoverlay.mixin.impl.entity;

import me.vinniciuslol.pepitaoverlay.events.SendMessageEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    public void onSendChatMessage(String message, CallbackInfo ci) {
        SendMessageEvent event = new SendMessageEvent(message);
        MinecraftForge.EVENT_BUS.post(event);

        if (event.isCanceled())
            ci.cancel();
    }
}
