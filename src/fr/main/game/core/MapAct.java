package fr.main.game.core;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import fr.main.engine.Manager;
import fr.main.engine.components.Button;
import fr.main.engine.graphics.Display;
import fr.main.engine.graphics.Renderer;
import fr.main.game.Activity;

public class MapAct extends Activity implements KeyListener{

	public static final int TO_ORG = 4;
	
	private Button orgBtn;
	private int size;
	private int time;
	private int energy;
	private int muscle;
	private int stem;
	
	private ArrayList<Integer> positions;
	private ArrayList<Integer> sizePlayers;
	private ArrayList<String> namePlayers;
	private ArrayList<Integer> foodPositions;
	private ArrayList<Integer> foodAmounts;
	private int foodNbr;
	private int playersNbr;
	private int id;
	
	public MapAct(Display display, Manager manager) {
		super(display, manager);
		
		orgBtn = new Button(50, 50, 100, 50, "Organisme");
		
		positions = new ArrayList<>();
		sizePlayers = new ArrayList<>();
		namePlayers = new ArrayList<>();
		foodPositions = new ArrayList<>();
		foodAmounts = new ArrayList<>();
		foodNbr = 0;
		playersNbr = 0;
		size = 0;
		time = 0;
		energy = 0;
		muscle = 0;
		stem = 100;
		
	}
	
	public void setPlayersPosition(String cmd) {
		positions.clear();
		String[] p = cmd.split(",");
		for(int i = 0; i<p.length; i++) {
			positions.add(Integer.parseInt(p[i]));
		}
	}
	public void setSizeLabels(String cmd) {
		sizePlayers.clear();
		String[] l = cmd.split(",");
		for(int i = 0; i<l.length; i++) {
			sizePlayers.add(Integer.parseInt(l[i]));
		}
	}
	public void setPlayersName(String cmd) {
		namePlayers.clear();
		String[] n = cmd.split(",");
		for(int i = 0; i<n.length; i++) {
			namePlayers.add(n[i]);
		}
	}
	public void setFoodPosition(String cmd) {
		foodPositions.clear();
		String[] f = cmd.split(",");
		for(int i = 0; i<f.length; i++) {
			foodPositions.add(Integer.parseInt(f[i]));
		}
	}
	public void setFoodAmount(String cmd) {
		foodAmounts.clear();
		String[] f = cmd.split(",");
		for(int i = 0; i<f.length; i++) {
			foodAmounts.add(Integer.parseInt(f[i]));
		}
	}
	public void setFoodNbr(String cmd) {
		foodNbr = Integer.parseInt(cmd);
	}
	public void setId(int id) {
		this.id=id;
	}
	public void setTime(int time) {
		//int delta = time-this.time;
		for(int i = 0; i<playersNbr; i++) {
			if(i!=id) {

				int tx = positions.get(2*i);
				int ty = positions.get(2*i+1);
				int tw = 50+sizePlayers.get(i);
				if(contain(tx, ty, tw, tw)) {
					//Collision detectee
					manager.playerHitOtherCLI(muscle, i);
					
				}
			}
		}
		for(int i = 0; i<foodNbr; i++) {
			int tx = foodPositions.get(2*i);
			int ty = foodPositions.get(2*i+1);
			int tw = 10;
			if(contain(tx,ty,tw,tw)) {
				//Collision detectee
				manager.playerEatFoodCLI(i, foodAmounts.get(i));
			}
		}
		this.time = time;
	}
	public void setEnergy(int energy) {
		this.energy = energy;
	}
	public void setMuscle(int muscle) {
		this.muscle = muscle;
	}
	public void setSize(int size) {
		this.size=size;
	}
	public void setStem(int stem) {
		this.stem=stem;
	}
	public void setPlayersNbr(int nbr) {
		playersNbr = nbr;
	}
	
	private boolean contain(int tx, int ty, int tw, int th) {
		int x = positions.get(2*id);
		int y = positions.get(2*id+1);
		int w = 50+sizePlayers.get(id);
		int h = w;
		
		//System.out.println("x:"+x+" y:"+y+" w:"+w+" / tx:"+tx+" ty:"+ty+" tw:"+tw);
		
		return ((tx>x && tx<x+w && ty>y && ty<y+h) || (tx+tw>x && tx+tw<x+w && ty>y && ty<y+h)
				|| (tx>x && tx<x+w && ty+th>y && ty+th<y+h) || (tx+tw>x && tx+tw<x+w && ty+th>y && ty+tw<y+h));
	}
	
	public void death() {
		System.out.println("mort");
	}
	
	private void renderPlayer(Renderer renderer, int id) {
		try{
			int x = positions.get(id*2);
			int y = positions.get(id*2+1);
			int size = sizePlayers.get(id);
			renderer.fillRect(x, y, 50+(Math.min(size, 1000)), 50+(Math.min(size, 1000)), Color.BLACK);
			renderer.fillRect(x+1, y+1, 50+(Math.min(size, 1000))-2, 50+(Math.min(size, 1000))-2, Color.PINK);
			renderer.drawCircle(x+20, y+30, 12, Color.BLACK);
			renderer.drawCircle(x+21, y+31, 10, Color.RED);
			renderer.drawText(namePlayers.get(id), x+10, y+20);
		}catch(IndexOutOfBoundsException e) {
			System.out.println("Erreur position. Taille du tableau position: "+positions.size()+" (form x,y) pour :"+playersNbr+" joueurs.");
		}
		//renderer.drawText("Size "+sizePlayers.get(id), positions.get(2*id)+10, positions.get(2*id+1)+20);
	}
	
	private void renderFood(Renderer renderer) {
		for(int i = 0; i<foodNbr; i++) {
			try{
				int x = foodPositions.get(2*i);
				int y = foodPositions.get(2*i+1);
				int size = 10;
				renderer.fillRect(x, y, size, size, Color.BLACK);
				renderer.fillRect(x+1, y+1, size-2, size-2, Color.YELLOW);
			} catch(IndexOutOfBoundsException | NullPointerException e) {
				System.out.println("Erreur position nourriture.");
			}
			
		}
	}
	
	@Override
	public void render(Renderer renderer) {

		renderer.setBackgroundColor(new Color(102,179,255));
		for(int i = 0; i<playersNbr; i++) {
			if(i!=id) renderPlayer(renderer, i);
		}
		renderPlayer(renderer, id);
		renderFood(renderer);
		
		renderer.drawText("Temps: "+time, 230, 50);
		renderer.drawText("Energie: "+energy, 400, 50);
		renderer.drawText("Muscle: "+muscle, 550, 50);
		renderer.drawText("Taille: "+size, 650, 50);
		renderer.drawText("Cellules souches: "+stem, 800, 50);
		orgBtn.render(renderer);
		
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
		canvas.addKeyListener(this);
		canvas.removeMouseListener(this);
		manager.update();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int mx = e.getX(); int my = e.getY();
		if(SwingUtilities.isLeftMouseButton(e)) {
			if(orgBtn.contain(mx, my)) {
				state = TO_ORG;
				leave();
			}
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		manager.playerMoveCLI(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
