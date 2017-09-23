package xml_start;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import backend.RuleSet;
import javafx.scene.paint.Color;

/**
 * Holds simulation parameters for a particular simulation, loaded from XML file
 * @author team14
 *
 * Some code structure adapted from Music.java by @author Robert C. Duvall
 *
 */
public class SimulationParameters {

    // name in data file that will indicate it represents data for this type of object
    public static final String DATA_TYPE = "SimulationParameters";
    // field names expected to appear in data file holding values for this object
    public static final List<String> DATA_FIELDS = Arrays.asList(new String[] {
        "author",
        "colorScheme",
        "year",
        "publisher"
    });
	
    private Color[] colors = {Color.WHITE, Color.BLACK, Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE};
    
    private Map<Integer, Color> simColorScheme = null;
    private int[][] simGrid;
    private String simAuthor;
    private String simTitle;
    private RuleSet simRules;
    private double[] simExtraParams;
    
 // specific data values for this instance
    private Map<String, String> myDataValues;

    public SimulationParameters (Map<String, String> dataValues) {
        myDataValues = dataValues;
        // call setupAllParameters();
    }
    
    public Map<String, String> getMap() {
    		return myDataValues;
    }
    
    // TODO: make setupAllParameters() method; calls all setup methods;
    
    
    // TODO: setupColorScheme: return map of int to Color object
    
    // TODO: setupGrid
    
    // TODO: setupAuthor
    
    // TODO: setupTitle
    
    // TODO: setupRules
    
    // TODO: setupExtraParams
    
    
    
    
    
    // TODO: get colorScheme
    
    // TODO: get Grid
    
    // TODO: get Author
    
    // TODO: get Title
    
    // TODO: get Rules
    
    // TODO: get ExtraParams
    
    // TODO: get numRows();
    
    // TODO: get numCols();
    
    
    
    
    
    
    
}
