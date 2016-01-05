package main.launcher;

import java.util.List;
import java.util.Stack;

import main.camera.ACamera;
import main.common.Color;
import main.common.Env;
import main.common.Ray;
import main.common.Vecteur;
import main.object.AObj;
import main.object.Id;
import main.shader.AShader;

public class BasicLauncher extends ALauncher {
	Ray			ray;
	Stack<AObj> milieu;
	
	public BasicLauncher (List<AObj> objects, AShader shader, ACamera camera) {
		super(objects, shader, camera);
		milieu = new Stack<AObj>();
		ray = new Ray();
	}
	
	public void getColor(Vecteur ori, Vecteur dir, Color ret, int lastId, int rebond) {
		double	dist;
		double	min;
		AObj	near;
		Vecteur tmpOri = new Vecteur();
		Vecteur tmpDir = new Vecteur();
		Id id = new Id();
		int currentId = -1;
		
		near = null;
		min = -1;
		for (AObj obj : objects) {
			tmpOri.val(ori);
			tmpDir.val(dir);
			dist = obj.primitive(tmpOri, tmpDir, lastId, id);
			if (dist <= 0.)
				continue;
			if (near == null || min > dist) {
				min = dist;
				near = obj;
				currentId = id.id;
			}
		}
		if (near == null) {
			ret.val(shader.bg);
			ret.id = -1;
		}
		else {
			Vecteur intersection = dir.scalClone(min).add(ori);
			Vecteur normal = new Vecteur();
			Color color = new Color();
			near.color(tmpOri.val(intersection), color);
			near.normal(tmpOri.val(intersection), dir, currentId, normal);
			dir.scal(-1);
			shader.getColor(ori, dir, intersection, normal, near, objects, color, ret);
			ret.mult(near.direct);
			if (near.mirroir != 0. || near.refraction != 0.) {
				Color cTmp = new Color();		
				if (near.mirroir != 0.) {
					if (rebond != Env.rebond) {
						tmpOri.val(intersection);
						tmpDir.val(normal).scal(normal.mult(dir) * 2).sub(dir);
						getColor(tmpOri, tmpDir, cTmp, currentId, rebond + 1);
					}
					else
						cTmp.val(0,0,0);
					cTmp.mult(near.mirroir);
					ret.add(cTmp);
				}
				if (near.refraction != 0.) {
					if (rebond != Env.rebond) {
						tmpOri.val(intersection);
						double n1;
						double n2;
						if (milieu.isEmpty()) {
							n1 = Env.refra;
							n2 = near.indRefra;
						}
						else { 
							n1 = near.indRefra;
							n2 = Env.refra;
							/*if (milieu.lastElement().id != near.id)
								n2 = near.indRefra;
							else
								n2 = (milieu.size() > 1 ? milieu.elementAt(milieu.size() - 2).indRefra : Env.refra);*/
						}
						double cos1 = normal.mult(dir);
						double cos2 = 1 - Math.pow(n1 / n2, 2) * (1 - cos1 * cos1);
						if (cos2 < 0.) {
							tmpDir.val(normal).scal(2 * cos1).sub(dir).normal();
							getColor(tmpOri, tmpDir, cTmp, currentId, rebond + 1);
						}
						else {
							if (milieu.empty())
								milieu.push(near);
							else
								milieu.pop();
							cos2 = Math.sqrt(cos2);
							tmpDir.val(normal).scal(n1 * cos1 / n2 - cos2);
							dir.scal(n1 / n2);
							tmpDir.sub(dir);
							getColor(tmpOri, tmpDir, cTmp, currentId, rebond + 1);	
						}					
					}
					else
						cTmp.val(color);
					cTmp.mult(near.refraction);
					ret.add(cTmp);
				}
			}
			ret.id = near.id;
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
		return new BasicLauncher(objects, shader.copy(), camera);
	}
	
}
