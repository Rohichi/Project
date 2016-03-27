package main.shader;

import java.util.List;

import main.common.Color;
import main.common.Vecteur;
import main.light.Light;
import main.object.AObj;
import main.util.Util;

public class BlinnPhongShader extends AShader {
	private Vecteur vLight;
	private Vecteur h;
	private Vecteur vTmp;
	private Color cTmp;
	private Color tmpDiffuse;
	private Color tmpSpeculaire;
	
	private Color coefAmbiant;
	private Color coefDiffuse;
	private Color coefSpeculaire;
	
	public Color ambiant;
	
	public BlinnPhongShader() {
		ambiant = null;
	}

	public void init(List<Light> lights) {
		super.init(lights);
		
		ambiant = (ambiant == null ? Color.getCoef(0.2, 0.2, 0.2) : ambiant);

		vLight = new Vecteur();
		h = new Vecteur();
		vTmp = new Vecteur();
		
		coefAmbiant = new Color();
		coefDiffuse = new Color();
		coefSpeculaire = new Color();
		tmpDiffuse = new Color();
		tmpSpeculaire = new Color();
		cTmp = new Color();
	}
	
	public void diffuse(Color k, Vecteur normal, Color ret){
		ret.val(k).mult(normal.scal(vLight));
	}
	
	public void speculaire(Color k, Vecteur normal, double rugosite, Color ret){
		ret.val(k).mult(Math.pow(normal.scal(h), rugosite));
	}
	
	@Override
	public void getColor(Vecteur ori, Vecteur viewer, Vecteur intersection, Vecteur normal, AObj current, List<AObj> objects, Color color, Color ret) {
		double dist;
		double atte;
		ret.val(color);
		coefAmbiant.val(ambiant).mult(current.coefAmbiant);
		coefDiffuse.val(0,0,0);
		coefSpeculaire.val(0,0,0);
		for (Light light : lights) {
			dist = vLight.val(light.pos).sub(intersection).norme();
			vLight.normal();
			if (Util.checkVisibility(current.getId(), objects, vLight, dist, intersection, normal, vTmp, h)) {
				atte = Util.attenuation(dist, light.attenuation);		
				diffuse(light.diffuse, normal, tmpDiffuse);
				tmpDiffuse.mult(light.color);
				tmpDiffuse.mult(atte);
				coefDiffuse.safeAdd(tmpDiffuse);
				h.val(vLight).add(viewer).normal();
				speculaire(light.speculaire, normal, current.rugosite, tmpSpeculaire);
				tmpSpeculaire.mult(light.color);
				tmpSpeculaire.mult(atte);
				coefSpeculaire.safeAdd(tmpSpeculaire);
			}
			else {
			}
		}
		cTmp.val(ret).mult(current.metal).add(1 - current.metal);
		coefSpeculaire.mult(current.metal).mult(current.coefSpeculaire);
		coefDiffuse.mult(current.coefDiffuse);
		ret.mult(coefAmbiant.add(coefDiffuse));
		ret.add(coefSpeculaire);
	}

	@Override
	public AShader copy() {
		BlinnPhongShader ret = new BlinnPhongShader();
		ret.ambiant = ambiant;
		ret.bg = bg;
		ret.init(lights);
		return ret;
	}

}
