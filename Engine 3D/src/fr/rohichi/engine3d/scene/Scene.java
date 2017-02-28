package fr.rohichi.engine3d.scene;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import fr.rohichi.engine3d.Intersection;
import fr.rohichi.engine3d.Ray;

public class Scene {
	private List<Triangle> listTriangles;
	private Triangle[] triangles;
	
	public Scene() {
		listTriangles = new LinkedList<>();
	}
	
	public void addFileObject(String fileName) throws NumberFormatException, IOException {
		listTriangles.addAll(Object3D.parseFile(fileName));
	}
	
	public void addTriangle(Triangle triangle) {
		listTriangles.add(triangle);
	}
	
	public void prepareBeforeGetIntersection() {
		triangles = new Triangle[listTriangles.size()];
		listTriangles.toArray(triangles);
		listTriangles.clear();
	}
	
	public Intersection getIntersection(final Ray ray) {
		Intersection min = new Intersection();
		for (Triangle triangle : triangles) {
			Intersection inter = triangle.getIntersection(ray);
			if (inter != null) {
				if (inter.getDist() < min.getDist()) {
					min = inter;
				}
			}		
		}
		if (min.getDist() == Double.MAX_VALUE)
			return null;
		return min;
	}
}
