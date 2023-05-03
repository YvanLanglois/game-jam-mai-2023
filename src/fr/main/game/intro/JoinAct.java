package fr.main.game.intro;

import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import fr.main.engine.Manager;
import fr.main.engine.components.Button;
import fr.main.engine.components.TextField;
import fr.main.engine.graphics.Display;
import fr.main.engine.graphics.Renderer;
import fr.main.game.Activity;

public class JoinAct extends Activity{
	
	public static final int TO_BACK = 2, TO_LAUNCH = 3;

	private TextField hostField;
	private TextField portField;
	private String hostLabel, portLabel;
	private Button back;
	private Button join;
	
	public JoinAct(Display display, Manager manager) {
		super(display, manager);
		
		hostField = new TextField(150, 100, 200, 30, 50,"localhost");
		portField = new TextField(150, 200, 200, 30, 50,"6565");
		hostLabel = "Hostname";
		portLabel = "Port";
		join = new Button(100, 300, 100, 30, "Join");
		back = new Button(100, 400, 100, 30, "Back");
	}

	@Override
	public void setState(int state) {
		this.state = state;

		if(state == ACTIVE_STATE) {
			hostField.create(canvas);
			portField.create(canvas);
			canvas.addMouseListener(this);
		}
	}
	
	
	protected void leave() {
		portField.select(false);
		portField.destroy(canvas);
		canvas.removeMouseListener(this);
		manager.update();
	}
	
	public int getPort() {
		return Integer.parseInt(portField.getTxt());
	}
	public String getHostname() {
		System.out.println("/"+hostField.getTxt()+"/");
		return hostField.getTxt();
	}
	
	@Override
	public void render(Renderer renderer) {

		renderer.drawText(hostLabel, 50, 120);
		hostField.render(renderer);
		renderer.drawText(portLabel, 50, 220);
		portField.render(renderer);
		back.render(renderer);
		join.render(renderer);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int mx = e.getX(); int my = e.getY();
		if(SwingUtilities.isLeftMouseButton(e)) {
			if(back.contain(mx, my)) {
				state = TO_BACK;
				leave();
			}
			else if(join.contain(mx, my)) {
				state = TO_LAUNCH;
				leave();
			}
			else if(hostField.contain(mx, my)) {
				hostField.select(true);
			}
			else if(portField.contain(mx, my)) {
				portField.select(true);
			}
			else {
				hostField.select(false);
				portField.select(false);
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
