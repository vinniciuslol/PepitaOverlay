package me.vinniciuslol.pepitaoverlay.module.impl.render;

import me.vinniciuslol.pepitaoverlay.module.Module;
import me.vinniciuslol.pepitaoverlay.module.annotations.ModuleInfo;
import me.vinniciuslol.pepitaoverlay.module.enums.ModuleCategory;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "ClikcGUI", category = ModuleCategory.Render, key = Keyboard.KEY_RCONTROL)
public class ClickGUI extends Module {

    @Override
    public void onEnable() {
        mc.displayGuiScreen(new me.vinniciuslol.pepitaoverlay.clickgui.ClickGUI());
        this.disable();
    }
}
