package csp;

import java.util.Arrays;
import java.util.Hashtable;

public abstract class ConstraintSatisfactionProblem {

	private int UNASSIGNED = -1;
	
	protected boolean mrv, lcv, mac3; //Whether or not to use these
	//Minimum remaining value, least-constraining value, and AC-3 algorithm
	
	//protected int[] variables, values;
	protected int numVar, numVal;

	Constraint allConstraints;
	
	
	// Return a valid assignment, or null if none can be found
	public int[] solve() {
		
		buildConstraints();
		
		if (true)
			return null;
		
		int[] assignment = new int[numVar];
		for (int i = 0; i < numVar; i++)
			assignment[i] = UNASSIGNED;
		
		return recursiveBacktrackSolver(assignment, 0);

	}


	//A recursive DFS through the search tree
	private int[] recursiveBacktrackSolver(int[] assignment, int totalAssigned) {
		
		if (totalAssigned == numVar) {
			System.out.println("Found solution");
			return assignment;
		}
		
		int varToAssign;
		if (mrv)
			varToAssign = getMrvVar(assignment);
		else
			varToAssign = getNextVar(assignment);
		
		//Assign value to variable
		int value;
		for (int i = 0; i < numVal; i++) {
			if (lcv)
				value = getLeastConstrainingVal(assignment, varToAssign);
			else
				value = i;
			
			assignment[varToAssign] = value;
			
			
			//If this assignment is not valid, then do not recurse
			
			if ( !allConstraints.isSatisfied(assignment, varToAssign) )
				continue;
				
			
			int[] sol = recursiveBacktrackSolver(assignment, totalAssigned+1);
			if (sol != null)
				return sol;
		}
		
		
		return null;
	}

	


	//Returns the next variable sequentially
	private int getNextVar(int[] assignment) {
		for (int i = 0; i < numVar; i++)
			if (assignment[i] != UNASSIGNED) {
				return i;
		}
		return -1;
	}


	//Returns the unassigned variable with the minimum remaining values
	private int getMrvVar(int[] assignment) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	//Returns the value that rules out as few values for adjacent variables as possible
	private int getLeastConstrainingVal(int[] assignment, int varToAssign) {
		// TODO Auto-generated method stub
		return 0;
	}


	//Must create a Constraint and assign it to allConstraints
	protected abstract void buildConstraints();	
	
}
