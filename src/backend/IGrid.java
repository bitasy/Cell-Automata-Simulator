package backend;

import java.util.Map;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public interface IGrid {

	/**
	 * Updates the current grid by creating a new one, filling it in, and making it the new grid. This method applies the specified rules on each cell, going from left to right then top to bottom of the grid. The results of each rule are noted in the new grid that is being filled in.
	 */
	void update();
	
	/**
	 * Adds all elements of this Grid to the Pane currently being viewed.
	 * @param the Pane to which all elements will be added.
	 */
	void drawTo(Pane pane);
	
	public int[] getPrimaryStates();
	
	public Map<Color, Integer> getStateCount();
}