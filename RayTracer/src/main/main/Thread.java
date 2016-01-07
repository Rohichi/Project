package main.main;

import java.util.concurrent.Callable;

import main.common.Env;
import main.common.Ray;
import main.indice.AIndice;
import main.launcher.ALauncher;
import main.util.Pair;

public class Thread implements Callable<Integer>{
	Env env;
	Pair coord;
	ALauncher launcher;
	Ray	ray;
	int nb;
	AIndice indice;
	
	public Thread (Env env, ALauncher launcher, AIndice indice, int nb) {
		coord = new Pair();
		ray = new Ray();
		this.env = env;
		this.launcher = launcher;
		this.indice = indice;
		this.nb = nb;
	}
	

	@Override
	public Integer call() {
		try {
			indice.getCoord(coord);
			while (coord.x != -1) {
				env.setImage(coord.x, coord.y, launcher.launch(coord.x, coord.y));
				indice.getCoord(coord);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
