package csp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CircuitBoardCSP extends ConstraintSatisfactionProblem {

	private final int MAX_DIM = 1000;
	
	private int width, height;
	
	private Pair[] circuits;
	
	public CircuitBoardCSP(int w, int h, Pair[] circuitDims, int numCircuits, 
			boolean mrv, boolean lcv, boolean mac3) {
		
		//circuits.length should be 2*numCircuits  (width and height for each circuit)
		
		if (w > MAX_DIM)
			width = MAX_DIM;
		else
			width = w;
		if (h > MAX_DIM)
			height = MAX_DIM;
		else
			height = h;
		
		numVal = width * height;
		numVar = numCircuits;
		
		circuits = circuitDims;
		
		super.mrv = mrv;
		super.lcv = lcv;
		super.mac3 = mac3;
		
		
		initDomains = new HashMap<Integer, List<Integer>>();
		for (int i = 0; i < numVar; i++) {
			Pair circuit = circuits[i];
			List<Integer> vals = new ArrayList<Integer>();
						
			for (int y = 0; y <= (height-circuit.v2); y++) {
				for (int x = 0; x <= (width-circuit.v1); x++) {
					int val = coordToVal(x, y);
					vals.add(val);
				}
			}
			initDomains.put(i, vals);
		}
		

		
		
		
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
	
	private int coordToVal(int x, int y) {
		return x + (width * y);
	}
	
	private int[] valToCoord(int val) {
		int x = val % width;
		int y = val / width;
		int coord[] = {x, y};
		return coord;
	}
	
	
	
	
	@Override
	protected void buildConstraints() {
		HashMap<Pair, HashSet<Pair>> constraints = new HashMap<Pair, HashSet<Pair>>();
		
		//For each pair of variables, build a constraint
		for (int i = 0; i < numVar; i++) {
			for (int j = 0; j < numVar; j++) {
				
				if (i == j)
					continue;
				
				Pair pair = new Pair(i, j);
				List<Integer> domain1 = initDomains.get(i);
				List<Integer> domain2 = initDomains.get(j);
				
				HashSet<Pair> validValues = new HashSet<Pair>();
				for (int val1 : domain1) {
					for (int val2 : domain2) {
						
						Pair vals = new Pair(val1, val2);
						if (isValidPair(pair, vals)) {
							validValues.add(vals);
							//System.out.println(pair+" ->  "+vals);
						}
						
					}
				}
				
				constraints.put(pair, validValues);
			}
		}
		
		allConstraints = new Constraint(constraints, numVar);
	}

	
	//Returns false if the two circuits overlap
	private boolean isValidPair(Pair pair, Pair vals) {
		Pair circ1 = circuits[pair.v1];
		Pair circ2 = circuits[pair.v2];
		int[] coord1 = valToCoord(vals.v1);
		int[] coord2 = valToCoord(vals.v2);
		
		int x1start = coord1[0];
		int y1start = coord1[1];
		int x1end = x1start+circ1.v1-1;
		int y1end = y1start+circ1.v2-1;
		
		int x2start = coord2[0];
		int y2start = coord2[1];
		int x2end = x2start+circ2.v1-1;
		int y2end = y2start+circ2.v2-1;
		
		boolean xOverlap = false, yOverlap = false;
		
		if (x1start <= x2end && x1end >= x2start)
			xOverlap = true;
		if (y1start <= y2end && y1end >= y2start)
			yOverlap = true;
		
		
		if (xOverlap && yOverlap)
			return false;
		else
			return true;
	}
	
	
	

	@Override
	protected void printAssignment(int[] assignment) {
		int[][] board = new int[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				board[x][y] = UNASSIGNED;
			}
		}
		
		//Construct the circuit board with the circuits in place
		for(int i = 0; i < assignment.length; i++) {
			int val = assignment[i];
			int w = circuits[i].v1;
			int h = circuits[i].v2;
			int[] coord = valToCoord(val);
			
			
			//Mark that area for circuit
			for (int x = coord[0]; x < coord[0]+w; x++) {
				for (int y = coord[1]; y < coord[1]+h; y++) {
					board[x][y] = i;
				}
			}
		}
		
		//Print the board
		for (int y = height-1; y >= 0; y--) {
			for (int x = 0; x < width; x++) {
				if (board[x][y] == UNASSIGNED)
					System.out.print(". ");
				else
					System.out.print(board[x][y]+" ");
				
			}
			System.out.println();
		}
		
		
	}

}
