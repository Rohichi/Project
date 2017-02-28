package fr.rohichi.engine3d.scene;

import java.awt.Color;

import fr.rohichi.engine3d.Intersection;
import fr.rohichi.engine3d.Ray;
import fr.rohichi.engine3d.Vector3D;
import fr.rohichi.engine3d.texture.ATexture;

public class Triangle {
	private static double EPSILON = 0.000001D;
	private static int ID = 0;
	private int id;
	private Vector3D e1;
	private Vector3D e2;
	private Vector3D a;
	private Vector3D b;
	private Vector3D c;
	
	private ATexture texture;
	
	public Triangle(Vector3D a, Vector3D b, Vector3D c) {
		this(a, b, c, null);
	}

	public Triangle(Vector3D a, Vector3D b, Vector3D c, ATexture texture) {
		this.a = a;
		this.b = b;
		this.c = c;
		e1 = new Vector3D(b).sub(a);
		e2 = new Vector3D(c).sub(a);
		id = ID++;
		this.texture = texture;
	}

	public void setTexture(ATexture texture) {
		this.texture = texture;
	}
	
	public int getColor(Intersection inter) {
		if (texture == null) {
			return Color.GREEN.getRGB();
		}
		else {
			return texture.getRGB(inter.getU(), inter.getV());
		}
	}
	
	// Möller–Trumbore intersection algorithm
	public Intersection getIntersection(final Ray ray){
		double dist;
		double u;
		double v;
		double det;
		Vector3D t;
		Vector3D q;
		Vector3D p = new Vector3D(ray.getDir()).crossProduct(e2);
		det = e1.dotProduct(p);
		//Parallel
		if (det > -EPSILON && det < EPSILON)
			return null;
		det = 1.0D / det;
		
		t = new Vector3D(ray.getOri()).sub(a);
		u = t.dotProduct(p) * det;
		//Outdise of triangle
		if (u < 0D || u > 1.0D)
			return null;
		q = new Vector3D(t).crossProduct(e1);
		v = ray.getDir().dotProduct(q) * det;
		//Outdise of triangle
		if (v < 0D || u + v > 1.0D)
			return null;
		dist = e2.dotProduct(q) * det;
		if (dist > EPSILON){
			return new Intersection(dist, u, v, this);
		}
		//TOO CLOSE
		return null;
	}
	
	
	public Vector3D getA() {
		return a;
	}

	public Vector3D getB() {
		return b;
	}

	public Vector3D getC() {
		return c;
	}

	public int getId() {
		return id;
	}
}
