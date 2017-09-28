/*package grids;

import java.util.Arrays;
import java.util.Map;

import backend.Cell;
import backend.IRuleSet;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import xml_start.SimulationParameters;

*//**
 * The Grid class manages a grid of Cells to be used in a simulation. Its functions include updating the state and color of each Cell in the grid based on the rules provided by a SimulationLoader object.
 * 
 * @author Brian Nieves
 *//*
public class RectangularGrid { //implements IGrid {

	private static final int DEFAULT_STATE = 0;
	private static final int DEFAULT_SECONDARY_STATE = -1;
	private Map<Integer, Color> colorMap;
	private Cell[][] grid;
	private int numStates;
	private IRuleSet myRuleSet;
	private double myCellSize;
	
	*//**
	 * Creates a new Grid that holds all the Cells to be used in a simulation.
	 * @param simdata the SimulationLoader object that holds information about the simulation parameters.
	 * @param cellSize the side length of each cell in the grid, in pixels.
	 *//*
	public RectangularGrid(SimulationParameters simdata, double cellSize) {
		grid = new Cell[simdata.getNumRows()][simdata.getNumCols()];
		colorMap = simdata.getColorScheme();
		numStates = (int) simdata.getExtraParameters()[0];
		myCellSize = cellSize;
		populate(simdata.getGrid());
		myRuleSet = simdata.getRules();
		myRuleSet.setGrid(grid);
		myRuleSet.setParams(Arrays.copyOfRange(simdata.getExtraParameters(), 1, numStates+1));
		
	}
	
	private void populate(int[][] initialState) {
		for(int i = 0; i < initialState.length; i++) {
			for(int j = 0; j < initialState[0].length; j++) {
				grid[i][j] = new Cell(initialState[i][j], numStates, colorMap, new int[] {i,j}, myCellSize);
				for(int k = 1; k < numStates; k++) grid[i][j].changeState(k, DEFAULT_SECONDARY_STATE);
			}
		}
	}
	
	 (non-Javadoc)
	 * @see backend.IGrid#update()
	 
	
	public void update() {
		int[][][] newStates = createStateGrid();
		myRuleSet.setNewGrid(newStates);
		applyRules();
		
		for(int i = 0; i < newStates.length; i++) {
			for(int j = 0; j < newStates[0].length; j++) {
				grid[i][j].changeState(newStates[i][j]);
			}
		}
		
	}

	private int[][][] createStateGrid() {
		int[][][] newStates = new int[grid.length][grid[0].length][numStates];
		for(int i = 0; i < newStates.length; i++) 
			for(int j = 0; j < newStates[0].length; j++)
				for(int k = 0; k < numStates; k++) 
					newStates[i][j][k] = k == 0 ? DEFAULT_STATE : DEFAULT_SECONDARY_STATE;
				
		
		return newStates;
	}
	
	private void applyRules() {
		for(int rule = 1; rule <= myRuleSet.numRules(); rule++)
			for(int i = 0; i < grid.length; i++) 
				for(int j = 0; j < grid[0].length; j++) {
					myRuleSet.applyRule(rule,grid[i][j]);
				}
	}
	
	 (non-Javadoc)
	 * @see backend.IGrid#getCellShape(int, int)
	 

	public Shape getCellShape(int row, int col) {
		if(row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) throw new ArrayIndexOutOfBoundsException("Row and Col must be positive and within the grid.");
		return grid[row][col].getView();
	}
}

*/