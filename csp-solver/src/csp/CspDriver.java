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
		
		countries.add("NSW");
		adj = new LinkedList<String>();
		adj.add("V");
		adj.add("WA");
		adjList.put("NSW", adj);
		
		countries.add("V");
		adj = new LinkedList<String>();
		adj.add("NSW");
		adj.add("WA");
		adjList.put("V", adj);
		
		countries.add("WA");
		adj = new LinkedList<String>();
		adj.add("V");
		adj.add("NSW");
		adjList.put("WA", adj);
		
		colors.add("r");
		colors.add("g");
		colors.add("b");

		ConstraintSatisfactionProblem csp = new MapColoringCSP(countries, colors, adjList, false, false, false);
		
		
		int[] sol = csp.solve();
		
		for (int i = 0; i < sol.length; i++)
			System.out.print(i+" ");
		System.out.println();
		
		
	}
	
}
