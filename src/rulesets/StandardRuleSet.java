package rulesets;

import java.lang.reflect.Method;
import backend.Cell;
import backend.IRuleSet;

public abstract class StandardRuleSet implements IRuleSet {
	
	protected Cell cell;
	protected Cell[][] neighborhood;
	protected int[][][] effects;
	protected Cell[][] myGrid;
	
	@Override
	public void setGrid(Cell[][] grid) {
		myGrid = grid;
	}
	
	@Override
	public void setNewGrid(int[][][] effects) {
		this.effects = effects;
	}
	
	/**
	 * Returns a 3x3 array that contains references to a cell and its neighbors on all sides. The parameter cell is in the position (1,1). If the cell is on the edge, the neighbors that do not exist are null.
	 * @param cell the cell whose neighbors are being returned.
	 * @return the array of cells in the parameter cell's neighborhood.
	 */
	protected void setNeighborhood(Cell cell) {
		//This method adapted from Vivek at https://stackoverflow.com/questions/4120609/more-efficient-way-to-check-neighbours-in-a-two-dimensional-array-in-java
		int[] location = cell.getLocation();
		
		int startPosX = (location[1] - 1 < 0) ? location[1] : location[1]-1;
		int startPosY = (location[0] - 1 < 0) ? location[0] : location[0]-1;
		int endPosX =   (location[1] + 1 > myGrid[0].length-1) ? location[1] : location[1]+1;
		int endPosY =   (location[0] + 1 > myGrid.length-1) ? location[0] : location[0]+1;
		
		Cell[][] neighborgrid = new Cell[3][3];
		
		for (int rowNum=startPosY; rowNum<=endPosY; rowNum++) {
		    for (int colNum=startPosX; colNum<=endPosX; colNum++) {
		        neighborgrid[rowNum-location[0]+1][colNum-location[1]+1] = myGrid[rowNum][colNum];
		    }
		}
		
		neighborhood = neighborgrid;
	}
	
	@Override
	public abstract int numRules();

	@Override
	public final void applyRule(int ruleNum, Cell cell) {
		this.cell = cell;
		setNeighborhood(cell);
		try {
			Method m = this.getClass().getDeclaredMethod("rule"+ruleNum, new Class[] {});
			m.invoke(this, new Object[] {});
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Could not find method rule"+ruleNum+". Please make sure it exists in "+this.getClass().getName()+" and is package visible.");
		}
	}

	@Override
	public abstract void setParams(double[] params);

}
