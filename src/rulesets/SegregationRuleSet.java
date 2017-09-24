package rulesets;

import java.util.ArrayList;
import java.util.Random;

import backend.Cell;

public class SegregationRuleSet extends StandardRuleSet {
	
	private double t;
	private int width;
	private int height;
	private int possibilities;
	private Random r;
	
	{
		r = new Random();
	}
	
	@Override
	public int numRules() {
		return 2;
	}
	
	private void rule1() {
		int[] location = cell.getLocation();
		if(cell.getPrimaryState() != 0 && isSatisfied()) {
			effects[location[0]][location[1]][0] = cell.getPrimaryState();
		}
	}

	private void rule2() {
		if (cell.getPrimaryState() != 0 && !isSatisfied()) {
			boolean valid = false;
			int row = 0;
			int col = 0;
			possibilities = width*height;
			used = new ArrayList<>();
			while (!valid) {
				int position = nextPosition();
				row = position/width;
				col = position%width;
				if (effects[row][col][0] == 0)
					valid = true;
			}
			effects[row][col][0] = cell.getPrimaryState();

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
        return chosen+extra;
	}
	
	private void rule3() {
		
	}
	
	private boolean isSatisfied() {
		int goodState = cell.getPrimaryState();
		int numSame = 0;
		int total = 0;
		for(Cell[] a : neighborhood) {
			for(Cell c : a) {
				if(c != null && c != cell && c.getPrimaryState() != 0) {
					total++;
					numSame = c.getPrimaryState() == goodState ? numSame + 1 : numSame;
				}
				
			}
		}
		//System.out.println(t + " " + numSame/total);
		if(total != 0)
			return t <= (double)numSame/total;
		return true;
	}

	@Override
	public void setParams(double[] params) {
		t = params[0];
		width = (int) params[1];
		height = (int) params[2];
		possibilities = width*height;
	}

}
