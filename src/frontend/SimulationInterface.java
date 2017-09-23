package frontend;

import javafx.scene.Group;
import javafx.scene.control.ChoiceBox;
import xml_start.Screen;

public class SimulationInterface extends Group {

	private int height = Screen.HEIGHT / 5;
	private String[] simNames;

	public SimulationInterface() {
		super();
		this.resize(Screen.WIDTH, height);
		simNames = new String[] {"Segregation", "Wator", "GOL", "Fire"};
		createDropDown();
	}
	
	public void createDropDown() {
		ChoiceBox<String> simulations = new ChoiceBox<>(); 
		simulations.getItems().addAll(simNames);
		simulations.setValue(simNames[0]);
//		simulation
		this.getChildren().add(simulations);
	}
}
