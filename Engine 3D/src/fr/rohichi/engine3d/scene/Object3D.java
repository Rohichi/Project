package fr.rohichi.engine3d.scene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fr.rohichi.engine3d.Vector3D;

public class Object3D {
	private Object3D() {
	}
	
	public static Collection<Triangle> parseFile(String fileName) throws NumberFormatException, IOException {
		Collection<Triangle> triangles = new ArrayList<>();
		List<Vector3D> sommet = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
		String line;
		String[] tab;
		while ((line = br.readLine()) != null) {
			line = line.trim();
			if (line.isEmpty() || line.startsWith("#"))
				continue;
			tab = line.split("\\s+");
			switch (tab[0]) {
				case "v" :
					sommet.add(new Vector3D(tab[1], tab[2], tab[3]));
					break;				
				case "f" :
					String[][] face = new String[3][];
					for (int i = 1; i < 4; i++)
						face[i - 1] = tab[i].split("/");
					Triangle triangle = new Triangle(sommet.get(Integer.parseInt(face[0][0])-1), sommet.get(Integer.parseInt(face[1][0])-1), sommet.get(Integer.parseInt(face[2][0]) -1));
					triangles.add(triangle);
					break;
				default :
					System.err.println("File obj : Unknow param = " + tab[0]);
			}
		}
		br.close();
		return triangles;
	}
}
