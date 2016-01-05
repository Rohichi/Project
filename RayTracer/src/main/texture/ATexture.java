package main.texture;

import main.common.Color;

public abstract class ATexture {
	public final static int SPHERE = 0;
	public final static int AFFICHE = 1;
	public final static int PLAN = 2;
	public final static int CONE = 3;
	public final static int CYLINDRE = 4;
	
	public abstract void getColor(int x, int y, Color c);
	public abstract double getSizeX();
	public abstract double getSizeY();
	public abstract int getHeight();
	public abstract int getWidth();
	public abstract int getRepeatX();
	public abstract int getRepeatY();
	public abstract boolean getExtendX();
	public abstract boolean getExtendY();
}
