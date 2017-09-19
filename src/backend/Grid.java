package backend;

import java.util.HashMap;

import javafx.scene.paint.Color;
import xml_start.SimulationLoader;

public class Grid {

	private HashMap<Integer, Color> colorMap;
	
	public Grid(SimulationLoader simdata) {
		
	}
	
	Color getColor(int state) {
		return colorMap.get(state);
	}
}

