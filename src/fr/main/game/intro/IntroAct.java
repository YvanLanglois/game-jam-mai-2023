package fr.main.game.intro;

import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import fr.main.engine.Manager;
import fr.main.engine.components.Button;
import fr.main.engine.graphics.Display;
import fr.main.engine.graphics.Renderer;
import fr.main.game.Activity;

public class IntroAct extends Activity{

	public static final int TO_HOST = 2, TO_JOIN = 3, TO_EXIT = 4;
	
	private Button host, join, exit;
	
	public IntroAct(Display display, Manager manager) {
		super(display, manager);
		
		host = new Button(200, 50, 100, 30, "Host");
		join = new Button(200, 150, 100, 30, "Join");
		exit = new Button(200, 250, 100, 30, "Exit");
		
	}
	
	public void render(Renderer renderer) {
		host.render(renderer);
		join.render(renderer);
		exit.render(renderer);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int mx = e.getX(); int my = e.getY();
		
		if(SwingUtilities.isLeftMouseButton(e)) {
			if(host.contain(mx, my)) {
				state = TO_HOST;
				leave();
			}
			else if (join.contain(mx, my)) {
				state = TO_JOIN;
				leave();
			}
			else if (exit.contain(mx, my)) {
				state = TO_EXIT;
				leave();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	
	
}
