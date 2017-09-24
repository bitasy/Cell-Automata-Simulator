package frontend;

import java.util.ArrayList;
import java.util.Map;

import backend.Grid;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import xml_start.MasterMap;
import xml_start.SimulationParameters;

public class CellSimulator extends Pane {

	private static final int HEIGHT = 4 * CellSociety.HEIGHT / 5;;
	private double cellSize;
	private int rows;
	private int cols;
	private Timeline animation;
	private double step;
	private Grid myGrid;
	private MasterMap masterMap = new MasterMap();
	private Map<String, SimulationParameters> XML_readings = masterMap.getMap();
	private SimulationParameters data;
	private Stage stage;
	private Alert alert = new Alert(AlertType.INFORMATION);

	public CellSimulator(Stage s) {
		super();
		stage = s;
		checkForErrors();
		this.resize(CellSociety.WIDTH, HEIGHT);
		step = 1000/((CellSociety.MAXSPEED - CellSociety.MINSPEED) / 2);
	}

	public void handleStart() {
		animation.play();
	}

	public void handlePause() {
		animation.pause();
	}

	public void handleReset() {
		System.out.println("Reset");
		addCellShapes();
	}

	public void handleStep() {
		System.out.println("updating!");
		myGrid.update();
	}

	public void handleSimulatorChange(String sim) {
		System.out.println("Change to " + sim);
		data = XML_readings.get(sim);
		stage.setTitle(data.getTitle());
		rows = data.getNumRows();
		cols = data.getNumCols();
		cellSize = Math.min(CellSociety.WIDTH / cols, HEIGHT / rows);
		myGrid = new Grid(data, cellSize);
		addCellShapes();
		startAnimation();
	}

	public void handleSpeedChange(double speed) {
		System.out.println("Speed Change!!");
		step = 1000 / speed;
		animation.stop();
		startAnimation();
	}

	public String[] getSimulationNames() {
		return XML_readings.keySet().toArray(new String[XML_readings.keySet().size()]);
	}

	public String getAuthor() {
		return data.getAuthor();
	}

	private void startAnimation() {
		if(animation != null) {
			animation.stop();
		}
		animation = new Timeline(new KeyFrame(Duration.millis(step), e -> handleStep()));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();
	}

	private void addCellShapes() {
		this.getChildren().removeAll(this.getChildren());
		double totalWidthPercent = cellSize * cols / CellSociety.WIDTH;
		double firstX = (.5 - totalWidthPercent / 2) * CellSociety.WIDTH;
		double totalHeightPercent = cellSize * rows / HEIGHT;
		double firstY = (.5 - totalHeightPercent / 2) * HEIGHT;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				Rectangle cell = (Rectangle) myGrid.getCellShape(i, j);
				cell.setOnMouseClicked(e -> System.out.println("Cell clicked!"));
				cell.setX(firstX + j * cellSize);
				cell.setY(firstY + i * cellSize);
				this.getChildren().add(cell);
			}
		}
	}

	private void checkForErrors() {
		alert.setTitle(CellSociety.ALERT_TITLE);
		alert.setHeaderText(null);
		Map<String, ArrayList<String>> errors = masterMap.getErrors();
		if (errors.size() != 0) {
			String errorMessage = String.join(", ", errors.keySet());
			alert.setContentText(CellSociety.ALERT_MESSAGE + errorMessage);
			alert.showAndWait();
		}
	}
}
