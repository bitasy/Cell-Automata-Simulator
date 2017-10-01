package backend;

public interface EffectGrid {
	
	void setState(int index, int[] states);
	
	int[] getStates(int i);
	
	int getPrimaryState(int i);

	int totalCells();
}
