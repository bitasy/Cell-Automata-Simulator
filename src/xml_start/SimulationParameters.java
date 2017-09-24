package xml_start;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import backend.RuleSet;
import rulesets.*;
import javafx.scene.paint.Color;

/**
 * Contains simulation parameters for a unique simulation, loaded from XML file
 * @author team14
 *
 * Some code structure adapted from Music.java by @author Robert C. Duvall
 *
 */
public class SimulationParameters {

    // XML related variables
    public static final String DATA_TYPE = "SimulationParameters";
    public static final List<String> DATA_FIELDS = Arrays.asList(new String[] {
    		"title",
    		"author",
        "colorScheme",
        "extraParams",
        "grid"
    });
	
    private Color[] colors = {Color.WHITE, Color.BLACK, Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE};
    private RuleSet[] rules = {new GameOfLifeRuleSet(), new SegregationRuleSet(), new FireRuleSet(), new PredatorPreyRuleSet()};
    public static final String[] SIMULATION_NAMES = {"GameOfLife", "Segregation", "Fire", "PredatorPrey"};
    public Map<String, RuleSet> rulesMap = new HashMap<String, RuleSet>();
    
    private Map<Integer, Color> simColorScheme = new HashMap<Integer, Color>();
    private int[][] simGrid;
    private String simAuthor;
    private String simTitle;
    private RuleSet simRules;
    private double[] simExtraParams;
    private String simName;
    
    // specific data values for this instance
    private Map<String, String> myDataValues;

    // constructor initializing all parameters for simulation
    public SimulationParameters (Map<String, String> dataValues, String simPathName) {
    	
        myDataValues = dataValues;    
        simName = simPathName;
        setupAllParameters();
        
    }
    
    /*=============================================================================
    |
    |
    |              SETUP PARAMETERS SECTION
    |
    |
    *===========================================================================*/
    
    
    // calls all setup methods
    public void setupAllParameters() {
    	
    		setupGrid();
    		setupAuthor();
    		setupTitle();
    		setupColorScheme();
    		setupExtraParams();
    		setupRules();
    	
    }
    
    
    // mapping states to color objects
    public void setupColorScheme() {
	    	
	    	String colorSchemeString = myDataValues.get("colorScheme");
	    	String[] colorSchemeStringArray = colorSchemeString.split("\\s+");
	    	
	    	// example: "-1 -1 0 -1 1" means State 0 maps to colors[i=2, RED], State 1 maps to colors[i=4, GREEN]
	    	for (int i = 0; i < colorSchemeStringArray.length; i++) {
	    		int state = Integer.valueOf(colorSchemeStringArray[i]);
	    		if (state > -1) {
	    			simColorScheme.put(state, colors[Integer.valueOf(i)]);
	    		}
	    		
	    	}
	    	
    }
    
    // convert String grid to int[][]
    public void setupGrid() {
    	
    		String s = myDataValues.get("grid");
    		
    		String[] rows = s.split(";");
    		String[] cols = rows[0].split("\\s+");
    		
    		int[][] myGrid = new int[rows.length][cols.length-1];
    		
    		// trimming first element of string array due to XML formatting
    		for (int r = 0; r < rows.length; r++) {
    			String[] rowString = rows[r].split("\\s+");
    			for (int c = 1; c < cols.length; c++) {
    				
    				myGrid[r][c-1] = Integer.valueOf(rowString[c]);
    				
    			}
    		}
    		
    		simGrid = myGrid;
    		return;
    		
    }
    
    
    // setup author
    public void setupAuthor() {
    		simAuthor = myDataValues.get("author");
    }
    
    
    // setup title
    public void setupTitle() {
    		simTitle = myDataValues.get("title");
    }
    
    
    // setup rule set file
    public void setupRules() {
    	
    		for (int i = 0; i < rules.length; i++) {
    			rulesMap.put(SIMULATION_NAMES[i], rules[i]);
    		}
    		
    		simRules = rulesMap.get(simName);
    	
    }
    
    
    
    // extra parameters that may be unique do different simulations
    public void setupExtraParams() {
    		
    		String EP = myDataValues.get("extraParams");
    		String[] EPStringArray = EP.split("\\s+");
    		double[] EPArray = new double[EPStringArray.length];
    		
    		for (int i = 0; i < EPArray.length; i++) {
    			EPArray[i] = Double.valueOf(EPStringArray[i]);
    		}

    		simExtraParams = EPArray; 
    
    }
    
    
    /*=============================================================================
    |
    |
    |                     GETTERS SECTION
    |
    |
    *===========================================================================*/
    
    // get color scheme
    public Map<Integer, Color> getColorScheme() {
    		return simColorScheme;
    }
    
    
    // get Grid
    public int[][] getGrid() {
    		return simGrid; 
    }
    
    // get Author
    public String getAuthor() {
    		return simAuthor;
    }
    
    // get Title
    public String getTitle() {
    		return simTitle;
    }
    
    // get rules object
    public RuleSet getRules() {
    		return simRules;
    }
    
    // get extra parameters
    public double[] getExtraParameters() {
    		return simExtraParams;
    }
    
    // get num of rows
    public int getNumRows() {
    		return simGrid.length;
    }
    
    // get num of cols
    public int getNumCols() {
    		return simGrid[0].length;
    }
    
    
    
    /*=============================================================================
    |
    |
    |                     TEMPORARY METHODS (FOR TESTING)
    |
    |
    *===========================================================================*/
    
    
    
    
    // TEMPORARY METHOD: print grid
    public void printGrid() {
    		for (int[] row : simGrid) {
    			System.out.println(Arrays.toString(row));
    		}
    		System.out.println();
    		System.out.println("rows: " + simGrid.length + " cols: " + simGrid[0].length);
    }
    
    
    
    // TEMPORARY METHOD: print extra parameters
    public void printEP() {
    		System.out.println(Arrays.toString(simExtraParams));
    }
    
    
    
    // TEMPORARY METHOD: print color scheme
    public void printCS() {
    	
    		for (int state : simColorScheme.keySet()) {
    			System.out.println("State " + state + ": " + simColorScheme.get(state).toString());
    		}
    	
    }
    

    
    
    
}
