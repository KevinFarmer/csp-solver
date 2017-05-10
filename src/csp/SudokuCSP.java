package csp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class SudokuCSP extends ConstraintSatisfactionProblem {
	
	int width = 9;
	int[][] board;
	
	public SudokuCSP(int[][] initBoard, boolean mrv, boolean lcv, boolean mac3) {
		
		numVar = width*width;
		numVal = width;
		
		board = initBoard;
		
		super.mrv = mrv;
		super.lcv = lcv;
		super.mac3 = mac3;
		
		initDomains = new HashMap<Integer, List<Integer>>();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < width; y++) {
				int val = coordToVal(x, y);
				List<Integer> vals = new ArrayList<Integer>();
				
				if (board[x][y] != UNASSIGNED) { //If given a preset value
					vals.add(board[x][y]);
				} else {
					for (int i = 1; i <= numVal; i++)
						vals.add(i);
				}
				initDomains.put(val, vals);
			}
		}
	}
	
	
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
		
		//For each conflicting pair of variables, build a constraint
		for (int i = 0; i < numVar; i++) {
			for (int j = 0; j < numVar; j++) {
				
				if (i == j)
					continue;
				
				int[] coord1 = valToCoord(i);
				int[] coord2 = valToCoord(j);
				int x1 = coord1[0];
				int y1 = coord1[1];
				int x2 = coord2[0];
				int y2 = coord2[1];
				boolean canConflict = false;
				
				if (x1 == x2 || y1 == y2) { //In same row or same column
					canConflict = true;
				} else if (x1/3 == x2/3 && y1/3 == y2/3) { //In same square of board
					canConflict = true;
				}
				
				if (!canConflict)
					continue;
				
				Pair pair = new Pair(i, j);
				List<Integer> domain1 = initDomains.get(i);
				List<Integer> domain2 = initDomains.get(j);
				
				HashSet<Pair> validValues = new HashSet<Pair>();
				for (int val1 : domain1) {
					for (int val2 : domain2) {
						
						if (val1 == val2) //cannot be same value
							continue;
						
						Pair vals = new Pair(val1, val2);
						validValues.add(vals);
						
					}
				}
				
				constraints.put(pair, validValues);
			}
		}
		
		allConstraints = new Constraint(constraints, numVar);
	}

	
	
	@Override
	protected void printAssignment(int[] assignment) {
		
		int[][] printBoard = new int[width][width];
		
		//Construct the circuit board with the circuits in place
		for(int i = 0; i < assignment.length; i++) {
			int val = assignment[i];
			int[] coord = valToCoord(i);
			int x = coord[0];
			int y = coord[1];
			
			printBoard[x][y] = val;
		}
		
		printBoard(printBoard);
	}
	
	//Prints the board
	public void printBoard(int[][] printBoard) {
		//Print the board
		for (int y = width-1; y >= 0; y--) {
			for (int x = 0; x < width; x++) {
				
				if (printBoard[x][y] == UNASSIGNED)
					System.out.print(". ");
				else
					System.out.print(printBoard[x][y]+" ");
				
				if (x > 0 && x < width-1 && (x+1) % 3 == 0)
					System.out.print("| ");
			}

			if (y > 0 && (y) % 3 == 0)
				System.out.println("\n----------------------");
			else
				System.out.println();
		}
	}

	
}
