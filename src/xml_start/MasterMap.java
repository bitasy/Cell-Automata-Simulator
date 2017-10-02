package xml_start;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

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
 * @author Paulo Flecha
 * 
 * Adapted from Main.java code from:
 * 
 * @author Rhondu Smithwick
 * @author Robert C. Duvall
 */
public class MasterMap {
	
	private Map<String, SimulationParameters> simulationMap = null;
	private Map<String, List<String>> errorsMap = new HashMap<String, List<String>>();
	
	public MasterMap() {
		
		makeMap();
		
	}
	
	// reads in XML file and assigns SimulationParameters values
    private SimulationParameters read(String s) throws Exception {
    	
		String fileName = "data/" + s + ".xml";
		
		//if (!isValidName(s)) {return null;}
		
        File dataFile = new File(fileName);
        SimulationParameters mySimulation = null;
        
        if (dataFile != null) {
        	
        		mySimulation = new XMLParserCS("simulation").getSimulation(dataFile, s);
        		
        }
        
        if (mySimulation != null) {mySimulation.setupGridObject();}
        return mySimulation;
    }
    
    // checks to see if there is a file name in rule sets
    private boolean isValidName(String s) {
    	
	    	File folder = new File("rulesets/");
	    	File[] listOfFiles = folder.listFiles();
	
	    	for (int i = 0; i < listOfFiles.length; i++) {
	    	      if (listOfFiles[i].isFile()) {
	    	    	  
	    	    	  	String fileString = listOfFiles[i].getName();
	    	    	  	if (fileString.contains(s)) {return true;}
	    	      }
	    	}
    	
	    	return false;
	    	
    }
    
    	
    	// creates map of all SimulationParameter instances for each simulation type
    	private void makeMap() {
    	
    		simulationMap = new HashMap<String, SimulationParameters>();
    		errorsMap.clear();
    		
	    	File folder = new File("data/");
	    	File[] listOfFiles = folder.listFiles();
	
	    	for (int i = 0; i < listOfFiles.length; i++) {
	    	      if (listOfFiles[i].isFile()) {
	    	    	  
	    	    	  	String fileString = listOfFiles[i].getName();
	    	    	  	// System.out.println("file string is: " + fileString);
	    	    	  	if (fileString.contains(".xml")) {
		    	    	  	String s = fileString.replace(".xml","");
    	    	  	 	  	// System.out.println(s);
		    	    	  	SimulationParameters currentSimulation;
		    				try {
		    					currentSimulation = read(s);
		    					simulationMap.put(s, currentSimulation);
		    				} catch (XMLFormatException e) {
	    	            		
		    		            	List<String> errorList = new ArrayList<String>();
		    	        			errorList.add(e.getMessage());
		    	        			errorsMap.put(s, errorList);    
		    					
		    				} catch (Exception e) {
		    					
		    		            	List<String> errorList = errorsMap.get(s);
		    		            	if (errorList == null) {errorList = new ArrayList<String>();}
		    		            	String errorMsg = "Error loading XML for " + s;
		    		            	errorList.add(errorMsg);
		    	        			errorsMap.put(s, errorList);          		

		    	            } 
		    				
	    	    	  	}
	    	    	  	

	    	      }
	    	}
    		
    		return;
    }
    	
    // getter for master map
    public Map<String, SimulationParameters> getMap() {
    		return simulationMap;
    }
    
    // getter for errors map
    public Map<String, List<String>> getErrors() {
    		return errorsMap;
    }
    

}

	
	

