/**
 *Cell represents a single cell automata in the simulation. It contains information regarding its shape, position, and states.  
 * @author Brian Nieves
 */
package backend;

import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class Cell {
	
	private int[] myState;
	private Shape myView;
	private Map<Integer, Color> myColorMap;
	private int myTag;
	
	/**
	 * Creates a new Cell with the specified parameters. A cell can have multiple states, but it must at least have one (used for coloring the cell) at all times. 
	 * @param initialState the initial state to set the cell to at the beginning of the simulation.
	 * @param numStates the number of different kinds of states this cell can hold.
	 * @param colorMap the map of states to colors used to display the cell as having a particular state.
	 * @param tag the unique identifier used by the grid to keep track of this cell.
	 */
	public Cell(int initialState, int numStates, Map<Integer, Color> colorMap, int tag) {
		myState = new int[numStates];
		myState[0] = initialState;
		myColorMap = colorMap;
		myTag = tag;
	}
	
	/**
	 * Changes the primary state of the cell and updates its display color.
	 * @param value the state to which the cell will be changed.
	 */
	public void changeState(int value) {
		changeState(0, value);
	}
	
	/**
	 * Changes the specified state of this cell. Updates its color if the state is the primary one.
	 * @param state the state of this cell to update.
	 * @param value the value to update the specified state to.
	 */
	public void changeState(int state, int value) {
		myState[state] = value;
		if(state == 0)
			myView.setFill(myColorMap.get(value));
	}
	
	/**
	 * Changes all of the states of this cell. The array of new states must be of the correct size.
	 * @param newStates the integer array containing the cell's new states.
	 */
	public void changeState(int[] newStates) {
		if(newStates.length == myState.length) {
			myState = newStates;
			myView.setFill(myColorMap.get(newStates[0]));
		} else throw new IllegalArgumentException("Cell must have "+ myState.length +" state(s).");
	}
	
	/**
	 * Returns the current primary state of the cell.
	 * @return the integer state of the cell.
	 */
	public int getPrimaryState() {
		return myState[0];
	}
	
	/**
	 * Returns the current specified state of the cell.
	 * @param state which state's value to get.
	 * @return the integer value of the specified state.
	 */
	public int getState(int state) {
		if(myState.length>state) {
			return myState[state];
		}
		throw new IllegalStateException("The state " + state + "does not exist in this cell.");
	}
	
	/**
	 * Returns the index, or tag, of this cell in its grid. This acts as a location marker for the cell in the grid.
	 * @return the integer tag of this cell in the context of its grid.
	 */
	public int getTag() {
		return myTag;
	}
	
	/**
	 * Sets the shape for this cell to display on the grid.
	 * @param shape The shape to be displayed.
	 */
	public void setShape(Shape shape) {
		myView = shape;
		myView.setFill(myColorMap.get(myState[0]));
	}

}
