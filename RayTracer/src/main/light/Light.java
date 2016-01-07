package main.light;

import main.common.Color;
import main.common.Vecteur;

public class Light {
	public Vecteur pos;
	public Color color;
	public Color diffuse;
	public Color speculaire;
	
	public Vecteur attenuation;
	
	public Light() {
		pos = null;
		color = null;
		diffuse = null;
		speculaire = null;
		attenuation = null;
	}
	
	public void init() {
		pos = (pos == null ? new Vecteur(0, 0, 0) : pos);
		color = (color == null ? new Color(255, 255, 255) : color);
		attenuation = (attenuation == null ? new Vecteur(1, 0, 0) : attenuation);
		diffuse = (diffuse == null ? Color.getCoef(1, 1, 1) : diffuse);
		speculaire = (speculaire == null ? Color.getCoef(1, 1, 1) : speculaire);		
	}
}
