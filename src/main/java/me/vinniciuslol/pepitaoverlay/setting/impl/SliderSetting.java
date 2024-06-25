package me.vinniciuslol.pepitaoverlay.setting.impl;

import me.vinniciuslol.pepitaoverlay.setting.Setting;

public class SliderSetting extends Setting {
	private final double min, max, inc;

	private double value;
	
	public SliderSetting(String settingName, double defaultValue, double min, double max, double inc) {
		super(settingName);
		this.min = min;
		this.max = max;
		this.inc = inc;
		setValue(defaultValue);
	}
	
	public void setValue(double value) {
		if (value < this.min)
			this.value = this.min;
		else if (value > this.max)
			this.value = this.max;
		else
			this.value = Math.round(value * (1.0D / this.inc)) / (1.0D / this.inc);;
	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}

	public double getValue() {
		return value;
	}
}
