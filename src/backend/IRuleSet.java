package backend;

import java.util.List;

import frontend.SliderInfo;

public interface IRuleSet {

	/**
	 * Returns the number of distinct rules this Simulation has. <code>applyRule</code> must be able to take up to this number as a parameter.
	 * @return the number of rules.
	 */
	int numRules();

	/**
	 * Returns the after applying the specified rule to a <code>cell</code>. Due to the guarantee of locality, all that is needed to calculate the result is the cell and its neighbors. However, the effect can take place anywhere in the grid. Conflicts are assured not to happen in writing to the grid.
	 * @param ruleNum which rule to call, in order from 1 to <code>numRules()</code>.
	 * @param cell The cell to which the rule will be applied.
	 * @param paramCells A Collection of cells which the rule must use to calculate either a condition or a result.
	 */
	void applyRule(int ruleNum, Cell cell, List<Cell> paramCells);
	
	/**
	 * Sets the Cell grid to which rules are applied. Must be called before <code>applyRule()</code>.
	 * @param effects
	 */
	void setNewGrid(EffectGrid effectGrid);
	
	/**
	 * Sets the parameters of the simultation. All parameters must be double values, and a simulation is required to understand the parameter list that it is given by its XML.
	 * @param params the list of parameters.
	 */
	void setParams(double[] params);
	
	SliderInfo[] getSliders();
}