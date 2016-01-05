package main.util;

import java.lang.reflect.Field;

import main.common.Color;
import main.common.Vecteur;
import main.object.AObj;
/*
public class AObjFactory {

	public void creat(AObj ret) {
		try {
			Field[] fields = ret.getClass().getFields();
			System.out.println("*------*");
			System.out.println(ret.getClass().getSimpleName());
			for(Field f : fields) {
				System.out.print(f.getType().getName() + " " + f.getName() + " = ");
				if (f.getType() == Integer.TYPE)
						System.out.println(f.getInt(ret));
				else if (f.getType() == Double.TYPE) {
					if (f.getName() == "alpha")
						System.out.println(f.getDouble(ret) * 180 / Math.PI);
					else
						System.out.println(f.getDouble(ret));
				}
				else if (f.getType() == main.common.Vecteur.class) {
					Vecteur v = (Vecteur)f.get(ret);
					if (f.getName() == "rot")
						System.out.println("(" + v.x * 180 / Math.PI + ", "+ v.y * 180 / Math.PI + ", " + v.z * 180 / Math.PI + ")");
					else
						System.out.println("(" + v.x + ", "+ v.y + ", " + v.z + ")");
				}
				else if (f.getType() == main.common.Color.class) {
					Color c = (Color)f.get(ret);
					if (f.getName() == "color")
						System.out.println("(" + (int)(c.red * 255) + ", "+ (int)(c.green * 255) + ", " + (int)(c.blue * 255) + ")");
					else
						System.out.println("(" + c.red + ", "+ c.green + ", " + c.blue + ")");
				}
				else
					System.out.println();
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
*/