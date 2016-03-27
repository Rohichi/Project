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
		if (a == 0.)
			ret.add(solve(b, c));
		else {
			double delta = b*b - 4*a*c;
			if (delta == 0.)
				ret.add(Double.valueOf(-b / (2. * a)));
			else if (delta > 0.)
			{
				ret.add(Double.valueOf((-b + Math.sqrt(delta)) / (2.*a)));
				ret.add(Double.valueOf((-b - Math.sqrt(delta)) / (2.*a)));
			}
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
			if (p != 0)
			{
				a = (3*q)/p - delta;
				b =-(3*q)/(2*p) - delta;
				if (a != b)
					ret.add(Double.valueOf(b));
				ret.add(Double.valueOf(a));
			}
			else
				ret.add(Double.valueOf(Math.cbrt(-q)));
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
	
	
	static public Collection<Double> solve(double a, double b, double c, double d, double e) {
		if (a == 0.)
			return solve(b,c,d,e);
		Collection<Double> ret;
		if (e == 0) {
			ret = solve(a, b, c, d);
			ret.add(Double.valueOf(0));
			return ret;
		}
		b /= a;
		c /= a;
		d /= a;
		e /= a;
		a = 1;
		double p = (-3*b*b / 8 + c); 
		double q =  Math.pow(b / 2, 3) - b*c/2 + d;
		double r = -3 * Math.pow(b / 4, 4) + c * Math.pow(b/4, 2) - b*d/4 + e;
		double y0 = 0;
		Collection<Double> subret = solve(8, -4*p, -8*r,4*r*p-q*q);
		for(Double x0 : subret) {
			y0 = x0;
		}
		double a0 = 2*y0 - p;
		if (a0 < 0)
			return (new TreeSet<Double>());
		a0 = Math.sqrt(a0);
		double b0 = (a0 == 0 ? y0*y0-r : -q/(2*a0));
		ret = solve(1, a0, y0 + b0);
		ret.addAll(solve(1, -a0, y0 - b0));
		subret.clear();
		for (Double double1 : ret) {
			subret.add(Double.sum(double1.doubleValue(), - b/4));
		}
		return subret;
	}
}
