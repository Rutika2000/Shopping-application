package model;

public class Customer {

	private int custId;
	private String custName;
	private String custMobileNumber;
	private String custAddress;
	private String userName;
	private String password;
	private double openingBalance;
	
	public Customer(int custId, String custName, String custMobileNumber, String custAddress, String userName,
			String password, double openingBalance) {
		super();
		this.custId = custId;
		this.custName = custName;
		this.custMobileNumber = custMobileNumber;
		this.custAddress = custAddress;
		this.userName = userName;
		this.password = password;
		this.openingBalance = openingBalance;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustMobileNumber() {
		return custMobileNumber;
	}

	public void setCustMobileNumber(String custMobileNumber) {
		this.custMobileNumber = custMobileNumber;
	}

	public String getCustAddress() {
		return custAddress;
	}

	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(double openingBalance) {
		this.openingBalance = openingBalance;
	}
	
	
}
