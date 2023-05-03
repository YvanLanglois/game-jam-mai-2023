package fr.main.engine;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import fr.main.engine.graphics.Display;
import fr.main.engine.graphics.Renderer;

public class Run implements Runnable{

	private boolean running;
	private Thread thread;
	
	private Display display;
	private Renderer renderer;
	private Manager manager;
	
	public Run() {
		running = false;
	}
	
	public void render() {
		renderer.start();
		
		manager.render(renderer);
		
		renderer.end();
	}
	
	public synchronized void start() {
		if(running) return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		if(!running) return;
		running = false;
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void init() {
		display = new Display();
		display.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				stop();
				e.getWindow().dispose();
				System.exit(0);
			}
		});
		renderer = new Renderer(display);
		manager = new Manager(display,this);
	}
	
	@Override
	public void run() {
		init();
		double FPS = 60.0;
		double time = 1_000_000_000/FPS;
		double delta = 0.0;
		long now;
		long last = System.nanoTime();
		while(running) {
			now = System.nanoTime();
			delta += (now-last)/time;
			if(delta>=1) {
				//update();
				render();
				delta--;
			}
		last = now;
		}
	}

}
