package main.texture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.common.Color;

public class FileTexture extends ATexture{
	BufferedImage img;
	int repeatX;
	int repeatY;
	double sizeX;
	double sizeY;
	boolean extendX;
	boolean extendY;
	
	public FileTexture (String filename) throws IOException {
		set(filename, 1, 1, 0, 0, false, false);
	}
	
	public FileTexture (String filename, int repeatX, int repeatY) throws IOException {
		set(filename, repeatX, repeatY, 0, 0, false, false);
	}

	public FileTexture (String filename, int repeatX, int repeatY, boolean extendX, boolean extendY) throws IOException {
		set(filename, repeatX, repeatY, 0, 0, extendX, extendY);
	}
	
	public FileTexture (String filename, int repeatX, int repeatY, double sizeX, double sizeY) throws IOException {
		set(filename, repeatX, repeatY, sizeX, sizeY, false, false);		
	}
	
	public FileTexture (String filename, int repeatX, int repeatY, double sizeX, double sizeY, boolean extendX, boolean extendY) throws IOException {
		set(filename, repeatX, repeatY, sizeX, sizeY, extendX, extendY);
	}
	
	private void set(String filename, int repeatX, int repeatY, double sizeX, double sizeY, boolean extendX, boolean extendY) throws IOException {
		img = ImageIO.read(new File("textures" +  File.separator + filename));
		this.repeatX = repeatX;
		this.repeatY = repeatY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.extendX = extendX;
		this.extendY = extendY;		
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
