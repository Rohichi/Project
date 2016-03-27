package main.main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

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
		JFileChooser fc = new JFileChooser(new File("."));
		fc.showOpenDialog(null);
		File file;
		file = fc.getSelectedFile();
		try{

			RayTracer raytracer = new RayTracer(file);
			
			JFrame fenetre = new JFrame();
			fenetre.setTitle("RayTracer");
			fenetre.setSize(300, 40);
			fenetre.setLocationRelativeTo(null);
			fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			fenetre.setResizable(false);
			JProgressBar bar = new JProgressBar();
			bar.setMaximum(raytracer.env.height * raytracer.env.width);
			bar.setMinimum(0);
			bar.setStringPainted(true);
			fenetre.add(bar);
			fenetre.repaint();
			fenetre.setVisible(true);
			
			double duree = raytracer.start(bar);
			System.out.println("L'image a été générée en " + duree + "s.");
			Image img = raytracer.getImage();
			fenetre.remove(bar);
			fenetre.setSize(raytracer.env.width, raytracer.env.height);
			fenetre.setLocationRelativeTo(null);
			fenetre.add(img);
			fenetre.repaint();
			fenetre.setVisible(true);
			BufferedImage bi = new BufferedImage(raytracer.env.width, raytracer.env.height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = bi.createGraphics();
			img.print(g2);
			if (raytracer.env.file != null) {
				file = new File("images" + File.separator + raytracer.env.file + ".bmp");
				int i = 0;
				while (file.exists()) {
					i++;
					file = new File("images" + File.separator + raytracer.env.file + "(" + i + ").bmp");
				}
				System.out.println(file.getAbsolutePath());
				ImageIO.write(bi, "bmp", file);
			}
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
