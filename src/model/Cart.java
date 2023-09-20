package model;

import java.util.Date;

public class Cart {

	private int custId;
	private int prodId;
	private String prodName;
	private int quantity;
	private double amount;
	private Date date_added;
	
	
	public Cart(int custId, int prodId, String prodName, int quantity, double amount, Date date_added) {
		super();
		this.custId = custId;
		this.prodId = prodId;
		this.prodName = prodName;
		this.quantity = quantity;
		this.amount = amount;
		this.date_added = date_added;
	}
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	public int getProdId() {
		return prodId;
	}
	public void setProdId(int prodId) {
		this.prodId = prodId;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Date getDate_added() {
		return date_added;
	}
	public void setDate_added(Date date_added) {
		this.date_added = date_added;
	}
	
	
}
