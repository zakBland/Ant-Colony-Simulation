import java.util.ArrayList;

public class Queen extends Ant{
		
	//data fields
	
	Queen(){
		maxLife = 20;
		antType = "queen";
		id = 0;
		canMove = false;
	}
	//methods
	
	public boolean eat(int centerFood, Square[][] grid, Environment e) {
		if(centerFood != 0) {
			grid[getCenterX()][getCenterY()].node.setFoodAmount(grid[getCenterX()][getCenterY()].numberFood--);
			return true;
		}	
		return false;
	}
	
	public void hatch(Square[][] grid, int centerX, int centerY, ArrayList<Ant> antColony) {
		String ant = generateAnt();
		if(ant.equals("forager")) {
			antColony.add(new Forager());
			antColony.get(antColony.size() - 1).id = idCounter++;
			antColony.get(antColony.size() - 1).antType = "forager";
			grid[centerX][centerY].numberForager++;
		}
		else if(ant.equals("soldier")) {
			antColony.add(new Soldier());
			antColony.get(antColony.size() - 1).id = idCounter++;
			antColony.get(antColony.size() - 1).antType = "soldier";
			grid[centerX][centerY].numberSoldier++;
		}
		else if(ant.equals("scout")) {
			
			antColony.add(new Scout());
			antColony.get(antColony.size() - 1).id = idCounter++;
			antColony.get(antColony.size() - 1).antType = "scout";
			grid[centerX][centerY].numberScout++;
		}
		
		antColony.get(antColony.size() - 1).antLocationX = centerX;
		antColony.get(antColony.size() - 1).antLocationY = centerY;
		
	}
	
	
}
