package fr.rohichi.engine3d.texture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageTexture extends ATexture {
	BufferedImage image;
	
	public ImageTexture(String fileName) throws IOException {
		image = ImageIO.read(new File(fileName));
	}
	
	@Override
	public int getRGB(double u, double v) {
		int x = (int)(u * image.getWidth());
		int y = (int)(v * image.getHeight());
		// y = image.getHeight() - y;
		return image.getRGB(x, y);
	}

}
