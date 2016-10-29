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

		ConstraintSatisfactionProblem csp = new MapColoringCSP(countries, colors, adjList, false, false, false);
		
		
		int[] sol = csp.solve();
		
		System.out.println();
		
		if (sol == null) {
			System.out.println("Failed!");
			return;
		} else {
			csp.printAssignment(sol);
		}
		
		//for (int i = 0; i < sol.length; i++)
		//	System.out.print(sol[i]+" ");
		
	}
	
}
