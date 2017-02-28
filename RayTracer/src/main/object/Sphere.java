package main.object;

import java.util.TreeSet;

import main.common.Color;
import main.common.Vecteur;
import main.util.Solver;

public class Sphere extends AObj{
	public double rayon;
	
	public Sphere() {
		super();
		rayon = 1;
	}

	@Override
	public TreeSet<Double> _primitive(Vecteur ori, Vecteur dir) {
		ori.sub(center);
		return Solver.solve(dir.scal(dir), 2 * ori.scal(dir), ori.scal(ori) - rayon*rayon);
	}


	@Override
	public void getTextureColor(Vecteur pos, Color ret) {
		pos.transformation(center, rotation);
		double alpha; 
		pos.normal();
		if (pos.getZ() > 0)
			alpha = Math.acos(-pos.getZ());
		else
			alpha = (Math.asin(pos.getZ()) + Math.PI / 2);
		double y = alpha * rayon;
		double r2 = rayon * Math.sin(alpha);
			
		Vecteur c = new Vecteur(0, 1 , 0);
		pos.setZ(0);
		pos.normal();
		
		double x;
		if (pos.getX() >= 0) {
			x = Math.acos(c.scal(pos));
		}
		else {
			c.setY(-1);
			x = Math.acos(c.scal(pos)) + Math.PI;
		}
			
		if (texture.getColor(x * r2, 2* r2 * Math.PI, y, Math.PI * rayon, ret) == -1)
			ret.val(color);
	}

	@Override
	public void _normal(Vecteur pos, Vecteur dir, int id, Vecteur ret) {
		pos.transformation(center, rotation);
		pos.reverseRotation(rotation);
		ret.val(pos).checkNormal(dir);
	}

}




