package main.object;

import java.io.IOException;
import java.util.TreeSet;

import main.common.Color;
import main.common.Vecteur;
import main.util.Solver;

public class Cone extends AObj{
	public double alpha;
	
	public Cone() {
		alpha = 45;
	}
	
	@Override
	public void init() throws NumberFormatException, IOException {
		super.init();
		alpha = alpha * Math.PI / 180.;
	}
	
	@Override
	public TreeSet<Double> _primitive(Vecteur ori, Vecteur dir) {
		ori.transformation(center, rotation);
		dir.transformation(null, rotation);
		double t = Math.pow(Math.tan(alpha), 2);
		double dx = dir.getX();
		double dy = dir.getY();
		double dz = dir.getZ();
		double ox = ori.getX();
		double oy = ori.getY();
		double oz = ori.getZ();
		return Solver.solve(dx * dx + dy * dy - dz * dz * t,
											  2*(ox * dx + oy * dy - oz * dz * t),
											  ox*ox + oy*oy - oz * oz * t);
		/*if (sol.size() < 2)
			return (-1);*/
	}

	@Override
	public void getTextureColor(Vecteur pos, Color ret) {
		pos.transformation(center, rotation);
		double x;
		double y = pos.norme();
		Vecteur c = new Vecteur(0, 1, 0);
		pos.setZ(0);
		double rayon = pos.norme();
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
		pos.setZ(-pos.getZ() * Math.pow(Math.tan(alpha), 2));
		pos.reverseRotation(rotation);
		ret.val(pos);
	}
}
