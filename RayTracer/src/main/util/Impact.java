package main.util;

public class Impact {
	public double dist;
	public int id;
	
	public Impact(double dist) {
		this.dist = dist;
		id = -1;
	}
	
	public Impact(double dist, int id) {
		this.dist = dist;
		this.id = id;
	}
}
