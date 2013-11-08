package com.rhc.trade;

import com.rhc.stock.Stock;

/**
 * 
 * @author Red Hat Consulting
 * 
 * Public class Trade holds a stock, an action (e.g. "buy", "sell", etc.) for 
 * a given situation, and a price point to use when bidding or asking.
 * This class is primarily utilized by the TradeResponse from the rules engine.
 *
 */
public class Trade {
	
	private Stock stock;
	private Float price;
	private String action;
	
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
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
}
