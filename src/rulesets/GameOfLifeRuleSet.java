package rulesets;

import backend.Cell;
import backend.RuleSet;

public class GameOfLifeRuleSet implements RuleSet {
	
	private Cell cell;
	private Cell[][] neighborhood;
	private int[][] effects;
	

	@Override
	public int numRules() {
		return 3;
	}

	@Override
	public void applyRule(int ruleNum, Cell[][] neighborhood) {
		if(effects == null) throw new IllegalArgumentException("The grid is null - please call setGrid first.");
		this.cell = neighborhood[1][1];
		this.neighborhood = neighborhood;
		
		switch(ruleNum) {
		case 1: 
			rule1();
			break;
		case 2: 
			rule2();
			break;
		case 3: 
			rule3();
			break;
		default:
			throw new IllegalArgumentException("The rule number "+ruleNum+" doesn't exist.");
		}
		
	}
	
	private void rule1() {
		if(cell.getState() == 1 && liveNeighborCount() < 2 ) {
			int[] location = cell.getLocation();
			effects[location[0]][location[1]] = 0;
			
		}			
	}

	private void rule2() {
		if(cell.getState() == 1 && liveNeighborCount() > 3) {
			int[] location = cell.getLocation();
			effects[location[0]][location[1]] = 0;
		}
	}

	private void rule3() {
		if(cell.getState() == 0 && liveNeighborCount() == 3) {
			int[] location = cell.getLocation();
			effects[location[0]][location[1]] = 1;
		}
	}
	
	private int liveNeighborCount() {
		int c = 0;
		for(Cell[] a : neighborhood)
			for(Cell n : a)
				if(n != null && n != cell && n.getState() == 1)
					c++;
		return c;
	}
	
	@Override
	public void setGrid(int[][] effects) {
		this.effects = effects;
	}
}
