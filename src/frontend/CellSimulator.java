package frontend;

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
import javafx.stage.Stage;
import javafx.util.Duration;
import xml_start.MasterMap;
import xml_start.SimulationParameters;

public class CellSimulator extends Pane {

	private static final double COS_THIRTY = Math.cos(Math.PI / 6);
	private static final double SIN_THIRTY = Math.sin(Math.PI / 6);
	private static final int HEIGHT = 4 * CellSociety.HEIGHT / 5;;
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

	public CellSimulator(Stage s) {
		super();
		stage = s;
		masterMap = new MasterMap();
		XML_readings = masterMap.getMap();
		checkForErrors();
		// this.resize(CellSociety.WIDTH, HEIGHT);
		// this.setMaxSize(CellSociety.WIDTH, HEIGHT);
		// this.setMinSize(CellSociety.WIDTH, HEIGHT);
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
	}

	public void handleSimulatorChange(String sim) {
		System.out.println("Change to " + sim);
		data = XML_readings.get(sim);
		stage.setTitle(CellSociety.TITLE + " - " + data.getTitle());
		rows = data.getNumRows();
		cols = data.getNumCols();
		cellSize = Math.min(CellSociety.WIDTH / cols, HEIGHT / rows);
		addCellShapes();
		startAnimation();
	}

	public void handleSpeedChange(double speed) {
		System.out.println("Speed Change!!");
		step = 1000 / speed;
		animation.stop();
		startAnimation();
	}

	public void handleSimCreation() {
		Stage newStage = new Stage();
		BorderPane newRoot = new BorderPane();
		Scene mainScene = new Scene(newRoot, CellSociety.WIDTH, CellSociety.HEIGHT, CellSociety.BACKGROUND);
		CellSimulator simulator = new CellSimulator(newStage);
		newRoot.setTop(simulator);
		newRoot.setBottom(new SimulationInterface(simulator));
		newStage.setScene(mainScene);
		newStage.show();
	}

	public String[] getSimulationNames() {
		return XML_readings.keySet().toArray(new String[XML_readings.keySet().size()]);
	}

	public String getAuthor() {
		return data.getAuthor();
	}

	private void startAnimation() {
		if (animation != null) {
			animation.stop();
		}
		animation = new Timeline(new KeyFrame(Duration.millis(step), e -> handleStep()));
		animation.setCycleCount(Timeline.INDEFINITE);
		// animation.play();
	}

	// private void addCellShapes() {
	// myGrid = new Grid(data, cellSize);
	// this.getChildren().removeAll(this.getChildren());
	// double totalWidthPercent = cellSize * cols / CellSociety.WIDTH;
	// double firstX = (.5 - totalWidthPercent / 2) * CellSociety.WIDTH;
	// double totalHeightPercent = cellSize * rows / HEIGHT;
	// double firstY = (.5 - totalHeightPercent / 2) * HEIGHT;
	// for (int i = 0; i < rows; i++) {
	// for (int j = 0; j < cols; j++) {
	// Rectangle cell = (Rectangle) myGrid.getCellShape(i, j);
	// cell.setOnMouseClicked(e -> System.out.println("Cell clicked!"));
	// cell.setX(firstX + j * cellSize);
	// cell.setY(firstY + i * cellSize);
	// this.getChildren().add(cell);
	// }
	// }
	// }

	private void addCellShapes() {
		this.getChildren().removeAll(this.getChildren());
		// Calculations are set by taking the totalWidth/totalHeight, equating them to
		// the respective widths/heights, solving for the side length, and taking the
		// minimum value as this ensures that all hexagons will be shown on screen.
		// tl;dr: math
		double sideLength = Math.min(CellSociety.WIDTH / ((1 + 2 * SIN_THIRTY) * (cols + .5)-(cols*SIN_THIRTY)+SIN_THIRTY),
				HEIGHT / 1.1 / (rows * 2 * COS_THIRTY + COS_THIRTY));
		double hexagonalWidth = sideLength + 2 * SIN_THIRTY * sideLength;
		double hexagonalHeight = 2 * sideLength * COS_THIRTY;
		double totalWidth = cols * hexagonalWidth + hexagonalWidth / 2 - ((cols-1)*sideLength*SIN_THIRTY);
		double totalHeight = rows * hexagonalHeight + sideLength * COS_THIRTY;
		double totalWidthPercent = totalWidth / CellSociety.WIDTH;
		double firstX = (.5 - totalWidthPercent / 2) * CellSociety.WIDTH + sideLength * SIN_THIRTY;
		double totalHeightPercent = totalHeight / HEIGHT;
		double firstY = (.5 - totalHeightPercent / 2) * HEIGHT + sideLength * COS_THIRTY;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				double x, y;
				if (j % 2 == 0) {
					x = firstX + j * (hexagonalWidth - SIN_THIRTY * sideLength);
					y = firstY + i * hexagonalHeight;
				} else {
					x = firstX + j * (hexagonalWidth - SIN_THIRTY * sideLength);
					y = firstY + i * hexagonalHeight + hexagonalHeight / 2;
				}
				Polygon cell = new Polygon();
				// Adding coordinates to the polygon
				cell.getPoints().addAll(new Double[] { x, y, x - sideLength * SIN_THIRTY, y - sideLength * COS_THIRTY,
						x, y - 2 * sideLength * COS_THIRTY, x + sideLength, y - 2 * sideLength * COS_THIRTY,
						x + sideLength + sideLength * SIN_THIRTY, y - sideLength * COS_THIRTY, x + sideLength, y });
				cell.setOnMouseClicked(e -> System.out.println("Cell clicked!"));
				cell.setFill(Color.WHITE);
				cell.setStroke(Color.BLACK);
				cell.setStrokeWidth(2);
				this.getChildren().add(cell);
			}
		}
	}

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
	// cell.setOnMouseClicked(e -> System.out.println("Cell clicked!"));
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
