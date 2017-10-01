package grids;

import xml_start.SimulationParameters;

public class CardinalGrid extends RectangularGrid {

	
	
	public CardinalGrid(SimulationParameters simdata) {
		super(simdata);
		NEIGHBOR_SET = new int[][]{ {-1, 0}, {0, -1}, {0, 1}, {1, 0} };
	}
}
