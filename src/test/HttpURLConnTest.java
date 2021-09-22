package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import main.HttpURLConn;
import main.StockData;

/**
 * Unit Tests for testing the functionality of the HttpURLConnTest class
 * 
 */

class HttpURLConnTest {

	
	/**
	 * Tests basic functionality - checks if API returns value for different tickers
	 * @throws IOException
	 * @throws JSONException
	 * @throws ParseException
	 */
	@Test
	void testAPI() throws IOException, JSONException, ParseException {
		//String apikey = ;
		//HttpURLConn http = new HttpURLConn();
		StockData sd = HttpURLConn.getStockData("MSFT");
		StockData sd2 = HttpURLConn.getStockData("AAPL");
		//checks if dd is empty
		assertNotNull(sd.getDd());
		//checks if first element in dd is empty
		assertNotNull(sd.getDd().get(0).toString());
		assertNotNull(sd2.getDd());
		assertNotNull(sd2.getDd().get(0).toString());
	}
	
	
	/**
	 * test if sendGet function is able to generate the correct string type
	 * @throws IOException
	 */
	@Test
	void testSendGet() throws IOException {
		String apikey = ;
		//HttpURLConn http = new HttpURLConn();
		String function = "TIME_SERIES_DAILY_ADJUSTED";
		String symbol = "AAPL";
		String outputsize = "full";
		String url = "https://www.alphavantage.co/query?function=" + function+ "&symbol=" + symbol + "&outputsize=" + outputsize + "&apikey=" + apikey; 
		String response = HttpURLConn.sendGet(url);
		//check the response is correct for Symbol that exists
		assertNotNull(response);
	}
	

	@Test
	/**
	 * Test if the API returns the correct data for the right ticker marks
	 * @throws IOException
	 * @throws JSONException
	 * @throws ParseException
	 */
	void testParseJSON() throws IOException, JSONException, ParseException {
		String apikey = ;
		//HttpURLConn http = new HttpURLConn();
		String function = "TIME_SERIES_DAILY_ADJUSTED";
		String outputsize = "full";
		List<String> stockSymbols = Arrays.asList("PEP","AVGO","NFLX");
		for(String symbol: stockSymbols) {
			String url = "https://www.alphavantage.co/query?function=" + function+ "&symbol=" + symbol + "&outputsize=" + outputsize + "&apikey=" + apikey; 
			String response = HttpURLConn.sendGet(url);
			StockData sd = HttpURLConn.parseJSON(response);
			// test if the data is not null
			assertNotNull(sd.getDd());
			// test to see if the correct symbol is pulled
			assertEquals(sd.getSymbol(), symbol);
		}	
		
	}

}
