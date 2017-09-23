package frontend;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import xml_start.Screen;

public class SimulationInterface extends BorderPane {

	private int height = Screen.HEIGHT / 4;
	private String[] simNames;

	public SimulationInterface() {
		super();
		this.setPrefSize(Screen.WIDTH, height);
		simNames = new String[] { "Segregation", "Wator", "GOL", "Fire" };
		createDropDown();
		createButtons();
	}

	private void createDropDown() {
		ChoiceBox<String> simulations = new ChoiceBox<>();
		ChangeListener<String> changeListener = new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observables, //
					String oldV, String newV) {
//				System.out.println(newV);
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
		VBox vButtons = new VBox(15);
	}
}
