package me.vinniciuslol.pepitaoverlay.module.impl.overlays;

import com.google.gson.JsonObject;
import me.vinniciuslol.pepitaoverlay.PepitaOverlay;
import me.vinniciuslol.pepitaoverlay.module.Module;
import me.vinniciuslol.pepitaoverlay.module.annotations.ModuleInfo;
import me.vinniciuslol.pepitaoverlay.module.enums.ModuleCategory;
import me.vinniciuslol.pepitaoverlay.setting.impl.BooleanSetting;
import me.vinniciuslol.pepitaoverlay.setting.impl.SliderSetting;
import me.vinniciuslol.pepitaoverlay.utils.RenderUtils;
import me.vinniciuslol.pepitaoverlay.utils.UrlUtils;
import me.vinniciuslol.pepitaoverlay.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.HashMap;

import static me.vinniciuslol.pepitaoverlay.utils.Utils.getValue;

@ModuleInfo(name = "BwOverlay", category = ModuleCategory.Overlays, key = Keyboard.KEY_J)
public class BwOverlay extends Module {

    public static SliderSetting overlayX, overlayY, margin, marginTextY, marginTextX, alpha;
    public static BooleanSetting Rounded, dropShadowT, apacheRequest;
    public static boolean active;
    public static double overlayWidth, overlayHeight, textY;
    public static int mainTextColour, linesDrawn, backgroundColour, errorColour;
    public static int bgColor;
    public static HashMap<String, int[]> playerStats = new HashMap<>();
    public static HashMap<StatType, Integer> statStart = new HashMap<>();
    private int rgb_c = 0;

