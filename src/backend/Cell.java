package backend;

import java.util.HashMap;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell {
	
	private int state;
	private Rectangle view;
	private HashMap<Integer, Color> myColorMap;
	private int[] myLocation;
	
	/**
	 * Creates a new Cell with the specified parameters. 
	 * @param initialState the initial state to set the cell to at the beginning of the simulation.
	 * @param size the side length of the cell's image in pixels.
	 * @param colorMap the map of states to colors used to display the cell as having a particular state.
	 * @param location the location of the cell inside the grid, given by {row, column}.
	 */
	public Cell(int initialState, double size, HashMap<Integer, Color> colorMap, int[] location) {
		state = initialState;
		myColorMap = colorMap;
		view = new Rectangle(size, size, myColorMap.get(initialState));
		myLocation = location;
	}
	
	/**
	 * Changes the state of the cell and updates its display color.
	 * @param newState the state to which the cell will be changed.
	 */
	public void changeState(int newState) {
		state = newState;
		view.setFill(myColorMap.get(newState));
	}
	
	/**
	 * Returns the current state of the cell.
	 * @return the integer state of the cell.
	 */
	public int getState() {
		return state;
	}
	
	public int[] getLocation() {
		return myLocation;
	}

}
