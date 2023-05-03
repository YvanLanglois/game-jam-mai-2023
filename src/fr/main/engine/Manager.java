package fr.main.engine;

import java.util.ArrayList;

import fr.main.engine.graphics.Display;
import fr.main.engine.graphics.Renderer;
import fr.main.game.Activity;
import fr.main.game.core.MapAct;
import fr.main.game.core.MasterAct;
import fr.main.game.core.OrgAct;
import fr.main.game.core.TechAct;
import fr.main.game.intro.HostAct;
import fr.main.game.intro.IntroAct;
import fr.main.game.intro.JoinAct;
import fr.main.network.Client;
import fr.main.network.ServerMaster;
import fr.main.network.ServerWorker;

public class Manager {

	private final int INTRO_ACT = 0, HOST_ACT = 1, JOIN_ACT = 2, MAP_ACT = 3, ORG_ACT = 4, TECH_ACT = 5, MASTER_ACT = 6;
	private int currentAct;

	private Run run;
	private ServerMaster server;
	private Client client;

	private ArrayList<ServerWorker> workers;
	private FoodManager foodManager;
	
	private IntroAct introAct;
	private HostAct hostAct;
	private JoinAct joinAct;
	private MasterAct masterAct;
	private OrgAct orgAct;
	private MapAct mapAct;
	private TechAct techAct;
	
	private int time;
	public Manager(Display display, Run run) {
		this.run = run;
		server = new ServerMaster(6565);
		client = new Client(6565, "localhost", this);
		workers = new ArrayList<>();

		foodManager = new FoodManager(this);
		
		introAct = new IntroAct(display, this);
		hostAct = new HostAct(display, this);
		joinAct = new JoinAct(display, this);
		masterAct = new MasterAct(display, this);
		orgAct = new OrgAct(display, this);
		mapAct = new MapAct(display, this);
		techAct = new TechAct(display, this);
		
		currentAct = INTRO_ACT;
		introAct.setState(Activity.ACTIVE_STATE);
	}
	
	public void render(Renderer renderer) {
		if(currentAct==INTRO_ACT) introAct.render(renderer);
		if(currentAct==HOST_ACT) hostAct.render(renderer);
		if(currentAct==JOIN_ACT) joinAct.render(renderer);
		if(currentAct==MASTER_ACT) masterAct.render(renderer);
		if(currentAct==ORG_ACT) orgAct.render(renderer);
		if(currentAct==MAP_ACT) mapAct.render(renderer);
		if(currentAct==TECH_ACT) techAct.render(renderer);
	}
	
	
	public void update() {
		if(currentAct==INTRO_ACT) {
			if(introAct.getState()==IntroAct.TO_HOST) {
				currentAct = HOST_ACT;
				hostAct.setState(Activity.ACTIVE_STATE);
			}
			else if(introAct.getState()==IntroAct.TO_JOIN) {
				currentAct = JOIN_ACT;
				joinAct.setState(Activity.ACTIVE_STATE);
			}
			else if(introAct.getState()==IntroAct.TO_EXIT) {
				run.stop();
				System.exit(0);
			}
		}
		else if (currentAct == HOST_ACT) {
			if(hostAct.getState()==HostAct.TO_BACK) {
				currentAct = INTRO_ACT;
				introAct.setState(Activity.ACTIVE_STATE);
			}
			else if (hostAct.getState()==HostAct.TO_LAUNCH) {
				startServer(hostAct.getPort());
				startUpdateSRV();
				currentAct = MASTER_ACT;
				masterAct.setState(MasterAct.ACTIVE_STATE);
			}
		}
		else if (currentAct == JOIN_ACT) {
			if(joinAct.getState()==JoinAct.TO_BACK) {
				currentAct = INTRO_ACT;
				introAct.setState(Activity.ACTIVE_STATE);
			}
			else if(joinAct.getState()==JoinAct.TO_LAUNCH) {
				startClient(joinAct.getPort(), joinAct.getHostname());
				currentAct = ORG_ACT;
				orgAct.setState(Activity.ACTIVE_STATE);
			}
		}
		else if (currentAct == ORG_ACT) {
			if(orgAct.getState()==OrgAct.TO_MAP) {
				currentAct = MAP_ACT;
				sendMsgToSrv("askPlayerPositions");
				sendMsgToSrv("askPlayersNbr");
				orgAct.updateToSrv();
				mapAct.setState(Activity.ACTIVE_STATE);
			}
			else if(orgAct.getState()==OrgAct.TO_TECH) {
				currentAct = TECH_ACT;
				techAct.setNeurontot(orgAct.getNeuronCellNbr());
				techAct.setState(Activity.ACTIVE_STATE);
			}
		}
		else if (currentAct == MAP_ACT) {
			if(mapAct.getState()==MapAct.TO_ORG) {
				currentAct = ORG_ACT;
				orgAct.setState(Activity.ACTIVE_STATE);
			}
		}
		else if (currentAct == TECH_ACT) {
			if(techAct.getState()==TechAct.TO_ORG) {
				currentAct = ORG_ACT;
				orgAct.setState(Activity.ACTIVE_STATE);
			}
		}
	}
	
	/*
	 * 
	 * SERVER
	 * 
	 * */
	
	public void startServer(int port) {
		if(server.isRunning()) return;
		server.setPort(port);
		server.startServer(this);		
	}
	
	public void stopServer() {
	}
	
	public void broadcastMsg(String msg) {
		//System.out.println("here: "+msg);
		for(int i = 0; i<workers.size(); i++) {
			workers.get(i).sendMsg(msg);
		}
	}
	
