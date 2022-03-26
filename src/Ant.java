import java.util.ArrayList;

public class Ant extends GenerateRandom{
	
	//Data fields
	int id;
	int age;
	boolean alive = true;
	boolean canMove;
	int maxLife;
	int antLocationX;
	int antLocationY;
	int previousLocX;
	int previousLocY;
	String antType;
	final static int NUM_TURN = 10;
	final static int NUM_YEAR = 365;
	
	//methods
	
	

	
	public boolean canMove() {
		return canMove;
	}
	
	
	
	public ArrayList<String> possibleMovesList(Square[][] grid, int locationX, int locationY, Ant ant) {
		ArrayList<String> possibleMoves = new ArrayList<String>();
		
		locationX = ant.antLocationX;
		locationY = ant.antLocationY;
		
		if((locationX + 1 <= getEdgeX() && locationX + 1 >= 0 && locationY + 1 >= 0 && locationY + 1 <= getEdgeY())) { //RD
			if(ant.canMove() || grid[locationX + 1][locationY + 1].discovered == true) 
				possibleMoves.add("RD");
		}
		
		if((locationX - 1 <= getEdgeX() && locationX - 1 >= 0 && locationY + 1 >= 0 &&locationY + 1 <= getEdgeY())) { //LD
			if(ant.canMove() || grid[locationX - 1][locationY + 1].discovered == true)
				possibleMoves.add("LD");
		}
		if((locationX + 1 <= getEdgeX() && locationX + 1 >= 0 && locationY - 1 >= 0 &&locationY - 1 <= getEdgeY())) { //RU
			if(ant.canMove() || grid[locationX + 1][locationY - 1].discovered == true) 
				possibleMoves.add("RU");
		}
		if((locationX - 1 <= getEdgeX() && locationX - 1 >= 0 && locationY - 1 >= 0 && locationY - 1 <= getEdgeY())) { //LU
			
			if(ant.canMove() || grid[locationX - 1][locationY - 1].discovered == true)
				possibleMoves.add("LU");
		}
		if((locationX + 1 <= getEdgeX() && locationX + 1 >= 0 && locationY >= 0 && locationY <= getEdgeY())) { //right
			if(ant.canMove() || grid[locationX + 1][locationY].discovered == true) 
				possibleMoves.add("R");
		}
		if((locationX - 1 <= getEdgeX() && locationX - 1 >= 0 && locationY >= 0 && locationY <= getEdgeY())) { //left
			if(ant.canMove() || grid[locationX - 1][locationY].discovered == true) 
				possibleMoves.add("L");
		}
		if((locationX <= getEdgeX() && locationX >= 0 && locationY + 1 >= 0 &&locationY + 1 <= getEdgeY())) { //down
			if(ant.canMove() || grid[locationX][locationY + 1].discovered == true) 
				possibleMoves.add("D");
		
		}
		if((locationX <= getEdgeX() && locationX >= 0 && locationY - 1 >= 0 &&locationY - 1 <= getEdgeY())) { //up
			if(ant.canMove() || grid[locationX][locationY -1].discovered == true) 
				possibleMoves.add("U");			

		}
		
		return possibleMoves;
	}
	
	public ArrayList<Integer> findAnts(ArrayList<Ant> antColony, String antType, int locationX, int locationY,
			ArrayList<Bala> balaColony){
		ArrayList<Integer> integerList = new ArrayList<Integer>();
		int size;
		if(antType.equals("bala"))
			size = balaColony.size();
		else
			size = antColony.size();
		
		for(int i = 0; i < size; i++) {
			if(antType.equals("bala")) {
				if(balaColony.get(i).antLocationX == locationX
						&& balaColony.get(i).antLocationY == locationY) {
					integerList.add(i);
				}
			}
			else {
				if(antColony.get(i).antType.equals(antType) && antColony.get(i).antLocationX == locationX
						&& antColony.get(i).antLocationY == locationY) {
					integerList.add(i);
				}
			}

				
			
		}
		return integerList;
	}
	

	
	
}

