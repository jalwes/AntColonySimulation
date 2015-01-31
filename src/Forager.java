import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;
import javax.swing.JOptionPane;
import javax.swing.JOptionPane;

public class Forager extends Ants {

	private boolean returnToNestMode = false;
	private ArrayList <foragersNodesTraveled> nodesTraveled = new ArrayList<foragersNodesTraveled> (10);
	private int modCount = 0;
	private String stringIdentifier = "Forager";
	
	Forager () {
		setStringIdentifier("Forager");
		//Set first nodesTraveled as the nest for returning back once the Forager has food
		foragersNodesTraveled temp = new foragersNodesTraveled();
		temp.setxCoordinateTraveled(14);
		temp.setyCoordinateTraveled(14);
		nodesTraveled.add(modCount, temp);
		modCount++;
	}
	
	public void move(ColonyNodeView [][] nodes) {
		
		
		
		//Variables to hold coordinates before move and after move		
		int oldx = this.getxCoordinate();
		int oldy = this.getyCoordinate();
		int newx = 0; 
		int newy = 0;
		if (!(this.returnToNestMode)) { //**************If forager is NOT returning to nest with food *************************
			
			/** Set up an ArrayList to hold surrounding nodes that contain pheromone. Also use if statements to figure out where to start the 
			 * loop to look for pheromone. 
			 */
			//Set up arraylist to hold surrounding nodes that contain pheromone
			ArrayList <nodesWithPheromone> nodesWithPheromoneArray = new ArrayList<nodesWithPheromone>(8);

			int xstartingPoint;
			int xendingPoint;
			
			if (oldx == 0) {
				xstartingPoint = oldx;
				xendingPoint = oldx + 1;
			} else if (oldx == 26) {
				xstartingPoint = oldx - 1;
				xendingPoint = oldx;
			} else {
				xstartingPoint = oldx - 1;
				xendingPoint = oldx + 1;
			}
			
			int ystartingPoint;
			int yendingPoint;
			
			if (oldy == 0) {
				ystartingPoint = oldy;
				yendingPoint = oldy + 1;
			} else if (oldy == 26) {
				ystartingPoint = oldy - 1;
				yendingPoint = oldy;
			} else {
				ystartingPoint = oldy - 1;
				yendingPoint = oldy + 1;
			}
			
			int counter = 0;
			
			//Loop through surrounding nodes looking for pheromone.
			//If a surrounding node has pheromone and forager has not yet visited this node, add it to the nodesWithPheromoneArray array.
			for (int i = xstartingPoint; i <= xendingPoint; i++) {
				for (int j = ystartingPoint; j <= yendingPoint; j++) {
					if (nodes[i][j].getPheromoneLevel() > 0 && (i != oldx && j != oldy) && (!(alreadyTraveled(i,j, nodesTraveled)))) {
						nodesWithPheromone temp = new nodesWithPheromone(i, j, nodes[i][j].getPheromoneLevel());
						nodesWithPheromoneArray.add(counter, temp);
						counter++;
					}
				}
			}
			
			/**************************************************************
			 * Depending on the size of nodesWithPheromoneArray, set new coordinates
			 * ************************************************************ 
			 */
			//************Random move. No pheromone in surrounding nodes**************
			if (nodesWithPheromoneArray.size() == 0) {
				do {
					newx = getRandomNumber(oldx);
					newy = getRandomNumber(oldy);
				} while (nodes[newx][newy].isVisible() == false);
			} else if (nodesWithPheromoneArray.size() == 1) { //***********Only one surrounding node has pheromone. Move to it.**********
				newx = nodesWithPheromoneArray.get(0).getxCoordinateWithPheromone();
				newy = nodesWithPheromoneArray.get(0).getyCoordinateWithPheromone();
			} else { //****************More than one surrounding node has pheromone************
				
				sort(nodesWithPheromoneArray); //Sort in ascending order to determine which surrounding node(s) has the most pheromone
				int maxPheromoneIndex = nodesWithPheromoneArray.size()-1; 
				
				//If more than one node has the same amount of pheromone
				if (nodesWithPheromoneArray.get(maxPheromoneIndex).getPheromoneLevel() == nodesWithPheromoneArray.get(maxPheromoneIndex-1).getPheromoneLevel()) {
					//do {
						int [] coordinatesToUse = getRandomIndex(nodesWithPheromoneArray);
						newx = coordinatesToUse[0];
						newy = coordinatesToUse[1];
					//} while (repeatedNode(newx, newy, nodesTraveled));
						//if (alreadyTraveled()) 
				} else { //One node has the most pheromone
					newx = nodesWithPheromoneArray.get(maxPheromoneIndex).getxCoordinateWithPheromone();
					newy = nodesWithPheromoneArray.get(maxPheromoneIndex).getyCoordinateWithPheromone();
				}
			}//End if/else

			setNewCoordinates(oldx, oldy, newx, newy, nodes); //Set new coordinates
		} //End if 
		else { //*****************************In returnToNestMode - Forager has food and is returning to nest **************************
			
			newx = this.nodesTraveled.get(modCount).getxCoordinateTraveled();
			newy = this.nodesTraveled.get(modCount).getyCoordinateTraveled();
			this.modCount--;
			setNewCoordinates(oldx, oldy, newx, newy, nodes);
		}
	} //End move()
	
