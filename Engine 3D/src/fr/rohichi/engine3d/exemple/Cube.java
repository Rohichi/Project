package fr.rohichi.engine3d.exemple;

import java.awt.Color;

import javax.swing.JFrame;

import fr.rohichi.engine3d.SceneExplorer;
import fr.rohichi.engine3d.Vector3D;
import fr.rohichi.engine3d.scene.Scene;
import fr.rohichi.engine3d.scene.Triangle;
import fr.rohichi.engine3d.texture.ColorTexture;

public class Cube {
	public static void main(String[] args) {
		Scene scene = new Scene();
		Vector3D a = new Vector3D(0, 0, 0);
		Vector3D b = new Vector3D(1, 0, 0);
		Vector3D c = new Vector3D(1, 0, 1);
		Vector3D d = new Vector3D(0, 0, 1);

		scene.addTriangle(new Triangle(a, b, c, new ColorTexture(Color.RED)));
		scene.addTriangle(new Triangle(a, d, c, new ColorTexture(Color.BLUE)));
		scene.prepareBeforeGetIntersection();
		SceneExplorer frame = new SceneExplorer(scene, 800, 600);
		
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.run();

	}
}
