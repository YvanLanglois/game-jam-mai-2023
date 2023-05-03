package fr.main.engine.components;

import java.awt.Color;

import fr.main.engine.graphics.Renderer;

public class Button {

	protected int x, y, w, h;
	protected String label;
	
	public Button(int x, int y, int w, int h, String label) {
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		this.label=label;
	}
	
	public void render(Renderer renderer) {
		renderer.fillRect(x, y, w, h, Color.BLACK);
		renderer.fillRect(x+2, y+2, w-4, h-4, Color.GRAY);
		renderer.drawText(label, x+10, y+20);
	}

	public boolean contain(int mx, int my) {
		return(mx>=x && mx<=x+w && my>=y && my<=y+h);
	}
	
}
