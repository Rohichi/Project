package main.util;

import java.util.Collection;
import java.util.TreeSet;

public class Solver {
		
	static public double solve(double a, double b) {
		if (a == 0)
			return -1;
		return -(b / a);
	}
	
	static public Collection<Double> solve(double a, double b, double c) {
		Collection<Double> ret = new TreeSet<Double>(); 
		if (a == 0.) {
			ret.add(solve(b, c));
			return ret;
		}
		double delta = b*b - 4*a*c;
		if (delta == 0.)
			ret.add(Double.valueOf(-b / (2. * a)));
		else if (delta > 0.)
		{
			ret.add(Double.valueOf((-b + Math.sqrt(delta)) / (2.*a)));
			ret.add(Double.valueOf((-b - Math.sqrt(delta)) / (2.*a)));
		}
		return ret;
	}

	static public Collection<Double> solve(double a, double b, double c, double d) {
		if (a == 0.)
			return solve(b, c ,d);
		double p = c / a - (b * b)/ (3 * a * a);
		double q = 2 * Math.pow(b/(3*a), 3) - (b*c)/(3*a*a) + d/a;
		double delta = q * q + 4 * Math.pow(p/3, 3);
		Collection<Double> ret = new TreeSet<Double>();
		if (delta > 0.){
			delta = Math.sqrt(delta);
			ret.add(Double.valueOf(Math.cbrt((-q + delta) / 2) + Math.cbrt((-q - delta) / 2) - b / (3*a)));
		}
		else if (delta == 0.){
			delta = b / (3*a);
			a = (3*q)/p - delta;
			b =-(3*q)/(2*p) - delta;
			if (a != b)
				ret.add(Double.valueOf(b));
			ret.add(Double.valueOf(a));
		}
		else{
			delta = Math.acos((3 * q * Math.sqrt(-3/p))/ (2 * p));
			c = 2 * Math.sqrt(-p/3);
			d = b / (3*a);
			ret.add(Double.valueOf(c * Math.cos(delta / 3) - d));
			ret.add(Double.valueOf(c * Math.cos((delta + 2 * Math.PI) / 3) - d));
			ret.add(Double.valueOf(c * Math.cos((delta + 4 * Math.PI) / 3) - d));
		}
		return ret;
	}
	
	/*static public Collection<Double> solve(double a, double b, double c, double d, double e) {
		Collection<Double> ret;
		b /= a;
		c /= a;
		d /= a;
		e /= a;
		a = 1;
		if (e == 0) {
			ret = solve(a, b, c, d);
			ret.add(Double.valueOf(0));
			return ret;
		}
		double b2 = (3*b*b / 8 - c) /2; 
		double b1 = 3 * Math.pow(b, 4) / 256 - b*b*c/16 + b*d/4 - e;
		double b0 = b1*b2 - Math.pow(b*b*b/16 - b*c/4 + d/2, 2); 
		Collection<Double> tmp = solve(1, b2, b1, b0);
		if (tmp.isEmpty())
			return new TreeSet<Double>();
		double z0 = 0;
		ret = new TreeSet<Double>();
		for (Double racine : tmp) {
			z0 = racine.doubleValue();
			break ;
		}
		double delta = 2 * (z0 + b2);
		double delta2 = 0;
		if (delta < 0)
			return ret;
		else if (delta == 0) {
			delta2 = z0*z0 + b1;
			if (delta2 < 0)
				return ret;
			delta2 = Math.sqrt(delta2);
		}
		else 
			delta2 = Math.sqrt((b2*b1 - b0)/ delta);
		
		
		return ret;
	}*/
}
