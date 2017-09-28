package backend;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.Parent;

public abstract class NeighborGrid implements IGrid {
	
	private HashMap<Integer, ArrayList<Cell>> myGrid;
	private IRuleSet myRuleSet;
	private int maxNeighbors;

	@Override
	public void update() {
		EffectGrid newStates = new NeighborEffectGrid();
		myRuleSet.setNewGrid(newStates);
		applyRules();
		
		for(int i : myGrid.keySet()) {
			myGrid.get(i).get(0).changeState(newStates.getStates(i));
		}
	}
	
	private void applyRules() {
		for(int rule = 1; rule <= myRuleSet.numRules(); rule++)
			for(int c : myGrid.keySet()) 
					myRuleSet.applyRule(rule, myGrid.get(c).get(0));
	}

	@Override
	public void draw(Parent parent) {

	}
	
	class NeighborEffectGrid implements EffectGrid {
		HashMap<Integer, int[]> newStates = new HashMap<>();

		@Override
		public int[] getStates(int i) {
			// TODO Auto-generated method stub
			return null;
		}
	}

}
