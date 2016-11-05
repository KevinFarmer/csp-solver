package csp;

import java.util.HashMap;
import java.util.HashSet;

public class Constraint {
	
	//protected Hashtable<int[], List<int[]>> constraints;
	private int UNASSIGNED = -1;
	
	HashMap<Pair, HashSet<Pair>> constraints;
	int numVar, numVal;
	
	public Constraint(HashMap<Pair, HashSet<Pair>> allConsts, int nv) {
		constraints = allConsts;
		numVar = nv;
		
	}
	
	
	//Args: the current assignment, the variable that was just updated
	public boolean isSatisfied(int[] assignment, int var) {
		
		for (int i = 0; i < numVar; i++) {
			if (var == i || assignment[i] == UNASSIGNED)
				continue;
			
			Pair vars = new Pair(var, i);// {var, i};
			Pair vals = new Pair(assignment[var], assignment[i]);
			
			HashSet<Pair> consts = constraints.get(vars);
			
			//If not adjacent, continue
			if (consts == null) {
				continue;
			}
			
			if (!consts.contains(vals)) {
				return false;
			}
		}
		
		
		for (int i = 0; i < numVar; i++) {
			if (var == i || assignment[i] == UNASSIGNED)
				continue;
			
			Pair vars = new Pair(i, var);
			Pair vals = new Pair(assignment[i], assignment[var]);
			
			HashSet<Pair> consts = constraints.get(vars);
			
			//If not adjacent, return true
			if (consts == null) {
				//System.out.println("Pair not adjacent");
				continue;
			}
			
			if (!consts.contains(vals)) {
				//System.out.println("false2");
				return false;
			}
		}
		
		return true;
	}
	
	//Returns the list of constraints for a pair of values
	public HashSet<Pair> getConstraintList(Pair vars) {
		return constraints.get(vars);
	}
	
	

}
