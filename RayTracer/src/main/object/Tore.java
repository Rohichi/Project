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
			sol = Solver.solve(
					d2 * d2,
					2 * od * d2,
					-4*R*R*(dx*dx + dy*dy) + 2 * or * d2 + od * od,	
					-8*R*R*(ox*dx + oy*dy) + 2 * or * od,
					or * or - 4*R*R*(ox*ox + oy*oy));
		}
		if (sol == null)
			return (-1);
		return Util.near(sol, lastId == this.id);
	}

	@Override
	public void getTextureColor(Vecteur pos, Color c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void normal(Vecteur pos, Vecteur dir, int id, Vecteur ret) {	
		pos.transformation(center, rotation);
		Vecteur c = new Vecteur(pos);
		c.setZ(0);
		c.scal(R / c.norme());
		pos.sub(c);
		pos.reverseRotation(rotation);
		ret.val(pos).checkNormal(dir);
	}

}
