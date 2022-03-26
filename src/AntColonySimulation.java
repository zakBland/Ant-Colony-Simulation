
public class AntColonySimulation {

	
	/** I set the turn speed to be 1 second. If you want it to go faster, 
	 * just change the thread time in the AntSimGUI class. I made it also not terminate the GUI 
	 * immediately after the queen dies so you can view it afterwards if needed. */
	public static void main(String[] args) {
		ColonyView col = new ColonyView(27,27);

	
		AntSimGUI gui = new AntSimGUI(col);		
		gui.initGUI(col);
		
	
	}
}

