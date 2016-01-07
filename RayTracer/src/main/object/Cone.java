package main.object;

import java.io.IOException;
import java.util.Collection;

import main.common.Color;
import main.common.Vecteur;
import main.util.Solver;
import main.util.Util;

public class Cone extends AObj{
	public double alpha;
	
	public Cone() {
		alpha = 45;
	}
	
	public void init() throws NumberFormatException, IOException {
		super.init();
		alpha = alpha * Math.PI / 180.;
	}
	
	@Override
	public double primitive(Vecteur ori, Vecteur dir, int lastId) {
		ori.transformation(center, rotation);
		dir.transformation(null, rotation);
		double t = Math.pow(Math.tan(alpha), 2);
		double dx = dir.getX();
		double dy = dir.getY();
		double dz = dir.getZ();
		double ox = ori.getX();
		double oy = ori.getY();
		double oz = ori.getZ();
		Collection<Double> sol = Solver.solve(dx * dx + dy * dy - dz * dz * t,
											  2*(ox * dx + oy * dy - oz * dz * t),
											  ox*ox + oy*oy - oz * oz * t);
		if (sol == null || sol.size() < 2)
			return (-1);
		return Util.near(sol, lastId == this.id);
	}

	@Override
	public void getTextureColor(Vecteur pos, Color c) {
		pos.transformation(center, rotation);
		
	}

	@Override
	public void normal(Vecteur pos, Vecteur dir, int id, Vecteur ret) {
		pos.transformation(center, rotation);
		pos.setZ(-pos.getZ() * Math.pow(Math.tan(alpha), 2));
		pos.reverseRotation(rotation);
		ret.val(pos).checkNormal(dir);
	}
}
