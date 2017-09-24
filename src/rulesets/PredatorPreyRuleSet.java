 package rulesets;

import java.util.Random;

import backend.Cell;

public class PredatorPreyRuleSet extends StandardRuleSet {

	private Cell[] neighborhood;
	private int width;
	private int height;
	private int sharkStarveTime;
	private int sharkBreedTime;
	private int fishBreedTime;
	private Random r;
	
	{
		r = new Random();
	}
	
	@Override
	public int numRules() {
		return 5;
	}

	private void rule1() {
		if (cell != null && cell.getPrimaryState() == 2) {
			int[] location = cell.getLocation();
			boolean eaten = false;
			for (Cell c : neighborhood) {
				if(c.getPrimaryState() == 1) {
					eaten = true;
					int[] fishLocation = c.getLocation();
					effects[fishLocation[0]][fishLocation[1]][0] = 2;
					effects[fishLocation[0]][fishLocation[1]][2] = sharkStarveTime; 
					effects[location[0]][location[1]][0] = 0;
					break;
				}
			} 
			
			if(!eaten) {
				for (Cell c : neighborhood) {
					if(c.getPrimaryState() == 0) {
						int[] neighborLocation = c.getLocation();
						effects[neighborLocation[0]][neighborLocation[1]][0] = 2;
						effects[neighborLocation[0]][neighborLocation[1]][2] = c.getState(2)-1; 
						effects[location[0]][location[1]][0] = 0;
						break;
					}
					
				} 
			}
			
			
		}
	}

	private void rule2() {
	}

	private void rule3() {
	}
	
	private void rule4() {
	}

	private void rule5() {
	}
	
	/** This simulation uses a single array of Cell neighbors, {top, right, bottom, left}.
	 * @see rulesets.StandardRuleSet#setNeighborhood(backend.Cell)
	 */
	@Override
	protected void setNeighborhood(Cell cell) {
		int[] location = cell.getLocation();
		Cell[] neighbors = new Cell[4];
		
		if(location[0] == 0) neighbors[0] = myGrid[myGrid.length-1][location[1]];
		else neighbors[0] = myGrid[location[0]-1][location[1]];
		
		if(location[1] == myGrid[0].length - 1) neighbors[1] = myGrid[location[0]][0];
		else neighbors[1] = myGrid[location[0]][location[1]+1];
		
		if(location[0] == myGrid.length - 1) neighbors[2] = myGrid[0][location[1]];
		else neighbors[2] = myGrid[location[0]+1][location[1]];
		
		if(location[1] == 0) neighbors[3] = myGrid[location[0]][myGrid[0].length - 1];
		else neighbors[3] = myGrid[location[0]][location[1]-1];
		
		neighborhood = neighbors;
	}

	@Override
	public void setParams(double[] params) {
		sharkStarveTime = (int)params[0];
		sharkBreedTime = (int)params[1];
		fishBreedTime = (int)params[2];

	}
}
