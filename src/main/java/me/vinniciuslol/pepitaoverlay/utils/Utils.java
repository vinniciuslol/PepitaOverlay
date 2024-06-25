package me.vinniciuslol.pepitaoverlay.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static final Minecraft mc = Minecraft.getMinecraft();

    public static List<NetworkPlayerInfo> getPlayers() {
        List<NetworkPlayerInfo> yes = new ArrayList<>();
        List<NetworkPlayerInfo> mmmm = new ArrayList<>();
        try{
            yes.addAll(mc.getNetHandler().getPlayerInfoMap());
        }catch (NullPointerException r){
            return yes;
        }

        for(NetworkPlayerInfo ergy43d : yes){
            if(!mmmm.contains(ergy43d)){
                mmmm.add(ergy43d);
            }
        }

        return mmmm;
    }

    public static boolean nullCheck() {
        return mc.thePlayer != null && mc.theWorld != null;
    }

    public static double rnd(double n, int d) {
        if (d == 0) {
            return (double) Math.round(n);
        } else {
            double p = Math.pow(10.0D, (double) d);
            return (double) Math.round(n * p) / p;
        }
    }

    public static boolean othersExist() {
        for(Entity wut : mc.theWorld.getLoadedEntityList()){
            if(wut instanceof EntityPlayer) return true;
        }
        return false;
    }

    public static JsonObject getStringAsJson(String text) {
        return new JsonParser().parse(text).getAsJsonObject();
    }

    public static String removeFormatCodes(String str) {
        return str.replace("§k", "").replace("§l", "").replace("§m", "").replace("§n", "").replace("§o", "").replace("§r", "");
    }

    public static int getValue(JsonObject type, String member) {
        try {
            return type.get(member).getAsInt();
        } catch (NullPointerException er) {
            return 0;
        }
    }
}
