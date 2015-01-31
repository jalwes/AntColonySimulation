import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;


public class OldForager extends Ants {

	private boolean returnToNestMode;
	private boolean hasFood;
	private ArrayList <foragersNodesTraveled> nodesTraveled = new ArrayList<foragersNodesTraveled> (10);
	private int modCount;
	
	OldForager() {
		modCount = 0;
		returnToNestMode = false;
	}
	
	public String getNodesTraveled(int size) {
		return this.nodesTraveled.get(size).getxCoordinateTraveled() + " " + getyCoordinate();
	}
	
	public int sizeOfNodesTraveled() {
		return nodesTraveled.size();
	}
	public void move(ColonyNodeView [][] nodes) {
		
		int oldxCoordinate = this.getxCoordinate();
	    int oldyCoordinate = this.getyCoordinate();
	    
	    
	    int newxCoordinate;
	    int newyCoordinate;
		/*****************************
		 *Regular move, in Forage mode
		 *
		 */////////////////////////////
		
		if (this.returnToNestMode == false) { //If Forager is not returning to the nest with food
			
			//System.out.println(oldxCoordinate + " " + oldyCoordinate);
			addNodeToNodesTraveled(oldxCoordinate, oldyCoordinate);
			
			//Go surrounding nodes to see if any have pheromone. If they do, add them to an array. 
			ArrayList <nodesWithPheromone> nodesWithPheromoneArray = new ArrayList<nodesWithPheromone>(8);
			
			//JOptionPane.showMessageDialog(null, "nodesWithPheromone.isEmpty() = " + nodesWithPheromoneArray.isEmpty());
			int xstartingPoint;
			int xendingPoint;
			
			if (oldxCoordinate == 0) {
				xstartingPoint = oldxCoordinate;
				xendingPoint = oldxCoordinate + 1;
			} else if (oldxCoordinate == 26) {
				xstartingPoint = oldxCoordinate - 1;
				xendingPoint = oldxCoordinate;
			} else {
				xstartingPoint = oldxCoordinate - 1;
				xendingPoint = oldxCoordinate + 1;
			}
			
			int ystartingPoint;
			int yendingPoint;
			
			if (oldyCoordinate == 0) {
				ystartingPoint = oldyCoordinate;
				yendingPoint = oldyCoordinate + 1;
			} else if (oldyCoordinate == 26) {
				ystartingPoint = oldyCoordinate - 1;
				yendingPoint = oldyCoordinate;
			} else {
				ystartingPoint = oldyCoordinate - 1;
				yendingPoint = oldyCoordinate + 1;
			}
			
			for (int i = xstartingPoint; i <= xendingPoint; i++) {
				for (int j = ystartingPoint; j <= yendingPoint; j++) {
					//if (nodes[i][j].isValid()) {
					if ((i <= 26 && j <= 26) && (i >= 0 && j >= 0)) {
						if (nodes[i][j].getPheromoneLevel() > 0 && (i != oldxCoordinate && j != oldyCoordinate)) {
							nodesWithPheromone tempNodeWithPheromone = new nodesWithPheromone(i, j, nodes[i][j].getPheromoneLevel());
							nodesWithPheromoneArray.add(tempNodeWithPheromone);
						}
					}
				}
			}

			//System.out.println(oldxCoordinate + " " + oldyCoordinate);

			ArrayList <nodesWithPheromone> nodesWithTheMostPheromone = new ArrayList<nodesWithPheromone>(8);
			//If no surrounding nodes have pheromone, do a random move 
			if (nodesWithPheromoneArray.isEmpty()) { 
				//JOptionPane.showMessageDialog(null, "In nodesWithPheromone.isEpmty(). So no surrounding nodes should have pheromone");
				//JOptionPane.showMessageDialog(null, "Old before do loop: " + oldxCoordinate + " " + oldyCoordinate);
				do {
					//System.out.println("Old in do loop " + oldxCoordinate + " " + oldyCoordinate);
					//System.out.println("Is old nodes visible? " + nodes[oldxCoordinate][oldyCoordinate].isVisible());
					newxCoordinate = getRandomNumber(oldxCoordinate);
					newyCoordinate = getRandomNumber(oldyCoordinate);
					//System.out.println("New: " + newxCoordinate + " " + newyCoordinate);
					//System.out.println("nodes[" + newxCoordinate + "][" + newyCoordinate + "].isVisible = " + nodes[newxCoordinate][newyCoordinate].isVisible());
					//System.out.println();
					//if ((newxCoordinate < 0 || newxCoordinate > 26) || (newyCoordinate < 0 || newyCoordinate > 26)) {
						//JOptionPane.showMessageDialog(null, "new Coordinates are not within range");
				//	}
					//System.out.println("In do loop. Coordinates: Old " + oldxCoordinate + " " + oldyCoordinate + " New: " + newxCoordinate + " " + newyCoordinate);
				} while (nodes[newxCoordinate][newyCoordinate].isVisible() == false); 
				//while ((newxCoordinate < 0 || newxCoordinate > 26) || (newyCoordinate < 0 || newyCoordinate > 26)|| (nodes[newxCoordinate][newyCoordinate].isVisible() == false) || (newxCoordinate == 14 && newyCoordinate == 14)); //Do while the returned node is either not yet visible (or isn't a valid node) or is the queen's node

				System.out.println();

			} else if (nodesWithPheromoneArray.size() > 1) {

				int [] greatestPheromoneLevels = new int [8]; //Forager should move to node with the greatest amount of pheromone. If two nodes have the same amount, randomly move to one of them.  
				greatestPheromoneLevels[0] = 0; //Indexes within nodesWithPheromoneArray to get() for random move. Choose one of the nodes with highest level of pheromone to move to
				int counter = 0;
				nodesWithPheromone temp = new nodesWithPheromone();

				//JOptionPane.showMessageDialog(null, "Ants indentifier = " + this.getIdentifier() + " " + this.getxCoordinate() + " " + this.getyCoordinate());				
				
				
				for (int i = 1; i < nodesWithPheromoneArray.size(); i++) {
					if (nodesWithPheromoneArray.get(i).getPheromoneLevel() >= nodesWithPheromoneArray.get(counter).getPheromoneLevel()) {
						//nodesWithPheromone temp = new nodesWithPheromone(nodesWithPheromoneArray.get(i).getxCoordinateWithPheromone(), nodesWithPheromoneArray.get(i).getyCoordinateWithPheromone(), nodesWithPheromoneArray.get(i).getPheromoneLevel());
						temp.setxCoordinateWithPheromone(nodesWithPheromoneArray.get(i).getxCoordinateWithPheromone());
						temp.setyCoordinateWithPheromone(nodesWithPheromoneArray.get(i).getyCoordinateWithPheromone());
						temp.setPheromoneLevel(nodesWithPheromoneArray.get(i).getPheromoneLevel());
						counter++;
					} else {
						temp.setxCoordinateWithPheromone(nodesWithPheromoneArray.get(0).getxCoordinateWithPheromone());
						temp.setyCoordinateWithPheromone(nodesWithPheromoneArray.get(0).getyCoordinateWithPheromone());					}
				}
				
				newxCoordinate = temp.getxCoordinateWithPheromone();
				newyCoordinate = temp.getyCoordinateWithPheromone();
				System.out.println("In else if, new Coordinates = " + newxCoordinate + " " + newyCoordinate);
						
			} else { //NodesWithPheromoneArray.size() == 1
				newxCoordinate = nodesWithPheromoneArray.get(0).getxCoordinateWithPheromone();
				newyCoordinate = nodesWithPheromoneArray.get(0).getyCoordinateWithPheromone();
				System.out.println("In else, new Coordinates = " + newxCoordinate + " " + newyCoordinate);

			}
			//System.out.println("Setting new Coordinates as " + newxCoordinate + ", " + newyCoordinate + ". Old Coords were " + oldxCoordinate + ", " + oldyCoordinate);
			setNewCoordinates(oldxCoordinate, oldyCoordinate, newxCoordinate, newyCoordinate, nodes);
			
		} 
		/***************************
		*Move in return-to-nest mode
		****************************/
		else { //In return to nest mode. Forager has food to take back to nest
			
			//if (this.modCount == 0) {
				//JOptionPane.showMessageDialog(null, "this.modCOunt = 0 and it's current location is " + oldxCoordinate + ", " + oldyCoordinate);
			//}
			if (oldxCoordinate == 14 && oldyCoordinate == 14) { //In Queen's nest
				nodes[oldxCoordinate][oldyCoordinate].foodAmount++; //Drop food queen's node
				nodes[oldxCoordinate][oldyCoordinate].setFoodAmount(nodes[oldxCoordinate][oldyCoordinate].foodAmount); //Update node to reflect food just dropped
				this.hasFood = false; //Forager no longer has food
				this.returnToNestMode = false; //Should return to forage mode
				
				//Clear array and start it again by setting the first node as the queen's node
				this.nodesTraveled.clear(); //Clear array holding nodes traveled				

			} else { //Not in Queen's node

				//JOptionPane.showMessageDialog(null, "this.getxCoordinate before move= " + this.getxCoordinate() + ", this.getyCoordinate before move = " + this.getyCoordinate());

				//Leave 10 units of pheromone in node if node's pheromone level is < 1000. Update pheromone display for this node
				if (nodes[oldxCoordinate][oldyCoordinate].getPheromoneLevel() < 1000) {
					nodes[oldxCoordinate][oldyCoordinate].pheromone+=10; 
					nodes[oldxCoordinate][oldyCoordinate].setPheromoneLevel(nodes[oldxCoordinate][oldyCoordinate].getPheromoneLevel());
				}
				
				//Set new coordinates based on the nodesTraveled array
			if (this.modCount == 0) {
				System.out.println("this.nodesTraveled.size = " + this.nodesTraveled.size());
				System.out.println("this ants current location = " + this.getxCoordinate() + this.getyCoordinate());
				System.out.println("Are oldcoordinates the same as above? " + oldxCoordinate + ", " + oldyCoordinate);
				System.out.println("this.returntonestMode = " + this.returnToNestMode);
				System.out.println("this.getNodesTraveled(0) = " + this.nodesTraveled.get(0).getxCoordinateTraveled() + " " + this.nodesTraveled.get(this.modCount).getyCoordinateTraveled());
				
				//System.out.println("Setting new Coordinates as " + newxCoordinate + ", " + newyCoordinate + ". Old Coords were " + oldxCoordinate + ", " + oldyCoordinate);
			}
				newxCoordinate = this.nodesTraveled.get(this.modCount).getxCoordinateTraveled();
				newyCoordinate = this.nodesTraveled.get(this.modCount).getyCoordinateTraveled();
				setNewCoordinates(oldxCoordinate, oldyCoordinate, newxCoordinate, newyCoordinate, nodes);
				//this.modCount--;
				
			}
		}
	}
	
