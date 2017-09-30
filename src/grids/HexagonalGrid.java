package grids;

import frontend.CellSociety;
import javafx.scene.shape.Rectangle;
import xml_start.SimulationParameters;

public class HexagonalGrid extends RectangularGrid {

	{NEIGHBOR_SET = new int[][]{ {-1, 0}, {-1, 1},
		{0, -1}, {0, 1}, {1, -1}, {1, 0} };}
	

	public HexagonalGrid(SimulationParameters simdata, double cellSize) {
		super(simdata, cellSize);
	}
	
	protected void draw() {
		// TODO draw hexagons
	}

}
