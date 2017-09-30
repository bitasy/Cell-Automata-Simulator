package rulesets;

import backend.Cell;

public class GameOfLifeRuleSet extends StandardRuleSet{
	
	private static final int MIN_NEIGHBORS = 2;
	private static final int MAX_NEIGHBORS = 3;
	private static final int BIRTH_NEIGHBORS = 3;
	private final static int ON = 1;
	private final static int OFF = 0;
	private static final int[] ZERO = new int[] {0};
	private static final int[] ONE = new int[] {1};
	

	@Override
	public int numRules() {
		return 2;
	}
	
	void rule1() {
		int tag = cell.getTag();
		if(cell.getPrimaryState() == ON && liveNeighborCount() < MIN_NEIGHBORS || liveNeighborCount() > MAX_NEIGHBORS) {
			effects.setState(tag, ZERO);
			
		} else {
			if(cell.getPrimaryState() != OFF)
				effects.setState(tag, ONE);
		}
	}

	void rule2() {
		if(cell.getPrimaryState() == OFF && liveNeighborCount() == BIRTH_NEIGHBORS) {
			int tag = cell.getTag();
			effects.setState(tag, ONE);
		}
	}
	
	private int liveNeighborCount() {
		int c = 0;
		
		for(Cell cell : paramCells ) {
			if(cell.getPrimaryState() != 0) c++;
		}
		
		return c;
	}

	@Override
	public void setParams(double[] params) {
		//This simulation requires no parameters.
	}
}
