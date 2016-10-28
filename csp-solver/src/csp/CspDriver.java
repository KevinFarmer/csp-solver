package csp;

import java.util.ArrayList;

public class CspDriver {

	
	public static void main(String[] args) {
		
		ArrayList<String> countries = new ArrayList<String>();
		ArrayList<String> colors = new ArrayList<String>();
		
		countries.add("NSW");
		countries.add("V");
		countries.add("WA");
		
		colors.add("r");
		colors.add("g");
		colors.add("b");

		ConstraintSatisfactionProblem csp = new MapColoringCSP(countries, colors);
		
		
		
		
		
		
		
		
	}
	
}
