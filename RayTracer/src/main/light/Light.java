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
		this(new Vecteur(0, 0, 0), new Color(255, 255, 255));
	}
	
	public Light(Vecteur pos, Color c){
		this(pos, c, new Vecteur(1,0,0), Color.getCoef(1, 1, 1), Color.getCoef(1, 1, 1));
	}
	
	public Light(Vecteur pos, Color c, Vecteur attenuation){
		this(pos, c, attenuation, Color.getCoef(1, 1, 1), Color.getCoef(1, 1, 1));
	}
	
	public Light(Vecteur pos, Color c, Vecteur attenuation, Color diffuse, Color speculaire){
		this.pos = pos;
		this.color = c;
		this.attenuation = attenuation;
		this.diffuse = diffuse;
		this.speculaire = speculaire;
	}
}
