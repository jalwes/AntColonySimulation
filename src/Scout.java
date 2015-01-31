import javax.swing.JOptionPane;


public class Scout extends Ants{
	
	Scout () {
		setStringIdentifier("Scout");
	}
	
	public void move(ColonyNodeView [][] nodes) {
		int oldxCoordinate = this.getxCoordinate();
		int oldyCoordinate = this.getyCoordinate();
		
		int newxCoordinate;
		int newyCoordinate;
		
		do {
			newxCoordinate = getRandomNumber(oldxCoordinate);
			newyCoordinate = getRandomNumber(oldyCoordinate);
		} while (newxCoordinate > 26 || newyCoordinate > 26 || newxCoordinate < 0 || newyCoordinate <0 || (newxCoordinate == oldxCoordinate && newyCoordinate == oldyCoordinate)); //If it returns random coordinates that are out of bounds or the same coordinates as ant's current position, do it again
		
		//Update old coordinates
		nodes[oldxCoordinate][oldyCoordinate].numScouts--;
		nodes[oldxCoordinate][oldyCoordinate].setScoutCount(nodes[oldxCoordinate][oldyCoordinate].getScoutCount());
		//If there are no scouts in old coordinates, hide icon
		if (nodes[oldxCoordinate][oldyCoordinate].getScoutCount() == 0) {
			nodes[oldxCoordinate][oldyCoordinate].hideScoutIcon();
		}
		
		//update new coordinates
		this.setxCoordinate(newxCoordinate);
		this.setyCoordinate(newyCoordinate);
		nodes[newxCoordinate][newyCoordinate].numScouts++;
		nodes[newxCoordinate][newyCoordinate].setScoutCount(nodes[newxCoordinate][newyCoordinate].getScoutCount());
		nodes[newxCoordinate][newyCoordinate].showScoutIcon();
		
		//If new node wasn't visible before, make it visible
		if (nodes[newxCoordinate][newyCoordinate].isVisible() == false) {
			nodes[newxCoordinate][newyCoordinate].showNode();
		}
		
		//Update list of new and old nodes identifiers
		nodes[oldxCoordinate][oldyCoordinate].antsInThisNode.remove(this);
		nodes[newxCoordinate][newyCoordinate].antsInThisNode.add(this);
		
	}

}
