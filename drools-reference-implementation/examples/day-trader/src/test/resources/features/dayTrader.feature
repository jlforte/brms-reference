Feature: Determine Day Trader

@Ask
Scenario: Determine to ask

Given a current price of "26.00" for a stock "GOOG"
And a day open of "25.00"
And a daily volatility of ".25" USD

When determining an action for stock "GOOG" 

Then ask to sell stock "GOOG" for "25.99"


@Bid
Scenario: Determine to bid

Given a current price of "10.00" for a stock "GOOG"
And a day open of "11.00"
And a daily volatility of ".25" USD

When determining an action for stock "GOOG"

Then bid to buy stock "GOOG" for "10.01"


@DoNotSell
Scenario: Determine not to ask

Given a current price of "25.10" for a stock "GOOG"
And a day open of "25.00"
And a daily volatility of ".25" USD

When determining an action for stock "GOOG" 

Then do not ask to sell stock "GOOG"


@DoNotBuy
Scenario: Determine not to bid

Given a current price of "10.90" for a stock "GOOG"
And a day open of "11.00"
And a daily volatility of ".25" USD

When determining an action for stock "GOOG"

Then do not bid to buy stock "GOOG"