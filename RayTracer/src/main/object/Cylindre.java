package main.object;

import java.util.TreeSet;

import main.common.Color;
import main.common.Vecteur;
import main.util.Solver;

public class Cylindre extends AObj{
	public double rayon;
	
	public Cylindre() {
		rayon = 1.;
	}
	
	@Override
	public TreeSet<Double> _primitive(Vecteur ori, Vecteur dir) {
		ori.transformation(center, rotation);
		dir.transformation(null, rotation);
		double dx = dir.getX();
		double dy = dir.getY();
		double ox = ori.getX();
		double oy = ori.getY();
		return Solver.solve(dx * dx + dy * dy, 2*(ox*dx + oy*dy), ox*ox + oy*oy - rayon*rayon);
	}

	@Override
	public void getTextureColor(Vecteur pos, Color ret) {
		pos.transformation(center, rotation);
		double x;
		double y = pos.getZ();
		Vecteur c = new Vecteur(0, 1, 0);
		pos.setZ(0);
		pos.normal();
		
		if (pos.getX() >= 0) {
			x = Math.acos(c.scal(pos));
		}
		else {
			c.setY(-1);
			x = Math.acos(c.scal(pos)) + Math.PI;
		}
		if (texture.getColor(rayon * x, 2 * rayon * Math.PI, y, -1, ret) == -1)
			ret.val(color);
	}

	@Override
	public void _normal(Vecteur pos, Vecteur dir, int id, Vecteur ret) {
		pos.transformation(center, rotation);
		pos.setZ(0);
		pos.reverseRotation(rotation);
		ret.val(pos).checkNormal(dir);
	}

}
