package main.object;

import java.util.Collection;

import main.common.Color;
import main.common.Vecteur;
import main.util.Solver;
import main.util.Util;

public class Tore extends AObj{

	public double R;
	public double r;
	
	@Override
	public double primitive(Vecteur ori, Vecteur dir, int lastId) {
		ori.transformation(center, rotation);
		dir.transformation(null, rotation);
		double dx = dir.getX();
		double dy = dir.getY();
		double dz = dir.getZ();
		double ox = ori.getX();
		double oy = ori.getY();
		double oz = ori.getZ();
		Collection<Double> sol;
		double d2 = dx*dx + dy*dy + dz*dz;
		double od = 2*ox*dx + 2*oy*dy + 2*oz*dz;
		
		if (dz == 0 && oz == 0)
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
		if (sol == null)
			return (-1);
		return Util.near(sol, lastId == this.id);
	}

	@Override
	public void getTextureColor(Vecteur pos, Color ret) {
		pos.transformation(center, rotation);
		/*double y;
		double x;
		Vecteur w = new Vecteur(pos);
		w.setZ(0);
		double rayon = w.norme();
		Vecteur c = new Vecteur(0, 1, 0);
		w.normal();
		if (w.getX() >= 0) {
			x = Math.acos(c.scal(w));
			
		}
		else {
			c.setY(-1);
			x = Math.acos(c.scal(w)) + Math.PI;
			
		}

		
		if (texture.getColor(x * rayon, 2 * rayon * Math.PI, y, 2 * Math.PI * r, ret) == -1)
		*/
			ret.val(color);
		
	}

	@Override
	public void normal(Vecteur pos, Vecteur dir, int id, Vecteur ret) {	
		pos.transformation(center, rotation);
		Vecteur c = new Vecteur(pos);
		c.setZ(0);
		c.mult(R / c.norme());
		pos.sub(c);
		pos.reverseRotation(rotation);
		ret.val(pos).checkNormal(dir);
	}

}
