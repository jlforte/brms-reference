package com.rhc.stock;

import java.util.Date;

/**
 * 
 * @author Red Hat Consulting
 * 
 * Public class StockDay is one day of information for a given Stock.
 * This information is crucial in providing information to develop
 * an informed trading algorithm.  
 * 
 * StockDay is a sortable class and is compared by Date day, which 
 * is a required field of the class in order to be sortable.
 *
 */
@SuppressWarnings("rawtypes")
public class StockDay implements Comparable{

	private Stock stock;
	private Float dayOpen;
	private Float dayClose;
	private Float dayLow;
	private Float dayHigh;
	private Date day;
	
	public StockDay() {
		day = new Date();
	}
	
	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Float getDayOpen() {
		return dayOpen;
	}

	public void setDayOpen(Float dayOpen) {
		this.dayOpen = dayOpen;
	}

	public Float getDayClose() {
		return dayClose;
	}

	public void setDayClose(Float dayClose) {
		this.dayClose = dayClose;
	}

	public Float getDayHigh() {
		return dayHigh;
	}

	public void setDayHigh(Float dayHigh) {
		this.dayHigh = dayHigh;
	}

	public Float getDayLow() {
		return dayLow;
	}

	public void setDayLow(Float dayLow) {
		this.dayLow = dayLow;
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	@Override
	public int compareTo(Object o) {
		return this.getDay().compareTo(((StockDay) o).getDay());
	}
	

}
