package me.vinniciuslol.pepitaoverlay.module.impl.overlays;

import me.vinniciuslol.pepitaoverlay.module.Module;
import me.vinniciuslol.pepitaoverlay.module.annotations.ModuleInfo;
import me.vinniciuslol.pepitaoverlay.module.enums.ModuleCategory;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "SwOverlay", category = ModuleCategory.Overlays, key = Keyboard.KEY_F)
public class SwOverlay extends Module {

    public static boolean active;

    public SwOverlay() {
        
    }

    @Override
    public void onEnable() {
        super.onEnable();
        System.out.println("Ativado");
        active = true;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        //playerStats.clear();
        System.out.println("desativado");
        active = false;
    }
}
