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
import main.StockData;
import main.Transactions;

class PortfolioTestTwo {

	
	
	//Method to test if the portfolio worth cases are calculating correctly 
	@Test
	void testPorfWorth() throws IOException, JSONException, ParseException {
		//Obtain the latest price of stock from stock data
		
		Portfolio porf = new Portfolio();
		StockData testSd = HttpURLConn.getStockData("AAPL");
		StockData testSdTwo = HttpURLConn.getStockData("MSFT");
		StockData testSdThree = HttpURLConn.getStockData("FB");
		
		porf.addStock(testSd);
		porf.addStock(testSdTwo);
		porf.addStock(testSdThree);
		//get the latest price of stock
		List<DailyData> ddList = porf.getPosition().get("AAPL").getDd();
		List<DailyData> ddListTwo = porf.getPosition().get("MSFT").getDd();
		List<DailyData> ddListThree = porf.getPosition().get("FB").getDd();
		
		Double closePrice = ddList.get(0).getAdjustedClose();
		Double closePriceTwo = ddListTwo.get(0).getAdjustedClose();
		Double closePriceThree = ddListThree.get(0).getAdjustedClose();
		
		
		//add 10 shares to apple 
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse("2012-05-08");
		Transactions trxn = new Transactions("AAPL",date,10,100);
	
		Transactions trxnTwo = new Transactions("MSFT",date,10,100);
		
		Transactions trxnThree = new Transactions("FB",date,10,100);
		
		
		//With this new transaction transact the stock
		porf.transactStock(trxn);
		porf.transactStock(trxnTwo);
		porf.transactStock(trxnThree);
		assertEquals(new Double ((closePrice + closePriceTwo + closePriceThree)*10) , porf.getPorfWorth());
	}
}
