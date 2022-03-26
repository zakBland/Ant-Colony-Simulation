import java.util.ArrayList;

public class Forager extends Ant{
	//data fields
	boolean carryingFood = false;
	private boolean inLoop;
	private String	secLoopLoc;
	private ArrayList<String> bannedLocs = new ArrayList<String>();
	ArrayList<String> history = new ArrayList<String>();
	
	Forager(){
		maxLife = 1;
		canMove = false;
		antType = "forager";
		history.add("14 14");
		bannedLocs.add("14 14");
	}
	
	//methods
	public void comparePheromone(Environment e, Square[][] grid, Ant ant, int antLocationX, int antLocationY, ArrayList<String> history) {
		ArrayList<String> choices = new ArrayList<String>();
		int max = 0;
		String movement = "";
		
		if((history.size() >= 4 ) && (history.get(history.size() - 2).equals(history.get(history.size() - 4))) &&
				(history.get(history.size() - 1).equals(history.get(history.size() - 3)))){
			
			inLoop = true;
			secLoopLoc = history.get(history.size() - 3);
		}
		
	
		if(inLoop && history.contains(secLoopLoc)) { //backtrack with while loop
			ant.previousLocX = ant.antLocationX;
			ant.previousLocY = ant.antLocationY;
			for(int i = 0; i < 2; i++) {
				ant.antLocationX = Integer.valueOf(secLoopLoc.substring(0, secLoopLoc.indexOf(" ")));
				ant.antLocationY = Integer.valueOf(secLoopLoc.substring(secLoopLoc.indexOf(" ") + 1));	
				bannedLocs.add(history.remove(history.size() - 1));
			}
			if(e.grid[ant.previousLocX][ant.previousLocY].numberForager == 0)
				e.grid[ant.previousLocX][ant.previousLocY].node.hideForagerIcon();
			e.grid[ant.antLocationX][ant.antLocationY].node.showForagerIcon();
		}
			
		inLoop = false;
		ArrayList<String> p = ant.possibleMovesList(e.grid, ant.antLocationX, ant.antLocationY, ant);
		
		//0 && 1
		if(p.contains("LD") && ant.antLocationX + 1 <= getEdgeX() && ant.antLocationX + 1 >= 0 && ant.antLocationY + 1 >= 0 && ant.antLocationY + 1 <= getEdgeY() &&
				grid[ant.antLocationX + 1][ant.antLocationY + 1].numberPheromone < grid[ant.antLocationX - 1][ant.antLocationY + 1].numberPheromone
				&& !bannedLocs.contains((ant.antLocationX - 1) + " " + (ant.antLocationY + 1)) ) {
			max = grid[ant.antLocationX - 1][ant.antLocationY + 1].numberPheromone; //	RD	and LD	
			choices.add("LD");
		}
			
		else if(ant.antLocationX + 1 <= getEdgeX() && ant.antLocationX + 1 >= 0 && ant.antLocationY + 1 >= 0 && ant.antLocationY + 1 <= getEdgeY()
			&& ant.antLocationX - 1 <= getEdgeX() && ant.antLocationX - 1 >= 0 && ant.antLocationY + 1 >= 0 && ant.antLocationY + 1 <= getEdgeY()
			&& grid[ant.antLocationX + 1][ant.antLocationY + 1].numberPheromone == grid[ant.antLocationX - 1][ant.antLocationY + 1].numberPheromone) {
			if(p.contains("LD") && !bannedLocs.contains((ant.antLocationX - 1) + " " + (ant.antLocationY + 1))) {
				choices.add("LD");	
				max = grid[ant.antLocationX - 1][ant.antLocationY + 1].numberPheromone; 
			}

			if(p.contains("RD") && !bannedLocs.contains((ant.antLocationX + 1) + " " + (ant.antLocationY + 1))) {
				choices.add("RD");
				max = grid[ant.antLocationX + 1][ant.antLocationY + 1].numberPheromone; 

			}	
		}
		else {
			if(p.contains("RD") && !bannedLocs.contains((ant.antLocationX + 1) + " " + (ant.antLocationY + 1))) {
				max = grid[ant.antLocationX + 1][ant.antLocationY + 1].numberPheromone;	
				choices.add("RD");
			}
		}
	
		//2
		if(p.contains("RU") && max < grid[ant.antLocationX + 1][ant.antLocationY - 1].numberPheromone && 
				!bannedLocs.contains((ant.antLocationX + 1) + " " + (ant.antLocationY - 1))) {
			max = grid[ant.antLocationX + 1][ant.antLocationY - 1].numberPheromone; //RU
			choices.clear();
			choices.add("RU");
		}
		else if(p.contains("RU") && max == grid[ant.antLocationX + 1][ant.antLocationY - 1].numberPheromone && 
				!bannedLocs.contains((ant.antLocationX + 1) + " " + (ant.antLocationY - 1))) 
				choices.add("RU");	

		//3
		if(p.contains("LU") && max < grid[ant.antLocationX - 1][ant.antLocationY - 1].numberPheromone && 
				!bannedLocs.contains((ant.antLocationX - 1) + " " + (ant.antLocationY - 1))) {
			max = grid[ant.antLocationX - 1][ant.antLocationY - 1].numberPheromone; //LU
				choices.clear();
				choices.add("LU");
		}
		else if(p.contains("LU") && max == grid[ant.antLocationX - 1][ant.antLocationY - 1].numberPheromone &&  
				!bannedLocs.contains((ant.antLocationX - 1) + " " + (ant.antLocationY - 1)))
				choices.add("LU");
		//4
		if(p.contains("R") && max < grid[ant.antLocationX + 1][ant.antLocationY].numberPheromone && 
				!bannedLocs.contains((ant.antLocationX + 1) + " " + ant.antLocationY)) {
			max = grid[ant.antLocationX + 1][ant.antLocationY].numberPheromone; //R
			choices.clear();
			choices.add("R");
		}
		else if(p.contains("R") && max == grid[ant.antLocationX + 1][ant.antLocationY].numberPheromone &&  
				!bannedLocs.contains((ant.antLocationX + 1) + " " + ant.antLocationY))
			choices.add("R");
		
		//5
		if(p.contains("L") && max < grid[ant.antLocationX - 1][ant.antLocationY].numberPheromone &&  
				!bannedLocs.contains((ant.antLocationX - 1) + " " + ant.antLocationY)) {
			max = grid[ant.antLocationX - 1][ant.antLocationY].numberPheromone; //L
			choices.clear();
			choices.add("L");
		}
		else if(p.contains("L") && max == grid[ant.antLocationX - 1][ant.antLocationY].numberPheromone && 
				!bannedLocs.contains((ant.antLocationX - 1) + " " + ant.antLocationY))
			choices.add("L");

		//6
		if(p.contains("D") && max < grid[ant.antLocationX][ant.antLocationY + 1].numberPheromone && 
				!bannedLocs.contains(ant.antLocationX + " " + (ant.antLocationY + 1))) {
			max = grid[ant.antLocationX][ant.antLocationY + 1].numberPheromone; //D
			choices.clear();
			choices.add("D");
		}
		else if(p.contains("D") && max == grid[ant.antLocationX][ant.antLocationY + 1].numberPheromone 
				&& !bannedLocs.contains(ant.antLocationX + " " + (ant.antLocationY + 1)))
			choices.add("D");
		
		//7
		if(p.contains("U") && max < grid[ant.antLocationX][ant.antLocationY - 1].numberPheromone && 
				!bannedLocs.contains(ant.antLocationX + " " + (ant.antLocationY - 1))) {
				max = grid[ant.antLocationX][ant.antLocationY - 1].numberPheromone; //U
				choices.clear();
				choices.add("U");
		}
		else if(p.contains("U") && max == grid[ant.antLocationX][ant.antLocationY - 1].numberPheromone && 
				!bannedLocs.contains(ant.antLocationX + " " + (ant.antLocationY - 1)))
				choices.add("U");
				
		//backtrack 
		if(choices.size() == 0) { 
			if(history.size() == 1) {
				bannedLocs.clear();
				bannedLocs.add("14 14");

			}else{
				movement = ((Forager)ant).history.get(((Forager)ant).history.size() - 2);
				bannedLocs.add(((Forager)ant).history.remove(history.size() - 1));
				e.grid[ant.antLocationX][ant.antLocationY].numberForager--;;
				ant.previousLocX = ant.antLocationX;
				ant.previousLocY = ant.antLocationY;
				e.grid[ant.antLocationX][ant.antLocationY].node.setForagerCount(e.grid[ant.antLocationX][ant.antLocationY].numberForager);
				ant.antLocationX = Integer.valueOf(movement.substring(0, movement.indexOf(" ")));
				ant.antLocationY = Integer.valueOf(movement.substring(movement.indexOf(" ") + 1));			
				if(e.grid[ant.previousLocX][ant.previousLocY].numberForager == 0)
					e.grid[ant.previousLocX][ant.previousLocY].node.hideForagerIcon();
				e.grid[ant.antLocationX][ant.antLocationY].node.showForagerIcon();
				e.grid[ant.antLocationX][ant.antLocationY].numberForager++;;
				e.grid[ant.antLocationX][ant.antLocationY].node.setForagerCount(e.grid[ant.antLocationX][ant.antLocationY].numberForager);
			}
		}else{  
			movement = ant.moveRandom(choices, grid, ant.antLocationX, ant.antLocationY, ant.antType);
			((Forager)ant).history.add(movement);

			ant.previousLocX = ant.antLocationX;
			ant.previousLocY = ant.antLocationY;
			
			ant.antLocationX = Integer.valueOf((((Forager)ant).history.get(((Forager)ant).history.size() - 1)).substring(0,((Forager)ant).history.get(((Forager)ant).history.size() - 1).indexOf(' ')));
			ant.antLocationY = Integer.valueOf(((Forager)ant).history.get(((Forager)ant).history.size() - 1).substring(((Forager)ant).history.get(((Forager)ant).history.size() - 1).indexOf(' ') + 1));
			
			if(e.grid[ant.previousLocX][ant.previousLocY].numberForager == 0)
				e.grid[ant.previousLocX][ant.previousLocY].node.hideForagerIcon();
			e.grid[ant.antLocationX][ant.antLocationY].node.showForagerIcon();
			
		}
}

	
	public void resetHistory() {
		bannedLocs.clear();
		bannedLocs.add("14 14");
		secLoopLoc = "";
		inLoop = false;
		carryingFood = false;
		history.clear();
		history.add("14 14");

	}
	
