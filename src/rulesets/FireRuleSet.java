package rulesets;

import backend.Cell;

public class FireRuleSet extends StandardRuleSet{

	private double probCatch;
	
	@Override
	public int numRules() {
		return 3;
	}
	
	void rule1() {
		if(cell != null && cell.getPrimaryState() == 0) {
			int[] location = cell.getLocation();
			for(Cell[] a : neighborhood) {
				for(Cell c : a) {
					if(c != null && c != cell && c.getPrimaryState() == 1) {
						effects[location[0]][location[1]][0] = Math.random() < probCatch ? 1: 0;
						return;
					}
				}
			}

			effects[location[0]][location[1]][0] = 0;
		}
	}
	
	void rule2() {
		if(cell != null && cell.getPrimaryState() == 1) {
			int[] location = cell.getLocation();
			effects[location[0]][location[1]][0] = 2;
		}
	}
	
	void rule3() {
		if(cell != null && cell.getPrimaryState() == 2) {
			int[] location = cell.getLocation();
			effects[location[0]][location[1]][0] = 2;
		}
	}

	@Override
	public void setParams(double[] params) {
		probCatch = params[0];
	}
	
}
