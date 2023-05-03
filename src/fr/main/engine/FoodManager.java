package fr.main.engine;

import java.util.ArrayList;

public class FoodManager extends Thread{
	
	
	private boolean running;
	private Manager manager;

	private ArrayList<Integer> foodQte;
	private ArrayList<Integer> foodPositions;
	private ArrayList<Integer> directions;
	
	private int totFood;
	
	public FoodManager(Manager manager) {
		this.manager = manager;
		
		foodPositions = new ArrayList<>();
		foodQte = new ArrayList<>();
		directions = new ArrayList<>();
	
		totFood = 100;
		
		init();
		
		running = false;
	}
	
	private void init() {
		for(int i = 0; i<totFood; i++) {
			foodPositions.add(400+(int)(200*Math.random()));
			foodPositions.add((int)(200*Math.random())-500);
			directions.add((int)(2*Math.random()));
			directions.add((int)(2*Math.random()));
			directions.add((int)(2*Math.random()));
			directions.add((int)(2*Math.random()));
			foodQte.add(10);
		}
	}
	
	public int getSize() {
		return foodQte.size();
	}
	public int getX(int id) {
		return foodPositions.get(2*id);
	}
	public int getY(int id) {
		return foodPositions.get(2*id+1);
	}
	public int getFoodQte(int id) {
		return foodQte.get(id);
	}
	public void playerEatFood(int id, int carCellNbr) {
		foodQte.set(id, foodQte.get(id)-carCellNbr);
		if(foodQte.get(id)<0) {
			foodQte.set(id, 10);
			foodPositions.set(2*id, (int)(400+300*Math.random()));
			foodPositions.set(2*id+1, (int)(200+400*Math.random()));
		}
	}
	public void setFoodNbr(int n) {
		foodQte.clear();
		foodPositions.clear();
		directions.clear();
		totFood = n;
		init();
		
	}
	
	@Override
	public void run() {
		running = true;
		
		while(running) {
			for(int i = 0; i<foodQte.size(); i++) {
				int x = foodPositions.get(2*i);
				int y = foodPositions.get(2*i+1);
				if(x<-100) x +=2000;
				else if(x>2200) x-=2250;
				if(y>1100) y -= 1150;
				else if (y<-100) y+=1000;
				foodPositions.set(2*i, x+directions.get(4*i)-directions.get(4*i+1));
				foodPositions.set(2*i+1, y+directions.get(4*i+2)-directions.get(4*i+3));

				if(Math.random()<0.2) {
					int e = directions.get(4*i);
					directions.set(4*i, e+(int)(1.5*Math.random()));
					if(directions.get(4*i)>2) directions.set(4*i, 2);
					
				}
				else if(Math.random()>0.8) {
					int e = directions.get(4*i);
					directions.set(4*i, e-(int)(1.5*Math.random()));
					if(directions.get(4*i)<0) directions.set(4*i, 0);
				}
				if(Math.random()<0.2) {
					int s = directions.get(4*i+2);
					directions.set(4*i+2, s+(int)(1.5*Math.random()));
					if(directions.get(4*i+2)>2) directions.set(4*i+2, 2);
				}
				else if(Math.random()>0.8) {
					int s = directions.get(4*i+2);
					directions.set(4*i+2, s-(int)(1.5*Math.random()));
					if(directions.get(4*i+2)<0) directions.set(4*i+2, 0);
				}
				
				
			}
			manager.getFoodPositionSRV();
			manager.getFoodNbrSRV();
			try {
				this.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
