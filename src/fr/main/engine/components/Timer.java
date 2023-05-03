package fr.main.engine.components;

import fr.main.game.core.MasterAct;

public class Timer extends Thread{

	private int time;
	private boolean running;
	private MasterAct master;
	
	public Timer(MasterAct master) {
		time = 0;
		running = true;
		this.master = master;
	}
	
	public int getTime() {
		return time;
	}
	
	@Override
	public void run() {
		while(running) {
			master.updateTime();
			time++;
			try {
				this.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
