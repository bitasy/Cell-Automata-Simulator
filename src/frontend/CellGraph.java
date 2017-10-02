package frontend;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * @author Simran
 *
 */
public class CellGraph extends Stage {

	private final int WIDTH = 400;
	private final int HEIGHT = 200;
	final NumberAxis xAxis = new NumberAxis();
	final NumberAxis yAxis = new NumberAxis();

	private LineChart<Number, Number> lineChart;
	@SuppressWarnings("rawtypes")
	private ArrayList<XYChart.Series> allSeries;
	private int count;

	// Graphing was adapted from
	// https:docs.oracle.com/javase/8/javafx/user-interface-tutorial/line-chart.htm#CIHGBCFI
	// I understand there are a lot of suppressed warnings, but I couldn't find an
	// easy/elegant way to fix them. A lot of the code from the java documentation
	// came with a lot of warnings and suggestions

	@SuppressWarnings("rawtypes")
	public CellGraph() {
		super();
		lineChart = new LineChart<Number, Number>(xAxis, yAxis);
		lineChart.setCreateSymbols(false);
		allSeries = new ArrayList<XYChart.Series>();
		count = 0;
		Scene scene = new Scene(lineChart, WIDTH, HEIGHT);
		this.setScene(scene);
		this.setAlwaysOnTop(true);
		this.setTitle(CellSociety.GRAPH_TITLE);
		this.show();
	}

	/**
	 * Used to instantiate a new line for each type of state for individual
	 * simulations (1 line for each possible state)
	 * 
	 * @param values
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addStartingPoints(int[] values) {
		for (Integer value : values) {
			XYChart.Series series = new XYChart.Series();
			series.getData().add(new XYChart.Data(count, value));
			lineChart.getData().add(series);
			allSeries.add(series);
		}
		count++;
	}

	/**
	 * Adds points to the graphs as the simulation continues
	 * 
	 * @param values
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addPoints(int[] values) {
		for (int i = 0; i < values.length; i++) {
			Integer value = values[i];
			XYChart.Series series = allSeries.get(i);
			series.getData().add(new XYChart.Data(count, value));
		}
		count++;
	}

	public void reset() {
		count = 0;
		lineChart.getData().removeAll(lineChart.getData());
		allSeries.clear();
	}
}
