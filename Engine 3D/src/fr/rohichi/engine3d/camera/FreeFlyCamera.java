package fr.rohichi.engine3d.camera;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import fr.rohichi.engine3d.Vector3D;

public class FreeFlyCamera extends Camera implements KeyListener{
	private final int KEY_ARROW_UP = 0;
	private final int KEY_ARROW_DOWN = 1;
	private final int KEY_ARROW_LEFT = 2;
	private final int KEY_ARROW_RIGHT = 3;
	private final int KEY_PLUS = 4;
	private final int KEY_MINUS = 5;
	
	private double speed;
	private double speedStep;
	private boolean[] key;
	
	public FreeFlyCamera(Vector3D ori, Vector3D dir, Vector3D up, double speedStep) {
		this(ori, dir, up, Math.toRadians(70), 10.0D, speedStep);
	}
	
	public FreeFlyCamera(Vector3D ori, Vector3D dir, Vector3D up, double fovY, double distance, double speedStep) {
		super(ori, dir, up, fovY, distance);
		key = new boolean[6];
		this.speedStep = speedStep;
		this.speed = speedStep;
	}
	
	public void update() {
		if (key[KEY_PLUS])
			speed += speedStep;
		if (key[KEY_MINUS])
			speed -= speedStep;
		if (speed < 0.0D)
			speed = 0.0D;
		/*	Angle	*/
		/*if (phi > 89)
			phi = 89;
		else if (phi < -89)
			phi = -89;
		double tmp = Math.cos(Math.toRadians(phi));
		Vector3D forward = new Vector3D(
				tmp * Math.cos(Math.toRadians(theta)),
				tmp * Math.sin(Math.toRadians(theta)),
				Math.sin(Math.toRadians(phi)));
		phi = 0.0D;
		theta = 0.0D;
		dir.add(forward).normal();
		up.add(forward).normal();
//		Vector3D left = new Vector3D(up).crossProduct(dir);
*/
		if (key[KEY_ARROW_UP])
			ori.add(speed, dir);
		if (key[KEY_ARROW_DOWN])
			ori.add(-speed, dir);
		Vector3D left = new Vector3D(up).crossProduct(dir);
		if (key[KEY_ARROW_LEFT])
			ori.add(speed, left);
		if (key[KEY_ARROW_RIGHT])
			ori.add(-speed, left);		
	}

	@Override
	public void keyTyped(KeyEvent event) {
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_UP) {
			key[KEY_ARROW_UP] = true;
		}
		if (event.getKeyCode() == KeyEvent.VK_DOWN) {
			key[KEY_ARROW_DOWN] = true;
		}
		if (event.getKeyCode() == KeyEvent.VK_LEFT) {
			key[KEY_ARROW_LEFT] = true;
		}
		if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
			key[KEY_ARROW_RIGHT] = true;
		}
		if (event.getKeyCode() == KeyEvent.VK_PLUS) {
			key[KEY_PLUS] = true;
		}
		if (event.getKeyCode() == KeyEvent.VK_MINUS) {
			key[KEY_MINUS] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		if (event.getKeyCode() == KeyEvent.VK_UP) {
			key[KEY_ARROW_UP] = false;
		}
		if (event.getKeyCode() == KeyEvent.VK_DOWN) {
			key[KEY_ARROW_DOWN] = false;
		}
		if (event.getKeyCode() == KeyEvent.VK_LEFT) {
			key[KEY_ARROW_LEFT] = false;
		}
		if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
			key[KEY_ARROW_RIGHT] = false;
		}
		if (event.getKeyCode() == KeyEvent.VK_PLUS) {
			key[KEY_PLUS] = false;
		}
		if (event.getKeyCode() == KeyEvent.VK_MINUS) {
			key[KEY_MINUS] = false;
		}
	}

}
