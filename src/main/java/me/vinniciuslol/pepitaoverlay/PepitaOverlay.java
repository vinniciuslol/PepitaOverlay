package me.vinniciuslol.pepitaoverlay;

import me.vinniciuslol.pepitaoverlay.command.CommandManager;
import me.vinniciuslol.pepitaoverlay.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Mod(modid = "pepita-overlay")
public class PepitaOverlay {
    public static ModuleManager MODULE_MANAGER;
    public static CommandManager COMMAND_MANAGER;
    public static final ScheduledExecutorService EXECUTOR = Executors.newScheduledThreadPool(2);
    public static Minecraft mc = Minecraft.getMinecraft();

    @Mod.EventHandler
    public static void onPreInit(FMLPreInitializationEvent e) {
        MODULE_MANAGER = new ModuleManager();
        COMMAND_MANAGER = new CommandManager();

        MODULE_MANAGER.loadModules();
        COMMAND_MANAGER.loadCommands();
    }
}
