package csp;

public class Pair {

	public int v1, v2;
		
	public Pair(int x, int y) {
		v1 = x;
		v2 = y;
	}
	
	public int getV1() { return v1; }
	public int getV2() { return v2; }
	
	@Override
	public boolean equals(Object obj){
		if (obj == null)
			return false;
		
		Pair other = (Pair) obj;
		if (v1 != other.getV1() || v2 != other.getV2())
			return false;
		
		return true;
	}
	
	@Override
	public int hashCode() {
	    return 1000*v1 + v2;
	}
	
	public String toString() { return "("+v1+","+v2+")"; }
}
