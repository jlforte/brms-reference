package com.rhc.stock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Red Hat Consulting
 * 
 * Public class Stock holds information on a given stock listing, as well as the most recent quote
 * for the given stock.  The Stock class also holds a map with a history of days, 
 * which include day's highs, lows, etc.  Stock is the central reference object for the day-trader example.
 *
 */
public class Stock{
	
	private String name;
	private String ticker;
	private StockQuote quote;
	private Map<Date, StockDay> history = new HashMap<Date, StockDay>();
	
	public Map<Date, StockDay> getHistory() {
		return history;
	}

	public void setHistory(Map<Date, StockDay> history) {
		this.history = history;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public StockQuote getQuote() {
		return quote;
	}
	public void setQuote(StockQuote quote) {
		this.quote = quote;
	}
	
	
	/**
	 * Populates the history with trading information from the 
	 * past 30 calendar days from Yahoo Finance.
	 * Creates a URL to access Yahoo Finance and get information in .csv format.
	 * Parses .csv file and loads information into an instance of StockDay for each line.
	 * Each day is then loaded into history.
	 * 
	 * @throws IOException
	 * @throws ParseException
	 * @throws MalformedURLException
	 */
	public void populateHistory() throws IOException, ParseException, MalformedURLException {
		Date date = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    
	    // Set up a connection to the Yahoo Finance CSV url
	    URL url = new URL(getHistoryUrl());
	    URLConnection urlConn = url.openConnection();
	    InputStreamReader inStream = new InputStreamReader(urlConn.getInputStream());
	    BufferedReader buff = new BufferedReader(inStream);
	    
	    // Spreadsheet Header (1st row) is ommitted
	    buff.readLine();
	    
	    // Prepare each line of spreadsheet data and assign values to POJOs
	    String data;
	    String [] line;

	    while((data = buff.readLine()) != null){
	    	line = data.split(",");
	    	date = sdf.parse(line[0]);
	    	StockDay stockDay = new StockDay();
			stockDay.setDay(date);
			
			stockDay.setDayOpen((float) Double.parseDouble(line[1]));
			stockDay.setDayHigh((float) Double.parseDouble(line[2]));
			stockDay.setDayLow((float) Double.parseDouble(line[3]));
			stockDay.setDayClose((float) Double.parseDouble(line[4]));
			
			this.getHistory().put(date, stockDay);
	    }
	    
	    inStream.close();
	    this.updateQuote();
	}

	/**
	 * Updates the quote with information from the most recent day in history.
	 * 
	 */
	public void updateQuote() {
		StockQuote quote = new StockQuote();
		quote.setStock(this);

		List<Date> dates = new ArrayList<Date>();
		dates.addAll(getHistory().keySet());
		Collections.sort(dates);
		Collections.reverse(dates);
		
		StockDay stockDay = new StockDay();
		stockDay = getHistory().get(dates.get(0));
		
		quote.setPrice(stockDay.getDayClose());
		setQuote(quote);
	}
	
	/**
	 * Creates the URL to access Yahoo Finance historical information from last 30 calendar days.
	 * Uses the current date to get the day for a month earlier.
	 * Creates a URL of format needed to access Yahoo Finance.
	 *  
	 * @return URL to access Yahoo Finance data for last 30 days.
	 */
	private String getHistoryUrl(){
		String url = new String();
		StringBuffer sb = new StringBuffer();
		
		//Yahoo Finance requires the month field in the URL to be the current month - 1 (ex: October would be 9 instead of 10)
		Integer startMonth = Calendar.getInstance().get(Calendar.MONTH) - 1;
		Integer startDay = Calendar.getInstance().get(Calendar.DATE);
		Integer startYear = Calendar.getInstance().get(Calendar.YEAR);
		Integer endMonth = Calendar.getInstance().get(Calendar.MONTH);
		Integer endDay = Calendar.getInstance().get(Calendar.DATE);
		Integer endYear = Calendar.getInstance().get(Calendar.YEAR);
		
		sb.append("http://ichart.yahoo.com/table.csv?s=");
		sb.append(getTicker());
		sb.append("&a=");
		sb.append( startMonth );
		sb.append("&b=");
		sb.append(startDay);
		sb.append("&c=");
		sb.append(startYear);
		sb.append("&d=");
		sb.append( endMonth );
		sb.append("&e=");
		sb.append(endDay);
		sb.append("&f=");
		sb.append(endYear);
		
		url = sb.toString();
		return url;
		
	}
	
	/**
	 * Populates Bollinger bands of a specific day
	 * Based on availability of history, Bollinger Band information is calculated.
	 * The middle band is the average of the close price of days used for data.
	 * The upper band is the middle band + (2 * standard deviation of middle band).
	 * The lower band is the middle band - (2 * standard deviation of middle band).
	 * 
	 */
//	public void populateBollingerBands(StockDay day) {
//		//If there is not enough history, the band for that day will not be created
//		//If there is enough history, the band will be created using all historical information available
//		if(!history.isEmpty() && history.size() > BOLLINGER_LENGTH) {
//			float middleBand = new Float(0);
//			float upperBand = new Float(0);
//			float lowerBand = new Float(0);
//			
//			// Determine the middle Bollinger Band
//			for (Date date : getHistory().keySet()){
//					middleBand += history.get(date).getDayClose() ;
//					
//			}
//			middleBand /= history.size();
//			day.setMiddleBand(middleBand);
//			
//			float sd = standardDeviation(middleBand);
//			
//			// Determine the bands based on the standard deviation
//			upperBand = middleBand + 2*sd;
//			lowerBand = middleBand - 2*sd;
//			day.setUpperBand(upperBand);
//			day.setLowerBand(lowerBand);
//			
//			System.out.println(day.getDay().toString() + "--MB--" + middleBand + "--SD--" + sd + "--LB--" + lowerBand + "--UB--" + upperBand);
//		}
//	}
//	
	
	/**
	 * Finds the standard deviation of the history given the average
	 */
//	private float standardDeviation(float average) {
//		// Determine the std deviation for the Upper & Lower Bands
//		float sd = new Float(0.0);
//		for (Date date : getHistory().keySet()){
//				sd = (float) (sd + Math.pow(history.get(date).getDayClose() - average, 2));
//		}
//		
//		sd = (float) Math.sqrt(sd/(history.size()-1));
//		return sd;
//	}
		
}
