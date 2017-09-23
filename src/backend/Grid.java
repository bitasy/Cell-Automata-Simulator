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
	private int defaultState;
	double myCellSize;
	
	/**
	 * Creates a new Grid that holds all the Cells to be used in a simulation.
	 * @param simdata the SimulationLoader object that holds information about the simulation parameters.
	 * @param cellSize the side length of each cell in the grid, in pixels.
	 */
	public Grid(SimulationLoader simdata, double cellSize) {
		grid = new Cell[simdata.numRows()][simdata.numCols()];
		myCellSize = cellSize;
		int defaultState = simdata.getDefaultState();
		populate(simdata.initialState());
	}
	
	private void populate(int[][] initialState) {
		for(int i = 0; i < initialState.length; i++) {
			for(int j : initialState[i]) {
				grid[i][j] = new Cell(0, myCellSize, colorMap, new int[] {i,j});
			}
		}
	}
	
	/**
	 * Updates the current grid by creating a new one, filling it in, and making it the new grid. This method applies the specified rules on each cell, going from left to right then top to bottom of the grid. The results of each rule are noted in the new grid that is being filled in.
	 */
	public void update() {
		int[][] newStates = new int[grid.length][grid[0].length];
		for(int i = 0; i < newStates.length; i++) {
			for(int j = 0; j < newStates[0].length; j++) {
				newStates[i][j] = defaultState;
			}
		}
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				
			}
		}
		
	}
	
	/**
	 * Returns a 3x3 array that contains references to a cell and its neighbors on all sides. The parameter cell is in the position (1,1). If the cell is on the edge, the neighbors that do not exist are null.
	 * @param cell the cell whose neighbors are being returned.
	 * @return the array of cells in the parameter cell's neighborhood.
	 */
	private Cell[][] getNeighborhood(Cell cell) {
		//This method adapted from Vivek at https://stackoverflow.com/questions/4120609/more-efficient-way-to-check-neighbours-in-a-two-dimensional-array-in-java
		int[] location = cell.getLocation();
		
		int startPosX = (location[1] - 1 < 0) ? location[1] : location[1]-1;
		int startPosY = (location[0] - 1 < 0) ? location[0] : location[0]-1;
		int endPosX =   (location[1] + 1 > grid.length-1) ? location[1] : location[1]+1;
		int endPosY =   (location[0] + 1 > grid[0].length-1) ? location[0] : location[0]+1;
		
		Cell[][] neighborhood = new Cell[3][3];
		
		for (int rowNum=startPosX; rowNum<=endPosX; rowNum++) {
		    for (int colNum=startPosY; colNum<=endPosY; colNum++) {
		        neighborhood[rowNum-location[0]+1][colNum-location[0]+1] = grid[rowNum][colNum];
		    }
		}
		
		return neighborhood;
	}
}

