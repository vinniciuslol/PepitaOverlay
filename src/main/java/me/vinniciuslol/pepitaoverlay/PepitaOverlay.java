package me.vinniciuslol.pepitaoverlay;

import me.vinniciuslol.pepitaoverlay.clickgui.ClickGUI;
import me.vinniciuslol.pepitaoverlay.module.Module;
import me.vinniciuslol.pepitaoverlay.module.ModuleManager;
import me.vinniciuslol.pepitaoverlay.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Mod(modid = "pepita-overlay")
public class PepitaOverlay {
    public static ModuleManager MODULE_MANAGER;
    public static Minecraft mc = Minecraft.getMinecraft();
    private static final ScheduledExecutorService ex = Executors.newScheduledThreadPool(2);

    @Mod.EventHandler
    public static void onPreInit(FMLPreInitializationEvent e) {
        MODULE_MANAGER = new ModuleManager();
        MODULE_MANAGER.loadModules();
    }

    public static ScheduledExecutorService getExecutor() {
        return ex;
    }

}
