package main.common;

public class Vecteur {

	public double x;
	public double y;
	public double z;
	
	public Vecteur() {
		x = 0;
		y = 0;
		z = 0;
	}	

	public Vecteur(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vecteur(Vecteur v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
	public Vecteur(String x, String y, String z) {
		this.x = Double.parseDouble(x);
		this.y = Double.parseDouble(y);
		this.z = Double.parseDouble(z);
	}

	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}
	
	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}
	
	@Override
	protected Vecteur clone() {
		return new Vecteur(this);
	}
	
	public Vecteur val(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	public Vecteur val(Vecteur v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
		return this;
	}
	
	public Vecteur add(Vecteur v) {
		x += v.x;
		y += v.y;
		z += v.z;
		return this;
	}
	
	public Vecteur addClone(Vecteur v) {
		return new Vecteur(x + v.x, y + v.y, z + v.z);
	}
	
	public Vecteur sub(Vecteur v) {
		x -= v.x;
		y -= v.y;
		z -= v.z;
		return this;
	}
	
	public Vecteur subClone(Vecteur v) {
		return new Vecteur(x - v.x, y - v.y, z - v.z);
	}
	
	public Vecteur prod(Vecteur v) {
		x *= v.x;
		y *= v.y;
		z *= v.z;
		return this;
	}
	
	public Vecteur prodClone(Vecteur v) {
		return new Vecteur(x * v.x, y * v.y, z * v.z);
	}	
	
	public Vecteur mult(double k) {
		x *= k;
		y *= k;
		z *= k;
		return this;
	}
	
	public Vecteur multClone(double k) {
		return new Vecteur(x * k, y * k, z * k);
	}	
	
	public Vecteur crossmult(Vecteur v) {
		double tmpx = y * v.z - z * v.y;
		double tmpy = z * v.x - x * v.z; 
		z = x * v.y - y * v.x;
		x = tmpx;
		y = tmpy; 
		return this;
	}
	
	public Vecteur crossmultClone(Vecteur v) {
		return new Vecteur(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
	}	
	
	public double scal(Vecteur v) {
		return x * v.x + y * v.y + z * v.z;
	}
	
	public double dist(Vecteur v){
		double a = v.x - x;
		double b = v.y - y;
		double c = v.z - z;
		return Math.sqrt(a*a + b*b + c*c);
	}
	
	public double norme() {
		return Math.sqrt(scal(this));
	}
	
	public Vecteur normal() {
		return mult(1.0 / norme());
	}
	
	public Vecteur normalClone() {
		return multClone(1.0 / norme());
	}
	
	public Vecteur rotx(double a) {
		double c = Math.cos(a);
		double s = Math.sin(a);
		double tmp = y * c + z * s;
		z = c * z - s * y;
		y = tmp;
		return this;
	}
	
	public Vecteur roty(double a) {
		double c = Math.cos(a);
		double s = Math.sin(a);
		double tmp = x * c + z * s;
		z = c*z - s*x;
		x = tmp;
		return this;
	}
	public Vecteur rotz(double a) {
		double	c = Math.cos(a);
		double	s = Math.sin(a);
		double	tmp = x * c + y * s;
		y = c * y - s * x;
		x = tmp;
		return this;
	}
	
	public Vecteur rotation(Vecteur v) {
		rotx(v.x);
		roty(v.y);
		rotz(v.z);
		return this;
	}
	
	public Vecteur reverseRotation(Vecteur v){
		rotx(-v.x);
		roty(-v.y);
		rotz(-v.z);
		return this;		
	}
	
	public Vecteur transformation(Vecteur trans, Vecteur rot) {
		if (trans != null)
			sub(trans);
		if (rot != null)
			rotation(rot);
		return this;
	}
	
	public String toStr(){
		return "("+x+", "+y+", "+z+")";
	}
	
	public void print(){
		System.out.println(toStr());
	}

	public Vecteur checkNormal(Vecteur ori) {
		double tmp = scal(ori);
		if (tmp > 0.)
			return mult(-1).normal();
		return normal();
	}
}
