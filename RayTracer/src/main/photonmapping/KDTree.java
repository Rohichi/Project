package main.photonmapping;

import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.Vector;

import main.common.Vecteur;

public class KDTree {
	private static short X = 0; 
	private static short Y = 1;
	private static short Z = 2;
	
	public static void print(Photon p, int n) {
		if (p != null) {
			for (int i = 0; i < n; i++)
				System.out.print("  ");
			System.out.println(p.getPos().toStr() + " " + p.getFlag());
			print(p.getLeft(), n + 1);
			print(p.getRight(), n + 1);
		}
	}
	
	
	private static short chooseDim(Vector<Photon> photons) {
		Vecteur tmp = photons.get(0).getPos();
		double maxx = tmp.getX();
		double maxy = tmp.getY();
		double maxz = tmp.getZ();
		double minx = tmp.getX();
		double miny = tmp.getY();
		double minz = tmp.getZ();
		
		for (Photon photon : photons) {
			tmp = photon.getPos();
			maxx = (maxx < tmp.getX() ? tmp.getX() : maxx);
			maxy = (maxy < tmp.getY() ? tmp.getY() : maxy);
			maxz = (maxz < tmp.getZ() ? tmp.getZ() : maxz);
			minx = (minx > tmp.getX() ? tmp.getX() : minx);
			miny = (miny > tmp.getY() ? tmp.getY() : miny);
			minz = (minz > tmp.getZ() ? tmp.getZ() : minz);
		}
		maxx -= minx;
		maxy -= miny;
		maxz -= minz;
		if (maxx > maxy)
			return (maxx > maxz ? X : Z);
		else
			return (maxy > maxz ? Y : Z);
	}
		
	public static Photon balance(Vector<Photon> photons) {
		if (photons.isEmpty())
			return (null);
		
		short dim = chooseDim(photons);

		if (dim == X) {
			Collections.sort(photons, new Comparator<Photon>() {
		        @Override
		        public int compare(Photon p1, Photon p2) {
		        	double tmp = p2.getPos().getX() - p1.getPos().getX(); 
		        	if (tmp == 0.)
		        		return (0);
		        	return (tmp < 0 ? -1 : 1);
		        }           
		    });
		}
		else if (dim == Y) {
			Collections.sort(photons, new Comparator<Photon>() {
		        @Override
		        public int compare(Photon p1, Photon p2) {
		        	double tmp = p2.getPos().getY() - p1.getPos().getY(); 
		        	if (tmp == 0.)
		        		return (0);
		        	return (tmp < 0 ? -1 : 1);
		        }           
		    });
		}
		else {
			Collections.sort(photons, new Comparator<Photon>() {
		        @Override
		        public int compare(Photon p1, Photon p2) {
		        	double tmp = p2.getPos().getZ() - p1.getPos().getZ(); 
		        	if (tmp == 0.)
		        		return (0);
		        	return (tmp < 0 ? -1 : 1);
		        }           
		    });
		}
		int ind = photons.size() / 2;
		
		Photon median = photons.elementAt(ind);
		median.setFlag(dim);
		
		Vector<Photon> sub = new Vector<Photon>();
		for (int i = 0; i < ind; i++)
			sub.addElement(photons.elementAt(i));
		median.setLeft(balance(sub));
		
		sub.clear();
		for (int i = ind + 1; i < photons.size(); i++)
			sub.addElement(photons.elementAt(i));
		median.setRight(balance(sub));
		return median;
	}
	
	private static int knnCmp(Photon photon, Vecteur pos){
		if (photon.getFlag() == X)
			return (int)(photon.getPos().getX() - pos.getX());
		else if (photon.getFlag() == Y)
			return (int)(photon.getPos().getY() - pos.getY());
		else
			return (int) (photon.getPos().getZ() - pos.getZ());
	}
	
	private static boolean knnCheck(Vecteur pos, Photon photon, double len) {
		if (photon.getFlag() == X){
			if (pos.getX() > photon.getPos().getX())
				return (pos.getX() - photon.getPos().getX() < len);
			return (photon.getPos().getX() - pos.getX() < len);			
		}
		else if (photon.getFlag() == Y){
			if (pos.getY() > photon.getPos().getY())
				return (pos.getY() - photon.getPos().getY() < len);
			return (photon.getPos().getY() - pos.getY() < len);
		}
		if (pos.getZ() > photon.getPos().getZ())
			return (pos.getZ() - photon.getPos().getZ() < len);
		return (photon.getPos().getZ() - pos.getZ() < len);
	}
	
	private static void knnAdd(Photon photon, Vecteur pos, TreeSet<Neighbor> ret, double dist, int k) {
		if (ret.size() < k)
		{
			ret.add(new Neighbor(photon, dist));
			return;
		}
		Neighbor last = ret.last(); 
		if (last.getDist() > dist) {
			ret.remove(last);
			ret.add(new Neighbor(photon, dist));
		}
	}
	
	private static void knnCore(Vecteur pos, Photon photon, TreeSet<Neighbor> ret, int k) {
		if (photon == null)
			return;
		double dist = photon.getPos().dist(pos);
		knnAdd(photon, pos, ret, dist, k);
		if (knnCmp(photon, pos) > 0) {
			knnCore(pos, photon.getLeft(), ret, k);
			if (ret.size() < k || knnCheck(pos, photon, dist))
				knnCore(pos, photon.getRight(), ret, k);
		}
		else {
			knnCore(pos, photon.getRight(), ret, k);
			if (ret.size() < k || knnCheck(pos, photon, dist))
				knnCore(pos, photon.getLeft(), ret, k);
		}
	}
	
	public static TreeSet<Neighbor> knn(Vecteur pos, Photon photon, int k) {
		TreeSet<Neighbor> ret = new TreeSet<Neighbor>();
		knnCore(pos, photon, ret, k);
		return ret;
	}
	
}
