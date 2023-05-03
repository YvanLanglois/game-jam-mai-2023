package fr.main.game.core;

import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import fr.main.engine.Manager;
import fr.main.engine.components.Button;
import fr.main.engine.components.CheckButton;
import fr.main.engine.components.Utils;
import fr.main.engine.graphics.Display;
import fr.main.engine.graphics.Renderer;
import fr.main.game.Activity;

public class TechAct extends Activity{

	public static final int TO_ORG = 4;
	
	private Button orgBtn;
	private int neuronUsed, neuronTot;

	private CheckButton photoCheck;
	private String photoLabel;
	private CheckButton divcelCheck;
	private String divcelLabel;
	private CheckButton digCheck;
	private String digLabel;
	private CheckButton dashCheck;
	private String dashLabel;
	
	public TechAct(Display display, Manager manager) {
		super(display, manager);
		neuronUsed = 0; neuronTot = 0;
		
		photoCheck = new CheckButton(100, 150, 150, 30, "Photosynthèse");
		photoLabel = "Prix : 10 neurones. Améliore considérablement l'efficacité des cellules végétales";
		divcelCheck = new CheckButton(100, 200, 150, 30, "Division cellulaire");
		divcelLabel = "Prix: 50 neurones. Augmente le nombre de cellules souches créee";
		digCheck = new CheckButton(100, 250, 150, 30, "Digestion");
		digLabel = "Prix: 10 neurones. Diminue la consommation des cellules carnivores";
		dashCheck = new CheckButton(100, 300, 150, 30, "Dash");
		dashLabel = "Prix: 20 neurones. Donne une petite accélération en échange d'énergie (appuyer sur espace)";
		orgBtn = new Button(50, 20, 100, 30, "Organisme");
	}
	
	public void setNeurontot(int n) {
		neuronTot = n;
	}
	
	public void death() {
		neuronUsed = 0;
		neuronTot = 0;
		dashCheck.reset();
		digCheck.reset();
		photoCheck.reset();
		divcelCheck.reset();
		state = TO_ORG;
		leave();
	}
	
	@Override
	public void render(Renderer renderer) {
		
		renderer.drawImg(Utils.loadImg("./res/dna.png"), 400, 10, 400, 100);

		renderer.drawText("Neurones : "+neuronUsed+" / "+neuronTot, 200, 40);

		photoCheck.render(renderer);
		renderer.drawText(photoLabel, 300, 170);
		divcelCheck.render(renderer);
		renderer.drawText(divcelLabel, 300, 220);
		digCheck.render(renderer);
		renderer.drawText(digLabel, 300, 270);
		dashCheck.render(renderer);
		renderer.drawText(dashLabel, 300, 320);
		
		orgBtn.render(renderer);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int mx = e.getX(); int my = e.getY();
		if(SwingUtilities.isLeftMouseButton(e)) {
			if(orgBtn.contain(mx, my)) {
				state = TO_ORG;
				leave();
			}
			else if(photoCheck.contain(mx, my) && neuronUsed+10<=neuronTot) {
				photoCheck.check();
				neuronUsed+=10;
				manager.enablePhoto();
			}
			else if(divcelCheck.contain(mx, my) && neuronUsed+50<=neuronTot) {
				divcelCheck.check();
				neuronUsed+=50;
				manager.enableDivcel();
			}
			else if(digCheck.contain(mx, my) && neuronUsed+10<=neuronTot) {
				digCheck.check();
				neuronUsed+=10;
				manager.enableDig();
			}
			else if(dashCheck.contain(mx, my) && neuronUsed+20<=neuronTot) {
				dashCheck.check();
				neuronUsed+=20;
				manager.enableDash();
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

	

}
