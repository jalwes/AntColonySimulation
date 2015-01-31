import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.Timer;


public class Simulation implements SimulationEventListener, ActionListener {

	 
	static AntSimGUI gui = new AntSimGUI(); //Create new AntSimGUI
	static ColonyView grid = new ColonyView(27, 27);//Make a new 'grid' 27 x 27
	static ColonyNodeView [][] nodes = new ColonyNodeView[27][27]; //Create a new two-dimensional array of nodes
	static ArrayList<Ants> ants = new ArrayList<>(65);
	static int counter = 0;
	static int numberOfTurns = 0;
	static int totalNumberOfTurnsTaken = 0; 
	Timer timer;
	
	public static void initializeNodes() {
		
		
		for (int i = 0; i < 27; i++) {
			for (int j = 0; j < 27; j++) {
				nodes[i][j] = new ColonyNodeView(); //Fill two dimensional array with ColonyNodeViews
				if (nodeShouldHaveFood()) {
					nodes[i][j].setFoodAmount(getRandomNumber(500,1000));
				}
				nodes[i][j].setPheromoneLevel(0);
				grid.addColonyNodeView(nodes[i][j], j, i); //Add nodes at specified coordinates. i = xCoordinate; j = yCoordinate 
				String coordinates = i + "," + j; //Create String for coordinate ID
				nodes[i][j].setID(coordinates); //Set coordinate ID
			}
		}
	
		gui.add(grid); //Add ColonyView 'grid' to gui
		gui.initGUI(grid);
		gui.setVisible(true); //Show GUI
		
	
	} //End initialize

	public static void initializeColony() {
		
		int count = 0;
		
		//Set initial 10 soldiers
		for (int i = 1; i <= 10; i++) {
			Soldier soldier = new Soldier();
			soldier.setIdentifier(i);
			soldier.setxCoordinate(14);
			soldier.setyCoordinate(14);
			ants.add(soldier);
			nodes[14][14].antsInThisNode.add(soldier);
			count++;
		}
		nodes[14][14].setSoldierCount(count);//Set the node [14][14] to show having 10 soldier ants		
		
		//Set initial 50 forager ants
		count = 0; 
		for (int i = 11; i < 61; i++) {
			Forager forager = new Forager();
			forager.setIdentifier(i);
			forager.setxCoordinate(14);
			forager.setyCoordinate(14);
			ants.add(forager);
			nodes[14][14].antsInThisNode.add(forager);
			nodes[14][14].numForagers++;
			count++;
		}
		//nodes[14][14].setForagerCount(count);//Set the node [14][14] to show having 50 forager ants	
		nodes[14][14].setForagerCount(nodes[14][14].getForagerCount());
		count = 0;
		
		//Set initial 4 scout ants
		for (int i = 62; i <= 65; i++) {
			Scout scout = new Scout();
			scout.setIdentifier(i);
			ants.add(scout);
			scout.setxCoordinate(14);
			scout.setyCoordinate(14);
			nodes[14][14].antsInThisNode.add(scout);
			count++;
		}
		nodes[14][14].setScoutCount(count);//Set the node [14][14] to show having 4 scout ants	
		
		
		Collections.shuffle(ants); //mix up the ants 
		Queen queen = new Queen();
		queen.setIdentifier(0);
		ants.add(0, queen);
		nodes[14][14].antsInThisNode.add(queen);
		
		
		//Set queen and show icons in middle node ([14][14])
		nodes[14][14].setQueen(true);
		nodes[14][14].showQueenIcon();
		nodes[14][14].showSoldierIcon();
		nodes[14][14].showScoutIcon();
		nodes[14][14].showForagerIcon();
		nodes[14][14].setFoodAmount(1000);

		//Make queen's node and surrounding nodes visible
		for (int j = 13; j < 16; j++) {
			for (int k = 13; k < 16; k++) {
				nodes[j][k].showNode();
			}
		}

	}
	
