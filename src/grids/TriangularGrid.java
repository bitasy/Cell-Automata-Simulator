/**
 * Describes the Grid in which cells are triangles, and neighbors share a side.
 * @author Brian Nieves
 * @author Simran Singh
 */
package grids;

import java.util.ArrayList;
import java.util.List;

import backend.Cell;
import frontend.CellSociety;
import javafx.scene.shape.Polygon;
import xml_start.SimulationParameters;

public class TriangularGrid extends RectangularGrid {

	private static final double COS_THIRTY = Math.cos(Math.PI / 6);

	public TriangularGrid(SimulationParameters simdata) {
		super(simdata);
		NEIGHBOR_SET = new int[][] { { 0, -1 }, { 0, 1 }};
	}
	
	@Override
	protected List<Cell> getNeighborhood(Cell cell) {
		
		int[] location = getLocation(cell.getTag());

		List<Cell> neighbors = new ArrayList<>();
		for (int[] neighbor : NEIGHBOR_SET) {
			addNeighbor(location, neighbors, neighbor);
		}
		
		boolean up = (location[0]+location[1])%2 == 1;
		if(up) {
			addNeighbor(location, neighbors, new int[] {-1,0});
		} else {
			addNeighbor(location, neighbors, new int[] {1,0});
		}
		return neighbors;

	}

	private void addNeighbor(int[] location, List<Cell> neighbors, int[] neighbor) {
		//This method adapted from Simon Forsberg at https://codereview.stackexchange.com/questions/68627/getting-the-neighbors-of-a-point-in-a-2d-grid
		if (isOnMap(location[0] + neighbor[0], location[1] + neighbor[1])) {
			neighbors.add(myGrid[location[0] + neighbor[0]][location[1] + neighbor[1]]);
		} else if (toroidal) {
			neighbors.add(myGrid[(location[0] + neighbor[0]+myGrid.length)%myGrid.length]
					[(location[1] + neighbor[1]+myGrid[0].length)%myGrid[0].length]);
		}
	}
	
	/**
	 * The author of this method is Simran Singh.
	 */
	@Override
	protected void draw() {
		myCanvas.getChildren().removeAll(myCanvas.getChildren());
		int rows = myGrid.length;
		int cols = myGrid[0].length;
		// Calculations are set by taking the totalWidth/totalHeight, equating them
		// the respective widths/heights, solving for the side length, and taking the
		// minimum value as this ensures that all triangles will be shown on screen.
		// tl;dr: math
		double sideLength = Math.min(CellSociety.WIDTH / cols, HEIGHT / (rows * COS_THIRTY * 1.2));
		double triangularHeight = sideLength * COS_THIRTY * 1.15;
		double totalWidth = cols / 2 * sideLength + sideLength;
		double totalHeight = rows * triangularHeight;
		double totalWidthPercent = totalWidth / CellSociety.WIDTH;
		double firstX = (.5 - totalWidthPercent / 2) * CellSociety.WIDTH;
		double totalHeightPercent = totalHeight / HEIGHT;
		double firstY = (.5 - totalHeightPercent / 2) * HEIGHT;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				Cell c = myGrid[i][j];
				double x, y;
				Double[] positions;
				Polygon cell = new Polygon();
				x = firstX + sideLength / 2 * (j + 1);
				y = firstY + i * triangularHeight;
				if ((i + j) % 2 == 0) {
					positions = new Double[] { x, y, x - sideLength / 2, y + sideLength, x + sideLength / 2,
							y + sideLength };
				} else {
					positions = new Double[] { x, y + sideLength, x - sideLength / 2, y, x + sideLength / 2, y };
				}
				cell.getPoints().addAll(positions);
				prepare(cell, c);
				c.setShape(cell);
				myCanvas.getChildren().add(cell);
			}
		}
	}
}
