package main.antialiasing;

import main.launcher.ALauncher;

public abstract class AAntialiasing extends ALauncher{
	protected ALauncher launcher;
	
	public void setLauncher(ALauncher launcher) {
		this.launcher = launcher;
	}
	public abstract void init();
}
