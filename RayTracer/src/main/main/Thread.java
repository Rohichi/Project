package main.main;

import java.util.concurrent.Callable;

import main.common.Env;
import main.common.Ray;
import main.indice.AIndice;
import main.launcher.ALauncher;
import main.util.Pair;

public class Thread implements Callable<Integer>{
	Env env;
	Pair<Integer, Integer> coord;
	ALauncher launcher;
	Ray	ray;
	int nb;
	AIndice indice;
	
	public Thread (Env env, ALauncher launcher, AIndice indice, int nb) {
		coord = new Pair<Integer, Integer>();
		ray = new Ray();
		this.env = env;
		this.launcher = launcher;
		this.indice = indice;
		this.nb = nb;
	}
	

	@Override
	public Integer call() {
		try {
			int x;
			int y;
			indice.getCoord(coord);
			while ((x = coord.getA()) != -1) {
				y = coord.getB();
				env.setImage(x, y, launcher.launch(x, y));
				indice.getCoord(coord);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
