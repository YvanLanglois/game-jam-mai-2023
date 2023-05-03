package fr.main.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import fr.main.engine.Manager;

public class ServerMaster extends Thread{

	private int port;
	private boolean running;
	private Manager manager;
	private int id;
	
	public ServerMaster(int port) {
		this.port = port;
		running = false;
		id = 0;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	@Override
	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			running = true;
			while(running) {
				System.out.println("Listening for clients");
				Socket socket = serverSocket.accept();
				new ServerWorker(socket, manager,id).start();
				id++;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startServer(Manager manager) {
		this.manager = manager;
		this.start();
	}
	
	public boolean isRunning() {
		return running;
	}
	
}
