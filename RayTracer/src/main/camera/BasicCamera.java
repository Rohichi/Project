package main.camera;

import main.common.Ray;
import main.common.Vecteur;

public class BasicCamera extends ACamera {
	public Vecteur u;
	public Vecteur v;
	public Vecteur ori;
	
	public BasicCamera() {
		this(new Vecteur(0, 0, 0), new Vecteur(1, 0, 0), new Vecteur(0, 0, 1), 20, 60, 800, 600);
	}
	
	public BasicCamera(Vecteur pos, Vecteur dir, Vecteur up, double dist, double fovy, double width, double height) {
		double h,l;
		dir.normal();
		up.normal();
		h = 2. * dist * Math.tan(Math.PI * fovy / 360);
		l = h * (width / height);
		
		this.pos = pos;
		u = dir.crossmultClone(up).normal().scal(l / width);
		v = dir.crossmultClone(u).normal().scal(h / height);
		ori = dir.scal(dist).add(pos);
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
	
}
