package frontend;

import backend.IGrid;
import xml_start.MasterMap;
import xml_start.SimulationParameters;
import xml_start.XMLWrite;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.List;
import java.util.Map;

public class CellSimulator {

	private static final double HEIGHT = .7 * CellSociety.HEIGHT;
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
	private Pane pane;
	private ScrollPane scroll;

	public CellSimulator(Stage s) {
		pane = new Pane();
		scroll = new ScrollPane();
		scroll.setContent(pane);
		scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Horizontal scroll bar
		scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Vertical scroll bar
		scroll.setPrefSize(CellSociety.WIDTH, HEIGHT);
		stage = s;
		masterMap = new MasterMap();
		XML_readings = masterMap.getMap();
		// System.out.println("map size: " + XML_readings.size());
		myGraph = new CellGraph();
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

	public void handleReset() {
		data.setupGridObject();
		myGrid = data.getGridObject();
		myGrid.drawTo(pane);
		data.getRules().setParams(parameters);
		animation.pause();
	}

	public void handleStep() {
		myGrid.update();
		int[] statusUpdate = myGrid.getCellCounts();
		populationText.setText(readStatus(statusUpdate));
		myGraph.addPoints(statusUpdate);
	}

	public void handleSimulatorChange(String sim) {
		data = XML_readings.get(sim);
		stage.setTitle(CellSociety.TITLE + " - " + data.getTitle());
		myGrid = data.getGridObject();
		myGrid.drawTo(pane);
		parameters = new double[data.getRules().getSliders().length];
		myGraph.reset();
		myGraph.addStartingPoints(myGrid.getCellCounts());
		startAnimation();
	}

	public void handleSpeedChange(double speed) {
		step = 1000 / speed;
		animation.stop();
		startAnimation();
	}

	public void handleExtraParameters(double newValue, int index) {
		animation.stop();
		parameters[index] = newValue;
		data.getRules().setParams(parameters);
		startAnimation();
	}

	public void handleSimCreation() {
		Stage newStage = new Stage();
		CellSociety game = new CellSociety();
		game.start(newStage);
	}

	public void handleSaveState() {
		XMLWrite xmlWriter = new XMLWrite();
		try {
			xmlWriter.saveState(data, myGrid.getPrimaryStates(), parameters);
		} catch (Exception e) {
			e.printStackTrace();
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

	private String readStatus(int[] stateCount) {
		String status = "";
		for (int i = 0; i < stateCount.length; i++) {
			status += CellSociety.STATE + (i + 1) + ": " + stateCount[i] + " ";
		}
		return status;
	}
}
