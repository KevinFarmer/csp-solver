package csp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class ConstraintSatisfactionProblem {

	protected int UNASSIGNED = -1;
	
	protected boolean mrv, lcv, mac3; //Whether or not to use these
	//Minimum remaining value, least-constraining value, and AC-3 algorithm
	
	protected int numVar, numVal;

	Constraint allConstraints;
	HashMap<Integer, List<Integer>> domains;
	//HashMap<Integer, List<Integer>> adjList;
	
	
	// Return a valid assignment, or null if none can be found
	public int[] solve() {
		
		buildConstraints();
		
		int[] assignment = new int[numVar];
		for (int i = 0; i < numVar; i++)
			assignment[i] = UNASSIGNED;
		
		//For testing only
		List<Integer> dm = domains.get(3);
		//dm.remove(0);
		
		return recursiveBacktrackSolver(assignment, 0);

	}


	//A recursive DFS through the search tree
	private int[] recursiveBacktrackSolver(int[] assignment, int totalAssigned) {
		//System.out.println("Recurse");
		
		if (totalAssigned == numVar) {
			System.out.println("Found solution");
			return assignment;
		}
		
		int varToAssign;
		if (mrv)
			varToAssign = getMrvVar(assignment);
		else
			varToAssign = getNextVar(assignment);
		
		//System.out.println("varToAssign:  "+varToAssign);
		
		System.out.println("\nVar: "+varToAssign + "   totalAssign: "+totalAssigned);
		
		List<Integer> domainBeforeAssign = domains.get(varToAssign);
		List<Integer> domain;
		
		System.out.println("Domain before assign:");
		for (int val : domainBeforeAssign)
			System.out.print(val+" ");
		System.out.println();
		
		
		if (lcv) {
			System.out.print("Before sort: ");
			for (int val : domainBeforeAssign)
				System.out.print(val+" ");
			System.out.println();
			
			sortByLCV(domainBeforeAssign, varToAssign);
			
			System.out.print("After sort: ");
			for (int val : domainBeforeAssign)
				System.out.print(val+" ");
			System.out.println();
		}

		
		//Assign value to variable
		//int value;
		Iterator<Integer> iter = domainBeforeAssign.iterator();
		while(iter.hasNext()) {
		//for (int val : domain) {
			int val = (int) iter.next();
			
			assignment[varToAssign] = val;
			
			//After making an assignment, set the domain to be that value
			domain = new ArrayList<Integer>();
			domain.add(val);
			domains.put(varToAssign, domain);
			
			//for (int j = 0; j < numVar; j++)
			//	System.out.print(assignment[j] +" ");
			//System.out.println();
			
			//If this assignment is not valid, then do not recurse
			
			if ( !allConstraints.isSatisfied(assignment, varToAssign) ) {
				//assignment[varToAssign] = UNASSIGNED;
				//System.out.println();
				
				continue;
			}
			
			for (int v : domains.get(varToAssign))
				System.out.print("-- "+v);
			System.out.println();
			
			if (mac3) {
				if (!runMAC3(varToAssign)) {
						//If AC-3 fails
					assignment[varToAssign] = UNASSIGNED;
					domain = domainBeforeAssign; //Reverse changes to domain
					return null; 
				}
			}
			
			
			int[] sol = recursiveBacktrackSolver(assignment, totalAssigned+1);
			if (sol != null) {
				return sol;
			}
			
			//System.out.println();
		}
		
		assignment[varToAssign] = UNASSIGNED;
		domain = domainBeforeAssign; //Reverse changes to domain
		
		System.out.println("Returning null");
		return null;
	}

	


	private void sortByLCV(List<Integer> domain, int varToAssign) {
		// TODO Auto-generated method stub
		
		int[] numAdj = new int[numVal];
		for (int i = 0; i < numAdj.length; i++)
			numAdj[i] = 0;
		
		for (int i = 0; i < numVar; i++) {
			HashSet<Pair> constraintList = allConstraints.getConstraintList(new Pair(varToAssign, i));
			if (constraintList == null) {
				//then not adjacent
				continue;
			} else {
				List<Integer> adjDomain = domains.get(i);
				
				//Increment for each val in adjacent domain
				for (int adjVal : adjDomain) {
					numAdj[adjVal]++;
				}
			}
		}
		
		//Sort domain based on values found
		Collections.sort(domain, new Comparator<Integer>() {
		    public int compare(Integer o1, Integer o2) {
		        return (Integer.valueOf(numAdj[o1])).compareTo(numAdj[o2]);
		    }
		});
		
		
	}


	//Returns the next variable sequentially
	private int getNextVar(int[] assignment) {
		for (int i = 0; i < numVar; i++) {
			System.out.println("=="+assignment[i]);
		
			if (assignment[i] == UNASSIGNED)
				return i;
		}
		return -1;
	}


	//Returns the unassigned variable with the minimum remaining values
	private int getMrvVar(int[] assignment) {
		// TODO Auto-generated method stub
		int var = 0;
		int num;
		int min = Integer.MAX_VALUE;
		
		for (int i = 0; i<numVar; i++) {
			if (assignment[i] != UNASSIGNED)
				continue;
			
			List<Integer> vals = domains.get(i);
			num = vals.size();
			if (num < min) {
				min = num;
				var = i;
			}
		}
		System.out.println(min+": "+var);
		return var;
	}
	
	
	
	private boolean runMAC3(int var) {
		
		System.out.println("Running AC-3");
		
		LinkedList<Pair> queue = new LinkedList<Pair>();
		
		//Add all arcs to queue that point to var
		for (int i = 0; i < numVar; i++) {
				Pair arc = new Pair(i, var);
				if (allConstraints.getConstraintList(arc) != null) {
					queue.add(arc);
				}
		}
		
		//Pop all arcs, enforce consistency
		while (!queue.isEmpty()) {
			Pair arc = queue.poll();
			int var1 = arc.getV1();
			int var2 = arc.getV2();
			System.out.println(var1+"--"+var2);
			
			List<Integer> domain1 = domains.get(var1);
			List<Integer> domain2 = domains.get(var2);
			
			//List<Integer> temp = domain1;
			
			HashSet<Pair> constrains = allConstraints.getConstraintList(arc);
			
			boolean madeDeletion = false;
			
			//For each value in the domain, check consistency
			Iterator<Integer> iter = domain1.iterator();
			while (iter.hasNext()) {
				int val1 = iter.next();
				boolean hasLegalPairing = false;
				
				//check if some valid pairing in domain2
				for (int val2 : domain2) {
					Pair vals = new Pair(val1, val2);
					if (constrains.contains(vals))
						hasLegalPairing = true;
				}
				
				//If not consistent for this val1 in domain1, then remove val1
				if (!hasLegalPairing) {
					iter.remove();
					madeDeletion = true;
					System.out.println("Deleting: "+var1+"  "+val1);
				}
				
			}
			
			if (domain1.size() == 0)
				return false;
			
			//If made a deletion, add all arcs pointing to var1 in arc
			if (madeDeletion) {
				for (int i = 0; i < numVar; i++) {
					Pair newArc = new Pair(i, var1);
					if (allConstraints.getConstraintList(newArc) != null) {
						queue.add(newArc);
					}
				}
			}
			
			
		}
		
		return true;
		
	}
	
	
	/*
	//Returns the value that rules out as few values for adjacent variables as possible
	private int getLeastConstrainingVal(int[] assignment, int varToAssign) {
		
		return 0;
	}
	*/
	

	//Must create a Constraint and assign it to allConstraints
	protected abstract void buildConstraints();	
	protected abstract void printAssignment(int[] assignment);
	
}
