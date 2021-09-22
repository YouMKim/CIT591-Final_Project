package main;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.json.JSONException;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

/**
 * The GUI class for user interaction
 * 
 * @author yolandashao
 * 
 */
public class StockTrackerGUI extends JFrame {

	// The Portfolio  for the data
	private Portfolio porf;

	// The serial version ID
	private static final long serialVersionUID = 1L;

	// textbox for users to type ticker, purchased quantity, date and price
	private JTextField textTicker, textQuantity, textDate, textPrice;

	// button to display historical price and add a stock to a portfolio
	private JButton display, add, simButton;

	// dropdown box to display simulation parameter
	private JComboBox<String> timeList;

	// label for error message , and attibutes for portoflio and stocks
	private JLabel labelError, labelShares, labelEquity, labelAvgCost, 
		labelPorfDiversity, labelTodayReturn, labelTotalReturn, labelOpen, 
		labelVolume, labelHigh, labelLow, labelPERatio, labelDiv, 
		labelThisStock, labelThisStock2;

	// panel for stock and portfolio graphs
	private JPanel portGraph;//, chartPortfolio;

	// variables to store user inputs
	private String stock;
	private int quantity;
	private Date date;
	private double price;

	/**
	 * Constructor for StockTrackerGUI
	 */
	public StockTrackerGUI() {
		createFrame();
		addListeners();

		// initialize the Portfolio object
		porf = new Portfolio();
	}

