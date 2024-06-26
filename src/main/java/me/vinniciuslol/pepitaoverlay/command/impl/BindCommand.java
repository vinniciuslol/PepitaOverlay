package me.vinniciuslol.pepitaoverlay.command.impl;

import me.vinniciuslol.pepitaoverlay.PepitaOverlay;
import me.vinniciuslol.pepitaoverlay.command.Command;
import me.vinniciuslol.pepitaoverlay.utils.BindUtils;
import net.minecraft.util.ChatComponentText;

public class BindCommand extends Command {

    @Override
    public String getName() {
        return "bind";
    }

    @Override
    public void onExecute(String[] args) {
        if (args.length == 1)
            BindUtils.unbind(PepitaOverlay.MODULE_MANAGER.getModuleFromName(args[0], false));
        else if (args.length == 2)
            BindUtils.setBind(PepitaOverlay.MODULE_MANAGER.getModuleFromName(args[0], false), args[1]);
        else
            mc.thePlayer.addChatMessage(new ChatComponentText("usage:\nbind: .bind (module name) (bind)\nunbind: .bind (module name) none or .bind (module name)"));
    }
}
