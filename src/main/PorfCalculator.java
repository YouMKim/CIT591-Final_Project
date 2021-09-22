package main;

import java.text.DecimalFormat;
import java.util.HashMap;

/** Class that can calculate the relevant metrics for a given porfolio 
 * These are the metrics that will be on display for the user
 * @author youmkim**/
public class PorfCalculator {
	private Portfolio porf;
	DecimalFormat df = new DecimalFormat();
	
	public PorfCalculator(Portfolio porfolio) {
		porf = porfolio;
		df.setMaximumFractionDigits(2);
	}
	
	/**
	 * Getter method for getting the portfolio object inside of the potfolio calculator
	 */
	
	public Portfolio getPorf() {
		return porf;
	}
	
	/**
	 * Method to calculate the net dollar made on each individual stock through out the full history
	 * When the stock does not exist in the portfolio, return 0
	 * @param String symbol : the stock ticker you are interested in 
	 */
	public String calcRawReturn(String symbol) {
		//The net change in total return would be, (money made investing + current worth of stock) - money spent investing
		return df.format(porf.moneyMade(symbol) + porf.getStockWorth(symbol) - porf.moneyInvested(symbol));
	}
	
	/**
	 * Method to calculate the net percent return on investment of a stock through out the full history
	 * When the stock does not exist in the portfolio, return 0% 
	 * @param String symbol : the stock ticker you are interested in 
	 */
	public String calcPercentReturn(String symbol) {
		//(total money gained - total money spent)/(total money spent)
		return df.format((porf.moneyMade(symbol) - porf.moneyInvested(symbol) + 
				porf.getStockWorth(symbol))/porf.moneyInvested(symbol) * 100)+ "%";
	}
	
	/**
	 * Method to calculate the net dollar made on a stock on one day basis
	 * When the stock does not exist in the portfolio, return 0
	 * @param String symbol : the stock ticker you are intersted in 
	 */
	public Double TodayRawReturn(String symbol) {
		//yesterday's porfolio worth 
		Double ystdPrice = porf.getPosition().get(symbol).getStockClosePrice(1);
		Integer stockCount = porf.getShares().get(symbol);
		//todays stock value
		return porf.getStockWorth(symbol) -  ystdPrice * stockCount;
	}
	
	/**
	 * Method to calculate the net percent return on investment of stock on one day basis
	 * When the stock does not exist in the portfolio, return 0% 
	 * @param String symbol : the stock ticker you are interested in 
	 */
	public String calcTodayPercentReturn(String symbol) {
		Double ystdPrice = porf.getPosition().get(symbol).getStockClosePrice(1);
		Integer stockCount = porf.getShares().get(symbol);
		//(Today Raw Return)/(Yesterday's total value)
		return df.format(TodayRawReturn(symbol)/(ystdPrice * stockCount)*100) + "%";
	}
	
	/**
	 * Method to calculate the Average cost of the stock in the porfolio in the transaction history
	 * when the stock does not exist in the portfolio, return 0
	 * @param String symbol : the stock ticker you are intersted in 
	 */
	public String avgCost(String symbol) {
		//Average cost of a stock is calculated as
		//(Total Money Invested)/ open positions
		return df.format(porf.moneyInvested(symbol) / porf.getShares().get(symbol));
	}
	
	/**
	 * Method to calculate the porfolio diversity measure
	 * Displays what % of current porfolio is currently occupied by this stock
	 * @param String symbol : the stock ticker you are interested in
	 * @return Double - the % a particular stock takes in the entire porfolio
	 */
	public String porfDiversity(String symbol) {
		// Stock's total worth / Total Portfolio Worth
		return df.format(porf.getStockWorth(symbol)/porf.getPorfWorth());
	}
	
	
	/**
	 * Method to calculate the break-even point for the investor for the stock
	 * This calcualtes how much more the share has to be worth for the person to not lose money
	 * @param porf
	 * @return Double Dollar value that represents the breakeven point, or price the stock has to reach to break even
	 */
	public Double breakEven(String symbol) {
		//(money Spent on Stock - current value)/ current share
		return (porf.moneyInvested(symbol) - porf.getStockWorth(symbol))/porf.getShares().get(symbol);
	}
	
	//Method that returns the total net amount of $ the porfolio has gained overall 
	public Double totalReturn()  {
		Double total = 0.0;
		for(String symbol : porf.getShares().keySet()) {
			total += porf.moneyMade(symbol) + porf.getStockWorth(symbol) - porf.moneyInvested(symbol);
		}
		return total;
	}
	
	//Method that returns the total net % the porfolio has gained overall 
	public String percentReturn() {
		Double totalInvested = 0.0;
		for(String symbol : porf.getShares().keySet()) {
			totalInvested += porf.moneyInvested(symbol);
		}
		return df.format(totalReturn()/totalInvested * 100) + "%";
	}
}