	/**
	 * Method for creating the frame
	 */
	private void createFrame() {
		JFrame container = new JFrame();

		// Setting up frame
		container.setTitle("StockTracker");
		container.setSize(1500, 1200);
		container.setResizable(false);
		container.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Setting up 2 * 2 overall panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));

		
		
		
		
		// Components for #1 panelUser
		//headers
		JLabel labelTicker = new JLabel("Ticker:");
		JLabel labelQuantity = new JLabel("Quantity:");
		JLabel labelDate = new JLabel("Date:(YYYYMMDD)");
		JLabel labelPrice = new JLabel("Price:($)");
		labelTicker.setFont(labelTicker.getFont().deriveFont((float) 20));// bigger font
		labelQuantity.setFont(labelQuantity.getFont().deriveFont((float) 20));
		labelDate.setFont(labelDate.getFont().deriveFont((float) 20));
		labelPrice.setFont(labelPrice.getFont().deriveFont((float) 20));
		//textboxes for user inputs
		textTicker = new JTextField(10);
		textQuantity = new JTextField(10);
		textDate = new JTextField(10);
		textPrice = new JTextField(10);
		textTicker.setFont(textTicker.getFont().deriveFont((float) 20));
		textQuantity.setFont(textQuantity.getFont().deriveFont((float) 20));
		textDate.setFont(textDate.getFont().deriveFont((float) 20));
		textPrice.setFont(textPrice.getFont().deriveFont((float) 20));
		//buttons
		display = new JButton("Display Graph");
		display.setFont(display.getFont().deriveFont((float) 20));
		add = new JButton("Add Transaction");
		add.setFont(display.getFont().deriveFont((float) 20));

		
		/**
		// component for simulation results
		String[] timeStrings = { "3-Month", "6-Month", "9-Month" };
		JComboBox<String> timeList = new JComboBox<>(timeStrings);
		timeList.setSelectedIndex(0);
		timeList.setFont(timeList.getFont().deriveFont((float) 20));
		// add actiona listener don't forget

		simButton = new JButton("Run Backtest Simulation");
		simButton.setFont(display.getFont().deriveFont((float) 20));
		 **/
		
		
		// Panel for headers
		JPanel panelHeader = new JPanel();
		panelHeader.setLayout(new GridLayout(1, 4));
		panelHeader.add(labelTicker);
		panelHeader.add(labelQuantity);
		panelHeader.add(labelDate);
		panelHeader.add(labelPrice);

		// Panel for user input text boxes
		JPanel panelUserText = new JPanel();
		panelUserText.setLayout(new GridLayout(1, 4));
		panelUserText.add(textTicker);
		panelUserText.add(textQuantity);
		panelUserText.add(textDate);
		panelUserText.add(textPrice);

		// placeholder for error message
		labelError = new JLabel("");
		labelError.setFont(labelError.getFont().deriveFont((float) 20));

		// Panel for buttons
		JPanel panelButtons = new JPanel();
		panelButtons.add(add);
		panelButtons.add(display);
		//panelButtons.add(timeList);
		//panelButtons.add(simButton);
		panelButtons.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		// Combine the user input and button panel into one panel
		JPanel panelUser = new JPanel();
		panelUser.setLayout(new GridLayout(10, 1));
		panelUser.add(panelHeader);
		panelUser.add(panelUserText);
		panelUser.add(panelButtons);
		panelUser.add(labelError);

		// Panel stock
		portGraph = new JPanel();
		portGraph.setPreferredSize(new Dimension(1000, 600));

		// Panel portfolio
		JPanel panelPortfolio = new JPanel();
		JLabel labelPort = new JLabel("Value to Your Portolio", SwingConstants.CENTER);
		labelPort.setFont(labelPort.getFont().deriveFont((float) 20));

		// Panel for portfolio metrics
		JPanel panelPositionMetrics = new JPanel();
		panelPositionMetrics.setLayout(new GridLayout(4, 2));
		labelShares = new JLabel("Composition:");
		labelEquity = new JLabel("Total worth Today: ");
		labelThisStock = new JLabel("Portfolio stats for: ");
		labelAvgCost = new JLabel("Average Cost:");
		labelPorfDiversity = new JLabel("Porf Diversity:");
		labelTodayReturn = new JLabel("Total Return from purchase:");
		labelTotalReturn = new JLabel("Total Return% from Purchase:");
		// bigger font
		labelShares.setFont(labelShares.getFont().deriveFont((float) 16));
		labelEquity.setFont(labelEquity.getFont().deriveFont((float) 16));
		labelThisStock.setFont(labelThisStock.getFont().deriveFont((float) 20));
		labelAvgCost.setFont(labelAvgCost.getFont().deriveFont((float) 16));
		labelPorfDiversity.setFont(labelPorfDiversity.getFont().deriveFont((float) 16));
		labelTodayReturn.setFont(labelTodayReturn.getFont().deriveFont((float) 16));
		labelTotalReturn.setFont(labelTotalReturn.getFont().deriveFont((float) 16));
		// Add the various labels
		panelPositionMetrics.add(labelShares);
		panelPositionMetrics.add(labelEquity);
		panelPositionMetrics.add(labelThisStock);
		panelPositionMetrics.add(new JLabel(""));
		panelPositionMetrics.add(labelAvgCost);
		panelPositionMetrics.add(labelPorfDiversity);
		panelPositionMetrics.add(labelTodayReturn);
		panelPositionMetrics.add(labelTotalReturn);

		// panel for stock stats
		JPanel stockStats = new JPanel();
		stockStats.setLayout(new GridLayout(4, 2));
		labelThisStock2 = new JLabel("Price info for:");
		labelOpen = new JLabel("Open: ");
		labelVolume = new JLabel("Volume: ");
		labelHigh = new JLabel("High: ");
		labelLow = new JLabel("Low: ");
		labelPERatio = new JLabel("Close: ");
		labelDiv = new JLabel("Div Amt: ");
		// bigger font
		labelThisStock2.setFont(labelThisStock2.getFont().deriveFont((float) 20));
		labelOpen.setFont(labelOpen.getFont().deriveFont((float) 16));
		labelVolume.setFont(labelVolume.getFont().deriveFont((float) 16));
		labelHigh.setFont(labelHigh.getFont().deriveFont((float) 16));
		labelLow.setFont(labelLow.getFont().deriveFont((float) 16));
		labelPERatio.setFont(labelPERatio.getFont().deriveFont((float) 16));
		labelDiv.setFont(labelDiv.getFont().deriveFont((float) 16));
		// add to the panel for stock stats
		stockStats.add(labelThisStock2);
		stockStats.add(new JLabel(""));
		stockStats.add(labelOpen);
		stockStats.add(labelVolume);
		stockStats.add(labelHigh);
		stockStats.add(labelLow);
		stockStats.add(labelPERatio);
		stockStats.add(labelDiv);

		// add to the panel portfolio section
		panelPortfolio.setLayout(new GridLayout(3, 1, 0, 50));
		panelPortfolio.add(labelPort);
		// panelPortfolio.add(chartPortfolio);
		panelPortfolio.add(panelPositionMetrics);
		panelPortfolio.add(stockStats);


		// add to overall panel
		
		
		//Create a new panel
		
		JPanel metricWrapper = new JPanel();
		
		metricWrapper.add(panelUser);
		metricWrapper.add(panelPortfolio);
		
		panel.add(metricWrapper);
		panel.add(portGraph);
		

		// add to jframe
		container.add(panel);
		container.setVisible(true);
	}

	/**
	 * action listeners to the buttons
	 */
	private void addListeners() {
		/**
		 * Action listener to the add button
		 */
		add.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				//today's date
				Date today = new Date();
				today.setHours(0);
				
				//ensures all 4 fields are filled in
				if (textTicker.getText().isEmpty() | textQuantity.getText().isEmpty() | textPrice.getText().isEmpty()
						| textDate.getText().isEmpty()) {
					labelError.setText("All 4 fields are mandatory.");
				} else {
					stock = textTicker.getText();
					try {
						StockData sd = HttpURLConn.getStockData(stock);
						quantity = Integer.parseInt(textQuantity.getText());
						price = Double.parseDouble(textPrice.getText());
						date = new SimpleDateFormat("yyyyMMdd").parse(textDate.getText());
						
						if (date.after(today)) {//Date has to be today or before
							labelError.setText("Date has to be today or before");
						} else if (quantity <= 0|price <= 0) {//Quantity and price have to be positive
							labelError.setText("Quantity and price have to be positive");
						} else {
							porf.addStock(sd);
							porf.transactStock(new Transactions(stock, date, quantity, price));
							labelError.setText(stock+" successfully added");
							
							
							//the stock where info is being displayed
							String thisStock = stock;

							//compute and display portfolio attibutes for individual stock
							PorfCalculator calculator = new PorfCalculator(porf);
							labelShares.setText("Composition: " + porf.getShares());
							labelEquity.setText("Total worth Today: " + String.format("%.2f",porf.getPorfWorth()));
							labelThisStock.setText("Portfolio stats for: " + thisStock);
							labelAvgCost.setText("Average Cost: " + calculator.avgCost(thisStock));
							labelPorfDiversity.setText("Porf Diversity: " + calculator.porfDiversity(thisStock));
							labelTodayReturn.setText("Total Return from purchase: " + calculator.calcRawReturn(thisStock));
							labelTotalReturn.setText("Total Return% from Purchase: " + calculator.calcPercentReturn(thisStock));
							
							//compute and display price info for individual stock
							List<DailyData> dd = HttpURLConn.getStockData(thisStock).getDd();
							DailyData latestDD = dd.get(0);
							labelThisStock2.setText("Price info for: " + thisStock);
							labelOpen.setText("Open: " + latestDD.getOpen());
							labelVolume.setText("Volume: " + latestDD.getVolume());
							labelHigh.setText("High: " + latestDD.getHigh());
							labelLow.setText("Low: " + latestDD.getLow());
							labelPERatio.setText("Close: " + latestDD.getClose());
							labelDiv.setText("Div Amt: " + latestDD.getDividendAmount());
						}
						

					} catch (IOException | JSONException APIerror) {
						labelError.setText("No data returned from API. Try another ticker");
					} catch (NumberFormatException quantityError) {
						labelError.setText("Quantity has to be an int, and price has to be a double");
					} catch (ParseException dateException) {
						labelError.setText("Wrong date format");
					}

					revalidate();

				}
			}
		});

		/**
		 * Action listener to the display button
		 */
		display.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (porf.isEmpty()) {//cannot display a graph for empty portfolio
					labelError.setText("Please add at least one transacion");
				} else {
					portGraph.removeAll();
					labelError.setText("");
					PlotGraph graph = new PlotGraph(porf);
					XYChart xyChart = graph.plotStockPrice();
					portGraph.add(new XChartPanel<XYChart>(xyChart));
					revalidate();
				}
				
			}
		});
		


	}

	
	/**
	 * The main() method of the StockTracker
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		// initailize a portfolio object
		// Portfolio porf = new Portfolio();

//		porf.addStock(HttpURLConn.getStockData("AAPL"));
//		porf.addStock(HttpURLConn.getStockData("MSFT"));
//		porf.addStock(HttpURLConn.getStockData("FB"));

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				@SuppressWarnings("unused")
				StockTrackerGUI stockTrackerGUI = new StockTrackerGUI();
			}
		});
	}
}