package me.vinniciuslol.pepitaoverlay.clickgui.component.component.impl;

import java.awt.Color;
import java.util.ArrayList;

import me.vinniciuslol.pepitaoverlay.PepitaOverlay;
import me.vinniciuslol.pepitaoverlay.clickgui.component.component.Component;
import me.vinniciuslol.pepitaoverlay.module.Module;
import org.lwjgl.opengl.GL11;

import me.vinniciuslol.pepitaoverlay.clickgui.component.Frame;
import me.vinniciuslol.pepitaoverlay.setting.Setting;
import me.vinniciuslol.pepitaoverlay.setting.impl.BooleanSetting;
import me.vinniciuslol.pepitaoverlay.setting.impl.ListSetting;
import me.vinniciuslol.pepitaoverlay.setting.impl.SliderSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class ButtonComponent extends Component {
	private final Minecraft mc = Minecraft.getMinecraft();
	public Module mod;
	public Frame parent;
	public int offset;
	private boolean isHovered;
	private ArrayList<Component> subcomponents;
	public boolean open;
	private int height;
	
	public ButtonComponent(Module mod, Frame parent, int offset) {
		this.mod = mod;
		this.parent = parent;
		this.offset = offset;
		this.subcomponents = new ArrayList<Component>();
		this.open = false;
		int opY = offset + 12;

		if(PepitaOverlay.MODULE_MANAGER.getSettingsFromModule(mod.getClass()) != null) {
			for(Setting s : PepitaOverlay.MODULE_MANAGER.getSettingsFromModule(mod.getClass())){
				if(s instanceof ListSetting){
					this.subcomponents.add(new ModeComponent((ListSetting) s, this, mod, opY));
					opY += 12;
				}
				if(s instanceof SliderSetting){
					this.subcomponents.add(new SliderComponent((SliderSetting) s, this, opY));
					opY += 12;
				}
				if(s instanceof BooleanSetting){
					this.subcomponents.add(new CheckboxComponent((BooleanSetting) s, this, opY));
					opY += 12;
				}
			}
		}
		height = 12;
	}
	
	@Override
	public void setOff(int newOff) {
		offset = newOff;
		int opY = offset + 12;
		for(Component comp : this.subcomponents) {
			comp.setOff(opY);
			opY += 12;
		}
	}
	
	@Override
	public void renderComponent() {
		Gui.drawRect(parent.getX(), this.parent.getY() + this.offset, parent.getX() + parent.getWidth(), this.parent.getY() + 12 + this.offset, this.isHovered ? (this.mod.isEnabled() ? new Color(0xFF222222).darker().getRGB() : 0xFF222222) : (this.mod.isEnabled() ? new Color(14,14,14).getRGB() : 0xFF111111));
		GL11.glPushMatrix();
		GL11.glScalef(0.5f,0.5f, 0.5f);

		mc.fontRendererObj.drawStringWithShadow(this.mod.getName(), (parent.getX() + 2) * 2, (parent.getY() + offset + 2) * 2 + 4, this.mod.isEnabled() ? 0x999999 : -1);

		if(this.subcomponents.size() > 2)
			Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.open ? "-" : "+", (parent.getX() + parent.getWidth() - 10) * 2, (parent.getY() + offset + 2) * 2 + 4, -1);

		GL11.glPopMatrix();

		if(this.open) {
			if(!this.subcomponents.isEmpty()) {
				ArrayList<Component> c = new ArrayList<>();
				
				for(Component comp : this.subcomponents) {
					if (comp.setting.isHide()) {
						c.add(comp);
						continue;
					}
					
					comp.renderComponent();
				}
				
				
				Gui.drawRect(parent.getX() + 2, parent.getY() + this.offset + 12, parent.getX() + 3, parent.getY() + this.offset + ((this.subcomponents.size() - c.size() + 1) * 12), -1);
			}
		}
	}
	
	@Override
	public int getHeight() {
		if(this.open) {
			return (12 * (this.subcomponents.size() + 1));
		}
		return 12;
	}
	
	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.isHovered = isMouseOnButton(mouseX, mouseY);
		if(!this.subcomponents.isEmpty()) {
			for(Component comp : this.subcomponents) {
				comp.updateComponent(mouseX, mouseY);
			}
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(isMouseOnButton(mouseX, mouseY) && button == 0) {
			this.mod.toggle();
		}
		if(isMouseOnButton(mouseX, mouseY) && button == 1) {
			this.open = !this.open;
			this.parent.refresh();
		}
		for(Component comp : this.subcomponents) {
			comp.mouseClicked(mouseX, mouseY, button);
		}
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		for(Component comp : this.subcomponents) {
			comp.mouseReleased(mouseX, mouseY, mouseButton);
		}
	}
	
	@Override
	public void keyTyped(char typedChar, int key) {
		for(Component comp : this.subcomponents) {
			comp.keyTyped(typedChar, key);
		}
	}
	
	public boolean isMouseOnButton(int x, int y) {
		if(x > parent.getX() && x < parent.getX() + parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset) {
			return true;
		}
		return false;
	}
}
