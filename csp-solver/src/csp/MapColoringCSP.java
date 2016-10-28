package csp;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class MapColoringCSP extends ConstraintSatisfactionProblem {

	private ArrayList<String> variableList;
	private ArrayList<String> colorList;
	private Hashtable<String, List<String>> borders;
	
	
	public MapColoringCSP(ArrayList<String> countries, ArrayList<String> colors, Hashtable<String, List<String>> adjList,
			boolean mrv, boolean lcv, boolean mac3) {
		
		variableList = countries;
		colorList = colors;
		borders = adjList;
		
		numVar = countries.size();
		numVal = colors.size();
		
		super.mrv = mrv;
		super.lcv = lcv;
		super.mac3 = mac3;
		
		//variables = new int[numVar];
		//values = new int[numVal];
		
	}
	
	
	
	@Override
	protected void buildConstraints() {
		Hashtable<int[], List<int[]>> constraints = new Hashtable<int[], List<int[]>>();;
		
		//For each pair of variables, build a constraint
		for (int i = 0; i < numVar; i++) {
			for (int j = i+1; j < numVar; j++) {
				
				int[] pair = {i, j};
				
				//Create list of possible pairs of values
				List<int[]> validValues = new ArrayList<int[]>();
				for (int m = 0; m < numVal; m++) {
					for (int n = 0; n < numVal; n++) {
						int[] vals = {m, n};
						if (isValidPair(pair, vals)) {
							validValues.add(vals);
							System.out.println("("+pair[0]+","+pair[1]+")  ->  "+m+","+n);
						}
						
					}
				}
				System.out.println();
				
				constraints.put(pair, validValues);
			}
		}
		
		
		allConstraints = new Constraint(constraints);
	}



	//For the map coloring problem, the values just cannot be equal
	private boolean isValidPair(int[] pair, int[] vals) {
		if (vals[0] != vals[1])
			return true;
		
		//If they do not border each other, they can be the same color
		String c1 = variableList.get(pair[0]);
		String c2 = variableList.get(pair[1]);
		List<String> adj = borders.get(c1);
		if (!adj.contains(c2))
			return true;

		return false;
	}



	@Override
	protected void printAssignment() {
		// TODO Auto-generated method stub
		
	}

}
