package frontend;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class SimulationInterface extends BorderPane {

	private static final double HEIGHT =  .3 * CellSociety.HEIGHT;
	private String[] simNames;
	private Text authorText = new Text();
	private CellSimulator simulator;

	public SimulationInterface(CellSimulator s) {
		super();
		simulator = s;
		this.setPrefSize(CellSociety.WIDTH, HEIGHT);
		simNames = s.getSimulationNames();
		createDropDownSection();
		createButtonsSection();
		createSpeedSection();
		createSliderSection();
		createGraphInfo();
	}

	private void createDropDownSection() {
		VBox vSim = new VBox();
		ChoiceBox<String> simulations = createChoiceBox();
		vSim.getChildren().add(simulations);
		VBox.setMargin(simulations, new Insets(10, 10, 5, 10));
		Button newSim = new Button(CellSociety.BUTTON_TITLES[4]);
		newSim.setOnAction(e -> simulator.handleSimCreation());
		newSim.setPrefWidth(150);
		vSim.getChildren().add(newSim);
		VBox.setMargin(newSim, new Insets(5, 10, 5, 10));
		Button saveSim = new Button(CellSociety.BUTTON_TITLES[5]);
		saveSim.setOnAction(e -> simulator.handleSaveState());
		saveSim.setPrefWidth(100);
		vSim.getChildren().add(saveSim);
		VBox.setMargin(saveSim, new Insets(5, 10, 0, 10));
		this.setLeft(vSim);
	}

	private void createGraphInfo() {
		VBox topBox = new VBox();
		Text populationText = new Text();
		populationText.setFill(Color.BLACK);
		topBox.getChildren().add(populationText);
		topBox.setAlignment(Pos.CENTER);
		simulator.setText(populationText);
		this.setTop(topBox);
	}

	private ChoiceBox<String> createChoiceBox() {
		ChoiceBox<String> simulations = new ChoiceBox<>();
		ChangeListener<String> changeListener = new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observables, String oldV, String newV) {
				simulator.handleSimulatorChange(newV);
				authorText.setText(simulator.getAuthor());
				createSliderSection(); 
			}
		};
		simulations.getSelectionModel().selectedItemProperty().addListener(changeListener);
		simulations.getItems().addAll(simNames);
		simulations.setValue(simNames[0]);
		simulations.setPadding(new Insets(3, 3, 3, 3));
		return simulations;
	}

	private void createButtonsSection() {
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

	private void createSpeedSection() {
		BorderPane speedSection = new BorderPane();
		VBox sliderBox = setSliderBox(CellSociety.MINSPEED, CellSociety.MAXSPEED,
				(CellSociety.MAXSPEED - CellSociety.MINSPEED) / 2, CellSociety.UNIT_TITLE);
		Slider slider = (Slider) sliderBox.getChildren().get(0);
		Text value = (Text) sliderBox.getChildren().get(1);
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				simulator.handleSpeedChange(new_val.doubleValue());
				value.setText(String.format("%.2f", new_val));
			}
		});
		speedSection.setTop(sliderBox);
		authorText.setFill(Color.BLACK);
		speedSection.setCenter(authorText);
		BorderPane.setMargin(authorText, new Insets(0, 0, 10, 0));
		this.setRight(speedSection);
	}

	private VBox setSliderBox(double min, double max, double def, String unitDescription) {
		VBox sliderBox = new VBox(5);
		Slider slider = new Slider(min, max, def);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(0.25f);
		slider.setBlockIncrement(0.1f);
		Text speed = new Text(String.format("%.2f", def));
		speed.setFill(Color.BLACK);
		Text units = new Text(unitDescription);
		units.setFill(Color.BLACK);
		sliderBox.getChildren().addAll(slider, speed, units);
		BorderPane.setMargin(sliderBox, new Insets(30, 15, 0, 0));
		return sliderBox;
	}

	private void createSliderSection() {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setPadding(new Insets(0, 10, 0, 10));
		SliderInfo[] sliders = simulator.getSliderInfo();
		System.out.println(sliders.length);
		for (int i = 0; i < sliders.length; i++) {
			SliderInfo sliderInformation = sliders[i];
			VBox sliderBox = setSliderBox(sliderInformation.getMin(), sliderInformation.getMax(),
					sliderInformation.getDefault(), sliderInformation.getTitle());
			Slider slider = (Slider) sliderBox.getChildren().get(0);
			Text value = (Text) sliderBox.getChildren().get(1);
			final int constantIndex = i;
			slider.valueProperty().addListener(new ChangeListener<Number>() {
				public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
					double setValue = new_val.doubleValue();
					if (!sliderInformation.isContinuous()) {
						setValue = Math.round(setValue);
					}
					slider.setValue(setValue);
					simulator.handleExtraParameters(setValue, constantIndex);
					value.setText(String.format("%.2f", setValue));
				}
			});
			grid.add(sliderBox, i, 0);
		}
		BorderPane.setAlignment(grid, Pos.TOP_CENTER);
		this.setBottom(grid);
	}
}
