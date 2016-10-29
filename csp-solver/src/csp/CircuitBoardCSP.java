package csp;

public class CircuitBoardCSP extends ConstraintSatisfactionProblem {

	private int width, height;
	
	public CircuitBoardCSP(int w, int h, int[] circuits, int numCircuits) {
		
		//circuits.length should be 2*numCircuits  (width and height for each circuit)
		
		width = w;
		height = h;
		
		numVal = width * height;
		numVar = numCircuits;
		
	}
	
	/* 
	 *   -Converting a (x,y) to a value and back
	 * 
	 * value -> (x, y)
	 * x = value / height;
	 * y = value / width;
	 * 
	 *  9 10 11
	 *  6 7 8
	 *  3 4 5
	 *  0 1 2
	 *  
	 *  (x, y) -> value
	 *  (x, y) -> (x + (width*y) )
	 *  (2, 2) -> (2 + (3*2) ) = 8
	 *  
	 */
	
	private int coordToVal(int[] coord) {
		return coord[0] + (width * coord[1]);
	}
	
	private int[] valToCoord(int val) {
		int x = val / height;
		int y = val / width;
		int coord[] = {x, y};
		return coord;
	}
	
	
	
	
	@Override
	protected void buildConstraints() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void printAssignment(int[] assignment) {
		// TODO Auto-generated method stub
		
	}

}
