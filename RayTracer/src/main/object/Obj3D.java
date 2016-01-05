package main.object;

import java.util.List;

import main.common.Color;
import main.common.Vecteur;

public class Obj3D extends AObj{
	List<AObj> triangles;
	
	public Obj3D(List<AObj> triangles) {
		this.triangles = triangles;
	}
	
	@Override
	public double primitive(Vecteur ori, Vecteur dir, int lastId, Id id) {
		double min = -1;
		int minId = -1;
		double tmp;
		for(AObj triangle : triangles) {
			tmp = triangle.primitive(ori, dir, lastId, id);
			if (tmp > 0. && (min < 0. || tmp < min)) {
				min = tmp;
				minId = id.id;
			}
		}
		id.id = minId;
		return min;
	}

	@Override
	public void getTextureColor(Vecteur pos, Color c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void normal(Vecteur pos, Vecteur dir, int id, Vecteur ret) {
		for(AObj triangle : triangles) {
			if (triangle.id == id) {
				triangle.normal(pos, dir, id, ret);
				break ;
			}
		}
	}
}
