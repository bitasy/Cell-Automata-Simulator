package backend;

import java.util.LinkedList;

import javafx.scene.Parent;

public abstract class NeighborGrid implements IGrid {
	
	private CellList head;
	private IRuleSet myRuleSet;

	@Override
	public void update() {
		myRuleSet.setCellList(head);
		applyRules();		
	}
	
	private void applyRules() {
		for(int rule = 1; rule <= myRuleSet.numRules(); rule++)
			for(Cell c : head) 
					myRuleSet.applyRule(rule, c);
	}

	@Override
	public void draw(Parent parent) {

	}

}
