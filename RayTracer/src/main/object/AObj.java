package main.object;

import main.common.Color;
import main.common.Env;
import main.common.Vecteur;
import main.texture.ATexture;

public abstract class AObj {
	static int nbid = 0;
	public int id;
	public Vecteur center;
	public Vecteur rot;
	public ATexture texture;
	public Color color;
	
	public Color coefAmbiant;
	public Color coefSpeculaire;
	public Color coefDiffuse;
	
	public double metal;
	public double rugosite;
	
	public double direct;
	public double mirroir;
	public double refraction;
	public double indRefra;
	
	public AObj(Vecteur center, Color color, double metal, double rugosite, Vecteur rot){
		this();
		this.center = center;
		this.color = color;
		this.metal = metal;
		this.rugosite = rugosite;
		this.rot = rot.scal(Math.PI / 180.);
	}
	
	public AObj(){
		indRefra = Env.refra;
		coefAmbiant = Color.getCoef(1, 1, 1);
		coefDiffuse = Color.getCoef(1, 1, 1);
		coefSpeculaire = Color.getCoef(1, 1, 1);
		texture = null;
		direct = 1;
		mirroir = 0;
		refraction = 0;
		indRefra = Env.refra;
		id = nbid++;
		rot = null;
		rugosite = 1;
		metal = 0;
	}
	
	public void color(Vecteur pos, Color c) {
		if (texture == null)
			c.val(color);
		else
			getTextureColor(pos, c);
	}
	

	public abstract double primitive(Vecteur ori, Vecteur dir, int lastId);
	public abstract void getTextureColor(Vecteur pos, Color c);
	public abstract void normal(Vecteur pos, Vecteur dir, int id, Vecteur ret);
}
