package main.jp.simplestock;

public class Utility {
	private static Utility instance = null;
	
	public Utility() {
		
	}
	
	public static Utility getInstance() {
		if (instance == null) {
			instance = new Utility();
		} 
		return instance;
	}
	
	public double calculateNthRoot(double priceProduct, int root) {
		return Math.pow(Math.E, Math.log(priceProduct)/root);
	}

}
