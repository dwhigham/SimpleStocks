package main.jp.simplestock;

import java.util.Date;

public class Trade implements Comparable<Trade> {
	private Date tradeTime;
	private double quantity;
	private TradeIndicator tradeIndicator;
	private double price;
	
	public Trade(double quantity, TradeIndicator tradeIndicator, double price) {
		this.tradeTime = new Date();
		this.quantity = quantity;
		this.tradeIndicator = tradeIndicator;
		this.price = price;
	}

	public Trade(Date date, double quantity, TradeIndicator tradeIndicator, double price) {
		this.tradeTime = date;
		this.quantity = quantity;
		this.tradeIndicator = tradeIndicator;
		this.price = price;
	}
	
	public double getPrice() {
		return price;
	}

	public double getQuantity() {
		return quantity;
	}

	public Date getDate() {
		return tradeTime;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Trade details : ");
		str.append(" Date - " + tradeTime);
		str.append(" Quantity - " + quantity);
		str.append(" Price - " + price);
		str.append(" Indicator - " + tradeIndicator);
		return str.toString();
	}

	/**
	 * Sort the Trade with older being last
	 */
	@Override
	public int compareTo(Trade o) {
	   if (this.tradeTime.after(o.tradeTime)) {
		   return -1;
	   } else if (this.tradeTime.before(o.tradeTime)) {
		   return 1;
	   } else return 0;
	}
}
