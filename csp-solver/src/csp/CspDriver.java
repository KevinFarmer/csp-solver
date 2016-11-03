package csp;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class CspDriver {

	
	public static void main(String[] args) {
		
		ArrayList<String> countries = new ArrayList<String>();
		ArrayList<String> colors = new ArrayList<String>();
		Hashtable<String, List<String>> adjList = new Hashtable<String, List<String>>();
		List<String> adj;
		
		countries.add("WA");
		countries.add("NT");
		countries.add("SA");
		countries.add("Q");
		countries.add("NSW");
		countries.add("V");

		//Western Australia
		adj = new LinkedList<String>();
		adj.add("NT");
		adj.add("SA");
		adjList.put("WA", adj);
		
		//Northern territory
		adj = new LinkedList<String>();
		adj.add("WA");
		adj.add("SA");
		adj.add("Q");
		adjList.put("NT", adj);
		
		//South Australia
		adj = new LinkedList<String>();
		adj.add("WA");
		adj.add("NT");
		adj.add("Q");
		adj.add("NSW");
		adj.add("V");
		adjList.put("SA", adj);
				
		//Queensland
		adj = new LinkedList<String>();
		adj.add("NT");
		adj.add("SA");
		adj.add("NSW");
		adjList.put("Q", adj);
		
		//New South Whales
		adj = new LinkedList<String>();
		adj.add("Q");
		adj.add("SA");
		adj.add("V");
		adjList.put("NSW", adj);
		
		//Victoria
		adj = new LinkedList<String>();
		adj.add("SA");
		adj.add("NSW");
		adjList.put("V", adj);
		
		colors.add("r");
		colors.add("g");
		colors.add("b");

		boolean mrv = false;
		boolean lcv = false;
		boolean mac3 = true;
		
		long start, end;
		int[] sol;
		
		/*
		//With none
		start = System.nanoTime();
		ConstraintSatisfactionProblem csp2 = new MapColoringCSP(countries, colors, adjList, mrv, lcv, mac3);
		sol = csp2.solve();
		end = System.nanoTime();
		System.out.println("\nTime(ns): "+(end-start));
		if (sol == null) {
			System.out.println("Failed!");
			return;
		} else {
			csp2.printAssignment(sol);
		}	*/	

		
		/*
		int w = 10, h = 3;
		int num = 4;
		Pair[] circuits = new Pair[num];
		circuits[0] = new Pair(3, 2);
		circuits[1] = new Pair(5, 2);
		circuits[2] = new Pair(2, 3);
		circuits[3] = new Pair(7, 1);
		*/
		int w = 30, h = 12;
		int num = 10;
		Pair[] circuits = new Pair[num];
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
		
		start = System.currentTimeMillis();
		ConstraintSatisfactionProblem csp = 
				new CircuitBoardCSP(w, h, circuits, num, mrv, lcv, mac3);

		System.out.println("----------------------------------------------------------");
		int[] ret = csp.solve();
		end = System.currentTimeMillis();
		System.out.println("\nTime(ms): "+(end-start));
		
		if (ret == null)
			System.out.println("Failed");
		else
			csp.printAssignment(ret);
		
	}
	
}
