
public class Square extends Environment{
	//data fields
	int locationX;
	int locationY;
	int numberFood;
	boolean discovered;
	int numberBala;
	int numberPheromone;
	int numberScout;
	int numberSoldier;
	int numberForager;
	int numberFriends = numberScout + numberSoldier + numberForager;
	ColonyNodeView node = new ColonyNodeView();
	
	
	Square(){
		discovered = false;
	}
	
	public String doMove(int x, int y, String move) {
		
		if(move.equals("U")) {
			y--;
		}
		
		else if(move.equals("D")) {
			y++;
		}
		
		else if(move.equals("L")) {
			x--;
		}
		
		else if(move.equals("R")) {
			x++;
		}
		
		else if(move.equals("LU")) {
			x--;
			y--;
		}
		
		else if(move.equals("RU")) {
			x++;
			y--;
		}
		
		else if(move.equals("LD")) {
			x--;
			y++;
		}
		
		else if(move.equals("RD")) {
			x++;
			y++;
		}
		
		return x + " " + y;
	}

}
