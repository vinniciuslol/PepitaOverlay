package me.vinniciuslol.pepitaoverlay.module;

import me.vinniciuslol.pepitaoverlay.module.enums.ModuleCategory;
import me.vinniciuslol.pepitaoverlay.module.impl.overlays.BwOverlay;
import me.vinniciuslol.pepitaoverlay.module.impl.render.ClickGUI;
import me.vinniciuslol.pepitaoverlay.setting.Setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModuleManager {
    private List<Module> modules = new ArrayList<>();

    public void loadModules() {
        addModules(
                new ClickGUI(),
                new BwOverlay()
        );
    }

    public List<Module> getModules() {
        return modules;
    }

    private void addModules(Module... mod) {
        this.modules.addAll(Arrays.asList(mod));
    }

    public List<Module> getModulesFromCategory(ModuleCategory category) {
        List<Module> mods = new ArrayList<>();

        for (Module mod : modules)
            if (mod.getCategory() == category)
                mods.add(mod);

        return mods;
    }

    public List<Setting> getSettingsFromModule(Class<? extends Module> clazz) {
        for (Module mod : modules)
            if (mod.getClass().equals(clazz) && !mod.getSettings().isEmpty())
                return mod.getSettings();

        return null;
    }
}
