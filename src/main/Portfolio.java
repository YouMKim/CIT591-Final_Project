package main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * A class that represents a porfolio of stocks and metrics associated with it
 * @author youmk
 */

public class Portfolio {
	//a hashmap for position data with ticker as key and stockdata as value
	private HashMap<String, StockData> position;
	//a hashmap for transaction data with ticker as key and arraylist of transactions as value
	private HashMap<String, ArrayList<Transactions>> trxnHist;	
	//a hashmap for shares data with ticker as key and quantity as value
	private HashMap<String, Integer> shares;
	
	/**
	 * Constructor for the portfolio class
	 * Position: a snapshot of the amount of stocks in a portfolio
	 * trxnHist: The full history of the transaction of a portfolio
	 * share: current number of shares in a portfolio
	 */
	public Portfolio() {
		position = new HashMap<String, StockData>();
		trxnHist = new HashMap<String, ArrayList<Transactions>>();
		shares = new HashMap<String, Integer>();
	}
	
	/**
	 * Getter Methods for the porfolio class
	 * 
	 */
	
	public HashMap<String, StockData> getPosition() {
		return position;
	}
	
	public HashMap<String, ArrayList<Transactions>> getTrxnHist(){
		return trxnHist;
	}
	
	public HashMap<String, Integer> getShares(){
		return shares;
	}
	
	

	
	/**
	 * Method to add a stock data to the portfolio object
	 * If the stock does not exist in the hashmap, the stock is added, and the share is set at 0
	 * @param sd StockData that is to be added
	 * @param shares the number of shares bought (When share is negative that means the stock is being sold)
	 */
	public void addStock(StockData sd) {
		String symbol = sd.getSymbol();
		//Create the tuple that contains information regarding the stock purchase if it doesn't exist
		if(!position.containsKey(symbol)) {
			position.put(sd.getSymbol(),sd);
			shares.put(sd.getSymbol(), 0);
		}
	}
	
	/**
	 * method to remove a stock data from the portfolio object
	 * @param symbol 
	 */
	public void removeStock(String symbol) {
		position.remove(symbol);
	}
	
	/**
	 * Method to perform transactions on stocks in the portfolio
	 * If there is currently no transaction, it creates a new transaction history
	 * If there is currently transaction, it adds to the transaction history
	 * It also updates the number of shares in the porfolio
	 * @param trxn Transaciton object containing information about the transaction
	 */
	public void transactStock(Transactions trxn) {
		// Check if the transaction hashmap already exists, if they do not exist create new map
		String symbol = trxn.getSymbol();
		//Create the tuple that contains information regarding the stock purchase if it doesn't exist
		if(!trxnHist.containsKey(symbol)) {
			ArrayList<Transactions> trxnList = new ArrayList<Transactions>();
			trxnList.add(trxn);
			trxnHist.put(symbol, trxnList);
		}
		// When the hashmap does exist, append the new transaction data to the map
		else {
			ArrayList<Transactions> trxnList = trxnHist.get(symbol);
			trxnList.add(trxn);
			trxnHist.put(symbol, trxnList);
		}
		//Also update the current shares in the stock of the portfolio to reflect the new transaction
		int currentShare = shares.get(symbol);
		currentShare += trxn.getShares();
		// When one tries to sell more shares then possible, it turns to zero
		if(currentShare < 0) {
			currentShare = 0;
		}
		shares.put(symbol, currentShare);
	}
	
	/**
	 * method to calculate the current worth of a stock position
	 * @param symbol: the ticker of stock of interest
	 */
	public Double getStockWorth(String symbol) {
		if(trxnHist.containsKey(symbol) && position.containsKey(symbol)) {
			List<DailyData> ddList = position.get(symbol).getDd();
			Double closePrice = ddList.get(0).getAdjustedClose();
			// multiply that by the current share 
			Integer share = shares.get(symbol);
			return closePrice * share;
		}
		else {
			return 0.0;
		}
	}
	
	
	/**
	 * method to calculate the current worth of stockData
	 */
	public Double getPorfWorth() {
		double worth = 0.0;
		for(String symbol : position.keySet()) {
			//sum up all of existing stock's monetary worth
			worth += getStockWorth(symbol);
		}
		return worth;
	}
	
	
	/** Method to calculate the net dollar spent on the investment of stocks 
	 * @param symbol: the ticker of stock of interest
	 */
	public Double moneyInvested(String symbol) {
		Double investment = 0.0;
		if(trxnHist.containsKey(symbol)) {
			//Iterate through the transaction list and obtain the dollars spent 
			for(Transactions trxn : trxnHist.get(symbol)) {
				//If the transaction is a purchasing type add it to money spent
				if(trxn.getShares() > 0) {
					investment += trxn.getTrxnCost();
				}
			}
			return investment;
		}
		else {
			return investment;
		}
	}
	
	/** Method to calclate the net dollar obtained from the selling of stocks in the past
	 * @param symbol: the ticker of stock of interest
	 */
	public Double moneyMade(String symbol) {
		Double sold = 0.0;
		if(trxnHist.containsKey(symbol)) {
			//Iterate through the transaction list and obtain the dollars spent 
			for(Transactions trxn : trxnHist.get(symbol)) {
				//If the transaction is a purchasing type add it to money spent
				if(trxn.getShares() < 0) {
					sold += trxn.getTrxnCost();
				}
			}
			return Math.abs(sold);
		}
		else {
			return sold;
		}
	}
	//we consider the portfolio as empty if there's no share in it
	public boolean isEmpty() {
		return shares.isEmpty();
	}
	
	
}	

	
	
	

