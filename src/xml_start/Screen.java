package xml_start;

import frontend.CellSimulator;
import frontend.SimulationInterface;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/*
 * Questions:
 * 
 * 1. [<—> SimulationInterface] How is SimulationInterface going to pass me info for Screen to update the cells in the screen? Can you pack all updated grid elements into a Group and pass it into my function updateCellsOnScreen(Group updatedCells) ? Can you do something similar for when initializing the sliders, etc? Consider this is assuming that SimulationInterface is still in charge of setting up the GUI, and Screen is in charge of displaying it.
 * 
 * 
 * 2. [<-> SimulationInterface] How will a user change simulations?
 * 
 * 
 * 
 */

/*
 * Assumptions I'm making:
 * 
 * 1. The sliders and layout of the screen itself will not change position or have new button and slider elements added when simulation types change; only changes will be the information in the grid, as well as the names of the labels (defined by an XML file like he showed in class this past Tues Sep 19 in the Browser class demo)
 * 
 * 
 */

// handles GUI display
public class Screen {

	// Variables
	private BorderPane root = new BorderPane();
	private Group currentCells = new Group();
	private Group layoutElements = new Group();
	private Scene mainScene = null;

	// Constants
	public static final int WIDTH = 700;
	public static final int HEIGHT = 700;
	public static final Paint BACKGROUND = Color.WHITE;
	public static final double MAXSPEED = 2.;
	public static final double MINSPEED = 0.;

	// constructor
	public Screen() {
		mainScene = new Scene(root, WIDTH, HEIGHT, BACKGROUND);
		CellSimulator simulator = new CellSimulator();
		root.setTop(simulator);
		root.setBottom(new SimulationInterface(simulator));
	}

	// constructor with GUI elements
	public Screen(Group allElements) {
		root.getChildren().add(allElements);
		mainScene = new Scene(root, WIDTH, HEIGHT, BACKGROUND);
	}

	// updates cells on screen
	public void updateCellsOnScreen(Group updatedCells) {

		root.getChildren().removeAll(currentCells.getChildren());
		currentCells.getChildren().clear();
		currentCells.getChildren().addAll(updatedCells.getChildren());
		root.getChildren().addAll(currentCells.getChildren());
		return;

	}

	// add new simulation cells with initial conditions to screen
	public void initializeNewSimulation(Group newSimulationCells) {
		// TODO: add in argument specifying new simulation name or identifier to update
		// labels, sliders, etc.

		updateCellsOnScreen(newSimulationCells);

		// TODO: swap out XML elements of titles, labels of buttons and sliders, etc.

		return;

	}

	// getter for mainScene
	public Scene getScene() {
		return mainScene;
	}

}
