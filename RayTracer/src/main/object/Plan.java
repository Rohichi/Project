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

	private int getInd(double x, double size, int repeat, int lon) {
		double	tmp;

		if (repeat != 0)
		{
			if (x < 0. && repeat == -1)
			{
				tmp = (double)((int)(x / size));
				x = size + (x - (tmp * size));
				if (x == size)
					x = 0.;
			}
			else if (x >= size)
			{
				tmp = (double)((int)(x / size));
				if ((int)tmp < repeat || repeat == -1)
					x = x - (tmp * size);
			}
		}
		return ((int)((x * (double)lon) / size));
	}
	
	@Override
	public void getTextureColor(Vecteur pos, Color c) {
		pos.transformation(center, rotation);
		int i = getInd(pos.getY(), texture.getSizeY(), texture.getRepeatY(), texture.getHeight());
		int j = getInd(pos.getX(), texture.getSizeX(), texture.getRepeatX(), texture.getWidth());
		if (j < 0 || j >= texture.getWidth() || i < 0 || i >= texture.getHeight())
			c.val(color);
		else
			texture.getColor(i, j, c);
	}

	@Override
	public void normal(Vecteur pos, Vecteur dir, int id, Vecteur ret) {
		ret.val(0,0,1).reverseRotation(rotation).checkNormal(dir);
	}

}
