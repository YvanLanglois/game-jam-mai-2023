package fr.main;

import fr.main.engine.Run;
import fr.main.network.Client;
import fr.main.network.ServerMaster;

public class Main {

	public static void main(String[] args) {
	
		new Run().start();
		
		/*
		boolean isServer = false;
		if(isServer) {
			ServerMaster master = new ServerMaster();
			master.startServer();	
		}
		else {
			Client cli = new Client(6565, "localhost");
			cli.start();
		}
		*/
		
	}
	

}
