package main.execption;

public class JsonElementMissing extends Exception{
	private static final long serialVersionUID = -4720042153444635887L;

	public JsonElementMissing(String elemName) {
		super("Invalid Json File : Element \"" + elemName + "\" missing.");
	}
}
