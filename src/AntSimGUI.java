import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

/**
 *	class AntSimGUI
 *
 *	main window for ant simulation
 *	contains:
 *		1.	a control panel for setting up and running the simulation
 *		2.	a graphical view of the ant colony
 
 		NOT MY CODE
 */
public class AntSimGUI extends JFrame
{
	
	/*************
	 *	attributes
	 ************/
	
	// view for colony
	public ColonyView colonyView;
	
	// scroll pane for colonyView
	private JScrollPane colonyPane;
	
	// panel containing buttons for controlling lation
	private ControlPanel controlPanel;
	
	// layout for positioning child components
	private SpringLayout layout;
	
	// user's screen width
	private int screenWidth;
	
	// user's screen height
	private int screenHeight;
	
	// list of event listeners for this view	
	private LinkedList simulationEventListenerList;
	
	//run
	
	
	
	/***************
	 *	constructors
	 **************/
	
	/**
	 *	create a new AntSimGUI
	 */
	

	public AntSimGUI(ColonyView cv)
	{
		// call superclass constructor and set title of window
		super("Ant Simulation GUI");
	
		colonyView = cv;
		// create anonymous listener to allow user to close window and end sim
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0);
			}
		});
		

		// get user's screen width and height
		screenWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		screenHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		
		// set layout
		getContentPane().setLayout(new BorderLayout());
		
		// set the size of the window		
		resizeGUI();
		
		// create event listener list
		simulationEventListenerList = new LinkedList();
		
		// show window
		setVisible(true);
		
		// validate all components
		validate();
		
	
	}
	
	
	/**********
	 *	methods
	 *********/
	
	/**
	 *	initialize this GUI
	 *
	 *	a control panel and scrollable pane for displaying the specified
	 *	ColonyView will be created and added to this GUI
	 *
	 *	@param	colonyView		the ColonyView to be displayed
	 */
	public void initGUI(ColonyView colonyView)
	{
		// create button control panel
		controlPanel = new ControlPanel();
		
		// set up colony view with default dimensions
		colonyPane = new JScrollPane(colonyView);
		colonyPane.setPreferredSize(new Dimension(800, 600));
		
		// add control panel and colony view
		getContentPane().add(controlPanel, BorderLayout.NORTH);
		getContentPane().add(colonyPane, BorderLayout.CENTER);
		
		// validate all components
		validate();
	}
	
	
	/**
	 *	set window size based on user's screen settings
	 *
	 *	initial size will be smaller than the dimensions of the user's screen
	 *	once sized, the window is maximized to fill the screen
	 */
	private void resizeGUI()
	{
		// set window size
		if (screenWidth >= 1280)
			setSize(1024, 768);
		else if (screenWidth >= 1024)
			setSize(800, 600);
		else if (screenWidth >= 800)
			setSize(640, 480);
		
		// maximize window
		setExtendedState(this.getExtendedState() | this.MAXIMIZED_BOTH);
	}
	
	
	/**
	 *	set the current simulation time
	 *
	 *	@param	time		String indicating simulation time in terms of days and turns
	 */
	public void setTime(String time)
	{
		controlPanel.setTime(time);		
	}
	
	
	/**
	 *	add an event listener to this view
	 *
	 *	@param	listener		listener interested in this view's events
	 */
	public void addSimulationEventListener(SimulationEventListener listener)
	{
		simulationEventListenerList.add(listener);
	}
	
	
	/**
	 *	remove an event listener from this view
	 *
	 *	@param	listener		listener to be removed
	 */
	public void removeSimulationEventListener(SimulationEventListener listener)
	{
		simulationEventListenerList.remove(listener);
	}
	
	
	/**
	 *	fire a simulation event
	 *
	 *	@param	eventType		the type of event that occurred (see the
	 *							SimulationEvent class for allowable types)
	 */
	private void fireSimulationEvent(int eventType)
	{
		// create event
		SimulationEvent simEvent = new SimulationEvent(this, eventType);
		
		// inform all listeners
		for (Iterator itr = simulationEventListenerList.iterator(); itr.hasNext(); )
		{
			((SimulationEventListener)itr.next()).simulationEventOccurred(simEvent);
		}
			
	}
	
	
	/**
	 *	inner class ControlPanel
	 *
	 *	contains buttons for controlling the simulation, and displays the
	 *	simulation time
	 */
	private class ControlPanel extends JPanel
	{
		
		/*************
		 *	attributes
		 ************/
		
		// button for setting up a normal simulation
		private JButton normalSetupButton;
		
		// button for setting up to test the queen ant
		private JButton queenTestButton;
		
		// button for setting up to test the scout ant
		private JButton scoutTestButton;
		
		// button for setting up to test the forager ant
		private JButton foragerTestButton;
		
		// button for setting up to test the soldier ant
		private JButton soldierTestButton;
		
		// button for running the simulation continuously
		private JButton runButton;
		
		// button for running the simulation one turn at a time
		private JButton stepButton;
		
		// label for displaying the time in the simulation
		private JLabel timeLabel;
		
		// event handler for button press events
		private ButtonHandler buttonHandler;
		
		
		/***************
		 *	constructors
		 **************/
		
		/**
		 *	create a new control panel
		 */
		public ControlPanel()
		{
			// call superclass constructor
			super();
						

			// create handler for button press events
			buttonHandler = new ButtonHandler();
			
			// initialize child components
			initComponents();
			
			// position child components
			layoutComponents();
		}
		
		
		/**
		 *	create child components
		 */
		private void initComponents()
		{
			// setup button
			normalSetupButton = new JButton("Normal Setup");
			normalSetupButton.addActionListener(buttonHandler);
			normalSetupButton.setToolTipText("Set up simulation for normal execution");
			
			// queen test button
			queenTestButton = new JButton("Queen Test");
			queenTestButton.addActionListener(buttonHandler);
			queenTestButton.setToolTipText("Set up simulation for testing the queen ant");
			
			// scout test button
			scoutTestButton = new JButton("Scout Test");
			scoutTestButton.addActionListener(buttonHandler);
			scoutTestButton.setToolTipText("Set up simulation for testing the scout ant");
			
			// forager test button
			foragerTestButton = new JButton("Forager Test");
			foragerTestButton.addActionListener(buttonHandler);
			foragerTestButton.setToolTipText("Set up simulation for testing the forager ant");
			
			// soldier test button
			soldierTestButton = new JButton("Soldier Test");
			soldierTestButton.addActionListener(buttonHandler);
			soldierTestButton.setToolTipText("Set up simulation for testing the soldier ant");
			
			// button for running simulation continuously
			runButton = new JButton("Run");
			runButton.addActionListener(buttonHandler);
			runButton.setToolTipText("Run the simulation continuously");
			
			// button for running simulation one turn at a time
			stepButton = new JButton("Step");
			stepButton.addActionListener(buttonHandler);

			
			stepButton.setToolTipText("Step through the simulation one turn at a time");
			
			// label for displaying simulation time
			timeLabel = new JLabel();
			timeLabel.setFont(new Font("Verdana", Font.BOLD, 12));
		}	
	
		
		/**
		 *	position child components and add them to this view
		 */
		private void layoutComponents()
		{
			this.add(normalSetupButton);
			this.add(queenTestButton);
			this.add(scoutTestButton);
			this.add(foragerTestButton);
			this.add(soldierTestButton);
			this.add(runButton);
			this.add(stepButton);
			this.add(timeLabel);
		}
	
	
		/**
	 	 *	set the current simulation time
	 	 *
	 	 *	@param	time		String indicating simulation time in terms of days and turns
	 	 */
		public void setTime(String time)
		{
			timeLabel.setText("     " + time);
		}
	
	
		/**
		 *	inner class ButtonHandler
		 *
		 *	responsible for handling button press events from the control panel
		 */
		private class ButtonHandler extends Thread implements ActionListener
		{
			Simulation s = new Simulation();
			String move;						//move string
			ArrayList<String> ar;				//list of moves
			Environment en = new Environment();	//new environment
			boolean stepTrue = false;
			boolean setUp = false;
	
			
			/**********
			 *	methods
			 *********/
			
			/**
 	 	 	 *	respond to a button action
 	 	 	 * 
 	 	 	 *	fires a simulation event appropriate for the button that is pressed
 	 	 	 */
			
			
			public void run() {
				while(en.antColony.get(0).alive) {
					s.start(colonyView, en, move, ar);
					setTime("Day: " + Environment.turnsPassed/10 + ", Turn: " + Environment.turnsPassed % 10);

					try {
						Thread.sleep(350);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
				}
				if(!en.antColony.get(0).alive)
					try {
						System.out.println("Queen died. 10 minutes to view GUI grid.");
						Thread.sleep(100000);
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	
			}
			
			public void actionPerformed(ActionEvent e)
			{
				// get the button that was pressed
				JButton b = (JButton)e.getSource();
	
				// fire appropriate event
				if (b.getText().equals("Normal Setup"))
				{
					// set up for normal simulation
					fireSimulationEvent(SimulationEvent.NORMAL_SETUP_EVENT);
					
					setUp = true;
					en.grid = en.initializeSquares(en.grid); //initialize squares of environment
					en.initialize(en.antColony, en.grid, colonyView); //initialize ants and properties as well as nodes	
					en.grid[en.getCenterX()][en.getCenterY()].numberFood = 1000; //For testing purposes; temporary
					setTime("Day: " + Environment.turnsPassed/10 + ", Turn: " + Environment.turnsPassed % 10);
				}
				else if (b.getText().equals("Queen Test"))
				{
					// set for testing the queen ant
					fireSimulationEvent(SimulationEvent.QUEEN_TEST_EVENT);
				}
				else if (b.getText().equals("Scout Test"))
				{
					// set for testing the scout ant
					fireSimulationEvent(SimulationEvent.SCOUT_TEST_EVENT);
				}
				else if (b.getText().equals("Forager Test"))
				{
					// set for testing the forager ant
					fireSimulationEvent(SimulationEvent.FORAGER_TEST_EVENT);
				}
				else if (b.getText().equals("Soldier Test"))
				{
					// set for testing the soldier ant
					fireSimulationEvent(SimulationEvent.SOLDIER_TEST_EVENT);
				}
				else if (b.getText().equals("Run"))
				{
					// run the simulation continuously
					if(setUp) {
						fireSimulationEvent(SimulationEvent.RUN_EVENT);
						this.start();	
					}
					
				}
				else if (b.getText().equals("Step"))
				{
					stepTrue = true;
					
					// run the simulation one turn at a time
					if(setUp) {
						fireSimulationEvent(SimulationEvent.STEP_EVENT);
						s.start(colonyView, en, move, ar);
						setTime("Day: " + Environment.turnsPassed/10 + ", Turn: " + Environment.turnsPassed % 10);
						stepTrue = false;
					}
						
				}
			}
		}
	}
}
