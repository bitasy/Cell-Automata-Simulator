package frontend;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import xml_start.Screen;

public class SimulationInterface extends BorderPane {

	private int height = Screen.HEIGHT / 4;
	private String[] simNames;
	private String[] buttonTitles;

	public SimulationInterface() {
		super();
		this.setPrefSize(Screen.WIDTH, height);
		simNames = new String[] { "Segregation", "Wator", "GOL", "Fire" };
		buttonTitles = new String[] { "Start", "Pause", "Stop" };
		createDropDown();
		createButtons();
	}

	private void createDropDown() {
		ChoiceBox<String> simulations = new ChoiceBox<>();
		ChangeListener<String> changeListener = new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observables, //
					String oldV, String newV) {
				// System.out.println(newV);
			}
		};
		simulations.getSelectionModel().selectedItemProperty().addListener(changeListener);
		simulations.getItems().addAll(simNames);
		simulations.setValue(simNames[0]);
		simulations.setPadding(new Insets(3, 3, 3, 3));
		VBox vSim = new VBox();
		vSim.getChildren().add(simulations);
		VBox.setMargin(simulations, new Insets(10, 10, 10, 10));
		this.setLeft(vSim);
	}

	private void createButtons() {
		BorderPane buttons = new BorderPane();
		buttons.setMaxWidth(300);
		VBox vLeftButtons = setLeftButtons();
		VBox vRightButtons = setRightButtons();
		buttons.setLeft(vLeftButtons);
		buttons.setRight(vRightButtons);
		BorderPane.setAlignment(buttons, Pos.CENTER_LEFT);
		this.setCenter(buttons);
	}

	private VBox setRightButtons() {
		VBox vRightButtons = new VBox(20);
		vRightButtons.setPadding(new Insets(25, 20, 0, 0));
		Button reset = new Button("Reset");
		reset.setOnAction(e -> System.out.println("3"));
		reset.setPrefWidth(100);
		Button step = new Button("Step");
		step.setOnAction(e -> System.out.println("4"));
		step.setPrefWidth(100);
		vRightButtons.getChildren().addAll(reset, step);
		return vRightButtons;
	}

	private VBox setLeftButtons() {
		VBox vLeftButtons = new VBox(20);
		vLeftButtons.setPadding(new Insets(25, 20, 0, 50));
		Button start = new Button("Start");
		start.setPrefWidth(100);
		start.setOnAction(e -> System.out.println("1"));
		Button pause = new Button("Pause");
		pause.setOnAction(e -> System.out.println("2"));
		pause.setPrefWidth(100);
		vLeftButtons.getChildren().addAll(start, pause);
		return vLeftButtons;
	}
}
