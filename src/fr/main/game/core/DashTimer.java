package fr.main.game.core;

import fr.main.network.ServerWorker;

public class DashTimer extends Thread{

	private boolean busy;
	private ServerWorker sw;
	
	public DashTimer(ServerWorker sw) {
		busy = false;
		this.sw = sw;
	}
	
	@Override
	public void run() {
		busy = true;
		try {
			sleep(1000);
			busy = false;
			sw.resetDash();
			join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isBusy() {
		return busy;
	}
	
}
