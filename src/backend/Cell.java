package backend;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell {
	
	private int[] myState;
	private Rectangle myView;
	private Map<Integer, Color> myColorMap;
	private int myTag;
	
	/**
	 * Creates a new Cell with the specified parameters. A cell can have multiple states, but it must at least have one (used for coloring the cell) at all times. 
	 * @param initialState the initial state to set the cell to at the beginning of the simulation.
	 * @param size the side length of the cell's image in pixels.
	 * @param colorMap the map of states to colors used to display the cell as having a particular state.
	 * @param location the location of the cell inside the grid, given by {row, column}.
	 */
	public Cell(int initialState, int numStates, Map<Integer, Color> colorMap, int tag, double size) {
		myState = new int[numStates];
		myState[0] = initialState;
		myColorMap = colorMap;
		myView = new Rectangle(size, size, myColorMap.get(initialState));
		myTag = tag;
		myView.setStroke(Color.BLACK);
		myView.setStrokeWidth(2);
	}
	
	/**
	 * Changes the primary state of the cell and updates its display color.
	 * @param newState the state to which the cell will be changed.
	 */
	public void changeState(int value) {
		changeState(0, value);
	}
	
	/**
	 * Changes the specified state of this cell.
	 * @param newState the state of this cell to update.
	 * @param value the value to update the specified state to.
	 */
	public void changeState(int state, int value) {
		myState[state] = value;
		if(state == 0)
			myView.setFill(myColorMap.get(value));
	}
	
	public void changeState(int[] newStates) {
		if(newStates.length == myState.length) {
			myState = newStates;
			myView.setFill(myColorMap.get(newStates[0]));
		} else throw new IllegalArgumentException("Cell must have "+ myState.length +" state(s).");
	}
	
	/**
	 * Returns the current state of the cell.
	 * @return the integer state of the cell.
	 */
	public int getPrimaryState() {
		return myState[0];
	}
	
	public int getState(int state) {
		if(myState.length>state) {
			return myState[state];
		}
		throw new IllegalStateException("The state " + state + "does not exist in this cell.");
	}
	
	/**
	 * Returns the index, or tag, of this cell in its grid. This acts as a location marker for the cell in the grid.
	 * @return the integer tag of this cell in the context of its grid..
	 */
	public int getTag() {
		return myTag;
	}
	
	/**
	 * Returns the Rectangle this Cell uses to display.
	 * @return the Rectangle object.
	 */
	public Rectangle getView() {
		// TODO does this have to be public?
		return myView;
	}

}