	public void setNewCoordinates(int oldx, int oldy, int newx, int newy, ColonyNodeView [][] nodes) {
		//Update node's forager count and icon status
		nodes[oldx][oldy].numForagers--;
		nodes[oldx][oldy].setForagerCount(nodes[oldx][oldy].getForagerCount());
		if (nodes[oldx][oldy].getForagerCount() == 0) { //If there are no longer any foragers in this square, hide the forager icon
			nodes[oldx][oldy].hideForagerIcon();
		}

		//Move this forager to it's new node
		this.setxCoordinate(newx); 
		this.setyCoordinate(newy);
		nodes[newx][newy].numForagers++; //Increase forager count in new node
		nodes[newx][newy].setForagerCount(nodes[newx][newy].getForagerCount()); //Update forager count for display
		nodes[newx][newy].showForagerIcon();

		//If the node Forager just moved into has food, take one unit of food and set returnToNest to true;
		if (nodes[newx][newy].getFoodAmount() > 0 && (newx != 14 && newy != 14)) { //Can't take food from queen's node
			nodes[newx][newy].foodAmount--;
			nodes[newx][newy].setFoodAmount(nodes[newx][newy].getFoodAmount());
			this.hasFood = true;
			this.returnToNestMode = true;
			this.modCount--;
		}
		
		System.out.println("this.nodesTraveled.size() =  " + this.nodesTraveled.size());
		
		if (nodes[newx][newy].isVisible() == false) {
			JOptionPane.showMessageDialog(null, "*******Forager just moved to invisble node********");
			System.out.println("*******Forager just moved to invisble node********");
			System.out.println("node["+newx+"]["+newy+"] is not visible");
			System.out.println("Came from node["+oldx+"]["+oldy+"]. Old node visibility = " + nodes[oldx][oldy].isVisible() );
			System.out.println("Ant is in foraging mode = " + this.returnToNestMode);
			System.out.println("*****************");
			
		}
	}
	
	public void addNodeToNodesTraveled(int x, int y) {
		foragersNodesTraveled temp = new foragersNodesTraveled();
		temp.setxCoordinateTraveled(x);
		temp.setyCoordinateTraveled(y);
		//JOptionPane.showMessageDialog(null, "Coordinates " + x + ", " + y + " just added to this ants nodesTraveled at modCount = " + this.modCount);
		//System.out.println(this.modCount + " " +  this.returnToNestMode + " " +  x + " " + y);
		try {
			this.nodesTraveled.add(this.modCount, temp);
			this.modCount++;
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Tring to add to index " + this.modCount + ". Size of nodesTraveled = " + this.nodesTraveled.size());
			//System.out.println(this.modCount + " " + x + " " + y);
		}
		
		
	}
	
	/*public static int getRandomNumber(int x, int y) {
		Random random = new Random();
		int number = random.nextInt((y - x) + 1)+ x;
		return number;
	}
	
	public int getRandomNumber (int n) {
		Random randNum = new Random();
		int x = randNum.nextInt((n+1) - (n-1) + 1) + (n-1);
		return x;
	}*/
}
