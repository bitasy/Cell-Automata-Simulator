package rulesets;

import java.lang.reflect.InvocationTargetException;
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
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Could not find method rule"+ruleNum+". Please make sure it exists in "+this.getClass().getName()+" and is package visible.");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void setNewCell(Cell cell, int[] states) {
		effects.setState(cell.getTag(), states);
	}

}
