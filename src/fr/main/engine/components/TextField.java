package fr.main.engine.components;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import fr.main.engine.graphics.Renderer;

public class TextField implements KeyListener{
	
	private int x, y, w, h, limit;
	private boolean selected;
	private StringBuffer msg;

	public TextField(int x, int y, int w, int h, int limit) {
		this(x,y,w,h,limit,"");
	}
	
	public TextField(int x, int y, int w, int h, int limit, String placeHolder) {
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		this.limit=limit;
		
		msg = new StringBuffer(placeHolder);
		selected = false;
	}
	
	public void create(Canvas canvas) {
		canvas.addKeyListener(this);
	}
	public void destroy(Canvas canvas) {
		canvas.removeKeyListener(this);
	}
	
	public void render(Renderer renderer) {
		renderer.fillRect(x, y, w, h, Color.BLACK);
		renderer.fillRect(x+2, y+2, w-4, h-4, Color.WHITE);
		renderer.drawText(msg.toString(), x+10, y+20);
	}
	
	public boolean contain(int mx, int my) {
		return (mx>=x && mx<=x+w && my>=y && my<=y+h);
	}
	
	public void select(boolean b) {
		selected = b;
	}
	
	public String getTxt() {
		return msg.toString();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(selected && msg.length()<limit && e.getKeyCode()!=KeyEvent.VK_BACK_SPACE && e.getKeyCode()!=KeyEvent.VK_ENTER && e.getKeyCode()!=KeyEvent.VK_SHIFT) {
			msg.append(e.getKeyChar());
		}
		if(selected && e.getKeyCode()==KeyEvent.VK_BACK_SPACE) {
			msg.deleteCharAt(msg.length()-1);
		}
		//if(selected && e.getKeyCode()==VK_ENTER)
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	

}
