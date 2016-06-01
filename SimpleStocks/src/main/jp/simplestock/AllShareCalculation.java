package main.jp.simplestock;

import java.util.ArrayList;

/**
 * Class containing method to calculate the all share index
 * @author Darren Whigham
 */
public class AllShareCalculation {
	private static Utility utility = Utility.getInstance();
	
	public  double calculateAllShareIndex(ArrayList<Double> volumeWeightStockPrices) {
		double priceProd = 1.0;
		for (Double price : volumeWeightStockPrices) {
			priceProd = priceProd * price;
		}
		return utility.calculateNthRoot(priceProd, volumeWeightStockPrices.size());
	}
}
