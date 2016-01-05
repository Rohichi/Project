package main.launcher;

import java.util.List;

import main.camera.ACamera;
import main.common.Color;
import main.object.AObj;
import main.shader.AShader;

public abstract class ALauncher {
	List<AObj> objects;
	AShader shader;
	ACamera camera;
	
	public ALauncher (List<AObj> list, AShader shader, ACamera camera) {
		this.objects = list;
		this.camera = camera;
		this.shader = shader;
	}
	
	public Color launch(int x, int y) {
		Color c = new Color();
		launch(x, y, c);
		return c;
	}
	public abstract void launch(int x, int y, Color c);
	public abstract ALauncher copy();
	
}
