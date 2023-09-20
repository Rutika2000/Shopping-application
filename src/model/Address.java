package model;

public class Address {

	private String lane1;
	private String lane2;
	private String city;
	private String State;
	private String pinCode;
	
	public Address(String lane1, String lane2, String city, String state, String pinCode) {
		super();
		this.lane1 = lane1;
		this.lane2 = lane2;
		this.city = city;
		State = state;
		this.pinCode = pinCode;
	}

	public String getLane1() {
		return lane1;
	}

	public void setLane1(String lane1) {
		this.lane1 = lane1;
	}

	public String getLane2() {
		return lane2;
	}

	public void setLane2(String lane2) {
		this.lane2 = lane2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	
	
}
