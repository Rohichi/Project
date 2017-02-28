package fr.rohichi.engine3d;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import fr.rohichi.engine3d.camera.FreeFlyCamera;
import fr.rohichi.engine3d.scene.Scene;

public class SceneExplorer extends JFrame{

	private static final long serialVersionUID = 1L;
	private Engine3D engine;
	private int width;
	private int height;
	private ImageIcon icon;
	private FreeFlyCamera cam;

	public SceneExplorer(Scene scene, int width, int height) {
		engine = new Engine3D(scene);
		this.width = width;
		this.height = height;
		this.cam = new FreeFlyCamera(new Vector3D(0, -10, 0),
				new Vector3D(0, 1, 0),
				new Vector3D(0, 0, 1), 0.1);
		icon = new ImageIcon(engine.getImage(cam, width, height));
	//	addMouseWheelListener(cam);
		addKeyListener(cam);
	//	addMouseMotionListener(cam);
		getContentPane().add(new JLabel(icon));
	}

	@Override
	public void repaint() {
		if (engine != null) {
			cam.init(width, height);
			icon.setImage(engine.getImage(cam, width, height));
		}
		super.repaint();
	}

	public void run(){
		long lastLoopTime = System.nanoTime();
		final int TARGET_FPS = 60;
		final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
		long lastFpsTime = 0;
		while(true){
			long now = System.nanoTime();
			long timeStep = now - lastLoopTime;
			lastFpsTime += timeStep;
			if(lastFpsTime >= 1000000000){
				lastFpsTime = 0;
			}
			cam.update();
			this.repaint();
			lastLoopTime = now;
			try{
				long gameTime = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
				Thread.sleep(gameTime);
			}catch(Exception e){
			}
		}
	}
}