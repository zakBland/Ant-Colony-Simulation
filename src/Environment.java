import java.util.ArrayList;

public class Environment extends Simulation{

	private final int edgeX = 26;
	private final int edgeY = 26;
	private final int centerX = 14;
	private final int centerY = 14;
	Square[][] grid = new Square[27][27];
	static int idCounter = 1;
	static int turnsPassed;
	static int daysPassed;
	
	ArrayList<Ant> antColony = new ArrayList<Ant>(); //maybe change type	
	ArrayList<Bala>	balaColony = new ArrayList<Bala>();
	

	//methods
	
	public void initialize(ArrayList<Ant> antColony, Square[][] grid, ColonyView col) {
		
		int index = 1;				
		antColony.add(new Queen());		grid[centerX][centerY].numberForager = 50;
		grid[centerX][centerY].numberScout = 4;
		grid[centerX][centerY].numberSoldier = 10;
		
		for(int i = 0; i < 50; i++) {
			int s = 10;
			int f = 50;
			int sc = 4;
			
			if(i < s) {
				antColony.add(new Soldier());
				antColony.get(index).id = idCounter;
				idCounter++;
				antColony.get(index).antType = "soldier";
				antColony.get(index).antLocationX = centerX;
				antColony.get(index).antLocationY = centerY;
				index++;
			}
			if(i < f) {
				antColony.add(new Forager());
				antColony.get(index).id = idCounter;
				idCounter++;
				antColony.get(index).antType = "forager";
				antColony.get(index).antLocationX = centerX;
				antColony.get(index).antLocationY = centerY;
				index++;
			}
			if(i < sc) {
				antColony.add(new Scout());
				antColony.get(index).id = idCounter;
				idCounter++;
				antColony.get(index).antType = "scout";
				antColony.get(index).antLocationX = centerX;
				antColony.get(index).antLocationY = centerY;
				index++;
			}
			
		}
				
		grid[getCenterX()][getCenterY()].numberFood = 1000;
		grid[14][14].node.showQueenIcon();
		grid[14][14].node.showScoutIcon();
		grid[14][14].node.showForagerIcon();
		grid[14][14].node.showSoldierIcon();

		grid[centerX][centerY].node.setQueen(true);
		
		grid[14][14].node.setID(14 + " " + 14);
		grid[15][15].node.setID(15 + " " + 15);
		grid[15][13].node.setID(15 + " " + 13);
		grid[14][15].node.setID(14 + " " + 15);
		grid[14][13].node.setID(14 + " " + 13);
		grid[13][13].node.setID(13 + " " + 13);
		grid[13][14].node.setID(13 + " " + 14);
		grid[13][15].node.setID(13 + " " + 15);
		grid[15][14].node.setID(15 + " " + 14);
		grid[14][14].discovered = true;
		grid[15][14].discovered = true;
		grid[15][15].discovered = true;
		grid[15][13].discovered = true;
		grid[14][15].discovered = true;
		grid[14][13].discovered = true;
		grid[13][13].discovered = true;
		grid[13][14].discovered = true;
		grid[13][15].discovered = true;
		grid[15][15].numberFood = GenerateRandom.generateFood();
		grid[15][14].numberFood = GenerateRandom.generateFood();
		grid[15][13].numberFood = GenerateRandom.generateFood();
		grid[14][15].numberFood = GenerateRandom.generateFood();
		grid[14][13].numberFood = GenerateRandom.generateFood();
		grid[13][13].numberFood = GenerateRandom.generateFood();
		grid[13][14].numberFood = GenerateRandom.generateFood();
		grid[13][15].numberFood = GenerateRandom.generateFood();
		grid[15][15].numberPheromone = GenerateRandom.generatePheromone();
		grid[15][14].numberPheromone = GenerateRandom.generatePheromone();
		grid[15][13].numberPheromone = GenerateRandom.generatePheromone();
		grid[14][15].numberPheromone = GenerateRandom.generatePheromone();
		grid[14][13].numberPheromone = GenerateRandom.generatePheromone();
		grid[13][13].numberPheromone = GenerateRandom.generatePheromone();
		grid[13][14].numberPheromone = GenerateRandom.generatePheromone();
		grid[13][15].numberPheromone = GenerateRandom.generatePheromone();
		grid[14][14].numberFriends = 65;
		setValues(14, 14, this, this.grid);
		col.addColonyNodeView(this.grid[14][14].node, 14, 14);
		setValues(15, 14, this, this.grid);
		col.addColonyNodeView(this.grid[15][14].node, 15, 14);
		setValues(15, 13, this, this.grid);
		col.addColonyNodeView(this.grid[15][13].node, 15, 13);
		setValues(14, 15, this, this.grid);
		col.addColonyNodeView(this.grid[14][15].node, 14, 15);
		setValues(14, 13, this, this.grid);
		col.addColonyNodeView(this.grid[14][13].node, 14, 13);
		setValues(13, 15, this, this.grid);
		col.addColonyNodeView(this.grid[13][15].node, 13, 15);
		setValues(13, 14, this, this.grid);
		col.addColonyNodeView(this.grid[13][14].node, 13, 14);
		setValues(13, 13, this, this.grid);
		col.addColonyNodeView(this.grid[13][13].node, 13, 13);
		setValues(15, 15, this, this.grid);
		col.addColonyNodeView(this.grid[15][15].node, 15, 15);
		grid[14][14].node.setQueen(true);


	}
	
	
	public int getCenterX() {
		return centerX;
	}
	
	public int getCenterY() {
		return centerY;
	}
	
	public int getEdgeX(){
		return edgeX;
	}
	
	public int getEdgeY() {
		return edgeY;
	}
	
	public Square[][] initializeSquares(Square[][] grid) {
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid.length; j++) {
				grid[i][j] = new Square();
			}
		}
		
		return grid;
	}
	
	public Square[][] decreasePheromone(Environment e, Square[][] grid) {
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid.length; j++) {
				e.grid[i][j].numberPheromone /= 2;
				e.grid[i][j].node.setPheromoneLevel(e.grid[i][j].numberPheromone); //14

			}
		}
		
		return grid;
	}
	
	public void setValues(int x, int y, Environment e, Square[][] grid) {
		Square loc = e.grid[x][y];
		
		loc.node.setForagerCount(loc.numberForager);
		loc.node.setScoutCount(loc.numberScout);
		loc.node.setBalaCount(loc.numberBala);
		loc.node.setSoldierCount(loc.numberSoldier);
		loc.node.setPheromoneLevel(loc.numberPheromone);
		loc.node.setFoodAmount(loc.numberFood);
	}
}
