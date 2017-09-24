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
 * @author ptflecha
 * 
 * Adapted from Main.java code from:
 * 
 * @author Rhondu Smithwick
 * @author Robert C. Duvall
 */
public class MasterMap {

	public static final String[] SIMULATION_NAMES = {"GameOfLife", "Segregation", "Fire", "PredatorPrey"};
	private Map<String, SimulationParameters> simulationMap = null;
	private Map<String, ArrayList<String>> errorsMap = new HashMap<String, ArrayList<String>>();
	
	public MasterMap() {
		
		makeMap();
		
	}
	
	// reads in XML file and assigns SimulationParameters values
    private static SimulationParameters read(String s) throws Exception {
    	
		String fileName = "data/" + s + ".xml";
        File dataFile = new File(fileName);
        SimulationParameters mySimulation = null;
        
        if (dataFile != null) {
        	
        		mySimulation = new XMLParserCS("simulation").getSimulation(dataFile, s);
        		
        }
        
        return mySimulation;
    }
    
    
    // creates map of all SimulationParameter instances for each simulation type
    	private void makeMap() {
    	
    		simulationMap = new HashMap<String, SimulationParameters>();
    		errorsMap.clear();
    		
    		for (String s : SIMULATION_NAMES) {
    			
    			SimulationParameters currentSimulation = null;
				try {
					currentSimulation = read(s);
				} catch (Exception e) {

					// e.printStackTrace();
					
		            	ArrayList<String> errorList = new ArrayList<String>();
	        			errorList.add(e.getMessage());
	        			errorsMap.put(s, errorList);          		

	            }	
				
			if (currentSimulation != null) {
					simulationMap.put(s, currentSimulation);			
			}
    		}
    		
    		return;
    }

    // getter for master map
    public Map<String, SimulationParameters> getMap() {
    		return simulationMap;
    }
    
    // getter for errors map
    public Map<String, ArrayList<String>> getErrors() {
    		return errorsMap;
    }
    

}

	
	

