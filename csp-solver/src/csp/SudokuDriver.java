package csp;

public class SudokuDriver {

	private static int UNASSIGNED = -1;
	private static int width = 9;
	
	public static void main(String[] args) {
		
		boolean easy = true; // ----------- Change for different board ---------
		//If easy = false, then run with either MRV or MAC-3 enabled or it is very slow
		
		boolean mrv = true;
		boolean lcv = false;
		boolean mac3 = false;
		
		int[][] board = new int[width][width];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < width; j++) {
				board[i][j] = UNASSIGNED;
			}
		}
		

		if (easy) {
			//Easy board
			board[0][2] = 7;
			board[0][7] = 2;
			board[0][8] = 5;

			board[1][0] = 3;
			board[1][1] = 8;
			board[1][6] = 9;
			
			board[2][0] = 2;
			board[2][1] = 5;
			board[2][3] = 4;
			board[2][4] = 7;
			board[2][5] = 1;
			
			board[3][3] = 6;
			board[3][5] = 9;
			board[3][8] = 4;
			
			board[4][2] = 4;
			board[4][4] = 3;
			board[4][6] = 6;
			
			board[5][0] = 8;
			board[5][3] = 1;
			board[5][5] = 7;
			
			board[6][3] = 9;
			board[6][4] = 1;
			board[6][5] = 3;
			board[6][7] = 5;
			board[6][8] = 6;
			
			board[7][2] = 2;
			board[7][4] = 6;
			board[7][5] = 5;
			board[7][7] = 7;
			board[7][8] = 9;
			
			board[8][0] = 9;
			board[8][1] = 6;
			board[8][6] = 3;	
		} else {
			//Do hard board
			board[0][1] = 4;
			board[0][2] = 9;
			board[0][3] = 1;
			board[0][7] = 7;
			
			board[1][0] = 8;
			board[1][3] = 7;
			board[1][8] = 1;
			
			board[2][4] = 4;
			board[2][7] = 2;
			
			board[3][5] = 1;
			board[3][6] = 3;
			
			board[4][0] = 4;
			board[4][2] = 6;
			board[4][6] = 5;
			board[4][8] = 2;
			
			board[5][2] = 1;
			board[5][3] = 2;
			
			board[6][1] = 9;
			board[6][4] = 6;
			
			board[7][0] = 6;
			board[7][5] = 3;
			board[7][8] = 5;
			
			board[8][1] = 2;
			board[8][5] = 5;
			board[8][6] = 6;
			board[8][7] = 9;
		}
		
		
		long start = System.currentTimeMillis();
		ConstraintSatisfactionProblem csp = new SudokuCSP(board, mrv, lcv, mac3);
		System.out.println("Starting board: ");
		((SudokuCSP) csp).printBoard(board);
		int sol[] = csp.solve();
		long end = System.currentTimeMillis();
		System.out.println("\nTime(ms): "+(end-start));
		csp.printStats();
		
		if (sol != null) {
			csp.printAssignment(sol);
		} else {
			System.out.println("Failed");
		}
		
		
	}
	
}
