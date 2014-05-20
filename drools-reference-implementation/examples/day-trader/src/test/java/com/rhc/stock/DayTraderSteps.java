package com.rhc.stock;

import javax.annotation.Resource;

import org.drools.logger.KnowledgeRuntimeLogger;
import org.springframework.test.context.ContextConfiguration;

import com.rhc.drools.reference.StatelessDroolsComponent;
import com.rhc.trade.Trade;
import com.rhc.trade.TradeRequest;
import com.rhc.trade.TradeResponse;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@ContextConfiguration
public class DayTraderSteps {
	
	@Resource(name = "stocksDroolsComponent")
	private StatelessDroolsComponent drools;
	static KnowledgeRuntimeLogger logger;

	private Stock stock = new Stock();
	private StockDay day = new StockDay();
	private TradeResponse tradeResponse;

	@Before
	public void init() {
	}
	
	@Given("^a current price of \"([^\"]*)\" for a stock \"([^\"]*)\"$")
	public void a_current_price_of_for_a_stock(String price, String name) throws Throwable {
	   
	    stock.setName(name);
	    StockQuote quote = new StockQuote();
	    quote.setPrice(Float.valueOf(price));
	    quote.setStock(stock);
	    stock.setQuote(quote);
	}

	@Given("^a day open of \"([^\"]*)\"$")
	public void a_day_open_of(String open) throws Throwable {
	 
		day.setDayOpen(Float.valueOf(open));
		day.setStock(stock);
	}

	@Given("^a daily volatility of \"([^\"]*)\" USD$")
	public void a_daily_volatility_of_USD(String volatility) throws Throwable {

		stock.getQuote().setVolatility(Float.valueOf(volatility));
	}

	
	@When("^determining an action for stock \"([^\"]*)\"$")
	public void determining_an_action_for_stock(String arg1) throws Throwable {
		
		TradeRequest tradeRequest = new TradeRequest();
		tradeRequest.setDay(day);
		tradeRequest.setQuote(stock.getQuote());
		
		tradeResponse = drools.execute(tradeRequest, TradeResponse.class);
	}
	

	@Then("^ask to sell stock \"([^\"]*)\" for \"([^\"]*)\"$")
	public void ask_to_sell_stock_for(String name, String price) throws Throwable {

		Trade trade = tradeResponse.getSellTrade();
		if (trade != null) {
			if (trade.getPrice().equals(Float.valueOf(price))
					&& trade.getStock().getName().equals(name))
				System.out.println("Sell stock of " + name + " for " + price );
			
		}
	}

	@Then("^bid to buy stock \"([^\"]*)\" for \"([^\"]*)\"$")
	public void bid_to_buy_stock_for(String name, String price) throws Throwable {

		Trade trade = tradeResponse.getBuyTrade();
		if (trade != null) {
			if (trade.getPrice().equals(Float.valueOf(price))
					&& trade.getStock().getName().equals(name))
				System.out.println("Buy stock of " + name + " for " + price );
		}
	}
	
	@Then("^do not ask to sell stock \"([^\"]*)\"$")
	public void do_not_ask_to_sell_stock(String name) throws Throwable {

		Trade trade = tradeResponse.getSellTrade();
		if (trade == null) {
			System.out.println("Don't sell stock of " + name );		
		}
	}

	@Then("^do not bid to buy stock \"([^\"]*)\"$")
	public void do_not_bid_to_buy_stock(String name) throws Throwable {

		Trade trade = tradeResponse.getBuyTrade();
		if (trade == null) {
			System.out.println("Don't buy stock of " + name );		
		}
	}


}
