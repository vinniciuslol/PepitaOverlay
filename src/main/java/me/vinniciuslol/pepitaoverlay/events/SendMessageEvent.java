package me.vinniciuslol.pepitaoverlay.events;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class SendMessageEvent extends Event {
    private String message;

    public SendMessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
