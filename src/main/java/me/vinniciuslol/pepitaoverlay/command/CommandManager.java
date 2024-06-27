package me.vinniciuslol.pepitaoverlay.command;

import me.vinniciuslol.pepitaoverlay.command.impl.*;
import me.vinniciuslol.pepitaoverlay.events.SendMessageEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {
    private List<Command> commands = new ArrayList<>();

    public CommandManager() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void loadCommands() {
        addCommands(
                new BindCommand()
        );
    }

    public void addCommands(Command... commands) {
        this.commands.addAll(Arrays.asList(commands));
    }

    @SubscribeEvent
    public void onSendMessage(SendMessageEvent e) {
        if (e.getMessage().startsWith(".")) {
            e.setCanceled(true);

            if (!commands.isEmpty())
                commands.forEach(c -> {
                    String[] args = e.getMessage().substring(1).split(" ");
                    String commandName = args[0];

                    if (commandName.equals(c.getName()))
                        c.onExecute(Arrays.copyOfRange(args, 1, args.length));
                });
        }
    }
}
