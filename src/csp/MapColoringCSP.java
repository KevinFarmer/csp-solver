package csp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

public class MapColoringCSP extends ConstraintSatisfactionProblem {

	private ArrayList<String> variableList;
	private ArrayList<String> colorList;
	private Hashtable<String, List<String>> borders;

	
	public MapColoringCSP(ArrayList<String> countries, ArrayList<String> colors, Hashtable<String, List<String>> brdrs,
			boolean mrv, boolean lcv, boolean mac3) {
		
		variableList = countries;
		colorList = colors;
		borders = brdrs;
		
		numVar = countries.size();
		numVal = colors.size();
		
		System.out.println("numVal: "+numVal);
		
		super.mrv = mrv;
		super.lcv = lcv;
		super.mac3 = mac3;
		
		initDomains = new HashMap<Integer, List<Integer>>();
		for (int i = 0; i < numVar; i++) {
			List<Integer> vals = new ArrayList<Integer>();
			for (int j = 0; j < numVal; j++)
				vals.add(j);
			initDomains.put(i, vals);
		}
		
	}
	
	
	@Override
	protected void buildConstraints() {
		HashMap<Pair, HashSet<Pair>> constraints = new HashMap<Pair, HashSet<Pair>>();
		
		//For each pair of variables, build a constraint
		for (int i = 0; i < numVar; i++) {
			for (int j = 0; j < numVar; j++) {
				
				
				List<String> adj = borders.get(variableList.get(i));
				if (!adj.contains(variableList.get(j)))
					continue;
				
				Pair pair = new Pair(i, j);
				
				//Create set of possible pairs of values
				HashSet<Pair> validValues = new HashSet<Pair>();
				for (int m = 0; m < numVal; m++) {
					for (int n = 0; n < numVal; n++) {
						
						Pair vals = new Pair(m, n);
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



	//For the map coloring problem, the values just cannot be equal
	private boolean isValidPair(Pair vars, Pair vals) {
		if (vals.getV1() != vals.getV2())
			return true;
		
		//If they do not border each other, they can be the same color
		String c1 = variableList.get(vars.getV1());
		String c2 = variableList.get(vars.getV2());
		List<String> adj = borders.get(c1);
		if (!adj.contains(c2))
			return true;

		return false;
	}



	@Override
	protected void printAssignment(int[] assignment) {
		String country, color;
		for(int i = 0; i < assignment.length; i++) {
			country = variableList.get(i);
			if (assignment[i] == UNASSIGNED)
				color = "Unassigned";
			else
				color = colorList.get(assignment[i]);
			
			System.out.println(country+": "+color);
		}
		
	}

}
