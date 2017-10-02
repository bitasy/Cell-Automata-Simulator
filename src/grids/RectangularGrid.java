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
import javafx.scene.shape.Shape;
import xml_start.SimulationParameters;

/**
 * The RectangularGrid class manages a grid of Cells to be used in a simulation. Its functions include updating the state and color of each Cell in the grid based on the rules provided by a SimulationLoader object. Every Grid with this type is represented by a 2D array of Cells.
 * 
 * @author Brian Nieves
 */
public class RectangularGrid implements IGrid {

	private static final int DEFAULT_STATE = 0;
	private static final int DEFAULT_SECONDARY_STATE = -1;
	private static final double GRID_LINE_WIDTH = 2;
	
	protected int[][] NEIGHBOR_SET = new int[][]{ {-1, -1}, {-1, 0},
		   {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1} };
	protected static final double HEIGHT = .7 * CellSociety.HEIGHT;
	
	private Map<Integer, Color> colorMap;
	private int numStates;
	protected boolean toroidal;
	private IRuleSet myRuleSet;
	protected Pane myCanvas;
	private double strokeWidth;

	protected Cell[][] myGrid;

	/**
	 * Creates a new Grid that holds all the Cells to be used in a simulation.
	 * @param simdata the SimulationLoader object that holds information about the simulation parameters.
	 * @param cellSize the side length of each cell in the grid, in pixels.
	 */
	public RectangularGrid(SimulationParameters simdata) {

		myGrid = new Cell[simdata.getNumRows()][simdata.getNumCols()];
		colorMap = simdata.getColorScheme();
		numStates = (int) simdata.getExtraParameters()[0];
		toroidal = simdata.isToroidal();
		strokeWidth = simdata.hasOutline() ? GRID_LINE_WIDTH : 0;
		populate(simdata.getInitialStates());
		myRuleSet = simdata.getRules();
		myRuleSet.setParams(Arrays.copyOfRange(simdata.getExtraParameters(), 1, simdata.getExtraParameters().length));
	}
	
	private void populate(int[] initialState) {
		for(int i = 0; i < initialState.length; i++) {
			int[] location = getLocation(i);
			myGrid[location[0]][location[1]] = new Cell(initialState[i], numStates, colorMap, i);
			for(int k = 1; k < numStates; k++) myGrid[location[0]][location[1]].changeState(k, DEFAULT_SECONDARY_STATE);
			
		}
	}
	
	@Override
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
	 * Returns a list that contains references to a cell's neighbors on all sides. If the cell is on the edge and toroidal is false, the neighbors that do not exist are null.
	 * @param cell the cell whose neighbors are being returned.
	 * @return the array of cells in the parameter cell's neighborhood.
	 */
	protected List<Cell> getNeighborhood(Cell cell) {
		//This method adapted from Simon Forsberg at https://codereview.stackexchange.com/questions/68627/getting-the-neighbors-of-a-point-in-a-2d-grid

		int[] location = getLocation(cell.getTag());

		List<Cell> neighbors = new ArrayList<>();
		for (int[] neighbor : NEIGHBOR_SET) {
			if (isOnMap(location[0] + neighbor[0], location[1] + neighbor[1])) {
				neighbors.add(myGrid[location[0] + neighbor[0]][location[1] + neighbor[1]]);
			} else if (toroidal) {
				neighbors.add(myGrid[(location[0] + neighbor[0]+myGrid.length)%myGrid.length]
						[(location[1] + neighbor[1]+myGrid[0].length)%myGrid[0].length]);
			}
		}
		return neighbors;

	}

	protected boolean isOnMap(int i, int j) {
		return (0<=i&&i<myGrid.length)&&(0<=j&&j<myGrid[0].length);
	}

	protected int[] getLocation(int index) {
		return new int[] {index/myGrid[0].length, index%myGrid[0].length};
	}
	
	@Override
	public int[] getPrimaryStates() {
		int[] states = new int[myGrid.length*myGrid[0].length];
		int c = 0;
		for(Cell[] row : myGrid) {
			for(Cell cell : row) {
				states[c] = cell.getPrimaryState();
				c++;
			}
		}
		return states;
	}
	
	@Override
	public int[] getCellCounts() {
		int[] states = new int[colorMap.size()];
		for(int i = 0; i < states.length; i++) {
			states[i] = getCellCount(i);
		}
		return states;
	}
	
	private int getCellCount(int state) {
		int counter = 0;
		for(Cell[] row : myGrid) {
			for(Cell cell : row) {
				if(cell.getPrimaryState() == state) counter++;
			}
		}
		return counter;
	}
	
	class RectangularEffectGrid implements EffectGrid {
		/**
		 * This class represents how new states are held for a RectangularGrid.
		 * @author Brian Nieves
		 */
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
		
		@Override
		public int getPrimaryState(int index) {
			return getStates(index)[0];
		}
		
		@Override
		public int totalCells() {
			return newStates.length*newStates[0].length;
		}
	}

	@Override
	public void drawTo(Pane pane) {
		myCanvas = pane;
		draw();
	}
	
	/**
	 * Draws the grid by assigning each cell a Shape and adding that to the Pane specified by drawTo.
	 */
	protected void draw() {
		myCanvas.getChildren().removeAll(myCanvas.getChildren());
		int rows = myGrid.length;
		int cols = myGrid[0].length;
		double cellSize = Math.min(CellSociety.WIDTH / cols, HEIGHT / rows);
		double totalWidthPercent = cellSize * cols / CellSociety.WIDTH;
		double firstX = (.5 - totalWidthPercent / 2) * CellSociety.WIDTH;
		double totalHeightPercent = cellSize * rows / HEIGHT;
		double firstY = (.5 - totalHeightPercent / 2) * HEIGHT;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				Cell c = myGrid[i][j];
				Rectangle body = new Rectangle(cellSize, cellSize, colorMap.get(myGrid[i][j].getPrimaryState()));
				body.setX(firstX + j * cellSize);
				body.setY(firstY + i * cellSize);
				prepare(body, c);
				myCanvas.getChildren().add(body);
				c.setShape(body);
			}
		}
	}
	
	protected void prepare(Shape shape, Cell c) {
		shape.setOnMouseClicked(e -> c.changeState((c.getPrimaryState()+1)%colorMap.size()));
		shape.setStroke(Color.BLACK);
		shape.setStrokeWidth(strokeWidth);
	}
}

