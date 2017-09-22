package xml_start;

import javafx.application.Application;

import javafx.stage.Stage;

/*
 * Questions:
 * 
 * 
 * 1. In the start() method, I'd need to find a way to ensure all the info that the Screen needs is available so that the screen can add all those GUI elements to it; therefore, most likely will have to create an instance of SimulationInterface to get data from SimulationInterface such that when we create the screen, it adds the elements to it;
 * 
 * 2. Will there be a default simulation that the program starts with? If so, which one? If not, how will the user select his or her initial simulation?
 * 
 * 
 * 3. The startAnimation() (i.e. step()) will not be defined here, correct? It will be defined in CellSimulator?
 * 
 * 
 *  * 4. [<-> SimulationInterface <-> CellSociety] CellSociety then passes this ArrayList of SimulationLoader objects to SimulationInterface through the Screen class, the latter of which will handle both displaying the simulation and the user interface in the stage." So will the SimulationInterface class read in that SimulationLoader object and return the current layout of elements to be displayed by Screen? Would it be easier for SimulationLoader object to be passed directly to SimulationInterface such that the Screen is ONLY responsible for handling GUI display as opposed to dealing with passing data to other classes?
 * 
 * 
 */



// starts the program
public class CellSociety extends Application {
	
	// Constants
	public static final String TITLE = "Cell Society";
	
	
    // main function
	public static void main(String[] args) {
		launch(args);
	}

	// start game
	@Override
	public void start(Stage s) throws Exception {
				
		//TODO: initialize SimulationLoader object(s) with XML file(s);
		
		//TODO: create ArrayList<SimulationLoader>
		
		//TODO: get data from SimulationInterface somehow, to use in Screen object creation (or find other way such that Screen can receive the elements that it will display)
		
		// change constructor to use this SimulationLoader's info to start screen
		Screen screen = new Screen();
		s.setScene(screen.getScene()); s.show();
		s.setTitle(TITLE);

		
		
	}
	
	

	
}