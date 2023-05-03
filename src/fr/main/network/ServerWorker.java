package fr.main.network;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import fr.main.engine.Manager;
import fr.main.game.core.DashTimer;

public class ServerWorker extends Thread{

	private Socket socket;
	private Manager manager;
	private boolean running;
	private int id;
	
	private int maxSpeed;
	private int x, y, speed, dash;
	private int size;
	private DashTimer dashTimer;
	private boolean dashEnabled;
	private String name;
	
	public ServerWorker(Socket socket, Manager manager, int id) {
		this.socket=socket;
		this.manager = manager;
		manager.serverNewClient(this);
		running = true;
		this.id=id;
		
		init();
	}
	
	private void init() {
		x = (int) (50+150*Math.random());
		y = (int) (150+150*Math.random());
		name = "Cell";
		size = 0;
		maxSpeed = 15;
		dash = 0;
		dashTimer = new DashTimer(this);
		dashEnabled = false;
		speed = Math.min(maxSpeed,3+(int)(size/maxSpeed));
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getSize() {
		return size;
	}
	public String getPlayerName() {
		return name;
	}
	
	@Override
	public void run() {
		InputStream input;
		try {
			System.out.println("New client");
			
			input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			
			sendMsg("workerId"+id);
			
			while(running) {
				String msg = reader.readLine();
				System.out.println(msg);

				if(msg.startsWith("askPlayerPositions")) {
					manager.getPlayersPositionSRV();
					
				}
				else if(msg.startsWith("askPlayersNbr")) {
					manager.getPlayersNbrSRV();
				}
				else if(msg.startsWith("playerMove")) {
					int keyCode = Integer.parseInt(msg.substring(10));
					if(keyCode==KeyEvent.VK_Z) y-=speed;
					else if(keyCode==KeyEvent.VK_S) y+=speed+dash;
					else if(keyCode==KeyEvent.VK_Q) x-=speed+dash;
					else if(keyCode==KeyEvent.VK_D) x+=speed+dash;
					else if(keyCode==KeyEvent.VK_SPACE && dashEnabled && !dashTimer.isBusy()) {
						try{
							dashTimer = new DashTimer(this);
							dashTimer.start();
							dash = 20;
							sendMsg("playerDash");
						} catch (IllegalThreadStateException e) {
							System.out.println("Illegal thread exception ERROR ! ");
						}
					}
					manager.getPlayersPositionSRV();
					sendMsg("askClientSize");
				}
				else if(msg.startsWith("enableDash")) {
					dashEnabled = true;
				}
				else if(msg.startsWith("clientSize")) {
					size = Integer.parseInt(msg.substring(10));
				}
				else if(msg.startsWith("clientName")) {
					name = msg.substring(10);
					manager.getPlayersPositionSRV();
				}
				else if(msg.startsWith("playerLost")) {
					init();
					sendMsg("askClientSize");
				}
				else if(msg.startsWith("playerHitOtherCLI")) {
					String[] str = msg.substring(17).split(",");
					manager.playerHitOtherSRV(Integer.parseInt(str[0]), Integer.parseInt(str[1]));
				}
				else if(msg.startsWith("playerEatFood")) {
					manager.playerEatFoodSRV(msg.substring(13));
				}
				//if(msg.contains("hey"))writer.println("yo");
				//if(msg=="stop") end();				
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void resetDash() {
		dash = 0;
	}
	public String getClientAddr() {
		return socket.getInetAddress().getCanonicalHostName();
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

	public void end() {
		try {
			socket.close();
			this.join();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
