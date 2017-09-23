package frontend;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import xml_start.Screen;

public class CellSimulator extends Pane {

	private int height = 4 * Screen.HEIGHT / 5;;
	private double size;
	private int rows;
	private int cols;
	private Timeline animation;
	private int step;

	public CellSimulator() {
		super();
		rows = 10;
		cols = 10;
		step = 1000;
		this.resize(Screen.WIDTH, height);
		createCellShapes();
		animation = new Timeline(new KeyFrame(Duration.millis(step), e -> update()));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();
	}

	private void update() {
//		System.out.println("updating!");
	}

	private void createCellShapes() {
		size = Math.min(Screen.WIDTH / cols, height / rows);
		double totalWidthPercent = size * cols / Screen.WIDTH;
		double firstX = (.5 - totalWidthPercent / 2) * Screen.WIDTH;
		double totalHeightPercent = size * rows / height;
		double firstY = (.5 - totalHeightPercent / 2) * height;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				Rectangle cell = new Rectangle(size, size);
				cell.setOnMouseClicked(e -> System.out.println("Cell clicked!"));
				cell.setX(firstX + j * size);
				cell.setY(firstY + i * size);
				cell.setFill(Color.WHITE);
				cell.setStroke(Color.BLACK);
				cell.setStrokeWidth(2);
				this.getChildren().add(cell);
			}
		}
	}
}
