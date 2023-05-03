package fr.main.game.core;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import fr.main.engine.Manager;
import fr.main.engine.components.Button;
import fr.main.engine.components.TextField;
import fr.main.engine.components.Utils;
import fr.main.engine.graphics.Display;
import fr.main.engine.graphics.Renderer;
import fr.main.game.Activity;

public class OrgAct extends Activity implements KeyListener{

	public static final int TO_MAP = 4, TO_TECH = 5;
	
	private TextField nameField;
	private Button validateNameBtn;
	private String nameLabel;
	
	private Button mapBtn;
	private Button techBtn;
	private Button increaseMuscleBtn;
	private Button increaseNeuronBtn;
	private Button increaseVegBtn;
	private Button increaseCarBtn;
	
	private String timeLabel;
	private String energyLabel;
	private String sizeLabel;
	private String stemCellNbrLabel;
	private String muscleCellNbrLabel;
	private String neuronCellNbrLabel;
	private String vegCellNbrLabel;
	private String carCellNbrLabel;
	
	private int time;
	private int energy;
	private int size;
	private int stemCellNbr;
	private int muscleCellNbr;
	private int neuronCellNbr;
	private int vegCellNbr;
	private int carCellNbr;
	
	private int coeffEnergy;
	private int coeffVeg;
	private int coeffCar;

	private int coeffConsCar;
	private int coeffConsVeg;
	private int stemCellRate;
	
	private boolean isShiftPressed;
	
	public OrgAct(Display display, Manager manager) {
		super(display, manager);
		
		init();
		
		update();
		mapBtn = new Button(50, 50, 100, 30, "Carte");
		techBtn = new Button(200, 50, 100, 30, "Evolution");
		increaseMuscleBtn = new Button(50, 200, 20, 20, "+");
		increaseNeuronBtn = new Button(50, 250, 20, 20, "+");
		increaseVegBtn = new Button(50, 300, 20, 20, "+");
		increaseCarBtn = new Button(50, 350, 20, 20, "+");
		nameLabel = "Cell";
		nameField = new TextField(300, 500, 200, 30, 6, nameLabel);
		validateNameBtn = new Button(550, 500, 30, 30, "OK");
		
		isShiftPressed = false;
		
	}
	
	private void init() {

		time = 0;
		energy = 1000;
		stemCellNbr = 100;
		muscleCellNbr = 0;
		neuronCellNbr = 0;
		vegCellNbr = 0;
		carCellNbr = 0;
		
		coeffEnergy = 5;
		coeffVeg = 2;
		coeffCar = 3;
		coeffConsCar = 5;
		coeffConsVeg = 3;
		stemCellRate = 1;
		
	}
	
	public void enablePhoto() {
		coeffVeg = 10;
	}
	public void enableDig() {
		coeffConsCar = 5;
	}
	public void enableDivcel() {
		stemCellRate = 4;
	}
	public void playerDash() {
		energy-=30;
		manager.updateDataCLI(energy, muscleCellNbr, size, stemCellNbr);
	}
	public void death() {
		init();
	}
	
	public void hitOther() {
		energy+=carCellNbr*coeffCar;
	}
	public void eatFood(int amount) {
		energy+=carCellNbr*coeffCar;
	}
	public void setTime(int time) {
		int delta = time-this.time;
		energy+=delta*vegCellNbr*coeffVeg;
		energy+=delta*carCellNbr*coeffCar;
		energy-=delta*((neuronCellNbr+muscleCellNbr)*coeffEnergy+vegCellNbr*coeffConsVeg+carCellNbr*coeffConsCar);
		stemCellNbr+=(Math.random()>=0.7)?stemCellRate*(int)(6*Math.random()):0;
		if(energy<0) manager.playerLost();
		
		manager.updateDataCLI(energy, muscleCellNbr, size, stemCellNbr);
		this.time = time;
		update();
	}
	public void setEnergy(int energy) {
		this.energy=energy;
	}
	public void addEnergy(int energy) {
		this.energy += energy;
	}
	public void setMuscleNbr(int muscle) {
		this.muscleCellNbr = muscle;
	}
	public void addStemCellNbr(int stemNbr) {
		stemCellNbr += stemNbr;
	}
	public int getMuscleCellNbr() {
		return muscleCellNbr;
	}
	public int getNeuronCellNbr() {
		return neuronCellNbr;
	}
	public int getVegCellNbr() {
		return vegCellNbr;
	}
	public int getCarCellNbr() {
		return carCellNbr;
	}
	public int getSize() {
		return size;
	}
	
