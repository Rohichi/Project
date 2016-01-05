package main.indice;

import main.util.Pair;

public class BasicIndice extends AIndice{
	int i;
	int j;
	int height;
	int width;
	
	public BasicIndice(int width, int height) {
		i = 0;
		j = 0;
		this.height = height;
		this.width = width;
	}
	
	@Override
	synchronized public void getCoord(Pair pair) {
		pair.x = i;
		pair.y = j;
		if (i != -1) {
			i++;
			if (i == height) {
				i = 0;
				j++;
				if (j == width) {
					i = -1;
					j = -1;
				}
			}
		}
	}

	@Override
	public boolean finish() {
		return (i == -1);
	}
}
