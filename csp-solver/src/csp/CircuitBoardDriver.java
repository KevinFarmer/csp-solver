package csp;

public class CircuitBoardDriver {

	public static void main(String[] args) {
		
		long start, end;
		boolean mrv = false;
		boolean lcv = false;
		boolean mac3 = false;
		
		boolean simple = false; //-------------- Change for different example --------------
		
		Pair[] circuits;
		int num, w , h;
		
		if (simple) {
			w = 10;
			h = 3;
			num = 4;
			circuits = new Pair[num];
			circuits[0] = new Pair(3, 2);
			circuits[1] = new Pair(5, 2);
			circuits[2] = new Pair(2, 3);
			circuits[3] = new Pair(7, 1);
			
		} else {
			w = 30;
			h = 10;
			num = 10;
			circuits = new Pair[num];
			circuits[0] = new Pair(3, 2);
			circuits[1] = new Pair(5, 2);
			circuits[2] = new Pair(2, 3);
			circuits[3] = new Pair(7, 5);
			circuits[4] = new Pair(4, 9);
			circuits[5] = new Pair(10, 10);
			circuits[6] = new Pair(10, 1);
			circuits[7] = new Pair(4, 1);
			circuits[8] = new Pair(7, 1);
			circuits[9] = new Pair(2, 6);
		}
		
		start = System.currentTimeMillis();
		ConstraintSatisfactionProblem csp = 
				new CircuitBoardCSP(w, h, circuits, num, mrv, lcv, mac3);

		int[] ret = csp.solve();
		end = System.currentTimeMillis();
		System.out.println("\nTime(ms): "+(end-start));
		csp.printStats();
		
		if (ret == null)
			System.out.println("Failed");
		else
			csp.printAssignment(ret);
		
	}
	
}
