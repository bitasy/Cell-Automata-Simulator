package rulesets;

import backend.Cell;
import frontend.SliderInfo;

public class GameOfLifeRuleSet extends StandardRuleSet{
	
	private int minNeighbors = 2;
	private int maxNeighbors = 3;
	private int birthNeighbors = 3;
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
		if(cell.getPrimaryState() == ON && liveNeighborCount() < minNeighbors || liveNeighborCount() > maxNeighbors) {
			effects.setState(tag, ZERO);
			
		} else {
			if(cell.getPrimaryState() != OFF)
				effects.setState(tag, ONE);
		}
	}

	void rule2() {
		if(cell.getPrimaryState() == OFF && liveNeighborCount() == birthNeighbors) {
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
		minNeighbors = (int)params[0];
		maxNeighbors = (int)params[1];
		birthNeighbors = (int)params[2];
	}
	
	public SliderInfo[] getSliders() {
		return new SliderInfo[] {
				new SliderInfo("Minimum Population", 1, 3, false, minNeighbors),
				new SliderInfo("Maximum Population", 2, 6, false, maxNeighbors),
				new SliderInfo("Reproduction Population", 1, 5, false, birthNeighbors)
		};
	}
}
