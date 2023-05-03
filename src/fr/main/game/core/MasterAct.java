package fr.main.game.core;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import fr.main.engine.Manager;
import fr.main.engine.components.Button;
import fr.main.engine.components.TextField;
import fr.main.engine.components.Timer;
import fr.main.engine.graphics.Display;
import fr.main.engine.graphics.Renderer;
import fr.main.game.Activity;

public class MasterAct extends Activity{

	public static final int TO_STOP = 4;
	
	private Button stop;
	private Button nextTime;
	private Button start;
	private String portLabel;
	private ArrayList<String> players;
	private Manager manager;
	private Timer timer;
	private TextField boidsNbrField;
	private Button validateBoidsBtn;
	
	public MasterAct(Display display, Manager manager) {
		super(display, manager);
		
		this.manager = manager;
		nextTime = new Button(100, 200, 100, 30, "Next Time");
		stop = new Button(100, 400, 100, 30, "Stop");
		start = new Button(400, 200, 100, 30, "Start");
		boidsNbrField = new TextField(100, 250, 200, 30, 6, "100");
		validateBoidsBtn = new Button(350, 250, 30, 30, "OK");
		
		players = new ArrayList<>();
		portLabel = "Listening on port : "+6565;
		timer = new Timer(this);
	}
	
	@Override
	public void setState(int state) {
		this.state = state;

		if(state == ACTIVE_STATE) {
			boidsNbrField.create(canvas);
			canvas.addMouseListener(this);
		}
	}
	
	
	protected void leave() {
		boidsNbrField.destroy(canvas);
		canvas.removeMouseListener(this);
		manager.update();
	}
	
	public void addPlayer(String addr) {
		players.add(addr);
	}
	
	public void updateTime() {
		manager.updateTimeSRV(timer.getTime());
	}
	
	@Override
	public void render(Renderer renderer) {
		
		renderer.drawText("Time: "+timer.getTime(), 100, 70);
		renderer.drawText(portLabel, 50, 20);
		nextTime.render(renderer);
		start.render(renderer);
		boidsNbrField.render(renderer);
		validateBoidsBtn.render(renderer);
		stop.render(renderer);
		for(int i = 0; i<players.size(); i++) {
			renderer.drawText("player "+i+" : "+players.get(i), 100, 100+80*i);
		}
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int mx = e.getX(); int my = e.getY();
		if(SwingUtilities.isLeftMouseButton(e)) {
			if(stop.contain(mx, my)) {
				state = TO_STOP;
				//todo
			}
			else if (nextTime.contain(mx, my)) {
				manager.increaseTimeSRV();
			}
			else if(start.contain(mx, my)) {
				timer.start();
			}
			else if(validateBoidsBtn.contain(mx, my)) {
				manager.setFoodNbr(Integer.parseInt(boidsNbrField.getTxt()));
			}
			if(boidsNbrField.contain(mx, my)) {
				boidsNbrField.select(true);
			} else boidsNbrField.select(false);
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
