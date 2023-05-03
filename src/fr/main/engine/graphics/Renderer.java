package fr.main.engine.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Renderer {

	public static final Color bckColor = new Color(255,255,204);
	
	private Canvas canvas;
	private BufferStrategy bs;
	private Graphics g;
	
	private Color backgroundColor;
	
	public Renderer(Display display) {
		canvas = display.getCanvas();
		canvas.createBufferStrategy(3);
		backgroundColor = bckColor;
	}
	
	public void setBackgroundColor(Color c) {
		backgroundColor = c;
	}
	
	public void start() {
		bs = canvas.getBufferStrategy();
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		g.setColor(backgroundColor);
		g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		g.setColor(Color.BLACK);
	}
	
	public void end() {
		bs.show();
		g.dispose();
	}
	
	public void fillRect(int x, int y, int w, int h, Color c) {
		g.setColor(c);
		g.fillRect(x, y, w, h);
	}
	
	public void drawText(String s, int x, int y) {
		g.setColor(Color.BLACK);
		g.drawString(s, x, y);
	}
	
	public void drawImg(BufferedImage texture, int x, int y, int w, int h) {
		g.drawImage(texture, x, y, w, h, null);
	}
	
	public void drawCircle(int x, int y, int r, Color c) {
		g.setColor(c);
		g.fillOval(x, y, r, r);
	}
	
}
