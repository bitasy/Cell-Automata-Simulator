/**
 * This interface describes the behavior of all Grid classes. A Grid contains a collection of Cells and updates them according to the current RuleSet being used. It also contains the information for how to display the Cells using shapes, and adds them to the display.
 * @author Brian Nieves
 */
package backend;

import javafx.scene.layout.Pane;

public interface IGrid {

	/**
	 *  Updates the current grid by creating an EffectGrid object, filling it in, and using it to update the new grid. This method applies the specified rules on each cell, in the order of their tags. The results of each rule are noted in the new grid that is being filled in.
	 */
	void update();
	
	/**
	 * Adds all elements of this Grid to the Pane currently being viewed.
	 * @param the Pane to which all elements will be added.
	 */
	void drawTo(Pane pane);
	
	/**
	 * Returns an array containing the primary states for every cell in the Grid, with the index being the cell's tag and the value its state.
	 * @return the array of primary states.
	 */
	int[] getPrimaryStates();

	/**
	 * Returns an array containing the number of cells that have each primary state, with the index being which state and the value being the number of cells with that state.
	 * @return the int array of cell counts.
	 */
	int[] getCellCounts();
}