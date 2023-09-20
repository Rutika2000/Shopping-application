package model;

import java.util.Date;

public class Bill {

	private int custId;
	private String prodId;
	private int noOfProducts;
	private double total_amount;
	private double gst_cost;
	private Date date;
	private double final_amount;
	
	public Bill(int custId, String prodId, int noOfProducts, double total_amount, double gst_cost, Date date,
			double final_amount) {
		super();
		this.custId = custId;
		this.prodId = prodId;
		this.noOfProducts = noOfProducts;
		this.total_amount = total_amount;
		this.gst_cost = gst_cost;
		this.date = date;
		this.final_amount = final_amount;
	}
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public int getNoOfProducts() {
		return noOfProducts;
	}
	public void setNoOfProducts(int noOfProducts) {
		this.noOfProducts = noOfProducts;
	}
	public double getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}
	public double getGst_cost() {
		return gst_cost;
	}
	public void setGst_cost(double gst_cost) {
		this.gst_cost = gst_cost;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getFinal_amount() {
		return final_amount;
	}
	public void setFinal_amount(double final_amount) {
		this.final_amount = final_amount;
	}
	
	
	
}
