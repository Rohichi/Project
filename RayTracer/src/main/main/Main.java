package main.main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import main.camera.ACamera;
import main.camera.BasicCamera;
import main.common.Color;
import main.common.Env;
import main.common.Vecteur;
import main.indice.AIndice;
import main.indice.BasicIndice;
import main.launcher.ALauncher;
import main.launcher.BasicLauncher;
import main.light.Light;
import main.object.AObj;
import main.object.Cone;
import main.object.Cylindre;
import main.object.Plan;
import main.object.Sphere;
import main.rendu.Image;
import main.shader.AShader;
import main.shader.BlinnPhongShader;
import main.texture.FileTexture;

public class Main {
	public static void main(String[] args) {
		try {
			AObj tmp;
			int width = 800;
			int height = 600;
			Env.init(1, "out", width, height, 10);
			ACamera camera = new BasicCamera(new Vecteur(0, -80, 0), new Vecteur(0, 1, 0), new Vecteur(0, 0, 1), 20, 60, width, height);
			
			
			List<AObj> list = new ArrayList<>();

			tmp = new Plan(new Vecteur(60, 0, 0), new Color(255,0,0), 0, 2, new Vecteur(0, 90, 0));
			list.add(tmp);

			tmp = new Plan(new Vecteur(-60, 0, 0), new Color(0,255,0), 0, 2, new Vecteur(0, 90, 0));
			list.add(tmp);
			
			tmp = new Plan(new Vecteur(0,0,60), new Color(0,0,255), 0, 2, new Vecteur(0,0,0));
			list.add(tmp);

			tmp = new Plan(new Vecteur(0,-100,0), new Color(255,0,255), 0, 2, new Vecteur(90,0,0));
			list.add(tmp);

			tmp = new Plan(new Vecteur(0,0,-20), new Color(0,255,255), 0.1, 1024, new Vecteur(0,0,0));
			tmp.texture = new FileTexture("marble.bmp", -1, -1, 25, 25);
			tmp.mirroir = 0.2;
			tmp.direct = 0.8;	
			list.add(tmp);
		
			tmp = new Plan(new Vecteur(0,50,0), new Color(255,255, 0), 0, 2, new Vecteur(90,0,0));
			tmp.texture = new FileTexture("brick.bmp", -1, -1, 25, 25);
			list.add(tmp);
			
			tmp = new Sphere(new Vecteur(-35, -10 ,-10), 10, new Color(255,0,255), 0.2, 2048, new Vecteur(0, 0, 90));
			tmp.texture = new FileTexture("earth.bmp", 1, 1, true, true);
			list.add(tmp);
			
			tmp = new Sphere(new Vecteur(40, -10 ,-10), 10, new Color(128,0,128), 1, 256, new Vecteur(0,0,90));
			tmp.texture = new FileTexture("billard.bmp", 1, 1, true, true);
			tmp.mirroir = 0.2;
			tmp.direct = 0.8;
			list.add(tmp);
		
			tmp = new Cylindre(new Vecteur(0, -10 ,-5), 8, new Color(255,0,0), 1, 128, new Vecteur(0, -45, 0));
			tmp.refraction = 1;
			tmp.direct = 0;
			tmp.indRefra = 1.5;
			list.add(tmp);
		
			tmp = new Cone(new Vecteur(20, 10 ,-10), 25, new Color(100,100,100), 1, 2048, new Vecteur(0, 45, 0));
			tmp.mirroir = 0.9;
			tmp.direct = 0.1;
			list.add(tmp);
		
			/*tmp = new Triangle(new Vecteur(0,0,0), new Vecteur(1,0,0), new Vecteur(0,0,1));
			tmp.color = new Color(255, 0, 0);
			list.add(tmp);
			tmp = new Triangle(new Vecteur(0,0,1), new Vecteur(1,0,1), new Vecteur(1,0,0));
			tmp.color = new Color(0, 255, 0);
			list.add(tmp);*/
			/*List<AObj> tmplist = Util.parseObjectFile(new File("obj/cube.obj"));
			for (AObj o : tmplist) {
				o.color = new Color(255, 128, 0);;
			}
			list.addAll(tmplist);*/
			
			List<Light> lights = new ArrayList<Light>();
			lights.add(new Light(new Vecteur(40, -80, 0), new Color(255,255,255)));
			lights.add(new Light(new Vecteur(-40, -80, 0), new Color(255,255,255)));
			AShader shader = new BlinnPhongShader(lights, new Color(0,0,0), Color.getCoef(0.2, 0.2, 0.2));
			//shader = new NoShader(new Color(0,0,0));
			ALauncher launcher = new BasicLauncher(list, shader, camera);
			//launcher = new GridAntiAliasing(new BasicLauncher(list, shader, camera), 5 , camera);
			AIndice indice = new BasicIndice(width, height);
			ExecutorService execute = Executors.newFixedThreadPool(Env.nbthread);
			Collection<Callable<Integer>> tasks = new ArrayList<Callable<Integer>>(Env.nbthread);
			long start = System.nanoTime();
			for (int i = 0; i < Env.nbthread; i++) {
				tasks.add(new Thread(launcher.copy(), indice, i + 1));
			}
			execute.invokeAll(tasks);
			
			long end = System.nanoTime();
			double duree = (end - start)/1E9;
			//Util.photo_expose(Env.image, width, height);
			
			
			System.out.println(duree + "s");
			JFrame fenetre = new JFrame();
			fenetre.setTitle("RayTracer");
			fenetre.setSize(width, height);
			fenetre.setLocationRelativeTo(null);
			fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			fenetre.setResizable(false);
			Image img = new Image(Env.image, width, height);
			fenetre.add(img);
			fenetre.repaint();
			fenetre.setVisible(true);
			BufferedImage bi = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = bi.createGraphics();
			img.printAll(g2);
			File file = new File("images" + File.separator + Env.file + ".bmp");
			int i = 0;
			while (file.exists()) {
				i++;
				file = new File("images" + File.separator + Env.file + "(" + i + ").bmp");
			}
			ImageIO.write(bi, "bmp", file);
		} catch (IOException e) {
			System.err.println("File out error =/");
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