    public BwOverlay()
    {
        overlayHeight = 170;
        overlayWidth = 300;

        //this.registerSetting(apacheRequest = new BooleanSetting("Apache", false));
        this.registerSetting(Rounded = new BooleanSetting("Rounded", false));
        this.registerSetting(dropShadowT = new BooleanSetting("Drop Shadow", false));
        //this.registerSetting(colorByName = new ButtonSetting("Color by Name", false));
        this.registerSetting(alpha = new SliderSetting("Opacity", 65, 0, 100, 1));
        this.registerSetting(overlayX = new SliderSetting("X", 7, 0, 250, 1));
        this.registerSetting(overlayY = new SliderSetting("Y", 7, 0, 250, 1));
        this.registerSetting(margin = new SliderSetting("Margin", 4, 0, 100, 1));
        this.registerSetting(marginTextX = new SliderSetting("Margin Text X", 21, 0, 100, 1));
        this.registerSetting(marginTextY = new SliderSetting("Margin Text Y", 8, 0, 100, 1));

        mainTextColour  = 0xffFEC5E5;
        backgroundColour = 0x2C2C2C2C;

        errorColour = 0xffff0033;
    }


    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent ev) {
        if (!active) {
            return;
        }
        if (mc.currentScreen != null) {
            return;

        }
        if (!mc.inGameHasFocus || mc.gameSettings.showDebugInfo) {
            return;
        }


        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        linesDrawn = 0;
        drawMain(sr, fr);
        linesDrawn++;

        if (drawError(sr, fr)) {
            linesDrawn++;
            //return;
        }

        for(NetworkPlayerInfo player : Utils.getPlayers()){
            drawStats(player, fr);
        }
        overlayHeight = margin.getValue() * 2 + fr.FONT_HEIGHT * linesDrawn + marginTextY.getValue() * --linesDrawn;

        fr.drawString("", 0, 0, 0xffffffff);
    }


    public void guiUpdate() {
        int alpha100 = (int) alpha.getValue();
        this.rgb_c = (new Color((int) 1, (int) 1, (int) 1, (int) (alpha100 * 255/100))).getRGB();
    }

    public void drawStats(NetworkPlayerInfo player, FontRenderer fr) {
        String name = player.getGameProfile().getName();
        String UUID = player.getGameProfile().getId().toString();

        double fkdr = 0.00;
        if(!playerStats.containsKey(name)){
            PepitaOverlay.getExecutor().execute(() -> getStats(name));
            playerStats.put(name, new int[] {-16});
            return;
        }

        int[] stats = playerStats.get(name);

        if(stats.length == 1 && stats[0] == -16){
            //we are loading player stats so return
            return;
        }

        //int rgb = rainbow.isToggled() ? Utils.getChroma(2L, 0L) : this.rgb_d;

        //int colorrr;
        //int colorrr1;

        //final int rgb = getColorFromTags(player.getDisplayName().getFormattedText());

        final int colorrr = new Color(240, 240, 240).getRGB();

            /*if (colorByName.isToggled()) {
                colorrr1 = getColorFromTags(player.getDisplayName().getFormattedText());
            } else {
                colorrr1 = new Color(240, 240, 240).getRGB();
            }

            colorrr = colorrr1;*/

        //final int colorByName = getColorFromTags(name);

        if (stats[4] != 0) {
            fkdr = Utils.rnd(stats[3] / stats[4], 2);
        } else {
            fkdr = 0;
        }

        if (!dropShadowT.isEnabled()) {
            fr.drawString(stats[0] + "", statStart.get(StatType.LEVEL), (int)textY, getLevelColour(stats[0]), false);
            fr.drawString(name, statStart.get(StatType.PLAYER_NAME), (int)textY, colorrr, false);
            fr.drawString(stats[2] + "", statStart.get(StatType.WS), (int)textY, getWSColour(stats[2]), false);
            fr.drawString(fkdr + "", statStart.get(StatType.FKDR), (int)textY, getFKDRColour(fkdr), false);
        } else {
            fr.drawString(stats[0] + "", statStart.get(StatType.LEVEL), (int)textY, getLevelColour(stats[0]), true);
            fr.drawString(name, statStart.get(StatType.PLAYER_NAME), (int)textY, colorrr, true);
            fr.drawString(stats[2] + "", statStart.get(StatType.WS), (int)textY, getWSColour(stats[2]), true);
            fr.drawString(fkdr + "", statStart.get(StatType.FKDR), (int)textY, getFKDRColour(fkdr), true);
        }

        textY += marginTextY.getValue() + fr.FONT_HEIGHT;
        linesDrawn++;
    }

    public static int getLevelColour(int stat){
        if(stat < 10){
            return Colours.GREY;
        } else if(stat < 15) {
            return Colours.WHITE;
        } else if(stat < 35) {
            return Colours.GREEN;
        } else if(stat < 50) {
            return Colours.AQUA;
        } else if(stat < 75) {
            return Colours.YELLOW;
        } else if(stat < 100) {
            return Colours.ORANGE;
        } else if(stat < 150) {
            return Colours.RED;
        } else if(stat >= 200) {
            return Colours.PURPLE;
        }
        return Colours.PURPLE;
    }


    private int getFKDRColour(double stat) {
        if(stat < 0.0){
            return Colours.GREY;
        } else if(stat < 2.0) {
            return Colours.WHITE;
        } else if(stat < 4.5) {
            return Colours.GREEN;
        } else if(stat < 6.0) {
            return Colours.AQUA;
        } else if(stat < 7.5) {
            return Colours.YELLOW;
        } else if(stat < 9.0) {
            return Colours.ORANGE;
        } else if(stat < 10.5) {
            return Colours.RED;
        } else if(stat >= 12.0) {
            return Colours.PURPLE;
        }
        return Colours.PURPLE;
    }

    private int getWSColour(int stat) {
        if(stat < 10){
            return Colours.GREY;
        } else if(stat < 15) {
            return Colours.WHITE;
        } else if(stat < 20) {
            return Colours.GREEN;
        } else if(stat < 25) {
            return Colours.AQUA;
        } else if(stat < 30) {
            return Colours.YELLOW;
        } else if(stat < 35) {
            return Colours.ORANGE;
        } else if(stat < 40) {
            return Colours.RED;
        } else if(stat >= 50) {
            return Colours.PURPLE;
        }
        return Colours.PURPLE;
    }


    private void getStats(String uuid) {
        final int[] stats = new int[9];

        String con = UrlUtils.getTextFromURL("https://mush.com.br/api/player/" + uuid);

        if (con.isEmpty()) {
            System.out.println("Conexão vazia");
            return;
        }

        if (con.equals("{\"success\":true,\"player\":null}")) {
            System.out.println("Player nulo");
            stats[0] = -1;
            playerStats.put(uuid, stats);
        }

        JsonObject bw;
        try {
            bw = Utils.getStringAsJson(con).getAsJsonObject("response").getAsJsonObject("stats").getAsJsonObject("bedwars");
        } catch (NullPointerException ex) {
            playerStats.put(uuid, stats);
            return;
        }
        stats[0] = getValue(bw, "level");
        //stats[1] = stats[0] < 0 ? -1 : 0;
        stats[2] = getValue(bw, "winstreak");
        stats[3] = getValue(bw, "final_kills");
        stats[4] = getValue(bw, "final_deaths");
        playerStats.put(uuid, stats);
    }

    public boolean drawError(ScaledResolution sr, FontRenderer fr) {
        String noPlayers = "No players in lobby!";
        if(!Utils.othersExist()){
            fr.drawString(noPlayers, (int)(overlayWidth + overlayX.getValue() - overlayWidth/2 - fr.getStringWidth(noPlayers)/2), (int)textY, errorColour);
            textY += fr.FONT_HEIGHT + marginTextY.getValue();
            return true;
        }

        return false;
    }

    public void drawMain(ScaledResolution sr, FontRenderer fr) {
        if (!Rounded.isEnabled()) {
            Gui.drawRect((int)overlayX.getValue(), (int)overlayY.getValue(), (int)(overlayWidth + overlayX.getValue()), (int)(overlayHeight + overlayY.getValue()), backgroundColour);
            double textX = margin.getValue() + overlayX.getValue();
            textY = margin.getValue() + overlayY.getValue();
            int stringWidth = 0;
            for(StatType statType : StatType.values()) {
                if (!dropShadowT.isEnabled()) {
                    fr.drawString(statType + "", (int)textX, (int)textY, mainTextColour, false);
                } else {
                    fr.drawString(statType + "", (int)textX, (int)textY, mainTextColour, true);
                }
                statStart.put(statType, (int)textX);
                stringWidth = fr.getStringWidth(statType + "");
                textX += stringWidth + marginTextX.getValue();
            }
            textY += marginTextY.getValue() + fr.FONT_HEIGHT;
            overlayWidth = textX + margin.getValue() - (marginTextX.getValue()) - overlayX.getValue();
        } else {
            RenderUtils.drawRoundedRectangle((int)overlayX.getValue(), (int)overlayY.getValue(), (int)(overlayWidth + overlayX.getValue()), (int)(overlayHeight + overlayY.getValue()), 8, backgroundColour);
            double textX = margin.getValue() + overlayX.getValue();
            textY = margin.getValue() + overlayY.getValue();
            int stringWidth = 0;
            for(StatType statType : StatType.values()) {
                if (!dropShadowT.isEnabled()) {
                    fr.drawString(statType + "", (int)textX, (int)textY, mainTextColour, false);
                } else {
                    fr.drawString(statType + "", (int)textX, (int)textY, mainTextColour, true);
                }
                statStart.put(statType, (int)textX);
                stringWidth = fr.getStringWidth(statType + "");
                textX += stringWidth + marginTextX.getValue();
            }
            textY += marginTextY.getValue() + fr.FONT_HEIGHT;
            overlayWidth = textX + margin.getValue() - (marginTextX.getValue()) - overlayX.getValue();
        }

    }

    @Override
    public void onEnable() {
        super.onEnable();
        active = true;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        //playerStats.clear();
        active = false;
    }

    public enum StatType {
        LEVEL,
        PLAYER_NAME,
        WS,
        FKDR,
    }

    private int getColorFromTags(String displayName) {
        displayName = Utils.removeFormatCodes(displayName);
        if (displayName.isEmpty() || !displayName.startsWith("§") || displayName.charAt(1) == 'f') {
        }
        switch (displayName.charAt(1)) {
            case '0':
                return Colours.BLACK;
            case '1':
                return Colours.DBLUE;
            case '2':
                return Colours.DGREEN;
            case '3':
                return Colours.DAQUA;
            case '4':
                return Colours.DRED;
            case '5':
                return Colours.DPURPLE;
            case '6':
                return Colours.GOLD;
            case '7':
                return Colours.GREY;
            case '8':
                return Colours.DGREY;
            case '9':
                return Colours.BLUE;
            case 'a':
                return Colours.GREEN;
            case 'b':
                return Colours.AQUA;
            case 'c':
                return Colours.RED;
            case 'd':
                return Colours.LPURPLE;
            case 'e':
                return Colours.YELLOW;
        }
        return Colours.WHITE;
    }

    public static class Colours {
        public static final int BLACK = 0x000000;
        public static final int GOLD = 0xffFFAA00;
        public static final int DGREY = 0xff555555;
        public static final int BLUE = 0xff5555FF;
        public static final int LPURPLE = 0xffFF55FF;
        public static final int DGREEN = 0xff00AA00;
        public static final int DRED = 0xffAA0000;
        public static final int GREY = 0xffAAAAAA;
        public static final int DPURPLE = 0xffAA00AA;
        public static final int WHITE = 0xffffffff;
        public static final int GREEN = 0xff00AA00;
        public static final int DAQUA = 0xff00AAAA;
        public static final int AQUA = 0xff55FFFF;
        public static final int YELLOW = 0xffFFFF55;
        public static final int ORANGE = 0xffFFAA00;
        public static final int RED = 0xffAA0000;
        public static final int PURPLE = 0xffAA00AA;
        public static final int DBLUE = 0xff0000AA;
    }
}
