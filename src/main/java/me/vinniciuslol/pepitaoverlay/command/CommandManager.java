package me.vinniciuslol.pepitaoverlay.command;

import me.vinniciuslol.pepitaoverlay.events.SendMessageEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private List<Command> commands = new ArrayList<>();

    public CommandManager() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onSendMessage(SendMessageEvent e) {
        if (e.getMessage().startsWith(".")) {
            e.setCanceled(true);

            String commandName = e.getMessage().substring(1, e.getMessage().split(" ")[0].length() - 1);
            String[] args = e.getMessage().substring(commandName.length() + 1).split(" ");

            if (!commands.isEmpty())
                commands.forEach(c -> {
                    if (commandName.equals(c.getName()))
                        c.onExecute(args);
                });
        }
    }
}
