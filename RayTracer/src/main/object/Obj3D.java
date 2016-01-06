package main.object;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.common.Color;
import main.common.Vecteur;
import main.texture.TextureCoord;

public class Obj3D extends AObj{
	List<AObj> triangles;
	
	public Obj3D(File file) throws NumberFormatException, IOException {
		triangles = new ArrayList<AObj>();
		List<Vecteur> sommet = new ArrayList<Vecteur>();
		List<Vecteur> normal = new ArrayList<Vecteur>();
		List<TextureCoord> texture = new ArrayList<TextureCoord>();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		String[] tab;
		while ((line = br.readLine()) != null) {
			line = line.trim();
			if (line.isEmpty() || line.startsWith("#"))
				continue;
			tab = line.split("\\s+");
			switch (tab[0]) {
				case "v" :
					sommet.add(new Vecteur(tab[1], tab[2], tab[3]));
					break;
				case "vn" :
					normal.add(new Vecteur(tab[1], tab[2], tab[3]));
					break;	
				case "vt" :
					texture.add(new TextureCoord(tab[1], tab[2]));
					break;
				case "f" :
					String[][] face = new String[3][];
					for (int i = 1; i < 4; i++)
						face[i - 1] = tab[i].split("/");
					Triangle triangle = new Triangle(sommet.get(Integer.parseInt(face[0][0])-1), sommet.get(Integer.parseInt(face[1][0])-1), sommet.get(Integer.parseInt(face[2][0]) -1));
					if (!face[0][2].isEmpty())
						triangle.setNormal(normal.get(Integer.parseInt(face[0][2])-1), normal.get(Integer.parseInt(face[1][2])-1), normal.get(Integer.parseInt(face[2][2]) -1));
					triangles.add(triangle);
					break;
				default :
					System.out.println(tab[0]);
					throw new IOException();
			}
		}
		br.close();
	}
	
	@Override
	public double primitive(Vecteur ori, Vecteur dir, int lastId) {
		double min = -1;
		double tmp;
		for(AObj triangle : triangles) {
			tmp = triangle.primitive(ori, dir, lastId);
			if (tmp > 0. && (min < 0. || tmp < min)) {
				min = tmp;
			}
		}
		return min;
	}

	@Override
	public void getTextureColor(Vecteur pos, Color c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void normal(Vecteur pos, Vecteur dir, int id, Vecteur ret) {
		for(AObj triangle : triangles) {
			if (triangle.id == id) {
				triangle.normal(pos, dir, id, ret);
				break ;
			}
		}
	}
}
