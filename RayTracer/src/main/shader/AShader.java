package main.shader;

import java.util.List;

import main.common.Color;
import main.common.Vecteur;
import main.light.Light;
import main.object.AObj;

public abstract class AShader {
	protected List<Light> lights;
	
	public Color bg;
	
	public AShader() {
		bg = null;
	}
	
	public void init(List<Light> lights){
		this.lights = lights;
		bg = (bg == null ? new Color(0,0,0) : bg);
		
	}
	
	public abstract void getColor(Vecteur ori, Vecteur dir, Vecteur pos, Vecteur normal, AObj current, List<AObj> objects, Color color, Color ret);

	public abstract AShader copy();
	
}
