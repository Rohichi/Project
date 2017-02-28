package fr.rohichi.engine3d.texture;

import java.awt.Color;

public class ColorTexture extends ATexture {
	int rgb;
	
	public ColorTexture(Color color) {
		this.rgb = color.getRGB();
	}
	
	@Override
	public int getRGB(double u, double v) {
		return rgb;
	}

}
