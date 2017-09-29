package rulesets;

import java.lang.reflect.Method;
import java.util.List;

import backend.Cell;
import backend.EffectGrid;
import backend.IRuleSet;

public abstract class StandardRuleSet implements IRuleSet {
	
	protected Cell cell;
	protected List<Cell> paramCells;
	protected EffectGrid effects;
	
	@Override
	public void setNewGrid(EffectGrid effects) {
		this.effects = effects;
	}

	@Override
	public final void applyRule(int ruleNum, Cell cell, List<Cell> params) {
		this.cell = cell;
		paramCells = params;
		try {
			Method m = this.getClass().getDeclaredMethod("rule"+ruleNum, new Class[] {});
			m.invoke(this, new Object[] {});
		} catch (Exception e) {
			throw new IllegalArgumentException("Could not find method rule"+ruleNum+". Please make sure it exists in "+this.getClass().getName()+" and is package visible.");
		}
	}
	
	protected void setNewCell(Cell cell, int[] states) {
		effects.setState(cell.getTag(), states);
	}

}
