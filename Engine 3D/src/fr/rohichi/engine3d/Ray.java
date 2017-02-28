package fr.rohichi.engine3d;

public class Ray {
	final private Vector3D ori;
	final private Vector3D dir;

	public Ray(Vector3D ori, Vector3D dir) {
		this.ori = ori;
		this.dir = dir;
	}

	public Vector3D getOri() {
		return ori;
	}

	public Vector3D getDir() {
		return dir;
	}
}
