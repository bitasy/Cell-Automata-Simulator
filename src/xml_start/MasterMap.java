package xml_start;
import java.io.File;
import java.util.Map;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

    // public static final String DATA_FILE_EXTENSION = "*.xml";

    private static SimulationParameters read(String s) throws Exception {
        File dataFile = new File(s);
        SimulationParameters mySimulation = null;
        if (dataFile != null) {
            try {
            	
            	mySimulation = new XMLParserCS("simulation").getSimulation(dataFile);
            		System.out.println("it may have worked");
            }
            catch (XMLException e) {
                Alert a = new Alert(AlertType.ERROR);
                a.setContentText(e.getMessage());
                a.showAndWait();
            }
        }
        
        return mySimulation;
    }


    public static void main (String[] args) throws Exception {
        
    		String fileName = "data/TestFile.xml";
    		SimulationParameters myTester = read(fileName);
    		System.out.println(myTester == null);
    		Map<String, String> myMap = myTester.getMap();
    		System.out.println(myMap.get("artist"));
    		
    		
    	
    }

    // TODO: getMap() method, returns map
    
    

}

	
	

