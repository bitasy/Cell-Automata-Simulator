package backend;

import java.util.HashMap;

import javafx.scene.paint.Color;
import xml_start.SimulationLoader;

/**
 * The Grid class manages a grid of Cells to be used in a simulation. Its functions include updating the state and color of each Cell in the grid based on the rules provided by a SimulationLoader object.
 * 
 * @author Brian Nieves
 */
public class Grid {

	private HashMap<Integer, Color> colorMap;
	private Cell[][] grid;
	double myCellSize;
	
	/**
	 * Creates a new Grid that holds all the Cells to be used in a simulation.
	 * @param simdata the SimulationLoader object that holds information about the simulation parameters.
	 * @param cellSize the side length of each cell in the grid, in pixels.
	 */
	public Grid(SimulationLoader simdata, double cellSize) {
		grid = new Cell[simdata.numRows()][simdata.numCols()];
		myCellSize = cellSize;
		populate(simdata.initialState());
	}
	
	private void populate(int[][] initialState) {
		for(int i = 0; i < initialState.length; i++) {
			for(int j : initialState[i]) {
				grid[j][i] = new Cell(0, myCellSize, colorMap);
			}
		}
	}
	
	/**
	 * Updates the current grid by creating a new one, filling it in, and making it the new grid. This method applies the specified rules on each cell, going from left to right then top to bottom of the grid. The results of each rule are noted in the new grid that is being filled in.
	 */
	public void update() {
		Cell[][] newGrid = new Cell[grid.length][grid[0].length];
		
	}
}

