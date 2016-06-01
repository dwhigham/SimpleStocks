package main.jp.simplestock;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApplicationLauncher {
	private static Logger LOGGER = LogManager.getLogger(ApplicationLauncher.class);
	private static Hashtable<String, Stock> stocks = new Hashtable<String, Stock>();
	private static AllShareCalculation allShare = new AllShareCalculation();
	
	public static void main(String[] args) {
		stocks.put("TEA", new Stock("TEA", StockType.COMMON, 0.0, 0.0, 100.0));
		stocks.put("POP", new Stock("POP", StockType.COMMON, 8.0, 0.0, 100.0));
		stocks.put("ALE", new Stock("ALE", StockType.COMMON, 23.0, 0.0, 60.0));
		stocks.put("GIN", new Stock("GIN", StockType.PREFERRED, 8.0, 0.02, 100.0));
		stocks.put("JOE", new Stock("JOE", StockType.COMMON, 13.0, 0.0, 205.0));
		
		ArrayList<Double> volumeWeightStockPrices = new ArrayList<Double>();
		
		try {
			for (Map.Entry<String, Stock> stock : stocks.entrySet()) {
				Stock stockValue = stock.getValue();
			    LOGGER.info("Dividend obtained for Stock "+ stockValue.getStockSymbol() + 
			    		" Price: 12.0 is " + stockValue.calculateDividend(12.0));
			    LOGGER.info("P/E Ratio obtained for Stock "+ stockValue.getStockSymbol() +
			    		" Price: 9.0 is " + stockValue.calculatePERatio(9.0));
			    
			    // Record trades
			    for (int i = 0; i < 4; i++) {
				    // Randomly generate the price, quantity and the trade indicator
				    double randomPrice = ThreadLocalRandom.current().nextDouble(1.0, 50.0 + 1.0);
				    double randomQty = (randomPrice/(2)) + 1;
				    TradeIndicator type = (ThreadLocalRandom.current().nextInt() < 0) ? 
				    								TradeIndicator.BUY : TradeIndicator.SELL;
				    Trade trade = new Trade(randomQty, type, randomPrice);
					stockValue.updateTradeList(trade);
				    LOGGER.info(trade.toString());
				    Thread.sleep(1000); // 1 sec
			    }
			    double volumeWeightStockPrice = stockValue.calculateVolumeWeightStockPrice();
			    LOGGER.info("Volume Weighted Stock Price for Stock Symbol " + stockValue.getStockSymbol() +  
			    							 " " + volumeWeightStockPrice);
			    volumeWeightStockPrices.add(volumeWeightStockPrice);
			    
			    LOGGER.info("All share index is: " + allShare.calculateAllShareIndex(volumeWeightStockPrices));
			    
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
	}
}
