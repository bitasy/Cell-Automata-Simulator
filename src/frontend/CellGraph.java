package frontend;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class CellGraph extends Stage {

	private final int WIDTH = 400;
	private final int HEIGHT = 200;
	final NumberAxis xAxis = new NumberAxis();
	final NumberAxis yAxis = new NumberAxis();

	private LineChart<Number, Number> lineChart;
	private ArrayList<XYChart.Series> allSeries;
	private int count;

	// Graphing was adapted from
	// https:docs.oracle.com/javase/8/javafx/user-interface-tutorial/line-chart.htm#CIHGBCFI
	// I understand there are a lot of suggestions, but I couldn't find an
	// easy/elegant way to fix them and since the code was also directly from the
	// java documentation I figured it was okay

	public CellGraph() {
		super();
		lineChart = new LineChart<Number, Number>(xAxis, yAxis);
		allSeries = new ArrayList<XYChart.Series>();
		count = 0;
		Scene scene = new Scene(lineChart, WIDTH, HEIGHT);
		this.setScene(scene);
		this.setTitle(CellSociety.GRAPH_TITLE);
		this.show();
	}

	public void addStartingPoints(Double[] values) {
		for (Double value : values) {
			XYChart.Series series = new XYChart.Series();
			series.getData().add(new XYChart.Data(count, value));
			lineChart.getData().add(series);
			allSeries.add(series);
		}
		count++;
	}

	public void addPoints(Double[] values) {
		for (int i = 0; i < values.length; i++) {
			double value = values[i];
			XYChart.Series series = allSeries.get(i);
			series.getData().add(new XYChart.Data(count, value));
		}
		count++;
	}

	public void reset() {
		System.out.println("this was called");
		count = 0;
		lineChart.getData().removeAll(lineChart.getData());
		allSeries.clear();
	}
}
