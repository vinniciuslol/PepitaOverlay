package me.vinniciuslol.pepitaoverlay.clickgui.component;

import java.util.ArrayList;

import me.vinniciuslol.pepitaoverlay.PepitaOverlay;
import me.vinniciuslol.pepitaoverlay.clickgui.component.component.Component;
import me.vinniciuslol.pepitaoverlay.module.Module;
import me.vinniciuslol.pepitaoverlay.module.enums.ModuleCategory;
import org.lwjgl.opengl.GL11;

import me.vinniciuslol.pepitaoverlay.clickgui.component.component.impl.ButtonComponent;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class Frame {

	public ArrayList<Component> components;
	public ModuleCategory category;
	private boolean open;
	private int width;
	private int y;
	private int x;
	private int barHeight;
	private boolean isDragging;
	public int dragX;
	public int dragY;
	
	public Frame(ModuleCategory cat) {
		this.components = new ArrayList<Component>();
		this.category = cat;
		this.width = 88;
		this.x = 5;
		this.y = 5;
		this.barHeight = 13;
		this.dragX = 0;
		this.open = false;
		this.isDragging = false;
		int tY = this.barHeight;
		
		for(Module mod : PepitaOverlay.MODULE_MANAGER.getModulesFromCategory(category)) {
			ButtonComponent modButton = new ButtonComponent(mod, this, tY);
			this.components.add(modButton);
			tY += 12;
		}
	}
	
	public ArrayList<Component> getComponents() {
		return components;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public void setDrag(boolean drag) {
		this.isDragging = drag;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	public void renderFrame(FontRenderer fontRenderer) {
		Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.barHeight, 0xFF111111);
		GL11.glPushMatrix();
		GL11.glScalef(0.5f,0.5f, 0.5f);
		fontRenderer.drawStringWithShadow(this.category.name(), (this.x + 2) * 2 + 5, (this.y + 2.5f) * 2 + 5, 0xFFFFFFFF);
		fontRenderer.drawStringWithShadow(this.open ? "-" : "+", (this.x + this.width - 10) * 2 + 5, (this.y + 2.5f) * 2 + 5, -1);
		GL11.glPopMatrix();
		if(this.open) {
			if(!this.components.isEmpty()) {
				for(Component component : components) {
					component.renderComponent();
				}
			}
		}
	}
	
	public void refresh() {
		int off = this.barHeight;
		for(Component comp : components) {
			comp.setOff(off);
			off += comp.getHeight();
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void updatePosition(int mouseX, int mouseY) {
		if(this.isDragging) {
			this.setX(mouseX - dragX);
			this.setY(mouseY - dragY);
		}
	}
	
	public boolean isWithinHeader(int x, int y) {
		if(x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight) {
			return true;
		}
		return false;
	}
	
}
