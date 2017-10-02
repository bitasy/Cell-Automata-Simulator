package xml_start;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import backend.IGrid;
import backend.IRuleSet;
import frontend.SliderInfo;
import grids.RectangularGrid;
import javafx.scene.paint.Color;
import rulesets.FireRuleSet;
import rulesets.GameOfLifeRuleSet;
import rulesets.PredatorPreyRuleSet;
import rulesets.SegregationRuleSet;

/**
 * Contains simulation parameters for a unique simulation, loaded from XML file
 * @author team14
 *
 * Some code structure adapted from Music.java by @author Robert C. Duvall
 *
 */
public class SimulationParameters {

    // XML related variables
	public static final Color[] COLORS = {Color.WHITE, Color.BLACK, Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE};
    public static final String DATA_TYPE = "SimulationParameters";
    public static final List<String> DATA_FIELDS = Arrays.asList(new String[] {
    		"title",
    		"author",
        "colorScheme",
        "extraParams",
        "grid",
        "initialConfig",
        "shape",
        "hasOutline",
        "neighborsConsidered",
        "toroidal",
        "sliders"
    });
    private IRuleSet[] rules = {new FireRuleSet(), new GameOfLifeRuleSet(), new PredatorPreyRuleSet(), new SegregationRuleSet()};
    public Map<String, IRuleSet> rulesMap = new HashMap<String, IRuleSet>();
    
    private Map<Integer, Color> simColorScheme = new HashMap<Integer, Color>();
    private int[][] simGrid;
    private String simAuthor;
    private String simTitle;
    private IRuleSet simRules;
    private double[] simExtraParams;
    private String simName;
    private String simInitialConfig;
    private String simShape;
    private List<SliderInfo> simSliders;
    private int[] simGridArray;
    private IGrid gridObject;
    
    // specific data values for this instance
    private Map<String, String> myDataValues;

