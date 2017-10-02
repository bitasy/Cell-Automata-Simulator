package frontend;

import java.util.List;
import java.util.Map;

import backend.IGrid;
import grids.RectangularGrid;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import xml_start.MasterMap;
import xml_start.SimulationParameters;
import xml_start.XMLWrite;

public class CellSimulator extends Pane {
	private static final double COS_THIRTY = Math.cos(Math.PI / 6);
	private static final double SIN_THIRTY = Math.sin(Math.PI / 6);
	private static final double HEIGHT = .7 * CellSociety.HEIGHT;
	private double cellSize;
	private int rows;
	private int cols;
	private Timeline animation;
	private double step;
	private IGrid myGrid;
	private MasterMap masterMap;
	private Map<String, SimulationParameters> XML_readings;
	private SimulationParameters data;
	private Stage stage;
	private Alert alert = new Alert(AlertType.INFORMATION);
	private double[] parameters;
	private Text populationText;
	private CellGraph myGraph;

	public CellSimulator(Stage s) {
		super();
		stage = s;
		masterMap = new MasterMap();
		XML_readings = masterMap.getMap();
		myGraph = new CellGraph();
		checkForErrors();
		this.setPrefSize(CellSociety.WIDTH, HEIGHT);
		step = 1000 / ((CellSociety.MAXSPEED - CellSociety.MINSPEED) / 2);
	}

	public void handleStart() {
		animation.play();
	}

	public void handlePause() {
		animation.pause();
	}

	public void handleReset() {
		System.out.println("Reset");
		myGrid.drawTo(this);
		animation.pause();
	}

	public void handleStep() {
		myGrid.update();
		// populationText.setText(myGrid.status());
		// myGraph.addPoints(myGrid.information);
	}

	public void handleSimulatorChange(String sim) {
		System.out.println("Change to " + sim);
		data = XML_readings.get(sim);
		stage.setTitle(CellSociety.TITLE + " - " + data.getTitle());
//<<<<<<< HEAD
		myGrid = new RectangularGrid(data);
		myGrid.drawTo(this);
//======= TODO choose grid dynamically
		parameters = new double[data.getSliders().size()];
		myGraph.reset();
		myGraph.addStartingPoints(new Integer[] { 1, 2 });
		startAnimation();
	}

	public void handleSpeedChange(double speed) {
		System.out.println("Speed Change!!");
		step = 1000 / speed;
		animation.stop();
		startAnimation();
	}

	public void handleExtraParameters(double newValue, int index) {
		parameters[index] = newValue;
		// myGrid.setParameters(parameters);
		animation.stop();
		startAnimation();
	}

	public void handleSimCreation() {
		Stage newStage = new Stage();
		CellSociety game = new CellSociety();
		game.start(newStage);
	}

	public void handleSaveState() {
		System.out.println("Save the state!");
		XMLWrite xmlWriter = new XMLWrite();
		try {
			// xmlWriter.saveState(data, Current version of grid, parameters);
		} catch (Exception e) {
			// remove e.printstackTrace
			e.printStackTrace();
			// Throw alert
		}
	}

	public String[] getSimulationNames() {
		return XML_readings.keySet().toArray(new String[XML_readings.keySet().size()]);
	}

	public String getAuthor() {
		return data.getAuthor();
	}

	public List<SliderInfo> getSliderInfo() {
		return data.getSliders();
	}

	public void setText(Text text) {
		populationText = text;
	}

	private void startAnimation() {
		if (animation != null) {
			animation.stop();
		}
		animation = new Timeline(new KeyFrame(Duration.millis(step), e -> handleStep()));
		animation.setCycleCount(Timeline.INDEFINITE);
	}

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
}
