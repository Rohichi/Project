package main.util;

import java.util.Collection;
import java.util.List;

import main.common.Color;
import main.common.Vecteur;
import main.object.AObj;

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
		for (AObj object : objects) {
			oriTmp.val(intersection);
			dirTmp.val(vLight);
			distTmp = object.primitive(oriTmp, dirTmp, current);
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
	
}