	public boolean alreadyTraveled(int x, int y, ArrayList <foragersNodesTraveled> nodesTraveled) {
		for (foragersNodesTraveled n : nodesTraveled) {
			if (x == n.getxCoordinateTraveled() && y == n.getyCoordinateTraveled()) {
				return true;
			}
		}
		return false;
	}
	
	public void setNewCoordinates(int oldx, int oldy, int newx, int newy, ColonyNodeView [][] nodes) {
		
		if(!this.returnToNestMode) {
			//Add node to nodesTraveled array
			foragersNodesTraveled temp = new foragersNodesTraveled();
			temp.setxCoordinateTraveled(newx);
			temp.setyCoordinateTraveled(newy);
			this.nodesTraveled.add(modCount, temp);
			modCount++;
			//If there's food in the new node and the new node isn't the nest, pick up food and enter returnToNest mode
			if (nodes[newx][newy].getFoodAmount() > 0 && (!(newx == 14 && newy == 14))) {		
				//JOptionPane.showMessageDialog(null, newx + ", " + newy + " has food and is not 14,14");
				nodes[newx][newy].foodAmount--;
				nodes[newx][newy].setFoodAmount(nodes[newx][newy].getFoodAmount());
				//if new node has less than 1,000 pheromone, leave 10 units of pheromone 
				if (nodes[newx][newy].getPheromoneLevel() < 1000) {
					nodes[newx][newy].setPheromoneLevel(nodes[newx][newy].getPheromoneLevel() + 10);
				}
				this.returnToNestMode = true;
				this.modCount--;//NEW
			}
		} else if (this.returnToNestMode && (!(newx == 14 && newy == 14)) && nodes[newx][newy].getPheromoneLevel() < 1000) {
			nodes[newx][newy].setPheromoneLevel(nodes[newx][newy].getPheromoneLevel() + 10);
		}
		
		//Update old nodes Forager count
		nodes[oldx][oldy].numForagers--;
		nodes[oldx][oldy].setForagerCount(nodes[oldx][oldy].getForagerCount());
		//If old node now has no foragers, hide the icon
		if (nodes[oldx][oldy].getForagerCount() == 0) {
			nodes[oldx][oldy].hideForagerIcon();
		}
		
		//Move to new node and update new node
		this.setxCoordinate(newx);
		this.setyCoordinate(newy);
		nodes[newx][newy].numForagers++;
		nodes[newx][newy].setForagerCount(nodes[newx][newy].getForagerCount());
		nodes[newx][newy].showForagerIcon();
				
		//If forager has returned to nest, leave food in the nest, set returnToNest to false, and reset modCount to 1
		if (this.returnToNestMode == true && (newx == 14 && newy == 14)) {
			nodes[newx][newy].foodAmount++;
			nodes[newx][newy].setFoodAmount(nodes[newx][newy].getFoodAmount());
			this.returnToNestMode = false;
			this.modCount = 1;
		}
		
		//Update old and new nodes antsInThisNode array
		nodes[oldx][oldy].antsInThisNode.remove(this);
		nodes[newx][newy].antsInThisNode.add(this);
	}
	
	public void sort(ArrayList<nodesWithPheromone> nodesWithPheromoneArray) {
		
		for (int i = 1; i < nodesWithPheromoneArray.size(); i++) {
			nodesWithPheromone temp = nodesWithPheromoneArray.get(i);
			int index = i - 1;
			
			while (index >= 0 && temp.getPheromoneLevel() < nodesWithPheromoneArray.get(index).getPheromoneLevel()) {
				nodesWithPheromoneArray.set(index+1, nodesWithPheromoneArray.get(index));
				index--;
			}
			nodesWithPheromoneArray.set(index+1, temp);
		}
	}//End sort()
	
	public int [] getRandomIndex(ArrayList<nodesWithPheromone> nodes) {
		ArrayList<nodesWithPheromone> temp = new ArrayList<nodesWithPheromone> (8);
		temp.add(0, nodes.get(nodes.size()-1)); 
		int counter = 1;
		for (int i = nodes.size()-2; i > 0; i--) {
			if (nodes.get(i).getPheromoneLevel() == temp.get(0).getPheromoneLevel()) {
				temp.add(counter, nodes.get(i));
			}
		}
		
		int index = getRandomNumber(0, temp.size()-1); //Get random index to move to from the indices with the most pheromone
		int [] coordinates = new int[2];
		coordinates[0] = temp.get(index).getxCoordinateWithPheromone();
		coordinates[1] = temp.get(index).getyCoordinateWithPheromone();
		return coordinates;
		
	}
}//End ants
