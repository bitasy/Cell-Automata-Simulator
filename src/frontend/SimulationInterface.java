package frontend;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class SimulationInterface extends BorderPane {

	private static final int HEIGHT = CellSociety.HEIGHT / 4;
	private String[] simNames;
	private Text authorText = new Text();
	private CellSimulator simulator;

	public SimulationInterface(CellSimulator s) {
		super();
		simulator = s;
		this.setPrefSize(CellSociety.WIDTH, HEIGHT);
		simNames = s.getSimulationNames();
		createDropDown();
		createButtons();
		createSliderSection();
	}

	private void createDropDown() {
		ChoiceBox<String> simulations = new ChoiceBox<>();
		ChangeListener<String> changeListener = new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observables, String oldV, String newV) {
				simulator.handleSimulatorChange(newV);
				authorText.setText(simulator.getAuthor());
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
		Button reset = new Button(CellSociety.BUTTON_TITLES[2]);
		reset.setOnAction(e -> simulator.handleReset());
		reset.setPrefWidth(100);
		Button step = new Button(CellSociety.BUTTON_TITLES[3]);
		step.setOnAction(e -> simulator.handleStep());
		step.setPrefWidth(100);
		vRightButtons.getChildren().addAll(reset, step);
		return vRightButtons;
	}

	private VBox setLeftButtons() {
		VBox vLeftButtons = new VBox(20);
		vLeftButtons.setPadding(new Insets(25, 20, 0, 50));
		Button start = new Button(CellSociety.BUTTON_TITLES[0]);
		start.setPrefWidth(100);
		start.setOnAction(e -> simulator.handleStart());
		Button pause = new Button(CellSociety.BUTTON_TITLES[1]);
		pause.setOnAction(e -> simulator.handlePause());
		pause.setPrefWidth(100);
		vLeftButtons.getChildren().addAll(start, pause);
		return vLeftButtons;
	}

	private void createSliderSection() {
		BorderPane sliderSection = new BorderPane();
		VBox sliderBox = setSliderBox();
		sliderSection.setTop(sliderBox);
		authorText.setFill(Color.BLACK);
		sliderSection.setCenter(authorText);
		BorderPane.setMargin(authorText, new Insets(0, 0, 30, 0));
		this.setRight(sliderSection);
	}

	private VBox setSliderBox() {
		VBox sliderBox = new VBox(5);
		Slider slider = new Slider(CellSociety.MINSPEED, CellSociety.MAXSPEED, (CellSociety.MAXSPEED - CellSociety.MINSPEED) / 2);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(0.25f);
		slider.setBlockIncrement(0.1f);
		Text speed = new Text(String.format("%.2f", (CellSociety.MAXSPEED - CellSociety.MINSPEED) / 2));
		speed.setFill(Color.BLACK);
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				simulator.handleSpeedChange(new_val.doubleValue());
				speed.setText(String.format("%.2f", new_val));
			}
		});
		Text units = new Text(CellSociety.UNIT_TITLE);
		units.setFill(Color.BLACK);
		sliderBox.getChildren().addAll(slider, speed, units);
		BorderPane.setMargin(sliderBox, new Insets(30, 15, 0, 0));
		return sliderBox;
	}
}
