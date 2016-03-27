package main.main;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JProgressBar;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import main.antialiasing.AAntialiasing;
import main.camera.ACamera;
import main.common.Env;
import main.execption.JsonElementMissing;
import main.indice.AIndice;
import main.indice.BasicIndice;
import main.launcher.ALauncher;
import main.launcher.BasicLauncher;
import main.light.Light;
import main.object.AObj;
import main.rendu.Image;
import main.shader.AShader;
import main.shader.NoShader;
import main.util.Parser;

public class RayTracer {
	Env env;
	ALauncher launcher;
	private Image image;

	public RayTracer(File file) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, InstantiationException, IOException, NoSuchMethodException, SecurityException, InvocationTargetException, JsonElementMissing {
		Scanner scanner = new Scanner(file);
		String jsonString = scanner.useDelimiter("\\Z").next();
		scanner.close();
		
		JsonElement root = new JsonParser().parse(jsonString);
		JsonElement tmp;

		env = new Env();
		tmp = root.getAsJsonObject().get("env");
		if (tmp == null)
			throw new JsonElementMissing("env");
		Parser.parseJsontoObject(env, tmp.getAsJsonObject());
		env.init();
		
		tmp = root.getAsJsonObject().get("camera");
		if (tmp == null)
			throw new JsonElementMissing("camera");
		ACamera camera = Parser.parseACamera(tmp.getAsJsonObject());
		camera.init(env.width, env.height);
		
		List<Light> lights = new ArrayList<Light>();
		tmp = root.getAsJsonObject().get("listLights");
		if (tmp != null) {
			for(JsonElement jsonObj : root.getAsJsonObject().get("listLights").getAsJsonArray()) {
				Light light = Parser.parseLight(jsonObj.getAsJsonObject());
				light.init();
				lights.add(light);
			}
		}
		
		tmp = root.getAsJsonObject().get("shader");
		AShader shader = (tmp == null ? new NoShader() : Parser.parseAShader(tmp.getAsJsonObject()));
		shader.init(lights);
		
		List<AObj> list = new ArrayList<>();
		tmp = root.getAsJsonObject().get("listObjs");
		if (tmp != null) {
			for(JsonElement jsonObj : tmp.getAsJsonArray()) {
				AObj obj = Parser.parseAObj(jsonObj.getAsJsonObject());
				obj.init();
				list.add(obj);
			}
		}
		tmp = root.getAsJsonObject().get("launcher");

		launcher = (tmp == null ? new BasicLauncher() : Parser.parseALauncher(tmp.getAsJsonObject()));
		launcher.init(list, shader, camera);		
		launcher.setEnv(env);
		tmp = root.getAsJsonObject().get("antialiasing");
		if (tmp != null) {
			AAntialiasing antialiasing = Parser.parseAntiAliasing(tmp.getAsJsonObject());
			antialiasing.setLauncher(launcher);
			antialiasing.init();
			launcher = antialiasing;
			launcher.setEnv(env);
		}
	}
	
	public double start(JProgressBar bar) throws InterruptedException, IOException{
		AIndice indice = new BasicIndice(env.width, env.height, bar);
		ExecutorService execute = Executors.newFixedThreadPool(env.nbthread);
		Collection<Callable<Integer>> tasks = new ArrayList<Callable<Integer>>(env.nbthread);
		long start = System.nanoTime();
		for (int i = 0; i < env.nbthread; i++) {
			tasks.add(new Thread(env, launcher.copy(), indice, i + 1));
		}
		execute.invokeAll(tasks);		
		long end = System.nanoTime();
		
		
		//Util.photo_expose(env.image, env.width, env.height);
		image = new Image(env.image, env.width, env.height);
		return (end - start)/1E9;
	}
	
	public Image getImage(){
		return image;
	}
	
}
