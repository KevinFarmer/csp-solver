package csp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class ConstraintSatisfactionProblem {

	protected int UNASSIGNED = -1;
	
	private int valsDeleted = 0;
	private int totalAssignments = 0;
	
	protected boolean mrv, lcv, mac3; //Whether or not to use these
	//Minimum remaining value, least-constraining value, and MAC-3 algorithm
	
	protected int numVar, numVal;

	Constraint allConstraints;
	HashMap<Integer, List<Integer>> initDomains; //Set by child class

	
	// Return a valid assignment, or null if none can be found
	public int[] solve() {
		System.out.println("MRV: "+mrv+",  LCV: "+lcv+",  MAC-3: "+mac3);
		buildConstraints();
		
		int[] assignment = new int[numVar];
		for (int i = 0; i < numVar; i++)
			assignment[i] = UNASSIGNED;
		
		HashMap<Integer, List<Integer>> domains = copyDomains(initDomains);
		
		return recursiveBacktrackSolver(assignment, 0, domains);
	}



	//Creates a copy of the domain HashMap passed in
	private HashMap<Integer, List<Integer>> copyDomains(HashMap<Integer, List<Integer>> domainToCopy) {
		HashMap<Integer, List<Integer>> domains = new HashMap<Integer, List<Integer>>();
		
		for(int var : domainToCopy.keySet()) {
			List<Integer> vals = new ArrayList<Integer>();
			for (int val : domainToCopy.get(var)) {
				vals.add(val);
			}
			domains.put(var, vals);
		}
		
		return domains;
	}




	//A recursive DFS through the search tree
	private int[] recursiveBacktrackSolver(int[] assignment, int totalAssigned, HashMap<Integer, List<Integer>> initDomains) {
		
		if (totalAssigned == numVar) {
			System.out.println("Found solution");
			return assignment;
		}
		
		int varToAssign;
		if (mrv)
			varToAssign = getMrvVar(assignment, initDomains);
		else
			varToAssign = getNextVar(assignment);
		
		HashMap<Integer, List<Integer>> domains = copyDomains(initDomains);
		List<Integer> domainBeforeAssign = domains.get(varToAssign);
		List<Integer> domain;
		

		if (lcv) {
			sortByLCV(domainBeforeAssign, varToAssign, domains);
		}
		
		
		//Assign value to variable
		Iterator<Integer> iter = domainBeforeAssign.iterator();
		while(iter.hasNext()) {
			int val = (int) iter.next();
			
			assignment[varToAssign] = val;
			totalAssignments++;
			
			//After making an assignment, set the domain to be that value
			domain = new ArrayList<Integer>();
			domain.add(val);
			domains.put(varToAssign, domain);

			
			//Check for conflict
			if ( !allConstraints.isSatisfied(assignment, varToAssign) ) {
				assignment[varToAssign] = UNASSIGNED;
				if (mac3)
					domains = copyDomains(initDomains);
				continue;
			}
			
			if (mac3) {
				if (!runMAC3(assignment, varToAssign, domains)) {
					//If MAC-3 fails
					domains = copyDomains(initDomains);
					assignment[varToAssign] = UNASSIGNED;
					continue; 
				}
			} 
			

			int[] sol = recursiveBacktrackSolver(assignment, totalAssigned+1, domains);
			if (sol != null) {
				return sol;
			}
			
			if (mac3)
				domains = copyDomains(initDomains);
		}
		
		assignment[varToAssign] = UNASSIGNED;
		
		return null;
	}

	

	//Sorts the domain so that the value that appears least in neighboring domains is first
	private void sortByLCV(List<Integer> domain, int varToAssign, HashMap<Integer, List<Integer>> domains) {
		HashSet<Pair> constraintList;
		
		int[] numAdj = new int[numVal+1]; //+1 b/c Sudoku values are offset by 1
		for (int i = 0; i < numAdj.length; i++)
			numAdj[i] = 0;
		
		for (int i = 0; i < numVar; i++) {
			constraintList = allConstraints.getConstraintList(new Pair(varToAssign, i));
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
			if (assignment[i] == UNASSIGNED)
				return i;
		}
		return -1;
	}


	//Returns the unassigned variable with the minimum remaining values
	private int getMrvVar(int[] assignment, HashMap<Integer, List<Integer>> domains) {
		
		int var = 0;
		int num;
		int min = Integer.MAX_VALUE;
		
		for (int i = 0; i < numVar; i++) {
			if (assignment[i] != UNASSIGNED)
				continue;
			
			List<Integer> vals = domains.get(i);
			num = vals.size();
			if (num < min) {
				min = num;
				var = i;
			}
		}

		return var;
	}
	
	
	//MAC-3 algorithm for constraint propagation
	private boolean runMAC3(int[] assignment, int var, HashMap<Integer, List<Integer>> domains) {
		
		LinkedList<Pair> queue = new LinkedList<Pair>();
		
		//Add arcs to queue that point to var
		for (int i = 0; i < numVar; i++) {
			if (assignment[i] != UNASSIGNED)
				continue;
			Pair arc = new Pair(i, var);
			if (allConstraints.getConstraintList(arc) != null) {
				queue.add(arc);
				//System.out.println(arc);
			}
		}
		
		//Pop all arcs, enforce consistency
		while (!queue.isEmpty()) {
			Pair arc = queue.poll();
			int var1 = arc.getV1();
			int var2 = arc.getV2();
			
			List<Integer> domain1 = domains.get(var1);
			List<Integer> domain2 = domains.get(var2);
			
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
					valsDeleted++;
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
	
	public void printStats() {
		System.out.println("Assignments visited: "+totalAssignments);
		System.out.println("Values deleted from domains: "+valsDeleted);
	}
	

	//Must create a Constraint and assign it to allConstraints
	protected abstract void buildConstraints();	
	protected abstract void printAssignment(int[] assignment);
	
}
