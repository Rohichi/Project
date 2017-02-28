package main.object;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.TreeSet;

import main.common.Color;
import main.common.Ray;
import main.common.Vecteur;
import main.texture.ATexture;
import main.util.Pair;
import main.util.Util;

public abstract class AObj {

	static int nbid = 0;
	
	protected int id;
	public Vecteur center;
	public Vecteur rotation;
	public ATexture texture;
	public Color color;
	
	public Color coefAmbiant;
	public Color coefSpeculaire;
	public Color coefDiffuse;
	
	public double metal;
	public double rugosite;
	
	public double direct;
	public double miroir;
	public double refraction;
	public double indRefra;
	
	public AObj(){
		id = nbid++;
		
		center = null;
		rotation = null;
		texture = null;
		color = null;

		coefAmbiant = null;
		coefSpeculaire = null;
		coefDiffuse = null;
		
		rugosite = 1;
		metal = 0;

		direct = 1;
		miroir = 0;
		refraction = 0;
		indRefra = 1;
	}
	
	public int getId(){
		return id;
	}
	
	public void init() throws FileNotFoundException, NumberFormatException, IOException {
		if (center == null)
			center = new Vecteur(0, 0, 0);
		
		if (rotation == null)
			rotation = new Vecteur(0, 0, 0);
		else 
			rotation.mult(Math.PI / 180.);
		if (color == null)
			color = new Color(255, 255, 255);
		else
			color.mult(1/255.);
		if (coefAmbiant == null)
			coefAmbiant = Color.getCoef(1, 1, 1);
		if (coefDiffuse == null)
			coefDiffuse = Color.getCoef(1, 1, 1);
		if (coefSpeculaire == null)
			coefSpeculaire = Color.getCoef(1, 1, 1);
		direct = 1 - miroir - refraction;
	}
	
	public void color(Vecteur pos, Color c) {
		if (texture == null)
			c.val(color);
		else
			getTextureColor(pos, c);
	}
	
	public static Pair<AObj, Double> getNear(Ray ray, List<AObj> objects, int lastId) {
		Vecteur ori = new Vecteur();
		Vecteur dir = new Vecteur();
		double dist;
		double min = -1;
		AObj near = null;
		for (AObj obj : objects) {
			ori.val(ray.ori);
			dir.val(ray.dir);
			dist = obj.primitive(ori, dir, lastId);
			if (dist <= 0.)
				continue;
			if (near == null || min > dist) {
				min = dist;
				near = obj;
			}
		}
		return new Pair<AObj, Double>(near, min);
	}
	
	public double primitive(Vecteur ori, Vecteur dir, int lastId) {
		TreeSet<Double> ret = this._primitive(ori, dir);
		return Util.near(ret, lastId == this.id);
	}
	
	public Vecteur normal(Vecteur pos, Vecteur dir, int id) {
		Vecteur ret = new Vecteur();
		this._normal(pos, dir, id, ret);
		return ret.checkNormal(dir);
	}
	
	
	public abstract TreeSet<Double> _primitive(Vecteur ori, Vecteur dir);
	public abstract void getTextureColor(Vecteur pos, Color ret);
	public abstract void _normal(Vecteur pos, Vecteur dir, int id, Vecteur ret);
}
