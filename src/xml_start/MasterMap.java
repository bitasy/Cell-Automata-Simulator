package xml_start;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Creates all SimulationParameter objects from all different XML files available;
 * Also creates Map<String, SimulationParameter>, mapping the title of the simulation to the SimulationParameter object
 * containing all the relevant parameters for that simulation
 * 
 * @author team 14
 * 
 * Adapted from Main.java code from:
 * 
 * @author Rhondu Smithwick
 * @author Robert C. Duvall
 */
public class MasterMap {

	public static final String[] SIMULATION_NAMES = {"GameOfLife", "Segregation", "Fire", "PredatorPrey"};
	private Map<String, SimulationParameters> simulationMap = null;
	
	public MasterMap() throws Exception {
		
		makeMap();
		
	}
	
	// TODO: handle exceptions somehow
	
	// reads in XML file and assigns SimulationParameters values
    private static SimulationParameters read(String s) throws Exception {
        File dataFile = new File(s);
        SimulationParameters mySimulation = null;
        if (dataFile != null) {
            try {
            	
            		mySimulation = new XMLParserCS("simulation").getSimulation(dataFile);
            		System.out.println("it may have worked");
            }
            catch (XMLException e) {
            	// TODO: do something relevant about error;
            	     System.out.println("Error loading " + s);
            	
//                Alert a = new Alert(AlertType.ERROR);
//                a.setContentText(e.getMessage());
//                a.showAndWait();
//                System.out.println("Some error here");
            }
        }
        
        return mySimulation;
    }
    
    
    // creates map of all SimulationParameter instances for each simulation type
    	private void makeMap() throws Exception {
    	
    		simulationMap = new HashMap<String, SimulationParameters>();
    	
    		for (String s : SIMULATION_NAMES) {
    			
    			// TODO: add throw and catch for errors
    			String fileName = "data/" + s + ".xml";
    			SimulationParameters currentSimulation = read(fileName);
    			simulationMap.put(s, currentSimulation);
    			
    		}
    		
    		return;
    	
    }

    // getter for master map
    public Map<String, SimulationParameters> getMap() {
    		return simulationMap;
    }

    
    
    public static void main (String[] args) throws Exception {
        
    		MasterMap myTester = new MasterMap();
    		SimulationParameters GOL = myTester.getMap().get("GameOfLife");
    		GOL.printGrid();
    		GOL.printEP();
    		GOL.printCS();
		
    }
    


}

	
	

