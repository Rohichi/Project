package main.photonmapping;

import main.common.Color;
import main.common.Vecteur;

public class Photon {
	private Vecteur pos;
	private Vecteur dir;
	private Color color;
	private short flag;
	private Photon left;
	private Photon right;
	
	public Photon(Vecteur pos, Vecteur dir, Color color) {
		this.color = color;
		this.pos = pos;
		this.dir = dir;
	}

	public Vecteur getPos() {
		return pos;
	}

	public void setPos(Vecteur pos) {
		this.pos = pos;
	}

	public Vecteur getDir() {
		return dir;
	}

	public void setDir(Vecteur dir) {
		this.dir = dir;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public short getFlag() {
		return flag;
	}

	public void setFlag(short flag) {
		this.flag = flag;
	}

	public Photon getLeft() {
		return left;
	}

	public void setLeft(Photon left) {
		this.left = left;
	}

	public Photon getRight() {
		return right;
	}

	public void setRight(Photon right) {
		this.right = right;
	}
	
	
}
