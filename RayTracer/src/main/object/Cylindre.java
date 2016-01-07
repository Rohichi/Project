package main.object;

import java.util.Collection;

import main.common.Color;
import main.common.Vecteur;
import main.util.Solver;
import main.util.Util;

public class Cylindre extends AObj{
	public double r;
	
	public Cylindre() {
		r = 1.;
	}
	
	@Override
	public double primitive(Vecteur ori, Vecteur dir, int lastId) {
		ori.transformation(center, rotation);
		dir.transformation(null, rotation);
		double dx = dir.getX();
		double dy = dir.getY();
		double ox = ori.getX();
		double oy = ori.getY();
		Collection<Double> sol = Solver.solve(dx * dx + dy * dy, 2*(ox*dx + oy*dy), ox*ox + oy*oy - r*r);
		if (sol == null || sol.size() < 2)
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
		pos.setY(0);
		pos.reverseRotation(rotation);
		ret.val(pos).checkNormal(dir);
	}

}
