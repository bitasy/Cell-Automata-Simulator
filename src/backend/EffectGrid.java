/**
 * This interface describes the behavior of the objects used by RuleSets to describe the states of the next generation. Every Grid class must have an inner EffectGrid class that it instantiates every time <code>update</update> is called, in order to pass it to the current RuleSet. This object will be completely filled and returned to Grid before it affects any actual cells.
 * 
 * @author Brian Nieves
 */
package backend;

public interface EffectGrid {
	
	/**
	 * Sets the states for the Effect Grid to hold at the specified position.
	 * @param index the tag of the cell whose state will be updated.
	 * @param states the array of states to update the cell with.
	 */
	void setState(int index, int[] states);
	
	/**
	 * Gets the new states of the specified cell.
	 * @param i the tag of the cell.
	 * @return the array of new states.
	 */
	int[] getStates(int i);
	
	/**
	 * Gets the primary state of the specified cell.
	 * @param i the tag of the cell.
	 * @return the cell's new primary state.
	 */
	int getPrimaryState(int i);

	/**
	 * Returns the total number of cells this EffectGrid holds the states for.
	 * @return the number of cell states contained in this EffectGrid.
	 */
	int totalCells();
}
