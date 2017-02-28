package main.util;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import main.antialiasing.AAntialiasing;
import main.camera.ACamera;
import main.common.Color;
import main.common.Vecteur;
import main.launcher.ALauncher;
import main.light.Light;
import main.object.AObj;
import main.shader.AShader;
import main.texture.ATexture;

public class Parser {
	public static void parseJsontoObject(Object obj, JsonObject json) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, InvocationTargetException, IOException {
		Class<?> objClass = obj.getClass();
		Field fields[] = objClass.getFields();
		for (Field f : fields) {
			 JsonElement elem = json.get(f.getName());
			if (elem != null) {
				if (f.getType() == Integer.TYPE)
					f.set(obj, Integer.valueOf(elem.getAsInt()));
				else if (f.getType() == Double.TYPE){
					f.set(obj, Double.valueOf(elem.getAsDouble()));
				}
				else if (f.getType() == String.class)
					f.set(obj, elem.getAsString());
				else if (f.getType() == main.common.Vecteur.class) {
					Vecteur v = new Vecteur();
					parseJsontoObject(v, elem.getAsJsonObject());
					f.set(obj, v);
				}
				else if (f.getType() == main.common.Color.class) {
					Color c = new Color();
					parseJsontoObject(c, elem.getAsJsonObject());
					f.set(obj, c);
				}
				else if (f.getType() == main.texture.ATexture.class) {
					ATexture texture = parseTexture(obj, elem.getAsJsonObject());
					texture.init();
					f.set(obj, texture);
				}
				else
					System.err.println("Champ \"" + f.getName() +"\" non géré !!");
			}
		}
	}
	
	public static ATexture parseTexture(Object obj, JsonObject jsonObj) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		Class<?> textureClass = Class.forName("main.texture." + jsonObj.get("type").getAsString());
		Constructor<?> cons = textureClass.getConstructor(String.class);
		ATexture ret = (ATexture) cons.newInstance(obj.getClass().getName());
		parseJsontoObject(ret, jsonObj);
		return ret;
	}
	
	
	public static AObj parseAObj(JsonObject jsonObj) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, InstantiationException, NoSuchMethodException, SecurityException, InvocationTargetException, IOException {
		Class<?> objClass = Class.forName("main.object." + jsonObj.get("type").getAsString());
		AObj ret = (AObj) objClass.newInstance();
		parseJsontoObject(ret, jsonObj);
		return ret;
	}


	public static Light parseLight(JsonObject jsonObj) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, InvocationTargetException, IOException {
		Light ret = new Light();
		parseJsontoObject(ret, jsonObj);
		return ret;
	}
	
	public static ALauncher parseALauncher(JsonObject jsonObj) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException, IOException {
		Class<?> objClass = Class.forName("main.launcher." + jsonObj.get("type").getAsString());
		ALauncher ret = (ALauncher) objClass.newInstance();
		parseJsontoObject(ret, jsonObj);
		return ret;
	}

	public static ACamera parseACamera(JsonObject jsonObj) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, InvocationTargetException, IOException {
		Class<?> objClass = Class.forName("main.camera." + jsonObj.get("type").getAsString());
		ACamera ret = (ACamera) objClass.newInstance();
		parseJsontoObject(ret, jsonObj);
		return ret;
	}

	public static AShader parseAShader(JsonObject jsonObj) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, InvocationTargetException, IOException {
		Class<?> objClass = Class.forName("main.shader." + jsonObj.get("type").getAsString());
		AShader ret = (AShader) objClass.newInstance();
		parseJsontoObject(ret, jsonObj);
		return ret;
	}

	public static AAntialiasing parseAntiAliasing(JsonObject jsonObj) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, InvocationTargetException, IOException {
		Class<?> objClass = Class.forName("main.antialiasing." + jsonObj.get("type").getAsString());
		AAntialiasing ret = (AAntialiasing) objClass.newInstance();
		parseJsontoObject(ret, jsonObj);
		return ret;
	}
	
}
