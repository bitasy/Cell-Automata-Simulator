package grids;

import frontend.CellSociety;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import xml_start.SimulationParameters;

public class HexagonalGrid extends RectangularGrid {
	
	private static final double COS_THIRTY = Math.cos(Math.PI / 6);
	private static final double SIN_THIRTY = Math.sin(Math.PI / 6);

	{
		NEIGHBOR_SET = new int[][] { { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 } };
	}

	public HexagonalGrid(SimulationParameters simdata) {
		super(simdata);
	}

	protected void draw() {
		myCanvas.getChildren().removeAll(myCanvas.getChildren());
		int rows = myGrid.length;
		int cols = myGrid[0].length;
		// Calculations are set by taking the totalWidth/totalHeight, equating them to
		// the respective widths/heights, solving for the side length, and taking the
		// minimum value as this ensures that all hexagons will be shown on screen.
		// tl;dr: math
		double sideLength = Math.min(
				CellSociety.WIDTH / ((1 + 2 * SIN_THIRTY) * (cols + .5) - (cols * SIN_THIRTY) + SIN_THIRTY),
				SIM_HEIGHT / 1.1 / (rows * 2 * COS_THIRTY + COS_THIRTY));
		double hexagonalWidth = sideLength + 2 * SIN_THIRTY * sideLength;
		double hexagonalHeight = 2 * sideLength * COS_THIRTY;
		double totalWidth = cols * hexagonalWidth + hexagonalWidth / 2 - ((cols - 1) * sideLength * SIN_THIRTY);
		double totalHeight = rows * hexagonalHeight + sideLength * COS_THIRTY;
		double totalWidthPercent = totalWidth / CellSociety.WIDTH;
		double firstX = (.5 - totalWidthPercent / 2) * CellSociety.WIDTH + sideLength * SIN_THIRTY;
		double totalHeightPercent = totalHeight / SIM_HEIGHT;
		double firstY = (.5 - totalHeightPercent / 2) * SIM_HEIGHT + sideLength * COS_THIRTY;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				double x, y;
				if (j % 2 == 0) {
					x = firstX + j * (hexagonalWidth - SIN_THIRTY * sideLength);
					y = firstY + i * hexagonalHeight;
				} else {
					x = firstX + j * (hexagonalWidth - SIN_THIRTY * sideLength);
					y = firstY + i * hexagonalHeight + hexagonalHeight / 2;
				}
				Polygon cell = new Polygon();
				// Adding coordinates to the polygon
				cell.getPoints().addAll(new Double[] { x, y, x - sideLength * SIN_THIRTY, y - sideLength * COS_THIRTY,
						x, y - 2 * sideLength * COS_THIRTY, x + sideLength, y - 2 * sideLength * COS_THIRTY,
						x + sideLength + sideLength * SIN_THIRTY, y - sideLength * COS_THIRTY, x + sideLength, y });
				cell.setOnMouseClicked(e -> System.out.println("Cell clicked!"));
				cell.setFill(Color.WHITE);
				cell.setStroke(Color.BLACK);
				cell.setStrokeWidth(2);
				myCanvas.getChildren().add(cell);
			}
		}
	}
}
