package com.rhc.stock;

/**
 * 
 * @author Red Hat Consulting
 * 
 * Public class StockQuote holds the most current price and volatility of a given stock.
 * This information is meant to be as close to real-time as possible, and is the point-in-time
 * measure used to determine when it is advantageous to buy/sell stock.
 *
 */
public class StockQuote{
	
	private Stock stock;
	private Float price;
	private Float volatility;

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getVolatility() {
		return volatility;
	}

	public void setVolatility(Float volatility) {
		this.volatility = volatility;
	}

}
