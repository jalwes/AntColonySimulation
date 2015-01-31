import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Bala extends Ants {
	
	Bala () {
		setStringIdentifier("Bala");
	}
	
	public void move(ColonyNodeView[][] nodes, ArrayList<Ants> ants) {
		int oldx = this.getxCoordinate();
		int oldy = this.getyCoordinate();
		
		int newx = getRandomNumber(oldx);
		int newy = getRandomNumber(oldy);
		
		setNewCoordinates(oldx, oldy, newx, newy, nodes, ants);
	}
	
	public void setNewCoordinates(int oldx, int oldy, int newx, int newy, ColonyNodeView[][] nodes, ArrayList<Ants> ants) {
		//Update old node
		nodes[oldx][oldy].setBalaCount(nodes[oldx][oldy].getBalaCount() - 1);
		nodes[oldx][oldy].antsInThisNode.remove(this);
		if (nodes[oldx][oldy].getBalaCount() == 0) {
			nodes[oldx][oldy].hideBalaIcon();
		}
		
		//Update new node
		this.setxCoordinate(newx);
		this.setyCoordinate(newy);
		nodes[newx][newy].setBalaCount(nodes[newx][newy].getBalaCount() + 1);
		nodes[newx][newy].antsInThisNode.add(this);
		nodes[newx][newy].showBalaIcon();
		
		//If there are ants (other than other Balas) in the new node, attack. 
		if (nodes[newx][newy].getTotalAnts() > 0) {
			attack(nodes, newx, newy, ants);
		}
	}
	
}
