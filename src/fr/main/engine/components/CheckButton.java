package fr.main.engine.components;

import java.awt.Color;

import fr.main.engine.graphics.Renderer;

public class CheckButton extends Button{

	private boolean check;
	
	public CheckButton(int x, int y, int w, int h, String label) {
		super(x, y, w, h, label);
		check = false;
	}
	
	@Override
	public void render(Renderer renderer) {
		renderer.fillRect(x, y, w, h, Color.BLACK);
		renderer.fillRect(x+2, y+2, w-4, h-4, (check)?Color.LIGHT_GRAY:Color.GRAY);
		renderer.drawText(label, x+10, y+20);
	}
	
	public void check() {
		check = true;
	}
	public void reset() {
		check = false;
	}

}
