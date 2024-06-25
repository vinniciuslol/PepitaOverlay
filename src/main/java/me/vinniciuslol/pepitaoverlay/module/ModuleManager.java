package me.vinniciuslol.pepitaoverlay.module;

import me.vinniciuslol.pepitaoverlay.module.enums.ModuleCategory;
import me.vinniciuslol.pepitaoverlay.module.impl.render.BwOverlay;
import me.vinniciuslol.pepitaoverlay.module.impl.render.ClickGUI;
import me.vinniciuslol.pepitaoverlay.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ModuleManager {
    private List<Module> modules = new ArrayList<>();
    private final Minecraft mc = Minecraft.getMinecraft();

    public ModuleManager() {
        MinecraftForge.EVENT_BUS.register(this);
    }

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

    @SubscribeEvent
    public void onTick(TickEvent e) {
        if (mc.currentScreen != null)
            return;

        int k = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey();

        if (Keyboard.getEventKeyState()) {
            modules.forEach(m -> {
                if (m.getKeybind() != 0 && k == m.getKeybind())
                    m.toggle();
            });
        }
    }
}
