package backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import xml_start.SimulationParameters;

public abstract class NeighborGrid implements IGrid {
	
	//Indexes every Cell in the grid, which is the always the first element in a map value. The rest of the Cells are neighbors.
	private Map<Integer, List<Cell>> myGrid;
	private IRuleSet myRuleSet;
	private int maxNeighbors;
	private Map<Integer, Color> myColorMap;
	private int numStates;

	public NeighborGrid(SimulationParameters simdata) {
		myGrid = populate(simdata.getGridLayout());
		myColorMap = simdata.getColorScheme();
		numStates = (int) simdata.getExtraParameters()[0];
		myRuleSet = simdata.getRules();
		myRuleSet.setParams(Arrays.copyOfRange(simdata.getExtraParameters(), 1, numStates+1));	
	}
	
	private Map<Integer, List<Cell>> populate(Object gridLayout) {
		// TODO Auto-generated method stub
		return null;
	}

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
					myRuleSet.applyRule(rule, myGrid.get(c).get(0), myGrid.get(c));
	}
	
	@Override
	public void draw(Parent parent) {

	}
	
	class NeighborEffectGrid implements EffectGrid{
		private Map<Integer, int[]> newStates = new HashMap<>();
		
		@Override
		public void setState (int index, int[] state) {
			newStates.put(index, state);
		}
		
		@Override
		public int[] getStates(int i) {
			return newStates.get(i);
		}
		
		/*@Override
		public int getIndex(Cell cell) {
			Map<Integer, List<Cell>> grid = myGrid;
			for(int i : grid.keySet()) {
				if(grid.get(i).get(0) == cell) return i; //The use of reference equality for objects in this line is intended.
			}
			
			return -1;
		}*/

		
	}

}
