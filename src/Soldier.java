import java.util.ArrayList;

public class Soldier extends Ant{

	Soldier(){
		maxLife = 1;
		canMove = false;
		antType = "soldier";
	}
	//methods
	public void attack(Environment e, Ant ant, Square[][] grid, int locationX, int locationY, ArrayList<Ant> antColony, ArrayList<Bala> balaColony, int i) {
		boolean sur = surviveRandom();
		ArrayList<Integer> intList; 
		 int randomNum; 
		 if(!sur) {
			intList = findAnts(e.antColony, "bala", ant.antLocationX, ant.antLocationY, e.balaColony);
			randomNum = (int)(Math.random() * intList.size());
	
			balaColony.remove((int)(intList.get(randomNum)));
			e.grid[ant.antLocationX][ant.antLocationY].numberBala--;
			e.grid[ant.antLocationX][ant.antLocationY].node.setBalaCount(grid[ant.antLocationX][ant.antLocationY].numberBala); //12
			
			if(e.grid[ant.antLocationX][ant.antLocationY].numberBala == 0)
				e.grid[ant.antLocationX][ant.antLocationY].node.hideBalaIcon(); //13
		}

		
	}

	public ArrayList<String> adjacent(Square[][] grid, int locationX, int locationY, Ant ant) { //fix grid
		ArrayList<String> possibleMoves = new ArrayList<String>();
		boolean validMovement = false;
		locationX = ant.antLocationX;
		locationY = ant.antLocationY;
		
		if((locationX + 1 <= getEdgeX() && locationX + 1 >= 0 && locationY + 1 >= 0 && locationY + 1 <= getEdgeY()) && (grid[locationX + 1][locationY + 1].numberBala != 0)) { //RD
			possibleMoves.add("RD");
			validMovement = true;
		}
		if((locationX - 1 <= getEdgeX() && locationX - 1 >= 0 && locationY + 1 >= 0 && locationY + 1 <= getEdgeY()) && (grid[locationX - 1][locationY + 1].numberBala != 0)) { //LD
			possibleMoves.add("LD");
			validMovement = true;
		}
		if((locationX + 1 <= getEdgeX() && locationX + 1 >= 0 && locationY - 1 >= 0 && locationY - 1 <= getEdgeY()) && (grid[locationX + 1][locationY - 1].numberBala != 0)) { //RU
			possibleMoves.add("RU");
			validMovement = true;
		}
		if((locationX - 1 <= getEdgeX() && locationX - 1 >= 0 && locationY - 1 >= 0 && locationY - 1 <= getEdgeY()) && (grid[locationX - 1][locationY - 1].numberBala != 0)) { //LU
			possibleMoves.add("LU");
			validMovement = true;
		}
		if((locationX + 1 <= getEdgeX() && locationX + 1 >= 0 && locationY >= 0 &&locationY <= getEdgeY()) && (grid[locationX + 1][locationY].numberBala != 0)) { //right
			possibleMoves.add("R");
			validMovement = true;
		}
		if((locationX - 1 <= getEdgeX() && locationX - 1 >= 0 && locationY >= 0 &&locationY <= getEdgeY()) && (grid[locationX - 1][locationY].numberBala != 0)) { //left
			possibleMoves.add("L");
			validMovement = true;
		}
		if((locationX <= getEdgeX() && locationX >= 0 && locationY + 1 >= 0 && locationY + 1 <= getEdgeY()) && (grid[locationX][locationY + 1].numberBala != 0)) { //down
			possibleMoves.add("D");
			validMovement = true;
		}
		if((locationX <= getEdgeX() && locationX >= 0 && locationY - 1 >= 0 && locationY - 1 <= 26 && (grid[locationX][locationY - 1].numberBala != 0))) {
			possibleMoves.add("U");
			validMovement = true;
				
		}
		
		ArrayList<String> moves = possibleMovesList(grid, locationX, locationY, ant);
		
		if(!moves.isEmpty()) {
			return moves;
		}
		
		else {
		//if no bala, move randomly to valid squares
		if(!validMovement && (locationX + 1 <= getEdgeX() && locationX + 1 >= 0 && locationY + 1 >= 0 && locationY + 1 <= getEdgeY()))
			possibleMoves.add("RD");
		
		if(!validMovement && (locationX - 1 <= getEdgeX() && locationX - 1 >= 0 && locationY + 1 >= 0 && locationY + 1 <= getEdgeY()))
			possibleMoves.add("LD");
		
		if(!validMovement && (locationX + 1 <= getEdgeX() && locationX + 1 >= 0 && locationY - 1 >= 0 && locationY - 1 <= getEdgeY()))
			possibleMoves.add("RU");
		
		if(!validMovement && (locationX - 1 <= getEdgeX() && locationX - 1 >= 0 && locationY - 1 >= 0 && locationY - 1 <= getEdgeY()))
			possibleMoves.add("LU");
		
		if(!validMovement && (locationX + 1 <= getEdgeX() && locationX + 1 >= 0 && locationY >= 0 && locationY <= getEdgeY()))
			possibleMoves.add("R");
		
		if(!validMovement && (locationX - 1 <= getEdgeX() && locationX - 1 >= 0 && locationY >= 0 && locationY <= getEdgeY()))
			possibleMoves.add("L");
		
		if(!validMovement && (locationX <= getEdgeX() && locationX >= 0 && locationY + 1 >= 0 && locationY + 1 <= getEdgeY()))
			possibleMoves.add("D");
		
		if(!validMovement && (locationX <= getEdgeX() && locationX >= 0 && locationY - 1 >= 0 && locationY - 1 <= getEdgeY()))
			possibleMoves.add("U");
		
		return possibleMoves;
		}
	}
}
