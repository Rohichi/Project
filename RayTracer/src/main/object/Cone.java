package main.object;

import java.util.Collection;

import main.common.Color;
import main.common.Vecteur;
import main.util.Solver;
import main.util.Util;

public class Cone extends AObj{
	public double alpha;
	
	public Cone(Vecteur center, double alpha, Color c){
		this(center, alpha, c, 1, 128);
	}
	
	public Cone(Vecteur center, double alpha, Color c, double metal, double rugosite){
		this(center, alpha, c, metal, rugosite, new Vecteur(0, 0, 0));
	}

	public Cone(Vecteur center, double alpha, Color c, double metal, double rugosite, Vecteur rot) {
		super(center, c, metal, rugosite, rot);
		this.alpha = alpha * Math.PI / 180.;
	}

	@Override
	public double primitive(Vecteur ori, Vecteur dir, int lastId, Id id) {
		ori.transformation(center, rot);
		dir.transformation(null, rot);
		double t = Math.pow(Math.tan(alpha), 2);
		id.id = this.id;
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
		pos.transformation(center, rot);
		
	}

	@Override
	public void normal(Vecteur pos, Vecteur dir, int id, Vecteur ret) {
		pos.transformation(center, rot);
		pos.setZ(-pos.getZ() * Math.pow(Math.tan(alpha), 2));
		pos.reverseRotation(rot);
		ret.val(pos).checkNormal(dir);
	}
}
