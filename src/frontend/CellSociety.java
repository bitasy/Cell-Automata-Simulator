package frontend;

import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
 * @author Simran
 *
 */
public class CellSociety extends Application {

	public static final ResourceBundle labels = ResourceBundle.getBundle("text"); // Used to populate text for interface 
	public static final String TITLE = labels.getString("title");
	public static final String[] BUTTON_TITLES = labels.getString("buttons").split(",");
	public static final String UNIT_TITLE = labels.getString("units");
	public static final String ALERT_TITLE = labels.getString("alert");
	public static final String ALERT_MESSAGE = labels.getString("alertMessage");
	public static final String SAVE = labels.getString("alertMessage");
	public static final String GRAPH_TITLE = labels.getString("graph");
	public static final String STATE = labels.getString("state");
	public static final String SAVING_ERROR = labels.getString("saveError");
	public static final String RULE_ERROR = labels.getString("ruleError");
	public static final int WIDTH = 700;
	public static final int HEIGHT = 700;
	public static final Paint BACKGROUND = Color.WHITE;
	public static final double MAXSPEED = 10.;
	public static final double MINSPEED = 0.;

	public static void main(String[] args) {
		launch(args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 * 
	 * Splits the top and bottom half into the grid and the interface. Instantiates
	 * the game and lets it run
	 */
	@Override
	public void start(Stage s) {
		s.setResizable(false);
		BorderPane root = new BorderPane();
		Scene mainScene = new Scene(root, WIDTH, HEIGHT, BACKGROUND);
		CellSimulator simulator = new CellSimulator(s);
		root.setTop(simulator.getPane());
		root.setBottom(new SimulationInterface(simulator));
		s.setScene(mainScene);
		s.show();
	}
}