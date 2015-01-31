import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;


public class Ants {
	private int identifier; 
	private int lifespan = 3650; //(365 day lifespan * 10 turns each day) 
	private int numberOfTurnsTaken = 0; 
	private boolean isAlive = true;
	private int xCoordinate; 
	private int yCoordinate;
	private String stringIdentifier;
	
	public int getNumberOfTurnsTaken() { return numberOfTurnsTaken; }
	public void setNumberOfTurnsTaken(int x) {
		numberOfTurnsTaken = x;
	}
	public boolean getIsAlive() { return this.isAlive; }
	
	public void setIsAlive(boolean x) {
		this.isAlive = x;
	}
	
	public void setStringIdentifier(String i) {
		this.stringIdentifier = i;
	}
	
	public String getStringIdentifier() { return this.stringIdentifier; }
	
	public int getIdentifier() {
		return identifier;
	}
	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}
	public int getLifespan() {
		return lifespan;
	}
	public void setLifespan(int lifespan) {
		this.lifespan = lifespan;
	}
	public int getxCoordinate() {
		return xCoordinate;
	}
	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	public int getyCoordinate() {
		return yCoordinate;
	}
	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	
	public void attack(ColonyNodeView[][] nodes, int newx, int newy, ArrayList<Ants> ants) {
		int chance = getPercentage();
		
		if ((this) instanceof Bala) {
			for (int i = 0; i < nodes[newx][newy].antsInThisNode.size(); i++){
				if (nodes[newx][newy].antsInThisNode.get(i).getStringIdentifier() != this.getStringIdentifier()) {
					if (chance <= 50) {
						int index = 0;
						for (Ants a : ants) {
							if (a.getIdentifier() == nodes[newx][newy].antsInThisNode.get(i).getIdentifier()) {
								updateNodesAntCount(nodes, ants, a.getStringIdentifier(), newx, newy);
								ants.remove(index);
								nodes[newx][newy].antsInThisNode.remove(i);//Remove from this node'ss ArrayList of ants
								break;
							} else {
								index++;
							}
						}
					}
					break;
				}
			}
		} else if ((this) instanceof Soldier) {
			for (int i = 0; i < nodes[newx][newy].antsInThisNode.size(); i++){
				if (nodes[newx][newy].antsInThisNode.get(i).getStringIdentifier() == "Bala") {
					if (chance <= 50) {
						int index = 0;
						for (Ants a : ants) {
							if (a.getIdentifier() == nodes[newx][newy].antsInThisNode.get(i).getIdentifier()) {
								updateNodesAntCount(nodes, ants, a.getStringIdentifier(), newx, newy);
								ants.remove(index);
								nodes[newx][newy].antsInThisNode.remove(i);//Remove from this node'ss ArrayList of ants
								break;
							} else {
								index++;
							}
						}
						break;
					}
				}
			}
		}
	}
	
	public void updateNodesAntCount(ColonyNodeView[][] nodes, ArrayList<Ants> ants, String type, int newx, int newy) {
		if (type == "Soldier") {
			nodes[newx][newy].numSoldiers--;
			nodes[newx][newy].setSoldierCount(nodes[newx][newy].getSoldierCount());
			if (nodes[newx][newy].getSoldierCount() == 0) {
				nodes[newx][newy].hideSoldierIcon();
			}
		} else if (type == "Scout") {
			nodes[newx][newy].numScouts--;
			nodes[newx][newy].setScoutCount(nodes[newx][newy].getScoutCount());
			if (nodes[newx][newy].getScoutCount() == 0) {
				nodes[newx][newy].hideScoutIcon();
			}
		} else if (type == "Forager") {
			nodes[newx][newy].numForagers--;
			nodes[newx][newy].setForagerCount(nodes[newx][newy].getForagerCount());
			if (nodes[newx][newy].getForagerCount() == 0) {
				nodes[newx][newy].hideScoutIcon();
			}
		} else if (type == "Bala") {
			nodes[newx][newy].numBalas--;
			nodes[newx][newy].setBalaCount(nodes[newx][newy].getBalaCount());
			if (nodes[newx][newy].getBalaCount() == 0) {
				nodes[newx][newy].hideBalaIcon();
			}
		} else if (type == "Queen") {
			ants.get(0).setIsAlive(false);
		}
	}
	
	public int getPercentage() {
		Random randNum = new Random();
		int rand = randNum.nextInt(100) + 1;
		return rand;
	}
	
	public static int getRandomNumber(int x, int y) {
		Random randNum = new Random();
		int minimum = x;
		int maximum = y;
		
		return minimum + randNum.nextInt(maximum - minimum +1);
	}
	
	public int getRandomNumber (int n) {
		Random randNum = new Random();
		int number;
		int minimum = 0;
		int maximum = 0;
		
		if ((n > 0) && (n < 26)) {
			 minimum = n-1;
			 maximum = n+1;
		} else if (n == 0) {
			minimum = n;
			maximum = n+1;
		} else {
			minimum = n-1; 
			maximum = n;
		}
		
		number = minimum + randNum.nextInt(maximum - minimum +1);
		return number;
	}
	
	
	
	
}
