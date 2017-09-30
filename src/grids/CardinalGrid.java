package grids;

import java.util.ArrayList;
import java.util.List;

import backend.Cell;
import xml_start.SimulationParameters;

public class CardinalGrid extends RectangularGrid {
	
	{NEIGHBOR_SET = new int[][]{ {-1, 0}, {0, -1}, {0, 1}, {1, 0} };}

	public CardinalGrid(SimulationParameters simdata, double cellSize) {
		super(simdata, cellSize);
	}
}
