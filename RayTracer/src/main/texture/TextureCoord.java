package main.texture;

public class TextureCoord {
	public double x;
	public double y;
	
	public TextureCoord(String x, String y) {
		this.x = Double.parseDouble(x);
		this.y = Double.parseDouble(y);
	}
}
