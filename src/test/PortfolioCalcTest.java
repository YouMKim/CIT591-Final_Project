package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.junit.jupiter.api.Test;

import main.HttpURLConn;
import main.PorfCalculator;
import main.Portfolio;
import main.StockData;
import main.Transactions;

//Junit tests for the portfolio calculator class, tests to make sure that various metrics are being calculated correctly

class PortfolioCalcTest {
	
	
	//Tests two methods at once
	//Method to test the amount of raw returns someone had in the stock market !
	//Method also tests the net % calculator of raw return someone has!
	
	@Test
	void testRawReturns()  throws IOException, JSONException, ParseException {
		//Initialize a portfolio and initailize a portfolio calcualtor
		StockData testSdOne = HttpURLConn.getStockData("AAPL");
		StockData testSdTwo = HttpURLConn.getStockData("MSFT");
		
		Portfolio porf = new Portfolio();
		//add two test stocks and transact on them
		porf.addStock(testSdOne);
		porf.addStock(testSdTwo);
		
		PorfCalculator porfcalc = new PorfCalculator(porf);
		//Create some transaction metadata and add them to the portfolio
		
		//Transactino One - bought 10 apple at $50
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = format.parse("2012-05-08");
		Transactions trxn = new Transactions("AAPL",date,10,50);
		
		//Transaction Two - Sold 5 apple at $150
		Date dateTwo = format.parse("2019-05-09");
		Transactions trxnTwo = new Transactions("AAPL",dateTwo,-5,150);

		//Transaction three - bought Microsoft at $100
		
		Date dateThree = format.parse("2019-05-09");
		Transactions trxnThree = new Transactions("MSFT",dateThree,5,100);
		
		porf.transactStock(trxn);
		porf.transactStock(trxnTwo);
		porf.transactStock(trxnThree);

		//Set formatting
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		
		// Now compare the raw return manually
		Double stockValue = porf.getStockWorth("AAPL") + porf.getStockWorth("MSFT");
		assertEquals(new Double(stockValue -250), porfcalc.totalReturn());
		assertEquals(df.format(new Double((stockValue-250)/1000) * 100) + "%", porfcalc.percentReturn());
	}
	
	
	//Method to test the sub method for calculating raw returns and percent returns
	@Test 
	void testRawStockReturns()  throws IOException, JSONException, ParseException {
		StockData testSdOne = HttpURLConn.getStockData("AAPL");
		Portfolio porf = new Portfolio();
		//add two test stocks and transact on them
		porf.addStock(testSdOne);
		PorfCalculator porfcalc = new PorfCalculator(porf);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		
		//Transaction One - bought 10 apple at $50
		Date date = format.parse("2012-05-08");
		Transactions trxn = new Transactions("AAPL",date,10,50);
		
		//Transaction Two - Sold 5 apple at $150
		Date dateTwo = format.parse("2012-05-08");
		Transactions trxnTwo = new Transactions("AAPL",dateTwo,-5,150);
		
		
		porf.transactStock(trxn);
		porf.transactStock(trxnTwo);
		
		//manually calculate the metrics
		
		//Set formatting
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		
		String rawReturn = df.format(porf.getStockWorth("AAPL") - 500 + 750); 
		String percentReturn = df.format((porf.getStockWorth("AAPL") - 500 + 750)/500 * 100) + "%";

		assertEquals(rawReturn , porfcalc.calcRawReturn("AAPL"));
		assertEquals(percentReturn , porfcalc.calcPercentReturn("AAPL"));
	}
	
	

	//Test other metrics such as average cost and portfolio diversity metrics
	@Test 
	void testPorfMetric()  throws IOException, JSONException, ParseException {
		StockData testSdOne = HttpURLConn.getStockData("AAPL");
		Portfolio porf = new Portfolio();
		//add two test stocks and transact on them
		porf.addStock(testSdOne);
		
		//Transaction One - bought 10 apple at $50
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = format.parse("2012-05-08");
		Transactions trxn = new Transactions("AAPL",date,10,50);
		//Calculate the average cost 
		
		//Transaction Two - buy 10 apple at $100
		Date dateTwo = format.parse("2012-05-08");
		Transactions trxnTwo = new Transactions("AAPL",dateTwo,10,100);
		
		porf.transactStock(trxn);
		porf.transactStock(trxnTwo);
		//Calculate the average cost 

		
	}

}
