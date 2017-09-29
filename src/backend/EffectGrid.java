package backend;

public interface EffectGrid {
	
	void setState(int index, int[] states);
	
	int[] getStates(int i);

}
