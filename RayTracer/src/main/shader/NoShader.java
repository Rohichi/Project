package main.shader;

import java.util.List;

import main.common.Color;
import main.common.Vecteur;
import main.object.AObj;

public class NoShader extends AShader{
	
	@Override
	public void getColor(Vecteur ori, Vecteur dir, Vecteur pos, Vecteur normal, AObj current, List<AObj> objects,
			Color color, Color ret) {
		ret.val(color);
	}

	@Override
	public AShader copy() {
		return this;
	}

}
