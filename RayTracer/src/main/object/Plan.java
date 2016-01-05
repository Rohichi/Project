package main.object;

import main.common.Color;
import main.common.Vecteur;
import main.util.Solver;

public class Plan extends AObj{

	public Plan(Vecteur center, Color c){
		this(center, c, 1, 128);
	}
	
	public Plan(Vecteur center, Color c, double metal, double rugosite){
		this(center, c, metal, rugosite, new Vecteur(0, 0, 0));
	}
	
	public Plan(Vecteur center, Color c, double metal, double rugosite, Vecteur rot) {
		super(center, c, metal, rugosite, rot);
	}

	@Override
	public double primitive(Vecteur ori, Vecteur dir, int lastId, Id id) {
		if (lastId == this.id)
			return -1;
		ori.transformation(center, rot);
		dir.transformation(null, rot);
		id.id = this.id;
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
		pos.transformation(center, rot);
		int i = getInd(pos.getY(), texture.getSizeY(), texture.getRepeatY(), texture.getHeight());
		int j = getInd(pos.getX(), texture.getSizeX(), texture.getRepeatX(), texture.getWidth());
		if (j < 0 || j >= texture.getWidth() || i < 0 || i >= texture.getHeight())
			c.val(color);
		else
			texture.getColor(i, j, c);
	}

	@Override
	public void normal(Vecteur pos, Vecteur dir, int id, Vecteur ret) {
		ret.val(0,0,1).reverseRotation(rot).checkNormal(dir);
	}

}
