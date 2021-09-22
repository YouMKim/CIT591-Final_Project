package main;

import java.util.Comparator;
import java.util.Date;

/**
 * record daily data of a particular stock for each day.
 * @author yujiazhang
 *
 */
public class DailyData {



	private Date date;
	private String dateString;
	private Double open;
	private Double high;
	private Double low;
	private Double close;
	private Double adjustedClose;
	private Integer volume;
	private Double dividendAmount;
	private Double splitCoefficient;



	public void setDate(Date date) {
		this.date = date;
	}
	public Date getDate() {
		return this.date;
	}
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
	public void setOpen(Double open) {
		this.open = open;
	}
	public void setHigh(Double high) {
		this.high = high;
	}
	public void setLow(Double low) {
		this.low = low;
	}
	public void setClose(Double close) {
		this.close = close;
	}
	public void setAdjustedClose(Double adjustedClose) {
		this.adjustedClose = adjustedClose;
	}
	public void setVolume(Integer volume) {
		this.volume = volume;
	}
	public void setDividendAmount(Double dividendAmount) {
		this.dividendAmount = dividendAmount;
	}
	public void setSplitCoefficient(Double splitCoefficient) {
		this.splitCoefficient = splitCoefficient;
	}
	public String getDateString() {
		return dateString;
	}
	public Double getOpen() {
		return open;
	}
	public Double getHigh() {
		return high;
	}
	public Double getLow() {
		return low;
	}
	public Double getClose() {
		return close;
	}
	public Double getAdjustedClose() {
		return adjustedClose;
	}
	public Integer getVolume() {
		return volume;
	}
	public Double getDividendAmount() {
		return dividendAmount;
	}
	public Double getSplitCoefficient() {
		return splitCoefficient;
	}

	@Override
	public String toString() {
		return "DailyData [date=" + date + ", dateString=" + dateString + ", open=" + open + ", high=" + high + ", low="
				+ low + ", close=" + close + ", adjustedClose=" + adjustedClose + ", volume=" + volume
				+ ", dividendAmount=" + dividendAmount + ", splitCoefficient=" + splitCoefficient + "]";
	}
	
	/**
	 * Comparator to sort date so we can sort data from API
	 * @author yolandashao
	 */
	public static Comparator<DailyData> dateComparator = new Comparator<DailyData>() {
		@Override
		public int compare(DailyData dd1, DailyData dd2) {
			return (dd1.getDate().compareTo(dd2.getDate()) > 0 ? -1 : 
			(dd1.getDate().compareTo(dd2.getDate()) == 0 ? 0 : 1));
		}

	};
}

