package fr.rohichi.engine3d;

import java.awt.Color;
import java.awt.image.BufferedImage;

import fr.rohichi.engine3d.camera.Camera;
import fr.rohichi.engine3d.scene.Scene;

public class Engine3D {
	private Scene scene;
	
	public Engine3D(Scene scene) {
		this.scene = scene;
	}
	
	public BufferedImage getImage(Camera cam, int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		cam.init(width, height);		
		for(int x = 0; x < width ; x++) {
			for(int y = 0; y < height; y++) {
				Ray ray = cam.getRay(x, y);
				Intersection inter = scene.getIntersection(ray);
				int rgb;
				if (inter == null) {
					//TODO skybox
					rgb = Color.black.getRGB();
				}
				else {
					rgb = inter.getTriangle().getColor(inter);
				}
				image.setRGB(x, y, rgb);
			}
		}
		return image;
	}
	
}
