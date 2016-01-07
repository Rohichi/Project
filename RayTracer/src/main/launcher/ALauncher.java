package main.launcher;

import java.util.List;

import main.camera.ACamera;
import main.common.Color;
import main.common.Env;
import main.object.AObj;
import main.shader.AShader;

public abstract class ALauncher {
	protected List<AObj> objects;
	protected AShader shader;
	protected ACamera camera;
	protected Env env;
	
	public void init(List<AObj> list, AShader shader, ACamera camera){
		this.objects = list;
		this.shader = shader;
		this.camera = camera;
	}
	
	public Color launch(int x, int y) {
		Color c = new Color();
		launch(x, y, c);
		return c;
	}
	
	public void setCamera(ACamera camera) {
		this.camera = camera;
	}
	
	public ACamera getCamera() {
		return camera;
	}
	
	public void setEnv(Env env){
		this.env = env;
	}
	
	public abstract void launch(int x, int y, Color c);
	public abstract ALauncher copy();
	
}
