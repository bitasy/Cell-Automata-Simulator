package backend;

public interface RuleSet {

	/**
	 * Returns the number of distinct rules this Simulation has. <code>applyRule</code> must be able to take up to this number as a parameter.
	 * @return the number of rules.
	 */
	int numRules();

	/**
	 * Updates the cell grid <code>effects</code> to reflect the changes after applying the specified rule to a <code>cell</code>. Due to the guarantee of locality, all that is needed to calculate the result is the cell and its neighbors. However, the effect can take place anywhere in the grid. Conflicts are assured not to happen in writing to the grid.
	 * @param ruleNum which rule to call, in order from 1 to <code>numRules()</code>.
	 */
	void applyRule(int ruleNum, Cell cell);
	
	/**
	 * Sets the Cell grid to which rules are applied. Must be called before <code>applyRule()</code>.
	 * @param effects
	 */
	void setNewGrid(int[][][] effects);
	
	/**
	 * Sets the parameters of the simultation. All parameters must be double values, and a simulation is required to understand the parameter list that it is given by its XML.
	 * @param params the list of parameters.
	 */
	void setParams(double[] params);
	
	/**
	 * Passes the RuleSet's Grid to the RuleSet.
	 * @param grid the Grid that has the RuleSet.
	 */
	public void setGrid(Cell[][] grid);

}