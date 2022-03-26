import java.util.ArrayList;

public class Bala extends Ant {
	
	Bala(){
		maxLife = 1;
		canMove = true;
		antType = "bala";
	}
	
	public void attack(Environment e, int i, int f, int s, int sd, Queen q, Square[][] grid, int locationX, int locationY, ArrayList<Ant> antColony, ArrayList<Bala> balaColony) {
		String attackType = chooseAttack(f, s, sd, grid, locationX, locationY); //might need to use dot reference
		boolean sur = surviveRandom();

		if(!sur) {	
			 
			if(attackType.equals("queen")) {
				System.out.println("Queen killed by Bala");
				e.antColony.get(0).alive = false;
			 	//terminate();
			}else{
			ArrayList<Integer> intList = findAnts(antColony, attackType, locationX, locationY, balaColony);
				if(intList.size() != 0) { 
					
				int randomNum = r.nextInt(intList.size());
				
				if(attackType.equals("Forager")){
					if(((Forager)(antColony.get((int)(intList.get(randomNum))))).carryingFood) {
						e.grid[locationX][locationY].numberFood++;
						e.grid[locationX][locationY].node.setFoodAmount(grid[locationX][locationY].numberFood);//
					}
					e.grid[locationX][locationY].numberForager--;
					e.grid[locationX][locationY].node.setForagerCount(grid[locationX][locationY].numberForager);
					
					if(e.grid[locationX][locationY].numberForager == 0)
						e.grid[locationX][locationY].node.hideForagerIcon();
				}
			 
				else if(attackType.equals("Scout")){
					e.grid[locationX][locationY].numberScout--;
					e.grid[locationX][locationY].node.setScoutCount(grid[locationX][locationY].numberScout);
					
					if(e.grid[locationX][locationY].numberScout == 0)
						e.grid[locationX][locationY].node.hideScoutIcon();
				}
			 
				else if(attackType.equals("Soldier")){
					e.grid[locationX][locationY].numberSoldier--;
					e.grid[locationX][locationY].node.setSoldierCount(grid[locationX][locationY].numberSoldier);
					
					if(e.grid[locationX][locationY].numberSoldier == 0)
						e.grid[locationX][locationY].node.hideSoldierIcon();
				}					
				
				antColony.remove((int)(intList.get(randomNum)));

			 } 	
			}
			 
		}
		

	}
	
}
