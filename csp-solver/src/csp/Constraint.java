package csp;

import java.util.Hashtable;
import java.util.List;

public class Constraint {
	
	//protected Hashtable<int[], List<int[]>> constraints;
	
	Hashtable<int[], List<int[]>> constraints;
	int numVar, numVal;
	
	public Constraint(Hashtable<int[], List<int[]>> allConsts) {
		constraints = allConsts;
		
		
	}
	
	
	//Args: the current assignment, the variable that was just updated
	public boolean isSatisfied(int[] assignment, int var) {
		
		
		
		return true;
	}

}
