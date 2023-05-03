package fr.main.engine.graphics;

import java.awt.Canvas;

import javax.swing.JFrame;

public class Display extends JFrame{

	private Canvas canvas;
	
	public Display() {
		super("CellGame");
		this.setDefaultCloseOperation(Display.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setLocation(500, 200);
		
		canvas = new Canvas();
		canvas.setSize(1000,600);
		
		this.add(canvas);
		pack();
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
}
