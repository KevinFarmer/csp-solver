package csp;

public class SudokuDriver {

	private static int UNASSIGNED = -1;
	private static int BOARD = 9;
	
	public static void main(String[] args) {
		
		boolean easy = true;
		
		int[][] board = new int[BOARD][BOARD];
		for (int i = 0; i < BOARD; i++) {
			for (int j = 0; j < BOARD; j++) {
				board[i][j] = UNASSIGNED;
			}
		}
		
		
		
		if (easy) {
			//Easy board
			
			
		} else {
			//Do hard board
			
			
		}
		
		
		
	}
	
}
