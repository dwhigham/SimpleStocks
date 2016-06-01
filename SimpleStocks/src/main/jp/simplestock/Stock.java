package main.jp.simplestock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Stock {
	private String stockSymbol;
	private StockType type;
	private double lastDividend;
	private double fixedDividend;
	private double parValue;
	private ArrayList<Trade> tradeList;
	private static Logger LOGGER = LogManager.getLogger(Stock.class);
	
	public Stock(String stockSymbol, StockType type, double lastDividend, double fixedDividend, double parValue) {
		this.stockSymbol = stockSymbol;
		this.type = type;
		this.lastDividend = lastDividend;
		this.fixedDividend = fixedDividend;
		this.parValue = parValue;
		tradeList = new ArrayList<Trade> ();
	}
	
	public String getStockSymbol() {
		return stockSymbol;
	}

	public double getLastDividend() {
		return lastDividend;
	}
	
	public double getFixedDividend() {
		return fixedDividend;
	}
	
	public double getParValue() {
		return parValue;
	}

	public ArrayList<Trade> getTrades() {
		Collections.sort(tradeList);
		return tradeList;
	}
	
	public double calculateDividend(double price) throws Exception {
		double dividend = 0.0;
		if (price >= 0.0) {
			if (type.equals(StockType.COMMON)) {
				dividend = lastDividend/price;
			} else {
				dividend = (fixedDividend * parValue)/price;
			}
		} else {
			throw new Exception("Calculating the Dividend: Price has to be greater than ZERO");
		}
		
		return dividend;
	}
	
	public double calculatePERatio(double price) throws Exception {
		double ratio = 0;
		if (price <= 0.0) {
			throw new Exception("Calculating the P/E Ratio: Price has to be greater than ZERO");	
		}
		
		double dividend = calculateDividend(price);
		if (dividend > 0.0) {
			ratio = price/calculateDividend(price);
		} 
		return ratio; // Return 0.0 if dividend is 0.0
	}
	
	public void updateTradeList(Trade trade) {
		tradeList.add(trade);
	}
	
	public double calculateVolumeWeightStockPrice() {
		double totalPriceQtyProduct = 0.0;
		double totalQty = 0.0;
		
		if (tradeList.size() == 0) {
			return 0.0;
		}
		
		// Sort the ArrayList to prevent unnecessary checks for time range
		Collections.sort(tradeList);
		
		long now = Calendar.getInstance().getTimeInMillis();
		Date fifteenMinutesBack = new Date(now - 900000); // Current time - 15 minutes
		//Need better way to get back 15 min
		LOGGER.debug("Calculating Volume Weight Stock Price for trades between: " 
				+ fifteenMinutesBack + " to now.");
		for (Trade trade : tradeList) {
			if (trade.getDate().after(fifteenMinutesBack)) {
				totalPriceQtyProduct += trade.getPrice() * trade.getQuantity() ;
				totalQty += trade.getQuantity();
			} else break;
		}
		
		return totalPriceQtyProduct/totalQty;
	}
}
