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
		if (object == "main.object.Plan") {
			repeatX = -1;
			repeatY = -1;
			extendX = false;
			extendY = false;
			sizeX = 10;
			sizeY = 10;
		}
		else if (object == "main.object.Sphere") {
			repeatX = 1;
			repeatY = 1;
			extendX = true;
			extendY = true;
			sizeX = 10;
			sizeY = 10;
		}
		else {
			repeatX = 1;
			repeatY = 1;
			extendX = false;
			extendY = false;
			sizeX = 10;
			sizeY = 10;
		}
	}	
	
	
	public void init() throws IOException{
		img = ImageIO.read(new File("textures" +  File.separator + filename));
	}
	
	@Override
	public void getColor(int x, int y, Color c) {
		int rgb = img.getRGB(y,  img.getHeight() - x - 1);
		c.red = ((double)((rgb >> 16 ) & 0x000000FF)) / 256.;
        c.green = ((double)((rgb >> 8 ) & 0x000000FF)) / 256.;
        c.blue = ((double)((rgb) & 0x000000FF)) / 256.;
	}

	@Override
	public double getSizeX() {
		return sizeX;
	}

	@Override
	public double getSizeY() {
		return sizeY;
	}

	@Override
	public int getHeight() {
		return img.getHeight();
	}

	@Override
	public int getWidth() {
		return img.getWidth();
	}

	@Override
	public int getRepeatX() {
		return repeatX;
	}

	@Override
	public int getRepeatY() {
		return repeatY;
	}

	@Override
	public boolean getExtendX() {
		return extendX;
	}

	@Override
	public boolean getExtendY() {
		return extendY;
	}

}
