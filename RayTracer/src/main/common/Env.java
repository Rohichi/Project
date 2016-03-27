package main.common;

public class Env {
	public Color[][] image;
	public int nbthread;
	public String file;
	public int rebond;
	public double refra;
	public int width;
	public int height;
		
	public Env() {
		width = 800;
		height = 600;
		nbthread = Runtime.getRuntime().availableProcessors();
		file = null;
		rebond = 1;
		refra = 1.00027;
	}
	
	public void init(){
		image = new Color[height][width];
	}
	
	public void setImage(int x, int y, Color c) {
		image[x][y] = c;
	}
	
}
