package main.texture;

import java.io.IOException;

import main.common.Color;

public abstract class ATexture {
	public abstract void getColor(int x, int y, Color c);
	public abstract void init() throws IOException;
	public abstract double getSizeX();
	public abstract double getSizeY();
	public abstract int getHeight();
	public abstract int getWidth();
	public abstract int getRepeatX();
	public abstract int getRepeatY();
	public abstract boolean getExtendX();
	public abstract boolean getExtendY();
}
