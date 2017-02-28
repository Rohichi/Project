package main.texture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.common.Color;

public class FileTexture extends ATexture{
	private BufferedImage img;
	public String filename;
	public int repeatX;
	public int repeatY;
	public double sizeX;
	public double sizeY;
	public boolean extendX;
	public boolean extendY;
	
	public FileTexture (String object){
		filename = null;
		sizeX = 10;
		sizeY = 10;
		if (object == "main.object.Plan") {
			repeatX = -1;
			repeatY = -1;
			extendX = false;
			extendY = false;
		}
		else if (object == "main.object.Sphere" || object == "main.object.Tore") {
			repeatX = 1;
			repeatY = 1;
			extendX = true;
			extendY = true;
		}
		else if (object == "main.object.Cylindre" || object == "main.object.Cone") {
			repeatX = 1;
			repeatY = -1;
			extendX = true;
			extendY = false;
		}
		else {
			repeatX = 1;
			repeatY = 1;
			extendX = false;
			extendY = false;
		}
	}	
	
	
	@Override
	public void init() throws IOException{
		img = ImageIO.read(new File("textures" +  File.separator + filename));
	}
	
	private static int getInd(double x, double maxx, boolean extend, double size, int repeat, int nbpix) {
		if (maxx != -1 && extend)
			size = maxx / repeat;
		double	tmp;

		if (x < 0. && repeat == -1) {
			tmp = (double)((int)(x / size));
			x = size + (x - (tmp * size));
			if (x == size)
				x = 0.;
		}
		else if (x >= size) {
			tmp = (double)((int)(x / size));
			if ((int)tmp < repeat || repeat == -1)
				x = x - (tmp * size);
		}
		return ((int)((x * (double)nbpix) / size));
	}
	
	
	@Override
	public int getColor(double x, double maxx, double y, double maxy, Color c)
	{
		int i = getInd(y, maxy, extendY, sizeY, repeatY, img.getHeight());
		int j = getInd(x, maxx, extendX, sizeX, repeatX, img.getWidth());
		if (j < 0 || j >= img.getWidth() || i < 0 || i >= img.getHeight())
			return -1;
		getColor(i, j, c);
		return (0);
	}
		
	private void getColor(int x, int y, Color c) {
		int rgb = img.getRGB(img.getWidth() - y - 1,  img.getHeight() - x - 1);
		c.red = ((double)((rgb >> 16 ) & 0x000000FF)) / 256.;
        c.green = ((double)((rgb >> 8 ) & 0x000000FF)) / 256.;
        c.blue = ((double)((rgb) & 0x000000FF)) / 256.;
	}
}
