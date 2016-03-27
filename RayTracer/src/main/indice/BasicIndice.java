package main.indice;

import javax.swing.JProgressBar;

import main.util.Pair;

public class BasicIndice extends AIndice{
	int i;
	int j;
	int height;
	int width;
	int last;
	int value;
	JProgressBar bar;
	
	public BasicIndice(int width, int height, JProgressBar bar) {
		i = 0;
		j = 0;
		last = -1;
		this.height = height;
		this.width = width;
		this.bar = bar;
		value = 0;
	}
	
	@Override
	synchronized public void getCoord(Pair pair) {
		pair.x = i;
		pair.y = j;
		if (i != -1) {
			int tmp = (i + j * height) * 100 / (height * width);
			if (tmp > last) {
				last = tmp;
			}
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
		bar.setValue(++value);
	}

	@Override
	public boolean finish() {
		return (i == -1);
	}
}
