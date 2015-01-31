import java.util.ArrayList;


public class Soldier extends Ants{
	private boolean scoutMode = true;
	
	Soldier () {
		setStringIdentifier("Soldier");
	}
	
	public void move(ColonyNodeView[][] nodes, ArrayList<Ants> ants) {
		
		int oldx = this.getxCoordinate();
		int oldy = this.getyCoordinate();
		
		int newx;
		int newy;
		
		if (scoutMode) {
			/** Set up an ArrayList to hold surrounding nodes that contain balas. Also use if statements to figure out which nodes to search (make sure they're in bounds). 
			 */
			//Set up arraylist to hold surrounding nodes that contain pheromone
			ArrayList <nodesWithBalas> nodesWithBalasArray = new ArrayList<nodesWithBalas>(8);

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
			//Loop through surrounding nodes looking for balas.
			//If a surrounding node has balas, add it to the array.
			for (int i = xstartingPoint; i < xendingPoint; i++) {
				for (int j = ystartingPoint; j < yendingPoint; j++) {
					if (nodes[i][j].getBalaCount() > 0 && (i != oldx && j != oldy) && nodes[i][j].isVisible()) {
						nodesWithBalas temp = new nodesWithBalas(i, j);
						nodesWithBalasArray.add(counter, temp);
						counter++;
					}
				}
			} // end for 
			
			/**
			 * Determine where to move based on which, if any, nodes have Balas.
			 * If there are no Balas, do a random move. 
			 */
			
			if (nodesWithBalasArray.isEmpty()) {
				//Do random move
				do {
					newx = getRandomNumber(oldx);
					newy = getRandomNumber(oldy);
				} while (nodes[newx][newy].isVisible() == false);
			} else if (nodesWithBalasArray.size() == 1) {
				//move to that node
				newx = nodesWithBalasArray.get(0).getXCoordinateWithBala();
				newy = nodesWithBalasArray.get(0).getYCoordinateWithBala();
			} else {
				//get a random index number from the array of nodes with balas
				int index = getRandomNumber(nodesWithBalasArray.size());
				newx = nodesWithBalasArray.get(index).getXCoordinateWithBala();
				newy = nodesWithBalasArray.get(index).getYCoordinateWithBala();
			}
			
			
			setNewCoordinates(oldx, oldy, newx, newy, nodes);
			
			if (nodes[newx][newy].getBalaCount() > 0) {
				attack(nodes, newx, newy, ants);
			}
			
			
		} //End if scoutMode
	} //End move()
	
	public void setNewCoordinates(int oldx, int oldy, int newx, int newy, ColonyNodeView[][] nodes) {
		//Update old node
		nodes[oldx][oldy].numSoldiers--;
		nodes[oldx][oldy].setSoldierCount(nodes[oldx][oldy].getSoldierCount());
		
		//if old node now has no soldiers, hide the icon
		if (nodes[oldx][oldy].getSoldierCount() == 0) {
			nodes[oldx][oldy].hideSoldierIcon();
		}

		//Move to and update new node
		this.setxCoordinate(newx);
		this.setyCoordinate(newy);
		nodes[newx][newy].numSoldiers++;
		nodes[newx][newy].setSoldierCount(nodes[newx][newy].getSoldierCount());
		nodes[newx][newy].showSoldierIcon();
		
		//Update old and new nodes antsInThisNode array
		nodes[oldx][oldy].antsInThisNode.remove(this);
		nodes[newx][newy].antsInThisNode.add(this);		//Move to new node and update new node
				
	}
	
}
