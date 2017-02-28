package main.photonmapping;

public class Neighbor implements Comparable<Neighbor>{
	private Photon p;
	private double dist;

	public Neighbor(Photon p, double dist) {
		this.p = p;
		this.dist = dist;
	}
	
	public Photon getP() {
		return p;
	}
	
	public double getDist() {
		return dist;
	}

	@Override
	public int compareTo(Neighbor o) {
		double tmp = o.dist - this.dist;
		if (tmp == 0)
			return (0);
		return (tmp < 0 ? -1 : 1);
	}	
}
