package main.texture;

import java.io.IOException;

import main.common.Color;

public abstract class ATexture {
	public abstract int getColor(double u, double maxu, double v, double maxv, Color c);
	public abstract void init() throws IOException;
}
