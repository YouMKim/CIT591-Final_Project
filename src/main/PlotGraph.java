package main;

import java.awt.Font;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;


/**
 * Class to plot price against date
 * @author yolandashao
 * @author ykim
 */
public class PlotGraph{
	//obtain the data from the portfolio object 
	private Portfolio porf;
	
	public PlotGraph(Portfolio portfolio) {
		porf = portfolio;
	}
	
	
	
	/**
	 * Obtain data from the portfolio object, and create a graph containing the price of stocks
	 */
	
    public XYChart plotStockPrice()  {

        //format a chart object to for the graph
        XYChart chart = new XYChartBuilder().width(700).height(1100).title("Historical prices for each stock").build();
        //bigger font
        chart.getStyler().setChartTitleFont(new Font(Font.SERIF, Font.PLAIN, 20));
        chart.getStyler().setLegendFont(new Font(Font.SERIF, Font.PLAIN, 20));
        chart.getStyler().setAxisTitleFont(new Font(Font.SERIF, Font.PLAIN, 20));
        chart.getStyler().setAxisTickLabelsFont(new Font(Font.SERIF, Font.PLAIN, 20));
        //formating x and y axis
        chart.getStyler().setDatePattern("MM-dd-yyyy");
        chart.getStyler().setYAxisDecimalPattern("$###");
    	
    	
    	//For the portfolio object, loop through all of the available stocks
    	for (String ticker: porf.getPosition().keySet()) {
    		StockData sd = porf.getPosition().get(ticker);
    		List<DailyData> dd = sd.getDd();
    		
    		//date and price array
        	ArrayList<Date> dates = new ArrayList<Date>();
        	ArrayList<Double> prices = new ArrayList<Double>();
        	
        	//populate date and price array from output of API
        	for (DailyData oneDayData : dd) {
        		dates.add(oneDayData.getDate());
        		//System.out.println(oneDayData.getDate());
        		prices.add(oneDayData.getAdjustedClose());
        		//System.out.println(oneDayData.getAdjustedClose());
        	}
        	//plot into the graph
            chart.addSeries(ticker, dates, prices);
    	}
        return chart;
    }
    
    
    /**
     * Method to obtain data from the portfolio object, and create a graph containing the portfolio size object
     * @param args
     * @throws Exception
     */
    
    
    

//    public static void main(String[] args) throws Exception {
//    	//initialize the portfolio object 
//    	Portfolio porf = new Portfolio();
//    	
//		porf.addStock(HttpURLConn.getStockData("AAPL"));
//		porf.addStock(HttpURLConn.getStockData("MSFT"));
//		porf.addStock(HttpURLConn.getStockData("FB"));
//		
//    	PlotGraph graph = new PlotGraph(porf);
//    	XYChart chart = graph.plotStockPrice();
//    	new SwingWrapper<> (chart).displayChart();
//      }

}