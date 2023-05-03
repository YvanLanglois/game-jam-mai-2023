package fr.main.game;

import java.awt.Canvas;

import javax.swing.event.MouseInputListener;

import fr.main.engine.Manager;
import fr.main.engine.graphics.Display;
import fr.main.engine.graphics.Renderer;

public abstract class Activity implements MouseInputListener{

	public static final int INIT_STATE = 0, ACTIVE_STATE = 1;
	
	protected Canvas canvas;
	protected Manager manager;
	protected int state;
	
	public Activity(Display display, Manager manager) {
		canvas = display.getCanvas();
		
		this.manager = manager;
		
		state = 0;
	}
	
	public abstract void render(Renderer renderer);
	
	protected void leave() {
		canvas.removeMouseListener(this);
		manager.update();
	}
	
	
	public void setState(int state) {
		if(state==ACTIVE_STATE) canvas.addMouseListener(this);
		this.state = state;
	}
	
	public int getState() {
		return state;
	}
}
