package fr.main.game.intro;

import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import fr.main.engine.Manager;
import fr.main.engine.components.Button;
import fr.main.engine.components.TextField;
import fr.main.engine.graphics.Display;
import fr.main.engine.graphics.Renderer;
import fr.main.game.Activity;

public class HostAct extends Activity{

	public static final int TO_LAUNCH = 2, TO_BACK = 3;
	
	private Button back;
	private Button host;
	private TextField portField;
	
	public HostAct(Display display, Manager manager) {
		super(display, manager);
		
		host = new Button(100, 300, 100, 30, "Host");
		back = new Button(100, 400, 100, 30, "Back");
		portField = new TextField(100, 200, 200, 30, 5,"6565");
		
	}
	
	@Override
	public void setState(int state) {
		this.state = state;
		if(state == ACTIVE_STATE) {
			portField.create(canvas);
			canvas.addMouseListener(this);
		}
	}
	
	@Override
	protected void leave() {
		portField.destroy(canvas);
		canvas.removeMouseListener(this);
		manager.update();
	}
	
	public int getPort() {
		return Integer.parseInt(portField.getTxt());
	}
	
	@Override
	public void render(Renderer renderer) {
		back.render(renderer);
		host.render(renderer);
		portField.render(renderer);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int mx = e.getX(); int my = e.getY();
		if(SwingUtilities.isLeftMouseButton(e)) {
			if(back.contain(mx, my)) {
				state = TO_BACK;
				leave();
			}
			if(portField.contain(mx, my)) {
				portField.select(true);
			} else portField.select(false);
			if(host.contain(mx, my)) {
				state = TO_LAUNCH;
				leave();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


}
