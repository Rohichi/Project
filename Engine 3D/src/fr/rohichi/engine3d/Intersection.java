package fr.rohichi.engine3d;

import fr.rohichi.engine3d.scene.Triangle;

public class Intersection {
	private Triangle triangle;
	private double dist;
	private double u;
	private double v;
	
	public Intersection() {
		dist = Double.MAX_VALUE;
	}

	public Intersection(double dist, double u, double v, Triangle triangle) {
		this.dist = dist;
		this.u = u;
		this.v = v;
		this.triangle = triangle;
	}

	public Triangle getTriangle() {
		return triangle;
	}

	public void setTriangle(Triangle triangle) {
		this.triangle = triangle;
	}

	public double getDist() {
		return dist;
	}

	public void setDist(double dist) {
		this.dist = dist;
	}

	public double getU() {
		return u;
	}

	public void setU(double u) {
		this.u = u;
	}

	public double getV() {
		return v;
	}

	public void setV(double v) {
		this.v = v;
	}

}
