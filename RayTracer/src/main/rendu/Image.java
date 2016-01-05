package main.rendu;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class Image extends Canvas{

	private static final long serialVersionUID = -7037339223313915239L;
	main.common.Color[][] image;
	int width;
	int height;
	
	public Image(main.common.Color[][] image, int width, int height) {
		this.image = image;
		this.width = width;
		this.height = height;
	}
	
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
            	if (image[y][x] != null)
            		g.setColor(new Color(image[y][x].getRed(), image[y][x].getGreen(), image[y][x].getBlue()));
            	else
            		g.setColor(new Color(0, 0, 0));
                		
                g.drawLine(x, y, x, y);
            }
        }
    }

}
