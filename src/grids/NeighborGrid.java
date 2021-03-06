/**
 * This abstract class defines some of the methods used by any grids that do not use a 2D array to describe its states. Any subclass must define how its cells are drawn, which allows for incredible generality of possible grids. Neighbors are defined explicity, which must be provided to the Grid.
 * @author Brian Nieves
 */
package grids;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import backend.Cell;
import backend.EffectGrid;
import backend.IGrid;
import backend.IRuleSet;
import javafx.scene.paint.Color;
import xml_start.SimulationParameters;

public abstract class NeighborGrid implements IGrid {
	
	//Indexes every Cell in the grid, which is the always the key element in a map. The value Cells are neighbors.
	private Map<Cell, List<Cell>> myGrid;
	private IRuleSet myRuleSet;
	private Map<Integer, Color> myColorMap;
	private int numStates;

	public NeighborGrid(SimulationParameters simdata) {
		myGrid = new TreeMap<Cell, List<Cell>>(new Comparator<Cell>() {
			@Override
			public int compare(Cell one, Cell two) {
				return one.getTag()-two.getTag();
			}
		}) ;
		populate(simdata.getGridLayout(), simdata.getInitialStates());
		myColorMap = simdata.getColorScheme();
		numStates = (int) simdata.getExtraParameters()[0];
		myRuleSet = simdata.getRules();
		myRuleSet.setParams(Arrays.copyOfRange(simdata.getExtraParameters(), 1, numStates+1));	
	}
	
	private void populate(Map<Integer, List<Integer>> gridLayout, int[] initialStates) {
		for(int i : gridLayout.keySet()) {
			myGrid.put(new Cell(initialStates[i], numStates, myColorMap, i), new ArrayList<Cell>());
		}
		for(int i : gridLayout.keySet()) {
			for(int j : gridLayout.get(i)) {
				myGrid.get(getCellbyTag(i)).add(getCellbyTag(j));
			}
		}
	}
	
	private Cell getCellbyTag(int tag) {
		for(Cell c : myGrid.keySet()) {
			if(c.getTag() == tag) return c;
		}
		
		return null;
	}

	@Override
	public void update() {
		EffectGrid newStates = new NeighborEffectGrid();
		myRuleSet.setNewGrid(newStates);
		applyRules();
		
		for(Cell i : myGrid.keySet()) {
			List<Cell> neighbors = myGrid.remove(i);
			i.changeState(newStates.getStates(i.getTag()));
			myGrid.put(i, neighbors);
		}
	}
	
	private void applyRules() {
		for(int rule = 1; rule <= myRuleSet.numRules(); rule++)
			for(Cell c : myGrid.keySet()) 
					myRuleSet.applyRule(rule, c, myGrid.get(c));
	}
	
	class NeighborEffectGrid implements EffectGrid{
		/**
		 * This class represents how new states are held for a NeighborGrid.
		 * @author Brian Nieves
		 */
		private Map<Integer, int[]> newStates = new HashMap<>();
		
		@Override
		public void setState (int index, int[] state) {
			newStates.put(index, state);
		}
		
		@Override
		public int[] getStates(int i) {
			return newStates.get(i);
		}
		
		@Override
		public int getPrimaryState(int i) {
			return newStates.get(i)[0];
		}

		@Override
		public int totalCells() {
			return myGrid.size();
		}
	}

}
