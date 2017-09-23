package rulesets;

import backend.Cell;
import backend.RuleSet;

public class GameOfLifeRuleSet implements RuleSet {
	
	private Cell cell;
	private Cell[] neighbors;
	private Cell[][] effects;
	private int defaultState;
	

	@Override
	public int numRules() {
		return 3;
	}

	@Override
	public void applyRule(int ruleNum, Cell cell, Cell[] neighbors) {
		if(effects == null) throw new IllegalArgumentException("The grid is null - please call setGrid first.");
		this.cell = cell;
		this.neighbors = neighbors;
		
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
			effects[location[0]][location[1]].changeState(0);
			
		}			
	}

	private void rule2() {
		if(cell.getState() == 1 && liveNeighborCount() > 3) {
			cell.changeState(0);
		}
	}

	private void rule3() {
		if(cell.getState() == 0 && liveNeighborCount() == 3) {
			cell.changeState(1);
		}
	}
	
	private int liveNeighborCount() {
		int c = 0;
		for(Cell n : neighbors)
			if(n != null && n.getState() == 1)
				c++;
		return c;
	}
	
	@Override
	public void setGrid(Cell[][] effects) {
		this.effects = effects;
	}
}
