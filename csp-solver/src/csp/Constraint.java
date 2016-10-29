package csp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

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
		
		System.out.println("Constraint check: " +var);
		
		for (int i = 0; i < numVar; i++) {
			System.out.println(var+" "+i);
			if (var == i || assignment[i] == UNASSIGNED)
				continue;
			
			Pair vars = new Pair(var, i);// {var, i};
			Pair vals = new Pair(assignment[var], assignment[i]);// {assignment[var], assignment[i]};
			System.out.println(vars+"   "+vals);
			
			HashSet<Pair> consts = constraints.get(vars);
			
			//If not adjacent, continue
			if (consts == null) {
				System.out.println("Pair not adjacent");
				continue;
			}
			
			if (!consts.contains(vals)) {
				System.out.println("false2");
				return false;
			}
		}
		
		System.out.println("--");
		
		for (int i = 0; i < numVar; i++) {
			if (var == i || assignment[i] == UNASSIGNED)
				continue;
			
			Pair vars = new Pair(i, var);// {i, var};
			Pair vals = new Pair(assignment[i], assignment[var]);
			System.out.println(vars+"   "+vals);
			
			HashSet<Pair> consts = constraints.get(vars);
			
			//If not adjacent, return true
			if (consts == null) {
				System.out.println("Pair not adjacent");
				continue;
			}
			
			if (!consts.contains(vals)) {
				System.out.println("false2");
				return false;
			}
		}
		
		System.out.println("Returning true");
		return true;
	}

}
