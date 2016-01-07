package main.camera;

import main.common.Ray;
import main.common.Vecteur;

public abstract class ACamera {
	public Vecteur pos;
	protected double width;
	protected double height;
	
	public abstract void getRay(int x, int y, Ray ray);
	public abstract Vecteur getU();
	public abstract Vecteur getV();
	public abstract void init(double width, double height);
}
