package main.camera;

import main.common.Ray;
import main.common.Vecteur;

public class BasicCamera extends ACamera {
	public Vecteur dir;
	public Vecteur up;
	public double distance;
	public double fovy;
	
	protected Vecteur u;
	protected Vecteur v;
	protected Vecteur ori;
	
	public BasicCamera() {
		pos = null;
		dir = null;
		distance = 20;
		fovy = 60;
	}
	
	public void init(double width, double height) {
		pos = (pos == null ? new Vecteur(0, 0, 0) : pos);
		dir = (dir == null ? new Vecteur(1, 0, 0) : dir);
		up = (up == null ? new Vecteur(0, 0, 1) : up);
		this.width = width;
		this.height = height;		
		double h,l;
		dir.normal();
		up.normal();
		h = 2. * distance * Math.tan(Math.PI * fovy / 360);
		l = h * (width / height);
		u = dir.crossmultClone(up).normal().scal(l / width);
		v = dir.crossmultClone(u).normal().scal(h / height);
		ori = dir.scal(distance).add(pos);
		ori.add(u.scalClone(-width / 2.));
		ori.add(v.scalClone(-height / 2.));
	}
	
		
	@Override
	public void getRay(int x, int y, Ray ray) {
		ray.ori.val(v).scal(x).add(ray.dir.val(u).scal(y)).add(ori);
		ray.dir.val(ray.ori).sub(pos).normal();
	}

	@Override
	public Vecteur getU() {
		return u;
	}

	@Override
	public Vecteur getV() {
		return v;
	}

	public void setOri(Vecteur ori) {
		this.ori = ori;
	}

	@Override
	public void setU(Vecteur u) {
		this.u = u;
	}

	@Override
	public void setV(Vecteur v) {
		this.v = v;
	}
	
}