	public void serverNewClient(ServerWorker e) {
		workers.add(e);
		masterAct.addPlayer(e.getClientAddr());
	}
	
	public void getPlayersPositionSRV() {
		StringBuffer cmd = new StringBuffer();
		cmd.append("playersPosition");
		for(int i = 0; i<workers.size(); i++) {
			cmd.append(workers.get(i).getX());
			cmd.append(",");
			cmd.append(workers.get(i).getY());
			cmd.append(",");
		}
		broadcastMsg(cmd.toString());
		cmd = new StringBuffer();
		cmd.append("playersSize");
		for(int i = 0; i<workers.size(); i++) {
			cmd.append(workers.get(i).getSize());
			cmd.append(",");
		}
		broadcastMsg(cmd.toString());
		cmd = new StringBuffer();
		cmd.append("playersName");
		for(int i = 0; i<workers.size(); i++) {
			cmd.append(workers.get(i).getPlayerName());
			cmd.append(",");
		}
		broadcastMsg(cmd.toString());
	}
	
	public void getPlayersNbrSRV() {
		broadcastMsg("playersNbr"+workers.size());
	}
	
	public void increaseTimeSRV() {
		time++;
		broadcastMsg("updateTime"+time);
	}
	
	public void updateTimeSRV(int newTime) {
		time=newTime;
		broadcastMsg("updateTime"+time);
	}
	
	private void startUpdateSRV() {
		foodManager.start();
	}
	
	public void playerHitOtherSRV(int muscle, int target) {
		workers.get(target).sendMsg("playerHitOtherSRV"+muscle);
	}
	public void getFoodPositionSRV() {
		StringBuffer cmd = new StringBuffer("foodPositions");
		for(int i = 0; i<foodManager.getSize(); i++) {
			cmd.append(foodManager.getX(i));
			cmd.append(",");
			cmd.append(foodManager.getY(i));
			cmd.append(",");
		}
		broadcastMsg(cmd.toString());
		cmd = new StringBuffer("foodAmount");
		for(int i = 0; i<foodManager.getSize(); i++) {
			cmd.append(foodManager.getFoodQte(i));
			cmd.append(",");
		}
		broadcastMsg(cmd.toString());
	}
	public void getFoodNbrSRV() {
		StringBuffer cmd = new StringBuffer("foodNbr");
		cmd.append(foodManager.getSize());
		broadcastMsg(cmd.toString());
	}
	public void playerEatFoodSRV(String cmd) {
		String[] data = cmd.split(",");
		int id = Integer.parseInt(data[0]);
		int carCellNbr = Integer.parseInt(data[1]);
		foodManager.playerEatFood(id, carCellNbr);
	}
	public void setFoodNbr(int n) {
		foodManager.setFoodNbr(n);
	}
	
	/*
	 * 
	 * CLIENT
	 * 
	 * */
	
	public void sendMsgToSrv(String msg) {
		client.sendMsg(msg);
	}
	
	public void setWorkerId(int id) {
		mapAct.setId(id);
	}
	public void getFoodPositionCLI(String cmd) {
		mapAct.setFoodPosition(cmd);
	}
	public void getFoodNbrCLI(String cmd) {
		mapAct.setFoodNbr(cmd);
	}
	public void getPlayersPositionCLI(String cmd) {
		mapAct.setPlayersPosition(cmd);
	}
	public void getFoodAmountCLI(String cmd) {
		mapAct.setFoodAmount(cmd);
	}
	public void getPlayersNameCLI(String cmd) {
		mapAct.setPlayersName(cmd);
	}
	public void getPlayersNbrCLI(int nbr) {
		mapAct.setPlayersNbr(nbr);
	}
	public void playerMoveCLI(int keyCode) {
		client.sendMsg("playerMove"+keyCode);
	}
	public void getPlayerSizeCLI(String cmd) {
		mapAct.setSizeLabels(cmd);
	}
	public void updatePlayerSize() {
		orgAct.updateToSrv();
	}
	public void playerLost() {
		orgAct.death();
		mapAct.death();
		techAct.death();
		client.sendMsg("playerLost");
	}
	public void updateTimeCLI(int time) {
		this.time = time;
		orgAct.setTime(time);
		mapAct.setTime(time);
	}
	public void updateDataCLI(int energy, int muscle, int size, int stem) {
		mapAct.setEnergy(energy);
		mapAct.setSize(size);
		mapAct.setMuscle(muscle);
		mapAct.setStem(stem);
		orgAct.setMuscleNbr(muscle);
		orgAct.setEnergy(energy);
	}
	public void playerHitOtherCLI(int muscle, int target) {
		client.sendMsg("playerHitOtherCLI"+muscle+","+target);
		orgAct.hitOther();
	}
	public void playerHittenByOther(int amount) {
		orgAct.addEnergy(-amount);
	}
	public void playerEatFoodCLI(int id, int amount) {
		int carCellNbr = orgAct.getCarCellNbr();
		orgAct.eatFood(amount);
		client.sendMsg("playerEatFood"+id+","+carCellNbr);
	}
	public void enablePhoto() {
		orgAct.enablePhoto();
	}
	public void enableDig() {
		orgAct.enableDig();
	}
	public void enableDivcel() {
		orgAct.enableDivcel();
	}
	public void enableDash() {
		client.sendMsg("enableDash");
	}
	public void playerDash() {
		orgAct.playerDash();
	}
	
	public void startClient(int port, String hostname) {
		if(client.isRunning()) return;
		client.setPort(port);
		client.setHostname(hostname);
		client.start();
	}
	
	
	
}
