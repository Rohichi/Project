package main.main;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;

import main.execption.JsonElementMissing;
import main.rendu.Image;

public class Main {
	public static void main(String[] args) {
	/*	
		tmp = new Cylindre(new Vecteur(0, -10 ,-5), 8, new Color(255,0,0), 1, 128, new Vecteur(0, -45, 0));
		tmp.refraction = 1;
		tmp.direct = 0;
		tmp.indRefra = 1.5;
		list.add(tmp);
		
		tmp = new Cone(new Vecteur(20, 10 ,-10), 25, new Color(100,100,100), 1, 2048, new Vecteur(0, 45, 0));
		tmp.mirroir = 0.9;
		tmp.direct = 0.1;
		list.add(tmp);
		
		/*
		tmp = new Triangle(new Vecteur(0,0,0), new Vecteur(1,0,0), new Vecteur(0,0,1));
		tmp.color = new Color(255, 0, 0);
		list.add(tmp);
		tmp = new Triangle(new Vecteur(0,0,1), new Vecteur(1,0,1), new Vecteur(1,0,0));
		tmp.color = new Color(0, 255, 0);
		list.add(tmp);
		
		List<AObj> tmplist = Util.parseObjectFile(new File("obj/cube.obj"));
		for (AObj o : tmplist)
			o.color = new Color(255, 128, 0);;
		list.addAll(tmplist);
		AShader shader = new BlinnPhongShader(lights, new Color(0,0,0), Color.getCoef(0.2, 0.2, 0.2));
		//shader = new NoShader(new Color(0,0,0));
		ALauncher launcher = new BasicLauncher(list, shader, camera);
		//launcher = new GridAntiAliasing(new BasicLauncher(list, shader, camera), 5 , camera);
		AIndice indice = new BasicIndice(env.width, env.height);

		//Util.photo_expose(Env.image, width, height);
			*/
		File file;
		/*if (args.length > 2)
			file = new File(args[1]);
		else
			file = new File("exemples/exemple.json");
		*/
		file = new File("test.json");
		try{
			RayTracer raytracer = new RayTracer(file);
			double duree = raytracer.start();
			System.out.println(duree + "s");
			Image img = raytracer.getImage();
			JFrame fenetre = new JFrame();
			fenetre.setTitle("RayTracer");
			fenetre.setSize(raytracer.env.width, raytracer.env.height);
			fenetre.setLocationRelativeTo(null);
			fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			fenetre.setResizable(false);
			fenetre.add(img);
			fenetre.repaint();
			fenetre.setVisible(true);
		} catch (IOException e) {
			System.err.println("File out error =/");
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonElementMissing e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
