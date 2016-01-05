package main.util;

import java.util.Comparator;

public class ImpactComparator implements Comparator<Impact>{
	public static ImpactComparator instance = new ImpactComparator();
	
	@Override
	public int compare(Impact o1, Impact o2) {
		return Double.compare(o1.dist, o2.dist);
	}
}
