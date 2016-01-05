package main.shader;

import java.util.List;

import main.common.Color;
import main.common.Vecteur;
import main.light.Light;
import main.object.AObj;

public abstract class AShader {
	List<Light> lights;
	public Color bg;
	public Color ambiant;
	
	public AShader(List<Light> lights, Color bg, Color ambiant) {
		this.lights = lights;
		this.bg = bg;
		this.ambiant = ambiant;
	}

	public abstract void getColor(Vecteur ori, Vecteur dir, Vecteur pos, Vecteur normal, AObj current, List<AObj> objects, Color color, Color ret);

	public abstract AShader copy();
	
}
