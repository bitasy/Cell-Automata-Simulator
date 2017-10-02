package rulesets;

import java.util.ArrayList;
import java.util.Random;

import backend.Cell;
import frontend.SliderInfo;

public class SegregationRuleSet extends StandardRuleSet {
	
	private final static int EMPTY = 0;
	
	private double t;
	private int possibilities;
	private Random r = new Random();
	
	@Override
	public int numRules() {
		return 2;
	}
	
	void rule1() {
		int tag = cell.getTag();
		if(cell.getPrimaryState() != EMPTY && isSatisfied()) {
			effects.setState(tag, new int[] {cell.getPrimaryState()});
		}
	}

	void rule2() {
		if (cell.getPrimaryState() != EMPTY && !isSatisfied()) {
			boolean valid = false;
			used = new ArrayList<>();
			possibilities = effects.totalCells();
			int position = nextPosition();
			while (!valid) {
				int[] states = effects.getStates(position);
				if (states[0] == EMPTY)
					valid = true;
				else
					position = nextPosition();
			}
			int[] state = new int[] {cell.getState(0)};
			effects.setState(position, state);

		}
		
	}
	
	private ArrayList<Integer> used;

	private int nextPosition() {
		//Algorithm adapted from Jon Skeet at https://stackoverflow.com/questions/4040001/creating-random-numbers-with-no-duplicates	
		
		
		int chosen = r.nextInt(possibilities);
        int extra = 0;
        for(int usedNum : used) {
            //if(usedNum >= possibilities) usedNum--;
            if(chosen >= usedNum) extra++;
        }
        for(int i = 0; i < used.size(); i++) {
            if(used.get(i)> chosen ) used.set(i, used.get(i)-1);
        }
        possibilities--;
        used.add(chosen);
        return chosen+extra < effects.totalCells() ? chosen+extra : effects.totalCells()-1;
	}
	
	private boolean isSatisfied() {
		int goodState = cell.getPrimaryState();
		int numSame = 0;
		int total = 0;
		for(Cell c : paramCells) {
			if(c != null && c != cell && c.getPrimaryState() != EMPTY) {
				total++;
				numSame = c.getPrimaryState() == goodState ? numSame + 1 : numSame;
			}

		}
		if(total != 0)
			return t <= (double)numSame/total;
		return true;
	}

	@Override
	public void setParams(double[] params) {
		t = params[0];
	}
	
	public SliderInfo[] getSliders() {
		return new SliderInfo[] {
				new SliderInfo("Tolerance", 0, 1, true, t)
		};
	}
}
