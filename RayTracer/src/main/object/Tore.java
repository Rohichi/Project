package main.object;

import java.util.TreeSet;

import main.common.Color;
import main.common.Vecteur;
import main.util.Solver;

public class Tore extends AObj{

	public double R;
	public double r;
	
	@Override
	public TreeSet<Double> _primitive(Vecteur ori, Vecteur dir) {
		ori.transformation(center, rotation);
		dir.transformation(null, rotation);
		double dx = dir.getX();
		double dy = dir.getY();
		double dz = dir.getZ();
		double ox = ori.getX();
		double oy = ori.getY();
		double oz = ori.getZ();
		TreeSet<Double> sol;
		double d2 = dx*dx + dy*dy + dz*dz;
		double od = 2*ox*dx + 2*oy*dy + 2*oz*dz;
		if (dz == 0. && oz == 0.)
		{
			double c = ox * ox + oy * oy;
			sol = Solver.solve(d2, od, c - Math.pow(R - r, 2));
			sol.addAll(Solver.solve(d2, od, c - Math.pow(R + r, 2)));
		}
		else {
			double or = ox*ox + oy*oy + oz*oz + R*R - r*r;
			double tmpR = 4 * R * R;
			sol = Solver.solve(
					d2 * d2,
					2 * od * d2,
					-tmpR*(dx*dx + dy*dy) + 2 * or * d2 + od * od,	
					-2*tmpR*(ox*dx + oy*dy) + 2 * or * od,
					or * or - tmpR*(ox*ox + oy*oy));
		}
		return (sol);
	}

	@Override
	public void getTextureColor(Vecteur pos, Color ret) {
		pos.transformation(center, rotation);
		
	
		Vecteur w = new Vecteur(pos);
		w.setZ(0);
		boolean out = w.norme() > R;
		double rayonY = w.norme();
		w.normal();
		Vecteur c = new Vecteur(0, 1, 0);
		double y;
		if (w.getX() >= 0) {
			y = Math.acos(c.scal(w));
		}
		else {
			c.setY(-1);
			y = Math.acos(c.scal(w)) + Math.PI;
		}
		c.val(pos);
		c.setZ(0);
		c.mult(R / c.norme());
		double z = pos.getZ();
		pos.sub(c);	
		double x = Math.acos(c.scal(pos)/(R * r));
		if (z > 0.)
			x = 2 * Math.PI - x;
		else if (z == 0)
			x = (out ? 0 : Math.PI);
		if (texture.getColor(x * r, 2 * r * Math.PI, y * rayonY, 2 * Math.PI * rayonY, ret) == -1)
			ret.val(color);	
	}

	@Override
	public Vecteur normal(Vecteur pos, Vecteur dir, int id) {	
		pos.transformation(center, rotation);
		Vecteur c = new Vecteur(pos);
		c.setZ(0);
		c.mult(R / c.norme());
		pos.sub(c);
		pos.reverseRotation(rotation);
		Vecteur ret = new Vecteur(pos);
		return ret.checkNormal(dir);
	}

	@Override
	public void _normal(Vecteur pos, Vecteur dir, int id, Vecteur ret) {
		// TODO Auto-generated method stub
		
	}

}
