package frontend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import backend.Grid;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import xml_start.MasterMap;
import xml_start.SimulationParameters;

public class CellSimulator extends Pane {

	private static final double COS_THIRTY = Math.cos(Math.PI / 6);
	private static final double SIN_THIRTY = Math.sin(Math.PI / 6);
	private static final int HEIGHT = 3 * CellSociety.HEIGHT / 4;
	private double cellSize;
	private int rows;
	private int cols;
	private Timeline animation;
	private double step;
	private Grid myGrid;
	private MasterMap masterMap;
	private Map<String, SimulationParameters> XML_readings;
	private SimulationParameters data;
	private Stage stage;
	private Alert alert = new Alert(AlertType.INFORMATION);
	private List<Double> parameters;
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
		addCellShapes();
		animation.pause();
	}

	public void handleStep() {
		myGrid.update();
		// populationText.setText(myGrid.status());
		myGraph.addPoints(new Double[] { 1., 2. });
	}

	public void handleSimulatorChange(String sim) {
		System.out.println("Change to " + sim);
		data = XML_readings.get(sim);
		stage.setTitle(CellSociety.TITLE + " - " + data.getTitle());
		rows = data.getNumRows();
		cols = data.getNumCols();
		cellSize = Math.min(CellSociety.WIDTH / cols, HEIGHT / rows);
		// parameters = new ArrayList<Double>(data.getSliderInfo().size());
		// populationText.setText(myGrid.status());
		myGraph.reset();
		myGraph.addStartingPoints(new Double[] { 1., 2. });
		addCellShapes();
		startAnimation();
	}

	public void handleSpeedChange(double speed) {
		System.out.println("Speed Change!!");
		step = 1000 / speed;
		animation.stop();
		startAnimation();
	}

	public void handleExtraParameters(double newValue, int index) {
		System.out.println("Value Changed!");
		parameters.set(index, newValue);
		myGrid.setParameters(parameters);
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
	}

	public String[] getSimulationNames() {
		return XML_readings.keySet().toArray(new String[XML_readings.keySet().size()]);
	}

	public String getAuthor() {
		return data.getAuthor();
	}

	public List<SliderInfo> getSliderInfo() {
		return data.getSliderInfo();
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

	private void addCellShapes() {
		myGrid = new Grid(data, cellSize);
		this.getChildren().removeAll(this.getChildren());
		double totalWidthPercent = cellSize * cols / CellSociety.WIDTH;
		double firstX = (.5 - totalWidthPercent / 2) * CellSociety.WIDTH;
		double totalHeightPercent = cellSize * rows / HEIGHT;
		double firstY = (.5 - totalHeightPercent / 2) * HEIGHT;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				Rectangle cell = (Rectangle) myGrid.getCellShape(i, j);
				// cell.setOnMouseClicked(e -> updateCell());
				cell.setX(firstX + j * cellSize);
				cell.setY(firstY + i * cellSize);
				this.getChildren().add(cell);
			}
		}
	}

	// private void addCellShapes() {
	// this.getChildren().removeAll(this.getChildren());
	// // Calculations are set by taking the totalWidth/totalHeight, equating them
	// to
	// // the respective widths/heights, solving for the side length, and taking the
	// // minimum value as this ensures that all hexagons will be shown on screen.
	// // tl;dr: math
	// double sideLength = Math.min(CellSociety.WIDTH / ((1 + 2 * SIN_THIRTY) *
	// (cols + .5)-(cols*SIN_THIRTY)+SIN_THIRTY),
	// HEIGHT / 1.1 / (rows * 2 * COS_THIRTY + COS_THIRTY));
	// double hexagonalWidth = sideLength + 2 * SIN_THIRTY * sideLength;
	// double hexagonalHeight = 2 * sideLength * COS_THIRTY;
	// double totalWidth = cols * hexagonalWidth + hexagonalWidth / 2 -
	// ((cols-1)*sideLength*SIN_THIRTY);
	// double totalHeight = rows * hexagonalHeight + sideLength * COS_THIRTY;
	// double totalWidthPercent = totalWidth / CellSociety.WIDTH;
	// double firstX = (.5 - totalWidthPercent / 2) * CellSociety.WIDTH + sideLength
	// * SIN_THIRTY;
	// double totalHeightPercent = totalHeight / HEIGHT;
	// double firstY = (.5 - totalHeightPercent / 2) * HEIGHT + sideLength *
	// COS_THIRTY;
	// for (int i = 0; i < rows; i++) {
	// for (int j = 0; j < cols; j++) {
	// double x, y;
	// if (j % 2 == 0) {
	// x = firstX + j * (hexagonalWidth - SIN_THIRTY * sideLength);
	// y = firstY + i * hexagonalHeight;
	// } else {
	// x = firstX + j * (hexagonalWidth - SIN_THIRTY * sideLength);
	// y = firstY + i * hexagonalHeight + hexagonalHeight / 2;
	// }
	// Polygon cell = new Polygon();
	// // Adding coordinates to the polygon
	// cell.getPoints().addAll(new Double[] { x, y, x - sideLength * SIN_THIRTY, y -
	// sideLength * COS_THIRTY,
	// x, y - 2 * sideLength * COS_THIRTY, x + sideLength, y - 2 * sideLength *
	// COS_THIRTY,
	// x + sideLength + sideLength * SIN_THIRTY, y - sideLength * COS_THIRTY, x +
	// sideLength, y });
	// cell.setOnMouseClicked(e -> updateCell());
	// cell.setFill(Color.WHITE);
	// cell.setStroke(Color.BLACK);
	// cell.setStrokeWidth(2);
	// this.getChildren().add(cell);
	// }
	// }
	// }

	// private void addCellShapes() {
	// this.getChildren().removeAll(this.getChildren());
	// // Calculations are set by taking the totalWidth/totalHeight, equating them
	// to
	// // the respective widths/heights, solving for the side length, and taking the
	// // minimum value as this ensures that all triangles will be shown on screen.
	// // tl;dr: math
	// double sideLength = Math.min(CellSociety.WIDTH / cols,
	// HEIGHT /(rows*COS_THIRTY*1.2));
	// double triangularHeight = sideLength * COS_THIRTY*1.15;
	// double totalWidth = cols/2 * sideLength + sideLength;
	// double totalHeight = rows * triangularHeight;
	// double totalWidthPercent = totalWidth / CellSociety.WIDTH;
	// double firstX = (.5 - totalWidthPercent / 2) * CellSociety.WIDTH;
	// double totalHeightPercent = totalHeight / HEIGHT;
	// double firstY = (.5 - totalHeightPercent / 2) * HEIGHT;
	// for (double i = 0; i < rows; i++) {
	// for (double j = 0; j < cols; j++) {
	// double x, y;
	// Double[] positions;
	// Polygon cell = new Polygon();
	// x = firstX + sideLength/2*(j+1);
	// y = firstY + i*triangularHeight;
	// if ((i+j)% 2 == 0) {
	// positions = new Double[] { x, y, x - sideLength/2, y+sideLength,
	// x + sideLength/2, y+sideLength};
	// } else {
	// positions = new Double[] { x, y+sideLength, x - sideLength/2, y,
	// x + sideLength/2, y};
	// }
	// cell.getPoints().addAll(positions);
	// // Adding coordinates to the polygon
	// cell.setOnMouseClicked(e -> updateCell());
	// cell.setFill(Color.WHITE);
	// cell.setStroke(Color.BLACK);
	// cell.setStrokeWidth(2);
	// this.getChildren().add(cell);
	// }
	// }
	// }

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