	public void takeSingleTurn() {
		totalNumberOfTurnsTaken++;
		if (counter > ants.size()-1) { //Reset counter to start a new round of turns
			counter = 0;
			//numberOfTurns++;

			//3% chance that each turn a Bala ant is hatched
			if (getPercentage() <= 3) {
				Bala bala = new Bala();
				bala.setxCoordinate(0);
				bala.setyCoordinate(0);
				bala.setIdentifier(ants.size());
				nodes[0][0].antsInThisNode.add(bala);
				ants.add(bala);
				nodes[0][0].setBalaCount(nodes[0][0].getBalaCount() + 1);
				if(nodes[0][0].isVisible()) {
					nodes[0][0].showBalaIcon();
				}
			}
			//Every 10 turns decrease amount of pheromone in nodes
			if (numberOfTurns % 10 == 0) {
				for (int i = 0; i < 27; i++) {
					for (int j = 0; j < 27; j++) {
						if (nodes[i][j].getPheromoneLevel() > 0) {
							nodes[i][j].pheromone = nodes[i][j].pheromone / 2;
							nodes[i][j].setPheromoneLevel(nodes[i][j].getPheromoneLevel());
						}
					}
				}
			}
		}
		
		Ants temp = ants.get(counter);
		
		
		//If this ant has reached its lifespan, remove it from the simulation
		if (temp.getLifespan() == temp.getNumberOfTurnsTaken()){
				removeAnt(temp);
				counter++;
				temp = ants.get(counter);
		}	
		
		if (temp instanceof Queen) {
			System.out.println(temp.getNumberOfTurnsTaken());
			if (numberOfTurns % 10 == 0) { //If it's the start of a new day, hatch a new ant
				((Queen) temp).hatch(ants, nodes); //One of Queen's methods
			} 
			if (!((Queen) temp).eat(nodes)) { //If eat() returns false, there is no food in Queen's node. Game over. If eat() == true, eat() will reduce food in queen's node
				((Queen) temp).setCauseOfDeath(1); //set cause of death to "Starvation"
				((Queen) temp).setIsAlive(false);
				gameOver(temp);
			}
		} else if (temp instanceof Soldier) {
			((Soldier) temp).move(nodes, ants);
		} else if (temp instanceof Scout) {
			((Scout) temp).move(nodes);
		} else if (temp instanceof Forager) {
			((Forager) temp).move(nodes);
		} else if (temp instanceof Bala) {
			((Bala) temp).move(nodes, ants);
		} else {
			takeSingleTurn();
		}
		
		temp.setNumberOfTurnsTaken(temp.getNumberOfTurnsTaken() + 1); //Increase number of turns taken for this ant
		counter++;
		numberOfTurns++;
		
	}
	
	public int getPercentage() {
		Random randNum = new Random();
		int rand = randNum.nextInt(100) + 1;
		return rand;
	}
	public static int getRandomNumber(int x, int y) {
		Random random = new Random();
		int number = random.nextInt((y - x) + 1)+ x;
		return number;
	}
	
	public int getRandomNumber (int n) {
		Random randNum = new Random();
		int x = randNum.nextInt((n+1) - (n-1) + 1) + (n-1);
		return x;
	}
	
	public static boolean nodeShouldHaveFood() {
		//25% chance of a node having food
		Random random = new Random();
		int rand = random.nextInt(100);
		if (rand < 25) {
			return true;
		} else {
		return false;
		}
	}
	
	public void removeAnt(Ants a) {
		if (a instanceof Queen) {
			gameOver(a);			
		} else {
			ants.remove(a);
		}
	}
	
	
	public void gameOver(Ants a) {
			JOptionPane.showMessageDialog(null, "Game Over. " + ((Queen) a).causeOfDeathToString());
			System.exit(0);
	}

	
	@Override
	public void simulationEventOccurred(SimulationEvent simEvent) {
		//Using getEventType() will return an integer from 0-6 in ascending order that corresponds to the buttons in the gui. 
		if (simEvent.getEventType() == 0) {
			initializeColony();
		}
		if (simEvent.getEventType() == 6) {
			takeSingleTurn();
		}
		if (simEvent.getEventType() == 5) {
			start();			
		}
	}
	
	public void start() {
		timer = new Timer(10, this);
		timer.start();	
	}
	
	public void addActionEventListener() {
		// TODO Auto-generated method stub;
		gui.addSimulationEventListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (ants.get(0).getIsAlive() == false) {
			timer.stop();
			gameOver(ants.get(0));
		} else {
			takeSingleTurn();
			timer.stop();
			start();
		}
	}
} //End simulation
