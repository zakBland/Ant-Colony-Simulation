import java.util.ArrayList;

import java.util.Collections;


public class Simulation extends Thread{

	
	ColonyView col;
	Environment e;
	String move;
	ArrayList<String> ar;

	public void start(ColonyView col, Environment e, String move, ArrayList<String> ar) {
			this.col = col;
			this.e = e;
			this.move = move;
			this.ar = ar;
			Collections.shuffle(e.antColony.subList(1, e.antColony.size()), GenerateRandom.r);
			if(!e.antColony.get(0).antType.equals("queen")) {
				System.out.println("Queen Removed");
				//terminate();
			} 
			
			//Prints out general information; temporary
			
				System.out.println("Turns: " + Environment.turnsPassed);

			//beginning of loop
			for(int i = 0; i < e.antColony.size(); i++){ //each turn for every ant
				
				Ant ant = e.antColony.get(i); //individual ant from colony
			
				//if ant is dead, skip
				if(!ant.alive) {
					e.antColony.remove(i);
					continue;
				}
				
				/*Queen*/
				if(ant.antType.equals("queen")){
									
					//Age check
					if(ant.age  == ant.maxLife * Ant.NUM_YEAR) {
						ant.alive = false;
						System.out.println("Queen died of old age");
					//	terminate();
					}
					
					//hatch
					if(Environment.turnsPassed % 10 == 0) 
						((Queen)ant).hatch(e.grid, e.getCenterX(), e.getCenterY(), e.antColony);	
							
					//Eat
					if(!((Queen)ant).eat(e.grid[e.getCenterX()][e.getCenterY()].numberFood, e.grid, e)) {
						System.out.println("Died of Starvation"); //print; temporary
						ant.alive = false;
						//terminate();
					}										
				}
				
				/*Soldiers*/
				
				if(ant.antType.equals("soldier")){ 
					e.grid[ant.antLocationX][ant.antLocationY].node.showSoldierIcon();
					ant.age++;
										
					if(ant.age/Ant.NUM_TURN == ant.maxLife * Ant.NUM_YEAR){
						System.out.println("Soldier died of old age");
						e.grid[ant.antLocationX][ant.antLocationY].numberSoldier--; 
						e.grid[ant.antLocationX][ant.antLocationY].node.setSoldierCount(e.grid[ant.antLocationX][ant.antLocationY].numberSoldier);
						if(e.grid[ant.antLocationX][ant.antLocationY].numberSoldier == 0)
							e.grid[ant.antLocationX][ant.antLocationY].node.hideSoldierIcon();
						e.antColony.remove(i); //remove if dead
						continue; //skips if dead		
					}
					
					if(e.grid[ant.antLocationX][ant.antLocationY].numberBala > 0){
						((Soldier)ant).attack(e, ant, e.grid, ant.locationX, ant.locationY, e.antColony, e.balaColony, i);
					}else{
						
						//movement
						ar = ((Soldier)ant).adjacent(e.grid, ant.locationX, ant.locationY, ant); //MAYBE FIX METHOD?????
						move = ant.moveRandom(ar, e.grid, ant.antLocationX, ant.antLocationY, ant.antType);
						
						ant.previousLocX = ant.antLocationX;
						ant.previousLocY = ant.antLocationY;
						
						//count display update and movement
						
						ant.antLocationX = Integer.valueOf(move.substring(0, move.indexOf(' ')));	
						ant.antLocationY = Integer.valueOf(move.substring(move.indexOf(' ') + 1));
						
						if(e.grid[ant.previousLocX][ant.previousLocY].numberSoldier == 0)
							e.grid[ant.previousLocX][ant.previousLocY].node.hideSoldierIcon(); //1
						e.grid[ant.antLocationX][ant.antLocationY].node.showSoldierIcon();     //2

					}			
					
				}
				
				
				/*Foragers*/
				if(ant.antType.equals("forager")){ 
					ant.age++;
					e.grid[ant.antLocationX][ant.antLocationY].node.showForagerIcon(); //3

				if(ant.age/Ant.NUM_TURN == ant.maxLife * Ant.NUM_YEAR){
					System.out.println("Forager died of old age");

						ant.alive = false;
						e.grid[ant.antLocationX][ant.antLocationY].numberForager--;
						e.grid[ant.antLocationX][ant.antLocationY].node.setForagerCount(e.grid[ant.antLocationX][ant.antLocationY].numberForager); //4
						if(e.grid[ant.antLocationX][ant.antLocationY].numberForager == 0)
							e.grid[ant.antLocationX][ant.antLocationY].node.hideForagerIcon(); //5
						
						e.antColony.remove(i);
						continue; 
					}
					
					//return to nest mode
					if(((Forager)ant).carryingFood){						
						
						//return back
						((Forager)ant).returnBack(e, ((Forager)ant).history, ant, ant.antLocationX, ant.antLocationY, e.grid);
				
						if(ant.antLocationX == e.getCenterX() && ant.antLocationY == e.getCenterY()){
							e.grid[e.getCenterX()][e.getCenterY()].numberFood++;
							e.grid[ant.antLocationX][ant.antLocationY].node.setFoodAmount(e.grid[ant.antLocationX][ant.antLocationY].numberFood); //6
							((Forager)ant).resetHistory();
						}	
					}
					
					//forage mode
					else{
						//move
						((Forager)ant).comparePheromone(e, e.grid, ant, ant.antLocationX, ant.antLocationY, ((Forager)ant).history);
					
						//pick up
						if(e.grid[ant.antLocationX][ant.antLocationY].numberFood != 0 && !((Forager)ant).carryingFood &&
							(ant.antLocationX != e.getCenterX() || ant.antLocationY != e.getCenterY())){
							((Forager)ant).pickFood(e.grid, ant.antLocationX, ant.antLocationY);
							((Forager)ant).carryingFood = true;
						}
					}
				}
				
				/*Scouts*/
				
				if((ant.antType).equals("scout")){					
					
					e.grid[ant.antLocationX][ant.antLocationY].node.showScoutIcon(); //7
					ant.age++;

					if(ant.age/Ant.NUM_TURN == ant.maxLife * Ant.NUM_YEAR){
						System.out.println("Scout died of old age");

						ant.alive = false; //maybe not needed
						e.grid[ant.antLocationX][ant.antLocationY].numberScout--;
						e.grid[ant.antLocationX][ant.antLocationY].node.setScoutCount(e.grid[ant.antLocationX][ant.antLocationY].numberScout); //8
						if(e.grid[ant.antLocationX][ant.antLocationY].numberScout == 0)
							e.grid[ant.antLocationX][ant.antLocationY].node.hideScoutIcon(); //9
						e.antColony.remove(i);
						continue;	
					}
					
					//move
					move = ant.moveRandom(ant.possibleMovesList(e.grid, ant.locationX, ant.locationY, ant), e.grid, ant.antLocationX, 
						ant.antLocationY, ant.antType); 
				
					ant.previousLocX = ant.antLocationX;
					ant.previousLocY = ant.antLocationY;
					
					ant.antLocationX = Integer.valueOf(move.substring(0, move.indexOf(' ')));
				    ant.antLocationY = Integer.valueOf(move.substring(move.indexOf(' ') + 1));
				    
				    if(e.grid[ant.previousLocX][ant.previousLocY].numberScout == 0)
						e.grid[ant.previousLocX][ant.previousLocY].node.hideScoutIcon(); //9
					e.grid[ant.antLocationX][ant.antLocationY].node.showScoutIcon(); // 8
				
				    //reveal contents
					if(e.grid[ant.antLocationX][ant.antLocationY].discovered == false){
						e.grid[ant.antLocationX][ant.antLocationY].discovered = true;
						((Scout)ant).revealContents(e, e.grid, ant.antLocationX, ant.antLocationY, col); //fixed
					}
				}

				
					/*Balas*/
					
					//possible spawn of bala
					String spawned = " ";
					if(i == 0) {
						spawned = ant.generateLocation(e, e.grid, e.balaColony);
					
						if(!spawned.equals(" ")) {
							e.balaColony.get(e.balaColony.size() - 1).antLocationX = Integer.valueOf(spawned.substring(0, spawned.indexOf(' ')));
							e.balaColony.get(e.balaColony.size() - 1).antLocationY = Integer.valueOf(spawned.substring(spawned.indexOf(' ') + 1));
							e.grid[e.balaColony.get(e.balaColony.size() - 1).antLocationX][e.balaColony.get(e.balaColony.size() - 1).antLocationY].numberBala++;
							e.grid[e.balaColony.get(e.balaColony.size() - 1).antLocationX][e.balaColony.get(e.balaColony.size() - 1).antLocationY].node.setBalaCount(e.grid[e.balaColony.get(e.balaColony.size() - 1).antLocationX][e.balaColony.get(e.balaColony.size() - 1).antLocationY].numberBala); //10
						}
					}
					
					if(e.balaColony.size() > 0 && i < e.balaColony.size()){
						Bala bala = e.balaColony.get(i); 
						e.grid[bala.antLocationX][bala.antLocationY].node.showBalaIcon(); //11
						bala.age++;

						if(bala.age / Ant.NUM_TURN == bala.maxLife * Ant.NUM_YEAR || !bala.alive){
							System.out.println("Bala died of old age");

							bala.alive = false;
						    e.grid[bala.antLocationX][bala.antLocationY].numberBala--;
							e.grid[bala.antLocationX][bala.antLocationY].node.setBalaCount(e.grid[bala.antLocationX][bala.antLocationY].numberBala); //12
							if(e.grid[bala.antLocationX][bala.antLocationY].numberBala == 0)
								e.grid[bala.antLocationX][bala.antLocationY].node.hideBalaIcon(); //13
							e.balaColony.remove(i);
						}
						else {
						//move
						
						Square square = e.grid[bala.antLocationX][bala.antLocationY];
						
						if(e.grid[bala.antLocationX][bala.antLocationY].numberFriends == 0){
							ar = bala.possibleMovesList(e.grid, bala.antLocationX, bala.antLocationY, bala);
							move = bala.moveRandom(ar, e.grid, bala.antLocationX, bala.antLocationY, bala.antType);
		
							bala.previousLocX = bala.antLocationX;
							bala.previousLocY = bala.antLocationY;

							bala.antLocationX = Integer.valueOf(move.substring(0, move.indexOf(' ')));
							bala.antLocationY = Integer.valueOf(move.substring(move.indexOf(' ') + 1));

							if(e.grid[bala.previousLocX][bala.previousLocY].numberBala == 0)
								e.grid[bala.previousLocX][bala.previousLocY].node.hideBalaIcon(); //13
							e.grid[bala.antLocationX][bala.antLocationY].node.showBalaIcon(); //12

						}else{
							bala.attack(e, i, square.numberForager, square.numberScout, square.numberSoldier, 
							((Queen)e.antColony.get(0)), e.grid, bala.antLocationX, bala.antLocationY, e.antColony, e.balaColony);
						}
					}
			}	
		}									
					/*Time passage*/

			Environment.turnsPassed++;
			if(Environment.turnsPassed % Ant.NUM_TURN == 0) {
				Environment.daysPassed++;		//a day passed
				e.antColony.get(0).age++; //Queen aged a day
				e.decreasePheromone(e, e.grid);		
			}
		
}
	
	public static void terminate() {
		System.exit(0);
	}
	
}
