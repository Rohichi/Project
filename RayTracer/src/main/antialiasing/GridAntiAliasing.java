package main.antialiasing;

import main.camera.BasicCamera;
import main.common.Color;
import main.common.Ray;
import main.launcher.ALauncher;

public class GridAntiAliasing extends AAntialiasing{
	public int gridSide;
	
	private BasicCamera subcamera;
	private Ray ray;
	private Color[][] grid;
	
	public GridAntiAliasing() {
		gridSide = 1;
	}

	@Override
	public void init() {
		this.camera = launcher.getCamera();
		subcamera = new BasicCamera();
		subcamera.init(gridSide, gridSide);
		grid = new Color[gridSide][gridSide];
		for(int i = 0; i < gridSide; i++) {
			for(int j = 0; j < gridSide; j++) {
				grid[i][j] = new Color();
			}
		}
		ray = new Ray();		
	}
	
	private void getMedColor(Color c) {
		c.val(0, 0, 0);
		//HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		//Integer tmp;
		for(int i = 0; i < gridSide; i++) {
			for(int j = 0; j < gridSide; j++) {
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
		c.mult(1. / (gridSide*gridSide));
		//c.id = id;
	}
	
	@Override
	public void launch(int x, int y, Color c) {
		camera.getRay(x, y, ray);
		subcamera.setU(camera.getU().multClone(1./gridSide));
		subcamera.setV(camera.getV().multClone(1./gridSide));
		subcamera.pos.val(camera.pos);
		ray.dir.val(subcamera.getU()).add(subcamera.getV()).mult(-1/2);
		subcamera.setOri(ray.ori.add(ray.dir));
		launcher.setCamera(subcamera);
		for(int i = 0; i < gridSide; i++) {
			for(int j = 0; j < gridSide; j++) {
				launcher.launch(i, j, grid[i][j]);
			}
		}
		getMedColor(c);
	}
	
	@Override
	public ALauncher copy() {
		GridAntiAliasing ret = new GridAntiAliasing();
		ret.gridSide = gridSide;
		ret.setLauncher(launcher.copy());
		ret.init();
		return ret;
	}
		
}
