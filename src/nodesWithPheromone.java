
public class nodesWithPheromone {
	private int xCoordinateWithPheromone;
	private int yCoordinateWithPheromone;
	private int pheromoneLevel;
	
	nodesWithPheromone() {}
	
	nodesWithPheromone(int x, int y, int p) {
		xCoordinateWithPheromone = x;
		yCoordinateWithPheromone = y;
		pheromoneLevel = p;
	}
	
	public int getPheromoneLevel() {
		return pheromoneLevel;
	}
	public void setPheromoneLevel(int pheromoneLevel) {
		this.pheromoneLevel = pheromoneLevel;
	}
	public int getxCoordinateWithPheromone() {
		return xCoordinateWithPheromone;
	}
	public void setxCoordinateWithPheromone(int xCoordinateWithPheromone) {
		this.xCoordinateWithPheromone = xCoordinateWithPheromone;
	}
	public int getyCoordinateWithPheromone() {
		return yCoordinateWithPheromone;
	}
	public void setyCoordinateWithPheromone(int yCoordinateWithPheromone) {
		this.yCoordinateWithPheromone = yCoordinateWithPheromone;
	}
	
	
	
	
}
