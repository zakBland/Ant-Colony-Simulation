import java.util.ArrayList;
import java.util.Random;

public class GenerateRandom extends Square{

	static Random r = new Random();
	
	public String chooseAttack(int f, int s, int sd, Square[][] grid, int locationX, int locationY) { //Add queen
		
		ArrayList<String> list = new ArrayList<String>();
		
		if((getCenterX() == locationX) && (getCenterY() == locationY))
			list.add("queen");
		if(f > 0)
			list.add("forager");
		if(s > 0)
			list.add("scout");
		if(sd > 0)
			list.add("soldier");
		
		int num = r.nextInt(list.size());
				
		return list.get(num);
			
	}
	
	public boolean surviveRandom() {
		int num = r.nextInt(50);
		
		if(num % 2 == 0) {
			return true;
		}
			return false;
	}

	public String generateLocation(Environment e, Square[][] grid, ArrayList<Bala> balaColony) {
		int x = 26;
		int y = 8;
		
		int num0 = r.nextInt(100);
		
		if(num0 < 3) { 
			int num1 = r.nextInt(4);
			int num2 = r.nextInt(26);
		
			switch(num1)
			{			
			//0 = (0, y), 1 = (x, 0), 2 = (maxX, y), 3 = (x, maxY)
			case 0: x = 0; y = num2; break;
			case 1: y = 0; x = num2; break;
			case 2: x = getEdgeX(); y = num2; break;
			case 3: y = getEdgeY(); x = num2; break;		
			}
			
			Bala bala = new Bala();
			e.balaColony.add(bala);
			bala.id = idCounter++;
			bala.antLocationX = x;
			bala.antLocationY = y;
	
			return x + " " + y;
						
		}
			
			return " ";
		
	}

	public static int generateFood() {
		
		int num1 = r.nextInt(100);
		
		if(num1 < 75) 
			return 0;	
		else 
			return r.nextInt(501) + 500;
		
	}

	public String generateAnt() {
		int num1 = r.nextInt(4);
		
		if(num1 == 0 || num1 == 2)
			return "forager";
		else if(num1 == 3) 
			return "scout";
		else
			return "soldier";
	}

	public int chooseInt(ArrayList<Ant> antColony, ArrayList<Ant> balaColony) {
		ArrayList<Integer> nums = new ArrayList<Integer>();
		
		for(int i = 0; i < antColony.size(); i++) {
			nums.add(i);
		}
		int index = r.nextInt();
		
		return index;
	}
	public String moveRandom(ArrayList<String> ar, Square[][] grid, int antLocationX, int antLocationY, String antType) {
		int num1 = r.nextInt(ar.size());
		
		String move = ar.get(num1);
		int x = 0;
		int y = 0;
	
		String newLoc = doMove(x,y, move);
		
		x = Integer.valueOf(newLoc.substring(0, newLoc.indexOf(' ')));
		y = Integer.valueOf(newLoc.substring(newLoc.indexOf(' ') + 1));


		if(antType.equals("bala")) {
			grid[antLocationX][antLocationY].numberBala--;
			grid[antLocationX + x][antLocationY + y].numberBala++;
			grid[antLocationX][antLocationY].node.setBalaCount(grid[antLocationX][antLocationY].numberBala); 
			grid[antLocationX + x][antLocationY + y].node.setBalaCount(grid[antLocationX + x][antLocationY + y].numberBala);
		}
		

		else if(antType.equals("soldier")) {
			grid[antLocationX][antLocationY].numberSoldier--;
			grid[antLocationX + x][antLocationY + y].numberSoldier++;
			grid[antLocationX][antLocationY].node.setSoldierCount(grid[antLocationX][antLocationY].numberSoldier);
			grid[antLocationX + x][antLocationY + y].node.setSoldierCount(grid[antLocationX + x][antLocationY + y].numberSoldier);
		}
		
		else if(antType.equals("scout")) {
			grid[antLocationX][antLocationY].numberScout--;
			grid[antLocationX + x][antLocationY + y].numberScout++;
			grid[antLocationX][antLocationY].node.setScoutCount(grid[antLocationX][antLocationY].numberScout);
			grid[antLocationX + x][antLocationY + y].node.setScoutCount(grid[antLocationX + x][antLocationY + y].numberScout);
		}
		
		else if(antType.equals("forager")) {
			grid[antLocationX][antLocationY].numberForager--;
			grid[antLocationX + x][antLocationY + y].numberForager++;
			grid[antLocationX][antLocationY].node.setForagerCount(grid[antLocationX][antLocationY].numberForager); //chech node counts
			grid[antLocationX + x][antLocationY + y].node.setForagerCount(grid[antLocationX + x][antLocationY + y].numberForager);

		}
		
		antLocationX += x;
		antLocationY += y;
		
		return antLocationX + " " + antLocationY; //new location value;
	}
	

	public static int generatePheromone() {
		return r.nextInt(200);
	}
}
