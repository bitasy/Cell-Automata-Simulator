package rulesets;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import backend.Cell;

public class PredatorPreyRuleSet extends StandardRuleSet {

	private static final int WATER = 0;
	private static final int FISH = 1;
	private static final int SHARK = 2;
	private static final int[] WATER_STATE = new int[] {0,0,0};
	
	private static final int BREED_STATE = 1;
	private static final int STARVE_STATE = 2;

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
		if (cell != null && cell.getPrimaryState() == SHARK) {
			if(cell.getState(BREED_STATE) == -1) cell.changeState(BREED_STATE, sharkBreedTime);
			if(cell.getState(BREED_STATE) <= 0) {
				int waterLocation = findState(WATER);
				if(waterLocation >=0) effects.setState(waterLocation, new int[] {SHARK, sharkBreedTime, sharkStarveTime});
				//cell.changeState(BREED_STATE, sharkBreedTime);
			}
			cell.changeState(BREED_STATE, cell.getState(BREED_STATE)-1);
		}
	}

	/**
	 * Sharks eat fish.
	 */
	void rule2() {
		if (cell != null && cell.getPrimaryState() == SHARK) {
			moveTo(FISH);
		}
	}

	/**
	 * Fish breed.
	 */
	void rule3() {
		if(cell != null && cell.getPrimaryState() == FISH) {
			if(cell.getState(BREED_STATE) == -1) cell.changeState(BREED_STATE, fishBreedTime);
			if(cell.getState(BREED_STATE) <= 0) {
				int waterLocation = findState(WATER);
				if(waterLocation >=0) effects.setState(waterLocation, new int[] {FISH, fishBreedTime, 0});
			} 
			cell.changeState(BREED_STATE, cell.getState(BREED_STATE)-1);
		}
	}

	/**
	 * Sharks move.
	 */
	void rule4() {
		if (cell != null && cell.getPrimaryState() == SHARK) {
			int location = cell.getTag();
			
			if(effects.getPrimaryState(location) == SHARK && cell.getState(STARVE_STATE) > 0) {
				cell.changeState(STARVE_STATE, cell.getState(STARVE_STATE)-1);
				moveTo(WATER);
			} else effects.setState(location, WATER_STATE);
		}
	}

	/**
	 * Fish move.
	 */
	void rule5() {
		if (cell != null && cell.getPrimaryState() == FISH) {
			if(effects.getPrimaryState(cell.getTag()) != SHARK)
				moveTo(WATER);
		}
	}
	
	private void moveTo(int state) {
		int location = findState(state);
		if(location >= 0) {
			int starveState = state == FISH ? sharkStarveTime : 0;
			effects.setState(location, new int[] {cell.getPrimaryState(), cell.getState(BREED_STATE), starveState});
			effects.setState(cell.getTag(), WATER_STATE);
			return;
		}
		effects.setState(cell.getTag(), new int[] {cell.getPrimaryState(), cell.getState(BREED_STATE), cell.getState(STARVE_STATE)});
	}
	
	private int findState(int state) {
		refill();
		for (int i = 0; i < choices.length; i++) {
			Cell c = paramCells.get(choices[i]);
			if(c.getPrimaryState() == state) {
				int location = c.getTag();
				if (effects.getPrimaryState(location) == state || state > WATER) {
					System.out.println(choices[i]);
					return location;
				}
			}
		}
		return -1;
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
