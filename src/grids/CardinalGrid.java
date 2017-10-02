/**
 * Describes the Grid in which neighbors are calcuated as being directly above, to the left of, to the right of, or below a cell.
 * @author Brian Nieves
 */
package grids;

import xml_start.SimulationParameters;

public class CardinalGrid extends RectangularGrid {
	
	public CardinalGrid(SimulationParameters simdata) {
		super(simdata);
		NEIGHBOR_SET = new int[][]{ {-1, 0}, {0, -1}, {0, 1}, {1, 0} };
	}
}
