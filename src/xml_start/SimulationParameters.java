package xml_start;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    // simple way to create an immutable list
    public static final List<String> DATA_FIELDS = Arrays.asList(new String[] {
        "artist",
        "title",
        "year",
        "publisher"
    });
	
	
 // specific data values for this instance
    private Map<String, String> myDataValues;


    public SimulationParameters (Map<String, String> dataValues) {
        myDataValues = dataValues;
    }
    
    public Map<String, String> getMap() {
    		return myDataValues;
    }
    
    
    
    
    
    
    
    
    
}
