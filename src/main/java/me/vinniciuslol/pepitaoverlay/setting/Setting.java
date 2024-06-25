package me.vinniciuslol.pepitaoverlay.setting;

import me.vinniciuslol.pepitaoverlay.setting.interfaces.BooleanFunction;

public class Setting {
	private String settingName;

	private BooleanFunction<?> predicate;
	
	public Setting(String settingName) {
		this.settingName = settingName;
	}

	public String getSettingName() {
		return settingName;
	}

	public void showOnly(BooleanFunction<?> check) {
		this.predicate = check;
	}
	
	public boolean isHide() {
		return this.predicate != null && !this.predicate.get();
	}
}
