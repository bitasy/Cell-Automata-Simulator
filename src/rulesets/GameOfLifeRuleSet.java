package rulesets;

import backend.Cell;

public class GameOfLifeRuleSet extends StandardRuleSet{

	@Override
	public int numRules() {
		return 2;
	}
	
	private void rule1() {
		int[] location = cell.getLocation();
		if(cell.getPrimaryState() == 1 && liveNeighborCount() < 2 || liveNeighborCount() > 3) {
			effects[location[0]][location[1]][0] = 0;
			
		} else {
			if(cell.getPrimaryState() != 0)
				effects[location[0]][location[1]][0] = 1;
		}
	}

	private void rule2() {
		if(cell.getPrimaryState() == 0 && liveNeighborCount() == 3) {
			int[] location = cell.getLocation();
			effects[location[0]][location[1]][0] = 1;
		}
	}
	
	private int liveNeighborCount() {
		int c = 0;
		if(neighborhood[0][0] != null && neighborhood[0][0].getPrimaryState() == 1) c++;
		if(neighborhood[0][1] != null && neighborhood[0][1].getPrimaryState() == 1) c++;
		if(neighborhood[0][2] != null && neighborhood[0][2].getPrimaryState() == 1) c++;
		if(neighborhood[1][0] != null && neighborhood[1][0].getPrimaryState() == 1) c++;
		if(neighborhood[1][2] != null && neighborhood[1][2].getPrimaryState() == 1) c++;
		if(neighborhood[2][0] != null && neighborhood[2][0].getPrimaryState() == 1) c++;
		if(neighborhood[2][1] != null && neighborhood[2][1].getPrimaryState() == 1) c++;
		if(neighborhood[2][2] != null && neighborhood[2][2].getPrimaryState() == 1) c++;
		
		return c;
	}

	@Override
	public void setParams(double[] params) {
		//This simulation requires no parameters.
	}
}
