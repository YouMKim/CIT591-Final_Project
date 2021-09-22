package main;
import java.util.Date;




/**
 * Class that represents a single stock transaction
 * @author youmk
 *
 */
public class Transactions {
	// The ticker of the stock
	private String symbol;
	// The day of the transaction
	private Date date;
	//The total number of shares being transacted, negative value indicates selling of shares
	private int shares;
	// The price of each share being transacted
	private double price;
	
	
	//constructor
	public Transactions(String symbol, Date date, int shares, double price) {
		this.symbol = symbol;
		this.date = date;
		this.shares = shares;
		this.price = price;
	}

	//Method to return total cost of transaction
	public Double getTrxnCost() {
		return shares * price;
	}
	
	/* The getter and setters for the object */
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getShares() {
		return shares;
	}
	public void setShares(int shares) {
		this.shares = shares;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
}


     