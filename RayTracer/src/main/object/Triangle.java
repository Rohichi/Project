package main.object;

import java.util.TreeSet;

import main.common.Color;
import main.common.Vecteur;

public class Triangle extends AObj{
	public static double EPSILON = 0.000001;
	Vecteur e1;
	Vecteur e2;
	Vecteur an;
	Vecteur bn;
	Vecteur cn;
	
	public Triangle(Vecteur v1, Vecteur v2, Vecteur v3) {
		e1 = new Vecteur(v2).sub(v1);
		e2 = new Vecteur(v3).sub(v1);
		center = new Vecteur(v1);
	}
	
	public void setNormal(Vecteur a, Vecteur b, Vecteur c) {
		an = a;
		bn = b;
		cn = c;
	}
	
	@Override
	public TreeSet<Double> _primitive(Vecteur ori, Vecteur dir) {
		TreeSet<Double> ret = new TreeSet<Double>();
		ret.add(prim(ori, dir));
		return ret;
	}

	private double prim(Vecteur ori, Vecteur dir) {
		Vecteur p = dir.crossmultClone(e2);
		double det = e1.scal(p);
		if (det > -EPSILON && det < EPSILON)
			return -1.;
		det = 1. / det;
		Vecteur t = ori.subClone(center);
		double u = t.scal(p) * det;
		if (u < 0. || u > 1.)
			return -1.;
		Vecteur q = t.crossmultClone(e1);
		double v = dir.scal(q) * det;
		if (v < 0. || u + v > 1.)
			return -1.;
		return e2.scal(q) * det;
	}

	@Override
	public void getTextureColor(Vecteur pos, Color c) {
		
	}

	@Override
	public void _normal(Vecteur pos, Vecteur dir, int id, Vecteur ret) {
		
		Vecteur tmp = new Vecteur();
		pos.transformation(center, null);
		double u = e1.scal(pos);
		double v = e2.scal(pos);
		if (1 - u - v < 0)
			System.out.println(u + " " + v);
		ret.val(an).mult(1. - u - v);
		tmp.val(bn).mult(u);
		ret.add(tmp);
		tmp.val(cn).mult(v);
		ret.add(tmp);
		ret.checkNormal(dir);
		//ret.val(e1).crossmult(e2).checkNormal(dir);
		
		
		
	}
	
}
