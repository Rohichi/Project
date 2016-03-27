package main.object;

import main.common.Color;
import main.common.Vecteur;
import main.util.Solver;

public class Plan extends AObj{

	@Override
	public double primitive(Vecteur ori, Vecteur dir, int lastId) {
		if (lastId == this.id)
			return -1;
		ori.transformation(center, rotation);
		dir.transformation(null, rotation);
		return Solver.solve(dir.getZ(), ori.getZ());	
	}

	@Override
	public void getTextureColor(Vecteur pos, Color c) {
		pos.transformation(center, rotation);
		if (texture.getColor(pos.getX(), -1, pos.getY(), -1, c) == -1)
			c.val(color);
	}

	@Override
	public void normal(Vecteur pos, Vecteur dir, int id, Vecteur ret) {
		ret.val(0,0,1).reverseRotation(rotation).checkNormal(dir);
	}

}
