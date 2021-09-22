package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.junit.jupiter.api.Test;

import main.DailyData;
import main.HttpURLConn;
import main.Portfolio;
import main.Transactions;
import main.StockData;

class PortfolioTest {
	
	

	//test to see if the method to add and remote stock works properly
	//initialize a new portfolio outside of class declaration and see if it works
	@Test
	void testAddRemoveStock() throws IOException, JSONException, ParseException {
	
		Portfolio porf = new Portfolio();
		//Create a new portfolio test case
		Portfolio test = new Portfolio();
		StockData testSd = HttpURLConn.getStockData("AAPL");
		porf.addStock(testSd);
		test.addStock(testSd);
		//assert adding stock
		assertEquals(porf.getPosition().keySet(),test.getPosition().keySet());
		Portfolio testTwo = new Portfolio();
		test.removeStock("AAPL");
		//assert removing stock
		assertEquals(test.getPosition().keySet(),testTwo.getPosition().keySet());
	}
		
	
	
	//test to see if the method for stock transaction is being calculated properly
	@Test
	void testStockTransactions() throws IOException, JSONException, ParseException {
		Portfolio porf = new Portfolio();
		StockData testSd = HttpURLConn.getStockData("AAPL");
		porf.addStock(testSd);
		System.out.println(porf.getShares().get("AAPL"));
		//Create a new transaction 

		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = format.parse("2012-05-08");
		Transactions trxn = new Transactions("AAPL",date,10,100);
		
		
		//With this new transaction transact the stock
		porf.transactStock(trxn);
		Integer shares = porf.getShares().get("AAPL");
		//check to see if correct number of shares has been added
		assertEquals(shares,new Integer(10));

	
		Date dateTwo = format.parse("2012-05-08");
		Transactions trxnTwo = new Transactions("AAPL",dateTwo,10,100);
		
		porf.transactStock(trxnTwo);
		Integer sharesTwo = porf.getShares().get("AAPL");
		//check to see if correct number of shares has been added
		assertEquals(sharesTwo,new Integer(20));
	}
		
	
	//Method to test if the worth of the stock is being calculated correctly
	@Test
	void testStockWorth() throws IOException, JSONException, ParseException {
		//Obtain the latest price of stock from stock data
		
		Portfolio porf = new Portfolio();
		StockData testSd = HttpURLConn.getStockData("AAPL");
		
		porf.addStock(testSd);
		//get the latest price of stock
		List<DailyData> ddList = porf.getPosition().get("AAPL").getDd();
		Double closePrice = ddList.get(0).getAdjustedClose();
		
		//add 10 shares to apple 
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse("2012-05-08");
		Transactions trxn = new Transactions("AAPL",date,10,100);
		
		
		//With this new transaction transact the stock
		porf.transactStock(trxn);
		assertEquals(new Double (closePrice * 10) , porf.getStockWorth("AAPL"));
	}


	//Method to test if the net dollar amount being obtained from the buying of stocks is calculated correctly
	@Test
	void testMoneyInvested() throws IOException, JSONException, ParseException {
		
		Portfolio porf = new Portfolio();
		StockData testSd = HttpURLConn.getStockData("AAPL");
		porf.addStock(testSd);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = format.parse("2012-05-08");
		Transactions trxn = new Transactions("AAPL",date,10,100);
		porf.transactStock(trxn);
		//Transact 10 stocks worth $1000 total
		assertEquals(new Double (1000) , porf.moneyInvested("AAPL"));
	}
	
	
	//Method to test if the net dollar amount obtained from selling of stock in the past is calculated correctly 
	@Test
	void testMoneyMade() throws IOException, JSONException, ParseException  {
		
		Portfolio porf = new Portfolio();
		StockData testSd = HttpURLConn.getStockData("AAPL");
		porf.addStock(testSd);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse("2012-05-08");
		Transactions trxn = new Transactions("AAPL",date,10,100);
		porf.transactStock(trxn);
		
		Date dateTwo = format.parse("2012-05-08");
		Transactions trxnSold = new Transactions("AAPL",dateTwo,-5,50);
		
		porf.transactStock(trxnSold);
		//Transact 10 stocks worth $1000 total
		assertEquals(new Double (250) , porf.moneyMade("AAPL"));
	}
	
	//Method to test if the empty portfolio method is working properly
	@Test
	void testIsEmpty() {
	Portfolio porf = new Portfolio();
	assertEquals(true, porf.isEmpty());
	}
	
}
