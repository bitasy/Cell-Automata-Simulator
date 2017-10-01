package grids;

import xml_start.SimulationParameters;

public class CardinalGrid extends RectangularGrid {

	
	
	public CardinalGrid(SimulationParameters simdata, double cellSize) {
		super(simdata, cellSize);
		NEIGHBOR_SET = new int[][]{ {-1, 0}, {0, -1}, {0, 1}, {1, 0} };
	}
}
