package backend;

public interface RuleSet {

	/**
	 * Returns the number of distinct rules this Simulation has. <code>applyRule</code> must be able to take up to this number as a parameter.
	 * @return the number of rules.
	 */
	int numRules();

	/**
	 * Updates the cell grid <code>effects</code> to reflect the changes after applying the specified rule to the <code>cell</code>. Due to the guarantee of locality, all that is needed to calculate the result is the cell and its neighbors. However, the effect can take place anywhere in the grid. Conflicts are assured not to happen in writing to the grid.
	 * @param ruleNum which rule to call, in order from 1 to <code>numRules()</code>.
	 * @param cell which cell to apply the rule to.
	 * @param neighbors a size 8 array of the neighbors of the cell, given from top-left clockwise. If a cell doesn't exist because it would be off the grid, that index is null.
	 */
	void applyRule(int ruleNum, Cell cell, Cell[] neighbors);
	
	/**
	 * Sets the Cell grid to which rules are applied. Must be called before <code>applyRule()</code>.
	 * @param effects
	 */
	void setGrid(Cell[][] effects);

}