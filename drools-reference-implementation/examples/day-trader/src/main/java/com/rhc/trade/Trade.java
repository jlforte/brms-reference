package com.rhc.trade;

/**
 * 
 * @author Red Hat Consulting
 * 
 * Public class Trade holds an action (e.g. "buy", "sell", etc.) for 
 * a given situation, and a price point to use when bidding or asking.
 * This class is primarily utilized by the TradeResponse from the rules engine.
 *
 */
public class Trade {
	
	private Float price;
	private String action;
	
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
