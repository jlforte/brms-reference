package com.rhc.trade;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.rhc.drools.reference.DroolsRequest;
import com.rhc.stock.StockDay;
import com.rhc.stock.StockQuote;

/**
 * 
 * @author Red Hat Consulting
 * 
 * Public class TradeRequest sends important stock information into  
 * the rules engine to execute.  
 * 
 * The getAllFacts() function is used by the StatelessDroolsComponent
 * to fire Drools executions on the objects set in the function.
 * 
 * The getProcessId() function is used by the StatelessDroolsComponent
 * to fire rules according to their given Rules Groups
 *
 */
public class TradeRequest implements DroolsRequest{

	private StockQuote quote;
	private StockDay day;
	
	public StockQuote getQuote() {
		return quote;
	}

	public void setQuote(StockQuote quote) {
		this.quote = quote;
	}

	public StockDay getDay() {
		return day;
	}

	public void setDay(StockDay day) {
		this.day = day;
	}

	@Override
	public Collection<Object> getAllFacts() {
		Set<Object> set = new HashSet<Object>();
		set.add(quote);
		set.add(day);
		return set;
	}

	@Override
	public String getProcessId() {
		return "com.rhc.drools.test";
	}

}
