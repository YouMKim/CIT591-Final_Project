package main;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONException;

/**record stock information
 * including symbol
 * 
 * @author yujiazhang
 *
 */
public class StockData {
	
	private String information;
	private String symbol;
	private String lastRefreshed;
	private String outputSize;
	private String timeZone;
	
	List<DailyData> dd = new ArrayList<>();

	public StockData() {
		
	}

	/**
	 * Method to obtain the latest stock closing price
	 * @param days : number of days back of stock data to obtain
	 */
	public Double getStockClosePrice(Integer days) {
		Double closePrice = dd.get(dd.size()- (days + 1)).getAdjustedClose();
		return closePrice;
	}
	
	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getLastRefreshed() {
		return lastRefreshed;
	}

	public void setLastRefreshed(String lastRefreshed) {
		this.lastRefreshed = lastRefreshed;
	}

	public String getOutputSize() {
		return outputSize;
	}

	public void setOutputSize(String outputSize) {
		this.outputSize = outputSize;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public List<DailyData> getDd() {
		return dd;
	}

	public void setDd(List<DailyData> dd) {
		this.dd = dd;
	}

	/**
	 * Sort by date so we can sort data from API
	 * @author yolandashao
	 */
	public void sortByDate() {
		Collections.sort(dd, DailyData.dateComparator);
	}
}
