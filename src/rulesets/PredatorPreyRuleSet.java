package rulesets;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import backend.Cell;

public class PredatorPreyRuleSet extends StandardRuleSet {

	private Cell[] neighborhood;
	private int sharkStarveTime;
	private int sharkBreedTime;
	private int fishBreedTime;

	@Override
	public int numRules() {
		return 5;
	}
	
	/**
	 * Sharks breed.
	 */
	void rule1() {
		if(cell != null && cell.getPrimaryState() == 2) {
			if(cell.getState(1) == -1) cell.changeState(1, sharkBreedTime);
			int[] location = cell.getLocation();
			if(cell.getState(1) <= 0) {
				refill();
				for (int i = 0; i < choices.length; i++) {
					Cell c = neighborhood[choices[i]];
					if(c.getPrimaryState() == 0) {
						int[] waterLocation = c.getLocation();
						if (effects[waterLocation[0]][waterLocation[1]][0] == 0) {
							setEffects(waterLocation[0], waterLocation[1], 2, sharkBreedTime, sharkStarveTime);
							effects[location[0]][location[1]][1] = sharkBreedTime;
							break;
						}
					}
				}
			} else if(cell.getState(1) > 0) {
				cell.changeState(1, cell.getState(1) - 1);
			}
		}
	}

	/**
	 * Sharks eat fish.
	 */
	void rule2() {
		if (cell != null && cell.getPrimaryState() == 2) {
			int[] location = cell.getLocation();
			setEffects(location[0], location[1], 2, cell.getState(1), cell.getState(2));	
			refill();		
			for (int i = 0; i < choices.length; i++) {
				Cell c = neighborhood[choices[i]];
				if(c.getPrimaryState() == 1) {
					int[] fishLocation = c.getLocation();
					if (effects[fishLocation[0]][fishLocation[1]][0] != 2) {
						setEffects(fishLocation[0], fishLocation[1], 2, cell.getState(1) - 1, sharkStarveTime);
						setEffects(location[0], location[1], 0);
						break;
					}
				}
			}
		}
	}

	/**
	 * Fish breed.
	 */
	void rule3() {
		if(cell != null && cell.getPrimaryState() == 1) {
			if(cell.getState(1) == -1) cell.changeState(1, fishBreedTime);
			int[] location = cell.getLocation();
			if(cell.getState(1) == 0) {
				boolean bred = false;
				refill();
				for (int i = 0; i < choices.length; i++) {
					Cell c = neighborhood[choices[i]];
					if(c.getPrimaryState() == 0) {
						int[] waterLocation = c.getLocation();
						if (effects[waterLocation[0]][waterLocation[1]][0] == 0) {
							bred = true;
							effects[waterLocation[0]][waterLocation[1]][0] = 1;
							effects[waterLocation[0]][waterLocation[1]][1] = fishBreedTime;
							effects[location[0]][location[1]][2] = fishBreedTime;
							break;
						}
					}
				}
				if(!bred) effects[location[0]][location[1]][2] = cell.getState(1) + 1;
			}
		}
	}

	/**
	 * Sharks move.
	 */
	void rule4() {
		if (cell != null && cell.getPrimaryState() == 2) {
			if(cell.getState(2) == -1) cell.changeState(2, sharkStarveTime);
			int[] location = cell.getLocation();
			if (cell.getState(2) != 0 && effects[location[0]][location[1]][0] == 2) {
				effects[location[0]][location[1]][1] = cell.getState(1) - 1;
				effects[location[0]][location[1]][2] = cell.getState(2) - 1;
				refill();
				for (int i = 0; i < choices.length; i++) {
					Cell c = neighborhood[choices[i]];
					if (c.getPrimaryState() == 0) {
						int[] neighborLocation = c.getLocation();
						if (effects[neighborLocation[0]][neighborLocation[1]][0] != 2) {
							setEffects(neighborLocation[0], neighborLocation[1], 2, cell.getState(1), cell.getState(2) - 1);
							setEffects(location[0], location[1], 0);
							break;
						}
					}

				}
			} else if(cell.getState(2) == 0) setEffects(location[0], location[1], 0);
		}
	}

	/**
	 * Fish move.
	 */
	void rule5() {
		if (cell != null && cell.getPrimaryState() == 1) {
			int[] location = cell.getLocation();
			if(effects[location[0]][location[1]][0] == 0) {
				setEffects(location[0], location[1], 1, cell.getState(1) - 1, cell.getState(2) - 1);
				refill();
				for (int i = 0; i < choices.length; i++) {
					Cell c = neighborhood[choices[i]];
					if (c.getPrimaryState() == 0) {
						int[] neighborLocation = c.getLocation();
						if (effects[neighborLocation[0]][neighborLocation[1]][0] == 0) {
							setEffects(neighborLocation[0], neighborLocation[1], 1, cell.getState(1)-1, cell.getState(2) - 1);
							setEffects(location[0], location[1], 0);
							break;
						}
					}
				}
			}

		}
	}

	private void setEffects(int row, int col, int... states) {
		for(int i = 0; i < states.length; i++) {
			effects[row][col][i] = states[i];
		}
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

	private Integer[] choices;

	private void refill() {
		choices = new Integer[]{0,1,2,3};
		List<Integer> temp = Arrays.asList(choices);
		Collections.shuffle(temp);
		choices = (Integer[]) temp.toArray();
	}

	@Override
	public void setParams(double[] params) {
		sharkStarveTime = (int)params[0];
		sharkBreedTime = (int)params[1];
		fishBreedTime = (int)params[2];

	}
}
