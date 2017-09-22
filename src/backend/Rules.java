package backend;

public class Rules {
	
	public static void fewerNeighbors(Cell cell, Cell[] neighbors, int maxNeighbors) {
		if(countNeighbors(neighbors) < maxNeighbors) {
			//do stuff
		}
	}
	
	public static void numNeighbors(Cell cell, Cell[] neighbors, int numNeighbors) {
		if(countNeighbors(neighbors) == numNeighbors) {
			//do stuff
		}
	}
	
	
	private static int countNeighbors(Object[] list) {
		int count = 0;
		for(Object o : list) {
			count += o != null ? 1 : 0;
		}
		return count;
	}
}
