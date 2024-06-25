package me.vinniciuslol.pepitaoverlay.setting.impl;

import java.util.ArrayList;
import java.util.Arrays;

import me.vinniciuslol.pepitaoverlay.setting.Setting;

public class ListSetting extends Setting {
	private ArrayList<String> modes = new ArrayList<>();
	
	private int index;
	
	public ListSetting(String settingName, int defaultValue, String... modes) {
		super(settingName);
		this.index = defaultValue;
		this.modes.addAll(Arrays.asList(modes));
	}

	public ArrayList<String> getModes() {
		return modes;
	}

	public void setMode(int mode) {
		if (mode < 0)
			index = 0;
		else if (mode > modes.size() - 1)
			index = modes.size() - 1;
		else
			index = mode;
	}

	public void setMode(String mode) {
		int i = modes.indexOf(mode);
		
		if (i < 0)
			index = 0;
		else if (i > modes.size() - 1)
			index = modes.size() - 1;
		else
			index = i;
	}
	
	public String getMode() {
		return modes.get(index);
	}
}
