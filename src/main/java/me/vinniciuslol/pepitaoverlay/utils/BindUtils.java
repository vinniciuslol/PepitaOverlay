package me.vinniciuslol.pepitaoverlay.utils;

import me.vinniciuslol.pepitaoverlay.interfaces.IMinecraft;
import me.vinniciuslol.pepitaoverlay.module.Module;
import net.minecraft.util.ChatComponentText;

import java.util.HashMap;

// somethings from haru, lel
public class BindUtils implements IMinecraft {
    private static HashMap<String, Integer> binds = new HashMap<>();

    public static void setBind(Module mod, String key) {
        if (mod != null) {
            if (binds.containsKey(key)) {
                mc.thePlayer.addChatMessage(new ChatComponentText(mod.getName() + " bound to " + binds.get(key)));
                mod.setKeybind(binds.get(key));
            } else {
                mc.thePlayer.addChatMessage(new ChatComponentText(mod.getName() + " bound to none"));
                mod.setKeybind(0);
            }
        } else
            mc.thePlayer.addChatMessage(new ChatComponentText("Module not found"));
    }

    public static void unbind(Module mod) {
        if (mod != null) {
            mc.thePlayer.addChatMessage(new ChatComponentText(mod.getName() + " bound to none"));
            mod.setKeybind(0);
        } else
            mc.thePlayer.addChatMessage(new ChatComponentText("Module not found"));
    }

    static {
        binds.put("none", 0);
        binds.put("a", 30);
        binds.put("b", 48);
        binds.put("c", 46);
        binds.put("d", 32);
        binds.put("e", 18);
        binds.put("f", 33);
        binds.put("g", 34);
        binds.put("h", 35);
        binds.put("i", 23);
        binds.put("j", 36);
        binds.put("k", 37);
        binds.put("l", 38);
        binds.put("m", 50);
        binds.put("n", 49);
        binds.put("o", 24);
        binds.put("p", 25);
        binds.put("q", 16);
        binds.put("r", 19);
        binds.put("s", 31);
        binds.put("t", 20);
        binds.put("u", 22);
        binds.put("v", 47);
        binds.put("w", 17);
        binds.put("x", 45);
        binds.put("y", 21);
        binds.put("z", 44);
        binds.put("0", 11);
        binds.put("1", 2);
        binds.put("2", 3);
        binds.put("3", 4);
        binds.put("4", 5);
        binds.put("5", 6);
        binds.put("6", 7);
        binds.put("7", 8);
        binds.put("8", 9);
        binds.put("9", 10);
        binds.put("numpad0", 82);
        binds.put("numpad1", 79);
        binds.put("numpad2", 80);
        binds.put("numpad3", 81);
        binds.put("numpad4", 75);
        binds.put("numpad5", 76);
        binds.put("numpad6", 77);
        binds.put("numpad7", 71);
        binds.put("numpad8", 72);
        binds.put("numpad9", 73);
        binds.put("rshift", 54);
        binds.put("lshift", 42);
        binds.put("lcontrol", 29);
        binds.put("tab", 15);
        binds.put("strg", 29);
        binds.put("alt", 56);
    }
}
