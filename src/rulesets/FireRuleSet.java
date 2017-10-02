package rulesets;

import backend.Cell;
import frontend.SliderInfo;

public class FireRuleSet extends StandardRuleSet{

	private static final int TREE = 0;
	private static final int FIRE = 1;
	private static final int EMPTY = 2;
	
	private static final int[] TREE_STATE = new int[] {0};
	private static final int[] FIRE_STATE = new int[] {1};
	private static final int[] EMPTY_STATE = new int[] {2};
	
	private double probCatch;
	
	@Override
	public int numRules() {
		return 3;
	}
	
	void rule1() {
		if(cell != null && cell.getPrimaryState() == TREE) {
			int location = cell.getTag();
				for(Cell c : paramCells) {
					if(c != null && c != cell && c.getPrimaryState() == FIRE) {
						effects.setState(location, Math.random() < probCatch ? FIRE_STATE : TREE_STATE);
						return;
					}
				}
			

			effects.setState(location, TREE_STATE);
		}
	}
	
	void rule2() {
		if(cell != null && cell.getPrimaryState() == FIRE) {
			int location = cell.getTag();
			effects.setState(location, EMPTY_STATE);
		}
	}
	
	void rule3() {
		if(cell != null && cell.getPrimaryState() == EMPTY) {
			int location = cell.getTag();
			effects.setState(location, EMPTY_STATE);
		}
	}

	@Override
	public void setParams(double[] params) {
		probCatch = params[0];
	}
	
	public SliderInfo[] getSliders() {
		return new SliderInfo[] {
				new SliderInfo("Catch Fire Probability", 0, 1, true)
		};
	}
	
}
