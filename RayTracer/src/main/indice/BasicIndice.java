package main.indice;

import main.util.Pair;

public class BasicIndice extends AIndice{
	int i;
	int j;
	int height;
	int width;
	int last;
	
	public BasicIndice(int width, int height) {
		i = 0;
		j = 0;
		last = -1;
		this.height = height;
		this.width = width;
	}
	
	@Override
	synchronized public void getCoord(Pair pair) {
		pair.x = i;
		pair.y = j;
		if (i != -1) {
			int tmp = (i + j * height) * 100 / (height * width);
			if (tmp > last) {
				last = tmp;
				//System.out.println(last);
			}
			i++;
			if (i == height) {
				i = 0;
				j++;
				if (j == width) {
					i = -1;
					j = -1;
					//System.out.println(100);
				}
			}
		}
	}

	@Override
	public boolean finish() {
		return (i == -1);
	}
}
