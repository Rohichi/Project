package fr.rohichi.engine3d;

public class Vector3D {
	private double x;
	private double y;
	private double z;
	
	public Vector3D() {
		this.x = 0.0D;
		this.y = 0.0D;
		this.z = 0.0D;
	}
	
	public Vector3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3D(Vector3D v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
	public Vector3D(String x, String y, String z) {
		this.x = Double.parseDouble(x);
		this.y = Double.parseDouble(y);
		this.z = Double.parseDouble(z);
	}

	public Vector3D add(Vector3D v) {
		this.x += v.x;
		this.y += v.y;
		this.z += v.z;		
		return this;
	}

	public Vector3D add(double x, double y, double z) {
		this.x += x;
		this.y += y;
		this.z += z;		
		return this;
	}
	
	public Vector3D add(double coef, Vector3D v) {
		this.x += (coef * v.x);
		this.y += (coef * v.y);
		this.z += (coef * v.z);		
		return this;
	}

	
	public Vector3D add(double c) {
		this.x += c;
		this.y += c;
		this.z += c;		
		return this;
	}
	
	public Vector3D sub(Vector3D v) {
		this.x -= v.x;
		this.y -= v.y;
		this.z -= v.z;		
		return this;
	}

	public Vector3D sub(double c) {
		this.x -= c;
		this.y -= c;
		this.z -= c;		
		return this;
	}
	
	public Vector3D scalarMultiplication(double c) {
		this.x *= c;
		this.y *= c;
		this.z *= c;
		return this;
	}
		
	public double dotProduct(Vector3D v) {
		return this.x * v.x + this.y * v.y + this.z * v.z;
	}
	
	public Vector3D crossProduct(Vector3D v) {
		return new Vector3D(
				y * v.z - z * v.y,
				z * v.x - x * v.z,
				x * v.y - y * v.x);
	}

	public double length() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	public Vector3D normal() {
		return this.scalarMultiplication(1.0D / length());
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
	
	@Override
	public String toString() {
		return "(" + x +", " + y + ", "+ z + ")";
	}
}
