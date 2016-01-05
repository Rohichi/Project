package main.launcher;

import main.camera.ACamera;
import main.camera.BasicCamera;
import main.common.Color;
import main.common.Ray;

public class GridAntiAliasing extends ALauncher{
	ALauncher launcher;
	BasicCamera subcamera;
	Ray ray;
	int size;
	Color[][] grid;
	
	public GridAntiAliasing(ALauncher launcher, int size, ACamera camera) {
		super(null, null, null);
		this.camera = camera;
		this.launcher = launcher;
		this.size = size;
		subcamera = new BasicCamera();
		grid = new Color[size][size];
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				grid[i][j] = new Color();
			}
		}
		ray = new Ray();
	}

	private void getMedColor(Color c) {
		c.val(0, 0, 0);
		//HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		//Integer tmp;
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				c.add(grid[i][j]);
			/*	tmp = map.get(Integer.valueOf(grid[i][j].id));
				if (tmp == null)
					map.put(Integer.valueOf(grid[i][j].id), Integer.valueOf(0));
				else
					map.put(Integer.valueOf(grid[i][j].id), Integer.valueOf(tmp.intValue() + 0));*/
			}
		}
/*		int max = -1;
		int id = -1;
		int value;
		for (Entry<Integer, Integer> entry : map.entrySet()) {
			value = entry.getValue().intValue(); 
			if (value > max) {
				max = value;
				id = entry.getKey().intValue();
			}
		}*/
		c.mult(1. / (size*size));
		//c.id = id;
	}
	
	@Override
	public void launch(int x, int y, Color c) {
		camera.getRay(x, y, ray);
		subcamera.u.val(camera.getU()).scal(1./size);
		subcamera.v.val(camera.getV()).scal(1./size);
		subcamera.pos.val(camera.pos);
		ray.dir.val(subcamera.u).add(subcamera.v).scal(-1/2);
		subcamera.ori = ray.ori.add(ray.dir);
		launcher.camera = subcamera;
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				launcher.launch(i, j, grid[i][j]);
			}
		}
		getMedColor(c);
	}
	
	@Override
	public ALauncher copy() {
		return new GridAntiAliasing(launcher.copy(), size, camera);
	}
		
}
