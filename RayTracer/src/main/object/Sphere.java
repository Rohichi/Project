package main.object;

import java.util.Collection;

import main.common.Color;
import main.common.Vecteur;
import main.util.Solver;
import main.util.Util;

public class Sphere extends AObj{
	public double r;
	
	public Sphere(Vecteur center, double r, Color c){
		this(center, r, c, 1, 128);
	}
	
	public Sphere(Vecteur center, double r, Color c, double metal, double rugosite){
		this(center, r, c, metal, rugosite, new Vecteur(0, 0, 0));
	}

	public Sphere(Vecteur center, double r, Color c, double metal, double rugosite, Vecteur rot) {
		super(center, c, metal, rugosite, rot);
		this.r = r;
	}

	@Override
	public double primitive(Vecteur ori, Vecteur dir, int lastId) {
		ori.sub(center);
		Collection<Double> sol = Solver.solve(dir.mult(dir), 2 * ori.mult(dir), ori.mult(ori) - r*r);
		if (sol.size() < 2)
			return -1;
		return Util.near(sol, lastId == this.id);
	}

	private int getI(Vecteur pos) {
		int i;
		pos.normal();
		double z = 0.5 + (Math.asin(pos.getZ()) / Math.PI);
		if (texture.getExtendY()) {
			double tmp = 1. / (double)texture.getRepeatY();
			i = (int)((z - (double)((int)(z / tmp)) * tmp) * (double)texture.getHeight() / tmp);
			i = (i == texture.getHeight() ? 0 : i);
		}
		else {
			z *= Math.PI * r;
			double tmp = (double)((int)(z / texture.getSizeY()));
			if ((int)tmp > texture.getRepeatY())
				return (-1);
			i = (int)((z - (tmp * texture.getSizeY())) * (double)texture.getHeight() / texture.getSizeY());
		}
		return i;	
	}
	
	private int getJ(Vecteur pos)
	{
		int		j;
		pos.setZ(0);
		pos.normal();
		double u = (Math.asin(pos.getY()) > 0. ? Math.acos(pos.getX()) : 2. * Math.PI - Math.acos(pos.getX()));
		if (texture.getExtendX()) {
			double tmp = 2. * Math.PI / (double)texture.getRepeatX();
			j = (int)((u - (double)((int)(u / tmp)) * tmp)
					* (double)texture.getWidth() / tmp);
			j = (j == texture.getWidth() ? 0 : j);
		}
		else {
			u *= r;
			double tmp = (double)((int)(u / texture.getSizeX()));
			if ((int)tmp > texture.getRepeatX())
				return (-1);
			j = (int)((u - (tmp * texture.getSizeX())) * (double)texture.getWidth() / texture.getSizeX());
		}
		return (j);
	}
	
	@Override
	public void getTextureColor(Vecteur pos, Color c) {
		pos.transformation(center, rot);
		Vecteur tmp = new Vecteur(pos);
		int i = getI(tmp);
		int j = getJ(pos);
		if (j < 0 || j >= texture.getWidth() || i < 0 || i >= texture.getHeight())
			c.val(color);
		else
			texture.getColor(i, j, c);
	}

	@Override
	public void normal(Vecteur pos, Vecteur dir, int id, Vecteur ret) {
		pos.transformation(center, rot);
		pos.reverseRotation(rot);
		
		ret.val(pos).checkNormal(dir);
	}

}
