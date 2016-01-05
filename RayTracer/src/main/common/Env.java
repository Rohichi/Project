package main.common;

public class Env {
	public static Color[][] image;
	public static int nbthread;
	public static String file;
	public static int rebond;
	public static double refra;
	
	public static void init(int nbthread, String file, int width, int height, int rebond) {
		Env.nbthread = nbthread;
		image = new Color[height][width];
		Env.file = file;
		Env.rebond = rebond;
		refra = 1.;
	}
	
	public static void setImage(int x, int y, Color c) {
		image[x][y] = c;
	}
	
}
