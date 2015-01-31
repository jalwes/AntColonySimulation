import java.util.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;
public class Queen extends Ants {
	private boolean isAlive;
	private int causeOfDeath = 0;
	
	Queen() {
		setStringIdentifier("Queen");
		isAlive = true;
		setLifespan(73000); //365 days in a year * 10 turns a day * 20 year lifespan
	}
	
	public void setIsAlive(boolean alive) {
		isAlive = alive;
	}
	
	public boolean getIsAlive () { return isAlive; }
	
	public void setCauseOfDeath(int cause) {
	    causeOfDeath = cause;
	}
	
	public int getCauseOfDeath() {
		return causeOfDeath;
	}
	
	public String causeOfDeathToString() {
		if (causeOfDeath == 0) {
			return "The Queen has died of old age.";
		} else if (causeOfDeath == 1) {
			return "The Queen has dies from starvation.";
		} else {
			return "The Queen has died from an attack.";
		}
	}
	public void hatch(ArrayList<Ants> ants, ColonyNodeView [][] nodes) {
		/**Generate a random number between 1 and 100.  Need to hatch at a rate as follows:
		 * Forager = 50%
		 * Scout = 25%
		 * Soldier = 25%
		 * 
		 * Determine breed type for hatch, then create new instance of that breed and add to ants.  Set coordinates to node [14][14] and increase the count of 
		 * that type of ant in that node. Update node's ant count showing the new ant
		 **/
		Random randNum = new Random();
		int number = randNum.nextInt(100) + 1;
		if (number <= 50) {
			Forager forager = new Forager();
			forager.setIdentifier(ants.size());
			ants.add(forager);
			forager.setxCoordinate(14);
			forager.setyCoordinate(14);
			nodes[14][14].numForagers++;
			nodes[14][14].setForagerCount(nodes[14][14].getForagerCount());
		} else if (number <= 75) {
			Scout scout = new Scout();
			scout.setIdentifier(ants.size());
			ants.add(scout);
			scout.setxCoordinate(14);
			scout.setyCoordinate(14);
			nodes[14][14].numScouts++;
			nodes[14][14].setScoutCount(nodes[14][14].getScoutCount());
		} else {
			Soldier soldier = new Soldier();
			soldier.setIdentifier(ants.size());
			ants.add(soldier);
			soldier.setxCoordinate(14);
			soldier.setyCoordinate(14);
			nodes[14][14].numSoldiers++;
			nodes[14][14].setSoldierCount(nodes[14][14].getSoldierCount());
		}
			
			
	}
	
	public boolean eat(ColonyNodeView [][] nodes) {
		if (nodes[14][14].getFoodAmount() == 0) { //If there's no food in Queen's square
			return false;
		} else {
			nodes[14][14].setFoodAmount(nodes[14][14].getFoodAmount() - 1); //Reduce amount of food in queen's node by 1
			return true;
		}
	}
}
