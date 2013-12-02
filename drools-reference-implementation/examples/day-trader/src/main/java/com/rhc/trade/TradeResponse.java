package com.rhc.trade;


import java.util.Collection;

import com.rhc.drools.reference.DroolsQueryInfo;

/**
 * 
 * @author Red Hat Consulting
 * 
 * Public class TradeResponse receives important trading information 
 * from the rules engine to determine how to react to given market conditions
 * 
 */
public class TradeResponse {
	@DroolsQueryInfo(binding = "$buyTrade", queryName = "Get buy trades")
	private Collection<Trade> buyTrades;
	
	@DroolsQueryInfo(binding = "$sellTrade", queryName = "Get sell trades")
	private Collection<Trade> sellTrades;
	
	public Collection<Trade> getBuyTrades() {
		return buyTrades;
	}


	public void setBuyTrades(Collection<Trade> buyTrades) {
		this.buyTrades = buyTrades;
	}


	public Collection<Trade> getSellTrades() {
		return sellTrades;
	}


	public void setSellTrades(Collection<Trade> sellTrades) {
		this.sellTrades = sellTrades;
	}


	/**
	 * Returns the first trade in buy trades
	 * 
	 * @return first Trade in buy trades
	 */
	public Trade getBuyTrade() {
		return (buyTrades.iterator().hasNext() ? buyTrades.iterator().next() : null);
	}
	
	/**
	 * Returns the first trade in sell trades
	 * 
	 * @return first Trade in sell trades
	 */
	public Trade getSellTrade() {
		return (sellTrades.iterator().hasNext() ? sellTrades.iterator().next() : null);
	}

}
