package me.vinniciuslol.pepitaoverlay.clickgui.component.component.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import me.vinniciuslol.pepitaoverlay.clickgui.component.component.Component;
import org.lwjgl.opengl.GL11;

import me.vinniciuslol.pepitaoverlay.setting.impl.SliderSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class SliderComponent extends Component {
	
	public SliderSetting set;
	private boolean hovered;

	private ButtonComponent parent;
	private int offset;
	private int x;
	private int y;
	private boolean dragging = false;

	private double renderWidth;
	
	public SliderComponent(SliderSetting value, ButtonComponent button, int offset) {
		super(value);
		this.set = value;
		this.parent = button;
		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
		this.offset = offset;
	}
	
	@Override
	public void renderComponent() {
		Gui.drawRect(parent.parent.getX() + 2, parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + offset + 12, this.hovered ? 0xFF222222 : 0xFF111111);
		int drag = (int)(this.set.getValue() / this.set.getMax() * this.parent.parent.getWidth());
		Gui.drawRect(parent.parent.getX() + 2, parent.parent.getY() + offset, parent.parent.getX() + (int) renderWidth, parent.parent.getY() + offset + 12,hovered ? 0xFF555555 : 0xFF444444);
		Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + 2, parent.parent.getY() + offset + 12, 0xFF111111);
		GL11.glPushMatrix();
		GL11.glScalef(0.5f,0.5f, 0.5f);
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.set.getSettingName() + ": " + this.set.getValue(), (parent.parent.getX()* 2 + 15), (parent.parent.getY() + offset + 2) * 2 + 5, -1);
		
		GL11.glPopMatrix();
	}
	
	@Override
	public void setOff(int newOff) {
		offset = newOff;
	}
	
	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.hovered = isMouseOnButtonD(mouseX, mouseY) || isMouseOnButtonI(mouseX, mouseY);
		this.y = parent.parent.getY() + offset;
		this.x = parent.parent.getX();
		
		double diff = Math.min(88, Math.max(0, mouseX - this.x));

		double min = this.set.getMin();
		double max = this.set.getMax();
		
		renderWidth = (88) * (this.set.getValue() - min) / (max - min);
		
		if (dragging) {
			if (diff == 0) {
				this.set.setValue(set.getMin());
			}
			else {
				double newValue = roundToPlace(((diff / 88) * (max - min) + min), 2);
				this.set.setValue(newValue);
			}
		}
	}
	
	private static double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(isMouseOnButtonD(mouseX, mouseY) && button == 0 && this.parent.open) {
			dragging = true;
		}
		if(isMouseOnButtonI(mouseX, mouseY) && button == 0 && this.parent.open) {
			dragging = true;
		}
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		dragging = false;
	}
	
	public boolean isMouseOnButtonD(int x, int y) {
		if(x > this.x && x < this.x + (parent.parent.getWidth() / 2 + 1) && y > this.y && y < this.y + 12) {
			return true;
		}
		return false;
	}
	
	public boolean isMouseOnButtonI(int x, int y) {
		if(x > this.x + parent.parent.getWidth() / 2 && x < this.x + parent.parent.getWidth() && y > this.y && y < this.y + 12) {
			return true;
		}
		return false;
	}
}
