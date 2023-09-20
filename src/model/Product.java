package model;

public class Product {

	private int prodId;
	private float prodPrice;
	private String prodName;
	private int prodStock;
	
	public Product(int prodId, String prodName, float prodPrice, int prodStock) {
		super();
		this.prodId = prodId;
		this.prodPrice = prodPrice;
		this.prodName = prodName;
		this.prodStock = prodStock;
	}
	
	public int getProdId() {
		return prodId;
	}
	public void setProdId(int prodId) {
		this.prodId = prodId;
	}
	public float getProdPrice() {
		return prodPrice;
	}
	public void setProdPrice(float prodPrice) {
		this.prodPrice = prodPrice;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public int getProdStock() {
		return prodStock;
	}
	public void setProdStock(int prodStock) {
		this.prodStock = prodStock;
	}
	
}
