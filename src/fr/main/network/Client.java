package fr.main.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import fr.main.engine.Manager;

public class Client extends Thread{

	private Socket socket;
	
	private int port;
	private String hostname;
	private boolean running;
	private Manager manager;
	
	public Client(int port, String hostname, Manager manager) {
		this.port = port;
		this.hostname = hostname;
		running = false;
		this.manager = manager;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	
	@Override
	public void run() {
		running = true;
		while(running) {
			
			try {
				System.out.println("host:"+hostname+" / port:"+port);
				socket = new Socket(hostname, port);
				
				InputStream input = socket.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(input));
				
				//writer.println("hey");
				while(running) {
					String msg = reader.readLine();
					//System.out.println(msg);
					
					if(msg.startsWith("playersPosition")) {
						manager.getPlayersPositionCLI(msg.substring(15));
					}
					else if(msg.startsWith("playersNbr")) {
						manager.getPlayersNbrCLI(Integer.parseInt(msg.substring(10)));
					}
					else if(msg.startsWith("playersName")) {
						manager.getPlayersNameCLI(msg.substring(11));
					}
					else if(msg.startsWith("workerId")) {
						manager.setWorkerId(Integer.parseInt(msg.substring(8)));
					}
					else if(msg.startsWith("playersSize")) {
						manager.getPlayerSizeCLI(msg.substring(11));
					}
					else if(msg.startsWith("askClientSize")) {
						manager.updatePlayerSize();
					}
					else if(msg.startsWith("updateTime")) {
						manager.updateTimeCLI(Integer.parseInt(msg.substring(10)));
					}
					else if(msg.startsWith("playerHitOtherSRV")) {
						manager.playerHittenByOther(Integer.parseInt(msg.substring(17)));
					}
					else if(msg.startsWith("playerDash")) {
						manager.playerDash();
					}
					else if(msg.startsWith("foodPositions")) {
						manager.getFoodPositionCLI(msg.substring(13));
					}
					else if(msg.startsWith("foodNbr")) {
						manager.getFoodNbrCLI(msg.substring(7));
					}
					else if(msg.startsWith("foodAmount")) {
						manager.getFoodAmountCLI(msg.substring(10));
					}
				}
				//writer.println("stop");
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
				try {
					this.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	public void sendMsg(String msg) {
		
		try {
			OutputStream output = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);
			writer.println(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isRunning() {
		return running;
	}
}
