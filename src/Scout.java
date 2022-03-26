
public class Scout extends Ant{
	
	Scout(){
		maxLife = 1;
		canMove = true;
		antType = "scout";
	}
	
	
	//methods
	public void revealContents(Environment e, Square[][] grid, int locationX, int locationY, ColonyView colony) {
		Square loc = e.grid[locationX][locationY];
		if(locationX != getCenterX() || locationY != getCenterY()) {
			loc.discovered = true;
			loc.node.setID(locationX + " " + locationY);;
			loc.numberFood = generateFood();
			loc.numberPheromone = generatePheromone();
			setValues(locationX, locationY, e, grid);
			colony.addColonyNodeView(e.grid[locationX][locationY].node, locationX, locationY);

		}

	}
	

}
