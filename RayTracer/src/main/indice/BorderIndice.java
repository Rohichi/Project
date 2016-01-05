package main.indice;

import main.common.Color;
import main.util.Pair;

public class BorderIndice extends AIndice{
	Color[][] image;
	int i;
	int j;
	int height;
	int width;
	
	public BorderIndice(int width, int height, Color[][] image) {
		i = 0;
		j = 0;
		this.height = height;
		this.width = width;
		this.image = image;
	}
	
	private void searchNext() {
		int id;	
		for (; i < height; i++) {
			for(; j < width; j++) {
				if (i == 0 || i == height - 1 || j == 0 || j == width - 1)
					return ;
				id = image[i][j].id;
				for (int k = i-1; k < i + 2; k++) {
					for (int l = j - 1; l < j + 2; l++) {
						if (image[k][l].id != id)
							return ;
					}
				}	
			}
			j = 0;
		}
		i = -1;
		j = -1;
	}
	
	@Override
	synchronized public void getCoord(Pair pair) {
		if (i == -1) {
			pair.x = i;
			pair.y = j;
		}
		else {
			searchNext();
			pair.x = i;
			pair.y = j;	
		}
		if (i != -1) {
			j++;
			if (j == width) {
				j = 0;
				i++;
				if (i == height) {
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
