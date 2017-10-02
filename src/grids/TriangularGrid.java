package grids;

import frontend.CellSociety;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import xml_start.SimulationParameters;

public class TriangularGrid extends RectangularGrid {

	private static final double COS_THIRTY = Math.cos(Math.PI / 6);

	{
		NEIGHBOR_SET = new int[][] { { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 } };
	}

	public TriangularGrid(SimulationParameters simdata) {
		super(simdata);
	}

	protected void draw() {
		myCanvas.getChildren().removeAll(myCanvas.getChildren());
		int rows = myGrid.length;
		int cols = myGrid[0].length;
		// Calculations are set by taking the totalWidth/totalHeight, equating them
		// the respective widths/heights, solving for the side length, and taking the
		// minimum value as this ensures that all triangles will be shown on screen.
		// tl;dr: math
		double sideLength = Math.min(CellSociety.WIDTH / cols, SIM_HEIGHT / (rows * COS_THIRTY * 1.2));
		double triangularHeight = sideLength * COS_THIRTY * 1.15;
		double totalWidth = cols / 2 * sideLength + sideLength;
		double totalHeight = rows * triangularHeight;
		double totalWidthPercent = totalWidth / CellSociety.WIDTH;
		double firstX = (.5 - totalWidthPercent / 2) * CellSociety.WIDTH;
		double totalHeightPercent = totalHeight / SIM_HEIGHT;
		double firstY = (.5 - totalHeightPercent / 2) * SIM_HEIGHT;
		for (double i = 0; i < rows; i++) {
			for (double j = 0; j < cols; j++) {
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
				cell.setOnMouseClicked(e -> System.out.println("Cell clicked!"));
				cell.setFill(Color.WHITE);
				cell.setStroke(Color.BLACK);
				cell.setStrokeWidth(2);
				myCanvas.getChildren().add(cell);
			}
		}
	}
}
