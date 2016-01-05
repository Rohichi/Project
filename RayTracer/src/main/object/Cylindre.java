package main.object;

import java.util.Collection;

import main.common.Color;
import main.common.Vecteur;
import main.util.Solver;
import main.util.Util;

public class Cylindre extends AObj{
	public double r;
	
	public Cylindre(Vecteur center, double r, Color c){
		this(center, r, c, 1, 128);
	}
	
	public Cylindre(Vecteur center, double r, Color c, double metal, double rugosite){
		this(center, r, c, metal, rugosite, new Vecteur(0, 0, 0));
	}
	
	public Cylindre(Vecteur center, double r, Color c, double metal, double rugosite, Vecteur rot) {
		super(center, c, metal, rugosite, rot);
		this.r = r;
	}
	
	@Override
	public double primitive(Vecteur ori, Vecteur dir, int lastId, Id id) {
		ori.transformation(center, rot);
		dir.transformation(null, rot);
		id.id = this.id;
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
		pos.transformation(center, rot);
		pos.setY(0);
		pos.reverseRotation(rot);
		ret.val(pos).checkNormal(dir);
	}

}
