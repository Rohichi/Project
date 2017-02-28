package fr.rohichi.engine3d.camera;

import fr.rohichi.engine3d.Ray;
import fr.rohichi.engine3d.Vector3D;

public class Camera {
	protected Vector3D ori;
	protected Vector3D dir;
	protected Vector3D up;
	private double distance;
	private double fovY;
	
	private Vector3D base;
	private Vector3D u;
	private Vector3D v;
	
	public Camera(Vector3D ori, Vector3D dir, Vector3D up) {
		this(ori, dir, up, Math.toRadians(70), 10.0D);
	}
	
	public Camera(Vector3D ori, Vector3D dir, Vector3D up, double fovY, double distance) {
		this.ori = ori;
		this.dir = dir;
		this.up = up;
		this.fovY = fovY;
		this.distance = distance;
	}

	public void init(int width, int height) {
		double h = 2.0D * distance * Math.tan(fovY / 2);
		double l = h * ((double)width / (double)height);
		u = new Vector3D(dir).crossProduct(up).normal().scalarMultiplication(l);
		v = new Vector3D(dir).crossProduct(u).normal().scalarMultiplication(h);
		base = new Vector3D(ori)
					.add(distance, dir)
					.add(-1 / 2.0D, v)
					.add( -1 / 2.0D, u);

		u.scalarMultiplication(1.0D / (double)width);
		v.scalarMultiplication(1.0D / (double)height);	
	}

	public Ray getRay(int x, int y) {
		Vector3D dir = new Vector3D(base).add((double)y, v).add((double)x, u).sub(ori);
		return new Ray(ori, dir);
	}

	public Vector3D getOri() {
		return ori;
	}

	public Vector3D getDir() {
		return dir;
	}

	public Vector3D getUp() {
		return up;
	}

	
	
}