    // constructor initializing all parameters for simulation
    public SimulationParameters (Map<String, String> dataValues, String simPathName) throws XMLFormatException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    	
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
    public void setupAllParameters() throws XMLFormatException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    	
    		setupShape();
    		setupGrid();
    		setupAuthor();
    		setupTitle();
    		setupColorScheme();
    		setupExtraParams();
    		setupRules();
    		setupSliders();
    		//setupGridObject();
    	
    }
    
    // setup sliders
    private void setupSliders() throws XMLFormatException {
    	
    		simSliders = new ArrayList<SliderInfo>();
    		String allSliders = myDataValues.get("sliders");
    		if (allSliders.contains("none")) {return;}
    		String[] sliderArray = allSliders.split(";");
    		
    		for (String data : sliderArray) {
    			SliderInfo sldr = new SliderInfo(data);
    			simSliders.add(sldr);
    		}
    	
    }
    
    // setup shape value
    private void setupShape() {
    		String s = myDataValues.get("shape");
    		if (!s.equals("CardinalGrid") && !s.equals("HexagonalGrid") &&
    				!s.equals("NeighborGrid") && !s.equals("RectangularGrid") && !s.equals("TriangularGrid")) {
    			s = "RectangularGrid";
    		}
    		simShape = s; 
	}

    // setup main grid
	public void setupGrid() throws XMLFormatException {
    	
    		simInitialConfig = myDataValues.get("initialConfig");
    		
    		if (simInitialConfig.equals("random")) {
        		setupGridRandom();
        		copy2DGridToArrayGrid();
    		} else if (simInitialConfig.equals("fixed")) {
    			if (simShape.equals("NeighborGrid")) { // only case in pemrose
    				setupGridFixedArray();
    			} else {
    				setupGridFixedMatrix();
    				copy2DGridToArrayGrid();
    			}
    		} else if (simInitialConfig.equals("probability")) {
    			setupGridProbability();
    			copy2DGridToArrayGrid();
    		} else {
    			// throw new XMLFormatException();
    			simInitialConfig = " ";
    			setupGridRandom();
    			copy2DGridToArrayGrid();
    		}
    	
    }
    
    
    // mapping states to color objects
    public void setupColorScheme() {
	    	
	    	String colorSchemeString = myDataValues.get("colorScheme");
	    	String[] colorSchemeStringArray = colorSchemeString.split("\\s+");
	    	
	    	// example: "-1 -1 0 -1 1" means State 0 maps to colors[i=2, RED], State 1 maps to colors[i=4, GREEN]
	    	for (int i = 0; i < colorSchemeStringArray.length; i++) {
	    		int state = Integer.valueOf(colorSchemeStringArray[i]);
	    		if (state > -1) {
	    			simColorScheme.put(state, COLORS[Integer.valueOf(i)]);
	    		}
	    		
	    	}
	    	
    }
    
    // setup "fixed" grid
    public void setupGridFixedArray() throws XMLFormatException {
    	
    		String s = myDataValues.get("grid");
    		String[] gridArray = s.split("\\s+|;");
    		int[] gridByTag = new int[gridArray.length];
    		for (int i = 0; i < gridArray.length; i++) {
    			
    			int stateValue = Integer.valueOf(gridArray[i]); 
    			if (stateValue < 0) {throw new XMLFormatException();}
    			gridByTag[i] =  stateValue;
    		}
    		
    		simGridArray = gridByTag;
    		return;
    		
    }
    
    public void copy2DGridToArrayGrid() {
		int[][] grid = simGrid;
		int[] cellList = new int[grid.length*grid[0].length];
		int tag = 0;
		for(int[] row : grid) {
			for(int state : row) {
				cellList[tag] = state;
				tag++;
			}
		}
		simGridArray = cellList;
		return;
    }
    
    // setup "fixed" grid
    public void setupGridFixedMatrix() throws XMLFormatException {
    	
    		String s = myDataValues.get("grid");
    		
    		String[] rows = s.split(";");
    		String[] cols = rows[0].split("\\s+");
    		
    		int[][] myGrid = new int[rows.length][cols.length-1];
    		
    		// trimming first element of string array due to XML formatting
    		for (int r = 0; r < rows.length; r++) {
    			String[] rowString = rows[r].split("\\s+");
    			for (int c = 1; c < cols.length; c++) {
    				
    				int stateValue = Integer.valueOf(rowString[c]);
    				
    				if (stateValue < 0) {throw new XMLFormatException();}
    				
    				myGrid[r][c-1] = stateValue;
    				
    			}
    		}
    		
    		simGrid = myGrid;
    		return;
    		
    }
    
    
    // setup "random" grid
    public void setupGridRandom() {
    	
    		int numRows; int numCols; int totalStates; int totalCells;
    	
    		String s = myDataValues.get("grid");
    		String[] s_array = s.split("\\s+");
    		
    		if (s_array.length == 4) {
        		numRows = Integer.valueOf(s_array[0]);
        		numCols = Integer.valueOf(s_array[1]);
        		totalStates = Integer.valueOf(s_array[2]);
        		totalCells = Integer.valueOf(s_array[3]);
    		} else { // fill in default values if incorrect formatting
    			numRows = 10;
    			numCols = 10;
    			totalStates = 1;
    			totalCells = 30;
    		}
    		
    		
    		int[][] myGrid = new int[numRows][numCols];
    		
    		// list of open grid positions
    		List<Integer> openSpaces = new ArrayList<Integer>();
    		for (int i = 0; i < numRows*numCols; i++) { openSpaces.add(i); }
    		
    		while (totalCells > 0) {
    			
    			// remove random open position
    			Random rand = new Random();
    			int  n = rand.nextInt(openSpaces.size());
    			int position = openSpaces.remove(n);
    			int row = position / numCols;
    			int col = position % numCols;
    			
    			int state = rand.nextInt(totalStates) + 1;
    			myGrid[row][col] = state;
    			totalCells--;
    			
    		}
    		
    		simGrid = myGrid;
    		return;
    		
    }
    
    // setup "probability" grid
    public void setupGridProbability() {
    	
		String s = myDataValues.get("grid");
		String[] s_array = s.split("\\s+");
		
		int numRows = Integer.valueOf(s_array[0]);
		int numCols = Integer.valueOf(s_array[1]);
		double totalCells = numRows * numCols;
		double[] probabilities = new double[s_array.length-2];
		for (int i = 0; i < s_array.length-2; i++) { probabilities[i] = Double.valueOf(s_array[i+2]);}
		int[][] myGrid = new int[numRows][numCols];
		
		// list of open grid positions
		List<Integer> openSpaces = new ArrayList<Integer>();
		for (int a = 0; a < numRows*numCols; a++) { openSpaces.add(a); }
		
		// make probability distribution list
		List<Integer> stateProbability = probabilityDistribution(totalCells, probabilities);
		
		int cellsToAdd = stateProbability.size();
		while (cellsToAdd > 0) {
			
			// remove random open position
			Random rand = new Random();
			int n = rand.nextInt(openSpaces.size());
			int position = openSpaces.remove(n);
			int row = position / numCols;
			int col = position % numCols;
			
			int stateIndex = rand.nextInt(stateProbability.size());
			int state = stateProbability.remove(stateIndex);
			
			myGrid[row][col] = state;
			cellsToAdd--;
			
		}
		
		simGrid = myGrid;
		return;
		
    	
    }

    // helper for setup "probability" grid: sets up probability distribution
	private List<Integer> probabilityDistribution(double totalCells, double[] probabilities) {
		List<Integer> stateProbability = new ArrayList<Integer>();
		for (int j = 0; j < probabilities.length; j++) {
			
			double prob = probabilities[j];
			int currState = j+1;
			int stateCells = (int) (prob*totalCells);
			for (int k = 0; k < stateCells; k++) { stateProbability.add(currState);}
			
		}
		return stateProbability;
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
    	
	    	File folder = new File("data/");
	    	File[] listOfFiles = folder.listFiles();
	
	    	for (int i = 0; i < listOfFiles.length; i++) {
	    	      if (listOfFiles[i].isFile()) {
	    	    	  
	    	    	  	String fileString = listOfFiles[i].getName();
	    	    	  	String simulationName = fileString.replace(".xml","");
	    	    	  	rulesMap.put(simulationName, rules[i]);
	    	    	  	
	    	      }
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
    
    // get Author
    public String getAuthor() {
    		return simAuthor;
    }
    
    // get Title
    public String getTitle() {
    		return simTitle;
    }
    
    // get rules object
    public IRuleSet getRules() {
    		return simRules;
    }
    
    // get extra parameters
    public double[] getExtraParameters() {
    		return simExtraParams;
    }
    
    // get num of different states
    public double getNumStates() {
    		return simExtraParams[0];
    }
    
    
    // get num of rows
    public int getNumRows() {
    		return simGrid.length;
    }
    
    
    // get num of cols
    public int getNumCols() {
    		return simGrid[0].length;
    }

    // method would be implemented if we did pemrose simulation (left intentionally empty)
    // gets mapping of state tag to list of neighbor tags
	public Map<Integer, List<Integer>> getGridLayout() {
		return null;
	}

	public int[] getInitialStates() {
		return simGridArray;
	}
    
    
    // get color scheme string
    public String getColorSchemeString() {
    		return myDataValues.get("colorScheme");
    }
    
    // get grid shape type
	public String getGridShape() {
		return simShape;
	}
	
    // getter for outline setting
    public boolean hasOutline() {
    		String outline = myDataValues.get("hasOutline");
    		return outline.contains("true");
    }
    
    // getter for neighbor setting
    public String getNeighborsConsidered() {
    		String n = myDataValues.get("neighborsConsidered");
    		if (!n.equals("cardinal") && !n.equals("diagonal") && !n.equals("all")) {n = "all";}
    		return n;
    }

    // getter for sliders
    public List<SliderInfo> getSliders() {
		return simSliders;
    }

    // getter for toroidal world setting
    public boolean isToroidal() {
    		String outline = myDataValues.get("toroidal");
    		return outline.contains("true");
    }
    
    // get original XML value
    public String getXMLTag(String tag) {
    		return myDataValues.get(tag);
    }
    
    public IGrid getGridObject() {
    		
    		return gridObject;
    }
    
    public void setupGridObject() {
    		try {
				Class gridClass = Class.forName("grids." + simShape);
				Constructor[] gridConstr = gridClass.getConstructors();
				Object gridObjectInstance = gridConstr[0].newInstance(this);
				gridObject = (IGrid) gridObjectInstance;
			} catch (Exception e) {
				gridObject = new RectangularGrid(this);
			}
    }
    
    
}


