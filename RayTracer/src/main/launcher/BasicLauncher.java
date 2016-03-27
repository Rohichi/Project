package main.launcher;

import java.util.Stack;

import main.common.Color;
import main.common.Ray;
import main.common.Vecteur;
import main.object.AObj;

public class BasicLauncher extends ALauncher {
	private Ray	ray;
	private Stack<AObj> milieu;
	
	public BasicLauncher () {
		milieu = new Stack<AObj>();
		ray = new Ray();
	}
	
	public void getColor(Vecteur ori, Vecteur dir, Color ret, int lastId, int rebond) {
		double	dist;
		double	min;
		AObj	near;
		Vecteur tmpOri = new Vecteur();
		Vecteur tmpDir = new Vecteur();
		
		near = null;
		min = -1;
		for (AObj obj : objects) {
			tmpOri.val(ori);
			tmpDir.val(dir);
			dist = obj.primitive(tmpOri, tmpDir, lastId);
			//System.out.println(dist);
			if (dist <= 0.)
				continue;
			if (near == null || min > dist) {
				min = dist;
				near = obj;
			}
		}
		if (near == null) {
			ret.val(shader.bg);
			ret.id = -1;
		}
		else {
			Vecteur intersection = dir.multClone(min).add(ori);
			Vecteur normal = new Vecteur();
			Color color = new Color();
			near.color(tmpOri.val(intersection), color);
			near.normal(tmpOri.val(intersection), dir, near.getId(), normal);
			dir.mult(-1);
			shader.getColor(ori, dir, intersection, normal, near, objects, color, ret);
			ret.mult(near.direct);
			if (near.miroir != 0. || near.refraction != 0.) {
				Color cTmp = new Color();		
				if (near.miroir != 0.) {
					if (rebond != env.rebond) {
						tmpOri.val(intersection);
						tmpDir.val(normal).mult(normal.scal(dir) * 2).sub(dir);
						getColor(tmpOri, tmpDir, cTmp, near.getId(), rebond + 1);
					}
					else
						cTmp.val(0,0,0);
					cTmp.mult(near.miroir);
					ret.add(cTmp);
				}
				
				//TODO problem refraction (peu être soucis de clipping)
				if (near.refraction != 0.) {
					if (rebond != env.rebond) {
						tmpOri.val(intersection);
						double n1;
						double n2;
						if (milieu.isEmpty()) {
							n1 = env.refra;
							n2 = near.indRefra;
						}
						else { 
							//n1 = milieu.lastElement().indRefra;
							n1 = near.indRefra;
							n2 = env.refra;
							/*if (milieu.lastElement().getId() != near.getId())
								n2 = near.indRefra;
							else
								n2 = (milieu.size() > 1 ? milieu.elementAt(milieu.size() - 2).indRefra : env.refra);
							*/
						}
						double cos1 = normal.scal(dir);
						double cos2 = 1 - Math.pow(n1 / n2, 2) * (1 - cos1 * cos1);
						if (cos2 < 0.) {
							tmpDir.val(normal).mult(2 * cos1).sub(dir).normal();
							getColor(tmpOri, tmpDir, cTmp, near.getId(), rebond + 1);
						}
						else {
							if (milieu.empty())// || milieu.lastElement().getId() != near.getId())
								milieu.push(near);
							else
								milieu.pop();
							cos2 = Math.sqrt(cos2);
							tmpDir.val(normal).mult(n1 * cos1 / n2 - cos2);
							dir.mult(n1 / n2);
							tmpDir.sub(dir);
							getColor(tmpOri, tmpDir, cTmp, near.getId(), rebond + 1);	
						}					
					}
					else
						cTmp.val(color);
					cTmp.mult(near.refraction);
					ret.add(cTmp);
				}
			}
			ret.id = near.getId();
		}
	}
	
	@Override
	public void launch(int x, int y, Color c){
		camera.getRay(x, y, ray);
		milieu.clear();
		getColor(ray.ori, ray.dir, c, -1, 0);
	}
	
	@Override
	public ALauncher copy(){
		ALauncher ret = new BasicLauncher();
		ret.init(objects, shader.copy(), camera);
		ret.setEnv(env);
		return ret;
	}
	
}
