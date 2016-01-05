package main.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import main.common.Color;
import main.common.Vecteur;
import main.object.AObj;
import main.object.Id;
import main.object.Triangle;
import main.texture.TextureCoord;

public class Util {
		public static double near(Collection<Double> list, boolean same){
		if (same) {
			boolean flag = false;
			for (Double d : list) {
				if (flag)
					return d.doubleValue();
				if (d.doubleValue() > 0)
					flag = true;
			}
		}
		else {
			for (Double d : list) {
				if (d.doubleValue() > 0)
					return d.doubleValue();
			}
		}
		return -1;
	}
	
	public static boolean checkVisibility(int current, List<AObj> objects, Vecteur vLight, double dist, Vecteur intersection, Vecteur normal, Vecteur oriTmp, Vecteur dirTmp) {
		if (normal.mult(vLight) < 0.)
			return false;
		double distTmp;
		Id id = new Id();
		for (AObj object : objects) {
			oriTmp.val(intersection);
			dirTmp.val(vLight);
			distTmp = object.primitive(oriTmp, dirTmp, current, id);
			if (distTmp > 0. && distTmp < dist)
				return false;
		}
		return true;
	}

	public static double attenuation(double dist, Vecteur attenuation) {
		return 1. / (attenuation.getX() + dist * (attenuation.getY() + dist * attenuation.getZ()));
	}

	static double maxFour(double a, double b, double c, double d)
	{
		if (a > b)
		{
			if (a > c)
				return (a > d ? a : d);
			return (c > d ? c : d);
		}
		if (b > c)
			return (b > d ? b : d);
		return (c > d ? c : d);
	}
	
	
	public static void photo_expose(Color[][] image, int width, int height) {
		double max = image[0][0].red;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++){
				max = maxFour(max, image[i][j].red, image[i][j].green, image[i][j].blue);
			}
		}
		if (max > 1.) {
			max = 1. / max;
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++){
					image[i][j].mult(max);
				}
			}
		}
	}
	
	public static List<AObj> parseObjectFile(File file) throws IOException {
		List<AObj> triangles = new ArrayList<AObj>();
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
		return triangles;
				//new Obj3D(triangle);
	}
	
}
