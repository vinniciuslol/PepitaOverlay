package me.vinniciuslol.pepitaoverlay;

import me.vinniciuslol.pepitaoverlay.module.ModuleManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "pepitaoverlay")
public class PepitaOverlay {
    public static ModuleManager MODULE_MANAGER;

    @Mod.EventHandler
    public static void onPreInit(FMLPreInitializationEvent e) {
        MODULE_MANAGER = new ModuleManager();
        MODULE_MANAGER.loadModules();
    }
}
