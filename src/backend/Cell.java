package backend;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell {
	
	private int[] myState;
	private Rectangle myView;
	private Map<Integer, Color> myColorMap;
	private int[] myLocation;
	
	/**
	 * Creates a new Cell with the specified parameters. A cell can have multiple states, but it must at least have one (used for coloring the cell) at all times. 
	 * @param initialState the initial state to set the cell to at the beginning of the simulation.
	 * @param size the side length of the cell's image in pixels.
	 * @param colorMap the map of states to colors used to display the cell as having a particular state.
	 * @param location the location of the cell inside the grid, given by {row, column}.
	 */
	public Cell(int initialState, int numStates, Map<Integer, Color> colorMap, int[] location, double size) {
		myState = new int[numStates];
		myState[0] = initialState;
		myColorMap = colorMap;
		myView = new Rectangle(size, size, myColorMap.get(initialState));
		myLocation = location;
		myView.setStroke(Color.BLACK);
		myView.setStrokeWidth(2);
	}
	
	/**
	 * Changes the state of the cell and updates its display color.
	 * @param newState the state to which the cell will be changed.
	 */
	public void changeState(int[] newState) {
		if(newState.length == 0) throw new IllegalArgumentException("New state must have at least a primary state determined.");
		myState = newState;
		myView.setFill(myColorMap.get(newState[0]));
	}
	
	/**
	 * Changes only the primary state of this cell, and removes all other states.
	 * @param newState the new primary state of this cell, used to calculate cell color.
	 */
	public void changeState(int newState) {
		myState[0] = newState;
		myView.setFill(myColorMap.get(newState));
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
	 * Returns the location of this cell in its grid.
	 * @return the integer array [row][col] of this cell's location.
	 */
	public int[] getLocation() {
		return myLocation;
	}
	
	/**
	 * Returns the Rectangle this Cell uses to display.
	 * @return the Rectangle object.
	 */
	Rectangle getView() {
		return myView;
	}

}