	public void update() {
		timeLabel = "Temps: "+time;
		energyLabel = "Energie: "+energy;
		stemCellNbrLabel = "Cellules souches: " + stemCellNbr;
		muscleCellNbrLabel = "Muscle: "+muscleCellNbr;
		neuronCellNbrLabel = "Neurones: "+neuronCellNbr;
		vegCellNbrLabel = "Vegetales: "+vegCellNbr;
		carCellNbrLabel = "Carnivores: "+carCellNbr;
		size = muscleCellNbr+neuronCellNbr+vegCellNbr+carCellNbr;
		sizeLabel = "Taille: "+size;
		
	}

	public void updateToSrv() {
		manager.sendMsgToSrv("clientSize"+size);
		manager.sendMsgToSrv("clientName"+nameLabel);
	}
	
	@Override
	public void render(Renderer renderer) {
		
		renderer.setBackgroundColor(Renderer.bckColor);
		
		renderer.drawImg(Utils.loadImg("./res/pen.png"), 700, 50, 150, 150);
		
		renderer.drawText(timeLabel, 500, 70);
		renderer.drawText(energyLabel, 350, 70);
		renderer.drawText(sizeLabel, 600, 70);
		renderer.drawText(stemCellNbrLabel, 50, 165);
		renderer.drawText(muscleCellNbrLabel, 80, 215);
		renderer.drawText(neuronCellNbrLabel, 80, 265);
		renderer.drawText(vegCellNbrLabel, 80, 315);
		renderer.drawText(carCellNbrLabel, 80, 365);
		
		increaseMuscleBtn.render(renderer);
		increaseNeuronBtn.render(renderer);
		increaseVegBtn.render(renderer);
		increaseCarBtn.render(renderer);
		
		renderer.fillRect(400, 200, 50+(Math.min(size, 1000)), 50+(Math.min(size, 1000)), Color.BLACK);
		renderer.fillRect(401, 201, 50+(Math.min(size, 1000))-2, 50+(Math.min(size, 1000))-2, Color.PINK);
		renderer.drawCircle(420, 230, 12, Color.BLACK);
		renderer.drawCircle(421, 231, 10, Color.RED);
		renderer.drawText(nameLabel, 410, 220);
		nameField.render(renderer);
		validateNameBtn.render(renderer);
		
		mapBtn.render(renderer);
		techBtn.render(renderer);

	}
	
	@Override
	public void setState(int state) {
		this.state = state;
		if(state == ACTIVE_STATE) {
			canvas.addKeyListener(this);
			canvas.addMouseListener(this);
		}
	}
	
	@Override
	protected void leave() {
		nameField.destroy(canvas);
		nameField.select(false);
		canvas.removeKeyListener(this);
		canvas.removeMouseListener(this);
		manager.update();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int mx = e.getX(); int my = e.getY();
		if(SwingUtilities.isLeftMouseButton(e)) {
			if(mapBtn.contain(mx, my)) {
				manager.updateDataCLI(energy, muscleCellNbr, size, stemCellNbr);
				state = TO_MAP;
				leave();
			}
			else if (techBtn.contain(mx, my)) {
				state = TO_TECH;
				leave();
			}
			else if(increaseMuscleBtn.contain(mx, my)) {
				int temp = (isShiftPressed)?10:1;
				if(stemCellNbr>=temp) {
					muscleCellNbr+=temp;
					stemCellNbr-=temp;
				}
				update();
				updateToSrv();
			}
			else if(increaseNeuronBtn.contain(mx, my)) {
				int temp = (isShiftPressed)?10:1;
				if(stemCellNbr>=temp) {
					neuronCellNbr+=temp;
					stemCellNbr-=temp;
				}
				update();
				updateToSrv();
			}
			else if(increaseVegBtn.contain(mx, my)) {
				int temp = (isShiftPressed)?10:1;
				if(stemCellNbr>=temp) {
					vegCellNbr+=temp;
					stemCellNbr-=temp;
				}
				update();
				updateToSrv();
			}
			else if(increaseCarBtn.contain(mx, my)) {
				int temp = (isShiftPressed)?10:1;
				if(stemCellNbr>=temp) {
					carCellNbr+=temp;
					stemCellNbr-=temp;
				}
				update();
				updateToSrv();
			}
			else if(validateNameBtn.contain(mx, my)) {
				nameLabel = nameField.getTxt();
			}
			
			if(nameField.contain(mx, my)) {
				nameField.select(true);
			} else {
				nameField.select(false);
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

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_SHIFT) isShiftPressed = true;
		nameField.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_SHIFT) isShiftPressed = false;
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	

}
