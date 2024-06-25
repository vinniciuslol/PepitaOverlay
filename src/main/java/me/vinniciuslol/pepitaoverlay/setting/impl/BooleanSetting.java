package me.vinniciuslol.pepitaoverlay.setting.impl;

import me.vinniciuslol.pepitaoverlay.setting.Setting;

public class BooleanSetting extends Setting {
	private boolean enabled;
	
	public BooleanSetting(String settingName, boolean defaultValue) {
		super(settingName);
		setEnabled(defaultValue);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