	public boolean getCarrying() {
		return carryingFood;
	}
	
	public void depositPheromone(Environment e, Square[][] grid, int antLocationX, int antLocationY) {
		if(grid[antLocationX][antLocationY].numberPheromone < 1000 && (antLocationX != getCenterX() 
				|| antLocationY != getCenterY())) {
			grid[antLocationX][antLocationY].numberPheromone += 10;
			e.grid[antLocationX][antLocationY].node.setPheromoneLevel(e.grid[antLocationX][antLocationY].numberPheromone);

		}
		
	}
	
	public void pickFood(Square[][] grid, int antLocationX, int antLocationY) {
		grid[antLocationX][antLocationY].numberFood--;
		grid[antLocationX][antLocationY].node.setFoodAmount(grid[antLocationX][antLocationY].numberFood);
		this.carryingFood = true;
	}
	
	
	//return back
	public void returnBack(Environment e, ArrayList<String> history, Ant ant, int antLocationX, int antLocationY, Square[][] grid) {
		if(grid[ant.antLocationX][ant.antLocationY].numberPheromone < 1000)
			((Forager)ant).depositPheromone(e, grid, ant.antLocationX, ant.antLocationY);
			
			ant.previousLocX = ant.antLocationX;
			ant.previousLocY = ant.antLocationY;
			e.grid[ant.antLocationX][ant.antLocationY].numberForager--;;
			e.grid[ant.antLocationX][ant.antLocationY].node.setForagerCount(e.grid[ant.antLocationX][ant.antLocationY].numberForager);
			
			ant.antLocationX = Integer.valueOf(((Forager)ant).history.get(((Forager)ant).history.size() - 2).substring(0, ((Forager)ant).history.get(((Forager)ant).history.size() - 2).indexOf(' ')));
			ant.antLocationY = Integer.valueOf(((Forager)ant).history.get(((Forager)ant).history.size() - 2).substring(((Forager)ant).history.get(((Forager)ant).history.size() - 2).indexOf(' ') + 1));
			
			if(e.grid[ant.previousLocX][ant.previousLocY].numberForager == 0)
				e.grid[ant.previousLocX][ant.previousLocY].node.hideForagerIcon();
			
			e.grid[ant.antLocationX][ant.antLocationY].numberForager++;;
			e.grid[ant.antLocationX][ant.antLocationY].node.showForagerIcon();
			
			e.grid[ant.antLocationX][ant.antLocationY].node.setForagerCount(e.grid[ant.antLocationX][ant.antLocationY].numberForager);
		((Forager)ant).history.remove(((Forager)ant).history.size() - 1);
		 
	}

}
