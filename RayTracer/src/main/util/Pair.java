package main.util;

public class Pair<K, T> {
	private K a;
	private T b;
	
	public Pair(K a, T b) {
		this.a = a;
		this.b = b;
	}
	
	public Pair() {
	}

	public void setA(K a) {
		this.a = a;
	}
	
	public K getA() {
		return a;
	}

	public void setB(T b) {
		this.b = b;
	}
	
	public T getB(){
		return b;
	}
}
