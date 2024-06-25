package me.vinniciuslol.pepitaoverlay.module;

import me.vinniciuslol.pepitaoverlay.module.annotations.ModuleInfo;
import me.vinniciuslol.pepitaoverlay.module.enums.ModuleCategory;
import me.vinniciuslol.pepitaoverlay.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.List;

public class Module {
    private String name;
    private ModuleCategory category;
    private int keybind;
    private boolean enabled;
    protected final Minecraft mc = Minecraft.getMinecraft();

    private List<Setting> settings = new ArrayList<>();

    public Module() {
        if (this.getClass().isAnnotationPresent(ModuleInfo.class)) {
            this.name = this.getClass().getAnnotation(ModuleInfo.class).name();
            this.category = this.getClass().getAnnotation(ModuleInfo.class).category();
            this.keybind = this.getClass().getAnnotation(ModuleInfo.class).key();
        }
    }

    public void registerSetting(Setting setting) {
        this.settings.add(setting);
    }

    public void registerSetting(Setting... setting) {
        this.settings.addAll(Arrays.asList(setting));
    }

    public List<Setting> getSettings() {
        return settings;
    }

    public String getName() {
        return name;
    }

    public ModuleCategory getCategory() {
        return category;
    }

    public int getKeybind() {
        return keybind;
    }

    public void setKeybind(int keybind) {
        this.keybind = keybind;
    }

    public void enable() {
        this.enabled = true;
        this.onEnable();
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void disable() {
        this.enabled = false;
        this.onDisable();
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    public void toggle() {
        if (!this.enabled)
            this.enable();
        else
            this.disable();
    }

    public void guiUpdate() {
    }

    public void onEnable() {}

    public void onDisable() {}

    public boolean isEnabled() {
        return enabled;
    }
}
