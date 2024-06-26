package me.vinniciuslol.pepitaoverlay.command;

import net.minecraft.client.Minecraft;

public class Command {
    private String name;
    private String[] alias;
    protected final Minecraft mc = Minecraft.getMinecraft();

    public String getName() {
        return name;
    }

    public String[] getAlias() {
        return alias;
    }

    public void onExecute(String args[]) {}
}
