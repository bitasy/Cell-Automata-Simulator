package grids;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import backend.Cell;
import backend.EffectGrid;
import backend.IGrid;
import backend.IRuleSet;
import frontend.CellSociety;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import xml_start.SimulationParameters;

/**
 * The Grid class manages a grid of Cells to be used in a simulation. Its functions include updating the state and color of each Cell in the grid based on the rules provided by a SimulationLoader object.
 * 
 * @author Brian Nieves
 */
public class RectangularGrid implements IGrid {

	private static final int DEFAULT_STATE = 0;
	private static final int DEFAULT_SECONDARY_STATE = -1;
	
	protected int[][] NEIGHBOR_SET = new int[][]{ {-1, -1}, {-1, 0}, 
			   {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1} };
	
	private Map<Integer, Color> colorMap;
	private int numStates;
	private IRuleSet myRuleSet;
	private double myCellSize;
	private boolean toroidal;
	private Pane myCanvas; 	//Pane is a base class, but still all that is necessary

	protected Cell[][] myGrid;

	/**
	 * Creates a new Grid that holds all the Cells to be used in a simulation.
	 * @param simdata the SimulationLoader object that holds information about the simulation parameters.
	 * @param cellSize the side length of each cell in the grid, in pixels.
	 */
	public RectangularGrid(SimulationParameters simdata, double cellSize) {
		myGrid = new Cell[simdata.getNumRows()][simdata.getNumCols()];
		colorMap = simdata.getColorScheme();
		numStates = (int) simdata.getExtraParameters()[0];
		//toroidal = simdata.isToroidal();
		myCellSize = cellSize;
		populate(simdata.getGrid());
		myRuleSet = simdata.getRules();
		myRuleSet.setParams(Arrays.copyOfRange(simdata.getExtraParameters(), 1, numStates+1));
	}
	
	private void populate(int[][] initialState) {
		for(int i = 0; i < initialState.length; i++) {
			for(int j = 0; j < initialState[0].length; j++) {
				myGrid[i][j] = new Cell(initialState[i][j], numStates, colorMap, i*myGrid[0].length+j, myCellSize);
				for(int k = 1; k < numStates; k++) myGrid[i][j].changeState(k, DEFAULT_SECONDARY_STATE);
			}
		}
	}
	
	public void update() {
		EffectGrid newStates = new RectangularEffectGrid();
		myRuleSet.setNewGrid(newStates);
		applyRules();
		
		for(int i = 0; i < myGrid.length; i++) {
			for(int j = 0; j < myGrid[0].length; j++) {
				myGrid[i][j].changeState(newStates.getStates(i*myGrid[0].length+j));
			}
		}
		
	}
	
	private void applyRules() {
		for(int rule = 1; rule <= myRuleSet.numRules(); rule++)
			for(int i = 0; i < myGrid.length; i++) 
				for(int j = 0; j < myGrid[0].length; j++) {
					myRuleSet.applyRule(rule,myGrid[i][j], getNeighborhood(myGrid[i][j]));
				}
	}
	
	/**
	 * Returns a 3x3 array that contains references to a cell and its neighbors on all sides. The parameter cell is in the position (1,1). If the cell is on the edge, the neighbors that do not exist are null.
	 * @param cell the cell whose neighbors are being returned.
	 * @return 
	 * @return the array of cells in the parameter cell's neighborhood.
	 */
	protected List<Cell> getNeighborhood(Cell cell) {
		//This method adapted from Simon Forsberg at https://codereview.stackexchange.com/questions/68627/getting-the-neighbors-of-a-point-in-a-2d-grid
		int[] location = getLocation(cell.getTag());
		
		List<Cell> neighbors = new ArrayList<>();
		for (int[] neighbor : NEIGHBOR_SET) {
			if (isOnMap(location[0] + neighbor[0], location[1] + neighbor[1])) {
				neighbors.add(myGrid[location[1] + neighbor[1]][location[0] + neighbor[0]]);
			} else if (toroidal) {
				neighbors.add(myGrid[(location[1] + neighbor[1]+myGrid.length)%myGrid.length]
						[(location[0] + neighbor[0]+myGrid[0].length)%myGrid[0].length]);
			}
		}
		
		return neighbors;
	}
	
	private boolean isOnMap(int i, int j) {
		return (0<=i&&i<myGrid[0].length)&&(0<=j&&j<myGrid.length);
	}

	protected int[] getLocation(int index) {
		return new int[] {index/myGrid[0].length, index%myGrid[0].length};
	}
	
	class RectangularEffectGrid implements EffectGrid {
		
		int[][][] newStates;
		
		RectangularEffectGrid(){
			newStates = new int[myGrid.length][myGrid[0].length][numStates];
			for(int i = 0; i < newStates.length; i++) 
				for(int j = 0; j < newStates[0].length; j++)
					for(int k = 0; k < numStates; k++) 
						newStates[i][j][k] = k == 0 ? DEFAULT_STATE : DEFAULT_SECONDARY_STATE;
		}
		
		@Override
		public void setState(int index, int[] states) {
			int row = index/newStates[0].length;
			int col = index%newStates[0].length;
			newStates[row][col] = states;
		}

		@Override
		public int[] getStates(int index) {
			int[] loc = RectangularGrid.this.getLocation(index);
			return newStates[loc[0]][loc[1]];
		}
		
		public int totalCells() {
			return newStates.length*newStates[0].length;
		}
	}

	@Override
	public void drawTo(Pane pane) {
		myCanvas = pane;
		draw();
	}
	
	protected void draw() {
		myCanvas.getChildren().removeAll(myCanvas.getChildren());
		
		int rows = myGrid.length;
		int cols = myGrid[0].length;
		double totalWidthPercent = myCellSize * cols / CellSociety.WIDTH;
		double firstX = (.5 - totalWidthPercent / 2) * CellSociety.WIDTH;
		double totalHeightPercent = myCellSize * rows / CellSociety.HEIGHT;
		double firstY = (.5 - totalHeightPercent / 2) * CellSociety.HEIGHT;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				Rectangle cell = myGrid[i][j].getView();
				cell.setOnMouseClicked(e -> System.out.println("Cell clicked!"));
				cell.setX(firstX + j * myCellSize);
				cell.setY(firstY + i * myCellSize);
				myCanvas.getChildren().add(cell);
			}
		}
	}
	
	
}

