package main.common;

public class Color {
	public double red;
	public double green;
	public double blue;
	public int id;
	
	static public Color getCoef(double red, double green, double blue){
		Color c = new Color();
		c.red = red;
		c.green = green;
		c.blue = blue;
		return c;
	}
	
	public Color() {
		red = 0;
		green = 0;
		blue = 0;
	}

	public Color(double red, double green, double blue) {
		this.red = red / 255.;
		this.green = green / 255.;
		this.blue = blue / 255.;
	}

	public Color(Color color) {
		this.red = color.red;
		this.green = color.green;
		this.blue = color.blue;
	}
	
	public int getRed(){
		int ret = (int)(red * 255.);
		return (ret > 255 ? 255 : ret);
	}
	
	public int getGreen(){
		int ret = (int)(green * 255.);
		return (ret > 255 ? 255 : ret);
	}

	public int getBlue(){
		int ret = (int)(blue * 255.);
		return (ret > 255 ? 255 : ret);
	}
	
	public Color add(Color color) {
		red += color.red;
		green += color.green;
		blue += color.blue;
		return this;
	}

	public Color mult(Color c) {
		red = red * c.red;
		green = green * c.green;
		blue = blue * c.blue;
		return this;
	}
	
	public Color safeAdd(Color color) {
		red = (color.red < 0 ? this.red : this.red + color.red);
		green = (color.green < 0 ? this.green : this.green + color.green);
		blue = (color.blue < 0 ? this.blue : this.blue + color.blue);
		return this;
	}

	public Color mult(double coef) {
		red *= coef;
		green *= coef;
		blue *= coef;
		return this;
	}

	public Color val(Color color) {
		this.red = color.red;
		this.green = color.green;
		this.blue = color.blue;
		return this;
	}

	public Color val(double red, double green, double blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		return this;
	}

	public Color add(double d) {
		red += d;
		green += d;
		blue += d;
		return this;
	}
}
