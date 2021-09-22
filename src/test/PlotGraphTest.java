package test;

import static org.junit.Assert.assertNotNull;
import java.io.IOException;
import java.text.ParseException;
import org.json.JSONException;
import org.junit.jupiter.api.Test;


import main.HttpURLConn;
import main.PlotGraph;
import main.Portfolio;

/**
 * Test PlotGraph class
 * @author yolandashao
 *
 */
class PlotGraphTest {

	Portfolio porf = new Portfolio();

	/**
	 * checks if plotStockPrice is not null for AAPL
	 * @throws IOException
	 * @throws JSONException
	 * @throws ParseException
	 */
	@Test
	void testPlotStockPrice() throws IOException, JSONException, ParseException {
		porf.addStock(HttpURLConn.getStockData("AAPL"));
		
		PlotGraph graph = new PlotGraph(porf);
		assertNotNull(graph.plotStockPrice());
	}

}
