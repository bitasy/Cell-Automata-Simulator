package frontend;

import backend.IGrid;
import xml_start.MasterMap;
import xml_start.SimulationParameters;
import xml_start.XMLWrite;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Simran
 *
 */
public class CellSimulator {

	private static final double HEIGHT = .7 * CellSociety.HEIGHT;
	private Timeline animation;
	private double step;
	private IGrid myGrid;
	private MasterMap masterMap; // Has XML_readings and string that contains all errors
	private Map<String, SimulationParameters> XML_readings;
	private SimulationParameters data;
	private Stage stage;
	private Alert alert = new Alert(AlertType.INFORMATION);
	private double[] parameters;
	private Text populationText;
	private CellGraph myGraph;
	private Pane pane;
	private ScrollPane scroll;

	/**
	 * CellSimulator contains all the visualization for the game and holds the
	 * displays for the grid. It handles all the actions that take place when there
	 * is interactions with the interface. It also throws all the errors and is
	 * responsible for sending relevant information to the Grid
	 * 
	 * @param s
	 *            Stage of the game
	 */
	public CellSimulator(Stage s) {
		pane = new Pane();
		scroll = new ScrollPane();
		scroll.setContent(pane);
		scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scroll.setPrefSize(CellSociety.WIDTH, HEIGHT);
		stage = s;
		masterMap = new MasterMap();
		XML_readings = masterMap.getMap();
		myGraph = new CellGraph();
		myGraph.setY(0);
		checkForErrors();
		pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				handlePause();
			}
		});
		step = 1000 / ((CellSociety.MAXSPEED - CellSociety.MINSPEED) / 2);
	}

	public void handleStart() {
		animation.play();
	}

	public void handlePause() {
		animation.pause();
	}

	/**
	 * Sets parameters and has a new Grid Object depending on the original
	 * specifications
	 */
	public void handleReset() {
		data.setupGridObject();
		myGrid = data.getGridObject();
		myGrid.drawTo(pane);
		data.getRules().setParams(parameters);
		animation.pause();
	}

	/**
	 * Method to handle all updating of the grid, dynamic text, and graph
	 */
	public void handleStep() {
		try {
			myGrid.update();			
		}
		catch (IllegalArgumentException e) {
			myGrid.freeze();
			alert.setContentText(e.getMessage());
			alert.show();
		}
		int[] statusUpdate = myGrid.getCellCounts();
		populationText.setText(readStatus(statusUpdate));
		myGraph.addPoints(statusUpdate);
	}

	/**
	 * Changes the titles, grid, sliders for the parameters, and the grid depending
	 * on if there is a new simulation that needs to be loaded.
	 * 
	 * @param sim
	 *            Name of the simulation
	 */
	public void handleSimulatorChange(String sim) {
		data = XML_readings.get(sim);
		stage.setTitle(CellSociety.TITLE + " - " + data.getTitle());
		myGrid = data.getGridObject();
		myGrid.drawTo(pane);
		parameters = Arrays.copyOfRange(data.getExtraParameters(), 1, data.getExtraParameters().length);
		myGraph.reset();
		myGraph.addStartingPoints(myGrid.getCellCounts());
		startAnimation();
	}

	/**
	 * Handle conversion so that slider speed becomes updates per second
	 * 
	 * @param speed
	 */
	public void handleSpeedChange(double speed) {
		step = 1000 / speed;
		animation.stop();
		startAnimation();
	}

	/**
	 * Changes the parameters in accordance with the sliders
	 * 
	 * @param newValue
	 *            The value that the parameter should be changed
	 * @param index
	 *            The corresponding index in the parameters array
	 */
	public void handleExtraParameters(double newValue, int index) {
		animation.stop();
		parameters[index] = newValue;
		data.getRules().setParams(parameters);
		startAnimation();
	}

	/**
	 * Creates new window for simulation
	 */
	public void handleSimCreation() {
		Stage newStage = new Stage();
		CellSociety game = new CellSociety();
		game.start(newStage);
	}

	/**
	 * Creates error if the button saving state doesn't work
	 */
	public void handleSaveState() {
		XMLWrite xmlWriter = new XMLWrite();
		try {
			xmlWriter.saveState(data, myGrid.getPrimaryStates(), parameters);
		} catch (Exception e) {
			alert.setContentText(CellSociety.SAVING_ERROR);
			alert.showAndWait();
		}
	}

	public String[] getSimulationNames() {
		return XML_readings.keySet().toArray(new String[XML_readings.keySet().size()]);
	}

	public String getAuthor() {
		return data.getAuthor();
	}

	/**
	 * @return Array of custom SliderInfo object that stores all the information
	 *         required to programmatically create a new slider
	 */
	public SliderInfo[] getSliderInfo() {
		return data.getRules().getSliders();
	}

	public void setText(Text text) {
		populationText = text;
	}

	public ScrollPane getPane() {
		return scroll;
	}

	private void startAnimation() {
		if (animation != null) {
			animation.stop();
		}
		animation = new Timeline(new KeyFrame(Duration.millis(step), e -> handleStep()));
		animation.setCycleCount(Timeline.INDEFINITE);
	}

	/**
	 * Sees if there were errors loading XMLs and starts alerts if there is an error
	 */
	private void checkForErrors() {
		alert.setTitle(CellSociety.ALERT_TITLE);
		alert.setHeaderText(null);
		Map<String, List<String>> errors = masterMap.getErrors();
		if (errors.size() != 0) {
			String errorMessage = String.join(", ", errors.keySet());
			alert.setContentText(CellSociety.ALERT_MESSAGE + errorMessage);
			alert.showAndWait();
		}
	}

	/**
	 * @param stateCount
	 *            Array that contains count of each state at the appropriate index
	 * @return String to display information on screen
	 */
	private String readStatus(int[] stateCount) {
		String status = "";
		for (int i = 0; i < stateCount.length; i++) {
			status += CellSociety.STATE + (i + 1) + ": " + stateCount[i] + " ";
		}
		return status;
	}
}
