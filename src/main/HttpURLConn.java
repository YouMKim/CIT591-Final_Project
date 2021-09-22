package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Iterator;

import org.json.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * Connection to Alpha Vantage API.
 * @author yujiazhang
 */
public class HttpURLConn {

	//inputs for API
	private static final String USER_AGENT = "Mozilla/5.0";
	private static final String FUNCTION = "TIME_SERIES_DAILY_ADJUSTED";
	private static final String OUTPUTSIZE = "compact";
	private static final String API_KEY = "YLM517YLDR2W4W8E";
	
	
	/**
	 * takes in the url request to the api, and returns the jsonobject from the Alpha Vantage API
	 * @param url 
	 * @return jsonobject from the api request
	 * @throws IOException 
	 */
	// HTTP GET request
	public static String sendGet(String url) throws IOException {
//		try {
			URL obj = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
			// optional default is GET
			conn.setRequestMethod("GET");

			//add request header
			conn.setRequestProperty("User-Agent", USER_AGENT);
			int responseCode = conn.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
//			System.out.println(response.toString());
			return response.toString();
			
	}
	
	/**
	 * parse data from jsonobject of jsonobject and creates a stockData object
	 * @param jsonString
	 * @return stockData object
	 * @throws JSONException 
	 * @throws ParseException 
	 */
	public static StockData parseJSON(String jsonString) throws JSONException, ParseException {
//		try {
			JSONObject obj = new JSONObject(jsonString);
			StockData sd = new StockData();
			//get stock information without daily quote
			JSONObject metaDataObj = obj.getJSONObject("Meta Data");
			sd.setInformation(metaDataObj.getString("1. Information"));
			sd.setSymbol(metaDataObj.getString("2. Symbol"));
			sd.setLastRefreshed(metaDataObj.getString("3. Last Refreshed"));
			sd.setOutputSize(metaDataObj.getString("4. Output Size"));
			sd.setTimeZone(metaDataObj.getString("5. Time Zone"));
			JSONObject timeSeriesDailyObj = obj.getJSONObject("Time Series (Daily)");
			//parse each day's quote from the nested jsonobject by looping each day's info
			Iterator<String> keys = timeSeriesDailyObj.keys();
			while(keys.hasNext()) {
				DailyData dd = new DailyData();
				String key = keys.next();
				dd.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(key));
				if (timeSeriesDailyObj.get(key) instanceof JSONObject) {
					JSONObject dailyObj = timeSeriesDailyObj.getJSONObject(key);
					dd.setOpen(dailyObj.getDouble("1. open"));
					dd.setHigh(dailyObj.getDouble("2. high"));
					dd.setLow(dailyObj.getDouble("3. low"));
					dd.setClose(dailyObj.getDouble("4. close"));
					dd.setAdjustedClose(dailyObj.getDouble("5. adjusted close"));
					dd.setVolume(dailyObj.getInt("6. volume"));
					dd.setDividendAmount(dailyObj.getDouble("7. dividend amount"));
					dd.setSplitCoefficient(dailyObj.getDouble("8. split coefficient"));
				}
				sd.dd.add(dd);
			}
			return sd;

	}
	
	public static StockData getStockData(String symbol) throws IOException, JSONException, ParseException {
		String url = "https://www.alphavantage.co/query?function=" + FUNCTION + "&symbol=" + symbol + "&outputsize=" + OUTPUTSIZE + "&apikey=" + API_KEY;
		String response = sendGet(url);
		StockData sd = parseJSON(response);
		//sort from oldest to newest date
		sd.sortByDate();
		return sd;
	}
	

	
//	public static void main(String[] args) {
//		//String apikey = ;
//		//HttpURLConn http = new HttpURLConn();
////		//System.out.println("Send Http GET request");
////		//Required: function. The time series of your choice./
////		//String function = "TIME_SERIES_DAILY_ADJUSTED";
////		//String outputsize = "full";
////		//String url = "https://www.alphavantage.co/query?function=" + function+ "&symbol=" + symbol + "&outputsize=" + outputsize + "&apikey=" + apikey; 
////		//System.out.println(url);
////		//String response = http.sendGet(url);
////		//StockData sd = parseJSON(response);
////		//System.out.println(sd.toString());
////		
//		String symbol = "MSFT";
//		StockData sd;
//		try {
//			sd = HttpURLConn.getStockData(symbol);
//			System.out.println(sd.toString());
//			List<DailyData> dd = sd.getDd();
//			DailyData latestPointer = dd.get(0);//dd.size()-1
//			System.out.println(latestPointer.toString());
//		} catch (IOException | JSONException | ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//	}
}
