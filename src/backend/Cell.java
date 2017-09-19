package backend;

import javafx.scene.shape.Rectangle;

public class Cell {
	
	private int state;
	private Rectangle view;
	private Grid myGrid;
	
	public Cell(int initialState, int width, int height, Grid grid) {
		state = initialState;
		myGrid = grid;
		view = new Rectangle(width, height, myGrid.getColor(initialState));
	}
	
	public void changeState(int newState) {
		state = newState;
		view.setFill(myGrid.getColor(newState));
	}
	
	public int getState() {
		return state;
	}

}
