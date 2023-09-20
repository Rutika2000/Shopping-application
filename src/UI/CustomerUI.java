package UI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import dao.ConnectionClass;
import model.Cart;
import model.Customer;

public class CustomerUI {

	Scanner s = new Scanner(System.in);
	ConnectionClass c = new ConnectionClass();
	Connection con = null;
	Statement state = null;
	int custId;
	boolean logined;
	ResultSet custResult;
	PreparedStatement ps = null;

	/**
	 * Register the Customer
	 */
	public void registerCustomer() {

		// get all the data from the user
		System.out.println("Name:- "); 
		String custName = s.nextLine();
		System.out.println("Mobile Number:- ");
		String custMobileNumber = s.next();
		System.out.println("User Name"); 
		String userName = s.next();
		System.out.println("Password");
		String password = s.next();
		System.out.println("Balance");
		double openingBalance = s.nextDouble();
		System.out.println("Address");
		s.nextLine();
		String custAddress = s.nextLine();

		// create an instance of customer
		Customer c = new Customer(1, custName, custMobileNumber, custAddress, userName, password, openingBalance);
		int i = new ConnectionClass().insertData(c);
		if(i > 0) {
			System.out.println("Registered Succesfully");
			// redirect to login Page
			loginCutomer();
		}else {
			System.out.println("OOPS something went wrong");
		}
	}


	/**
	 * login the customer
	 */
	public void loginCutomer() {

		// check whether the user has logined or not
		logined = false;

		// do while loop for the user until he/she has logined
		do {

			// get the userName and password entered by the customer
			System.out.println("Login");
			System.out.println("User Name");
			String userName = s.next();
			System.out.println("Password");
			String password = s.next();

			// get the connection
			con = new dao.ConnectionClass().getConnection();

			String passFromDB = null;

			// extract the password from database
			try {
				ps = con.prepareStatement("select * from customer where userName = ?" );
				ps.setString(1, userName);
				custResult = ps.executeQuery();
				if(custResult.next()) {
					passFromDB = custResult.getString(6);

					// check whether the password is correct or not
					if(password.equals(passFromDB)) {
						System.out.println("LOGGGED In");
						logined = true;

						// fetch the custId from database for purchasing
						custId = custResult.getInt(1);

						//show Customer Dashboard
						customerDashboard();
					}
					else {
						System.out.println("wrong password");
						logined = false;
					}
				}else {
					System.out.println("User does not exist");
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}while(!logined);

	}

	private void customerDashboard() {
		do {
			// the customer dashoard

			System.out.println("Customer Dashboard");
			System.out.println("1.View Products \n2.Blling \n3.View Transactions \n4.Add Balance \n5.Logout");
			switch(s.nextInt()) {
			case 1 :
				displayAndBuyProducts();
				break;

			case 2 :
				viewCartOrBilling();
				break;

			case 3 :
				viewTransactions();
				break;

			case 4 :
				addBalance();
				break;

			case 5 :
				logined = false;
				break;

			default :
				System.out.println("Wrong option");
			}
		}while(logined);

	}

	/**
	 *  method for displaying all products and purchasing
	 */
	private void displayAndBuyProducts() {
		do {
			
				
				c.displayPrdoucts();

				System.out.println("Enter the product Id you want to purchase");
				int prodId = s.nextInt();
				System.out.println("Quantity");
				int quantity = s.nextInt();



				try {

					ps = con.prepareStatement("select * from product where prodId = ?");
					ps.setInt(1, prodId);
					ResultSet result = ps.executeQuery();
					if(result.next()) {
						int stock = result.getInt(4);
						if(quantity <= stock) {
							
							//Cart cart = new Cart(custId, prodId, prodName, quantity, amount, date_added)
							ps = con.prepareStatement("insert into cart values(?,?,?,?,?,sysdate)");
							ps.setInt(1, custId);
							ps.setInt(2, prodId);
							ps.setString(3, result.getString(2));
							ps.setInt(4, quantity);
							ps.setFloat(5, (result.getFloat(3) * quantity));
							int i = ps.executeUpdate();
							if(i > 0) {

								ps = con.prepareStatement("update product set stock = ? where prodId = ?");
								ps.setInt(1, (stock - quantity));
								ps.setInt(2, prodId);
								i = ps.executeUpdate();
								if(i > 0 ) {
									System.out.println("Product Added to cart");
								}else {
									System.out.println("OPPS something went wrong");	
								}
							}else {

							}
						}else {
							System.out.println("OOPS only " + stock + " pieces available");
						}
					}else {
						System.out.println("Enter valid product Id");
					}
				
				System.out.println("Do you want to buy more products? type yes or else no");
				// fire this command so as the cursor comes at the top position
				/*prodResult.beforeFirst();
				prodResult.next();*/


			}catch(SQLException e1) {
				e1.printStackTrace();
			}
		}while(s.next().equals("yes"));
		viewCartOrBilling();

	}

	private void viewCartOrBilling() {
		System.out.println("Do you want to view Cart or Billing");
		System.out.println("1.Cart \n2.Billing");
		switch(s.nextInt()) {
		case 1 :
			System.out.println("---------CART-----------------");
			viewCart();
			break;

		case 2 :
			billing();
			break;

		default :
			customerDashboard();
		}
	}



	private void billing() {
		try {

			
			state = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultset = state.executeQuery("select prodId, sum(quantity), sum(amount) from cart where custId = " + custId + " group by prodId");
			resultset.next();

			int count = 0;
			try {
				resultset.last();
				count = resultset.getRow();

			}catch(SQLException e1) {
				e1.printStackTrace();	
			}finally {
				resultset.beforeFirst();
				resultset.next();
			}

			String prodIdString = "";
			float amount = 0f;
			for(int i = 0; i < count; i++) {
				prodIdString = prodIdString + resultset.getInt(1) + ",";
				amount += resultset.getFloat(3);
				resultset.next();
			}

			ps = con.prepareStatement("select * from customer where custId = ?");
			ps.setInt(1, custId);
			ResultSet resultSet = ps.executeQuery();
			resultSet.next();

			double balance = resultSet.getDouble(7);
			if(balance > amount) {

				double gst = amount * 0.08;
				double finalAmt = amount + gst;
				ps = con.prepareStatement("insert into bill values(?,?,?,?,?, sysdate,?)");
				ps.setInt(1, custId);
				ps.setString(2, prodIdString);
				ps.setInt(3, count);
				ps.setDouble(4, amount);
				ps.setDouble(5, gst);
				ps.setDouble(6, (amount + gst));

				int i = ps.executeUpdate();
				if(i > 0) {
					System.out.println("---------BILL-----------------");
					System.out.println("Id\tName\tQuantity\tAmount");
					try {/*
						ps = con.prepareStatement("select c.prodID, p.prodName, c.quantity, c.amount from cart c"
								+ ", product p where c.prodId = p.prodId");*/

						Statement state = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
						ResultSet result = state.executeQuery("select prodId, prodName, sum(quantity), sum(amount) from cart where custId = " + custId + " group by prodId, prodName order by prodID");
						result.next();



						int count1 = 0;
						try {
							result.last();
							count1 = result.getRow();
							result.beforeFirst();
							result.next();
						}catch(SQLException e2) {
							e2.printStackTrace();
						}
						for(int i1 = 0; i1 < count1; i1++) {
							System.out.println(result.getInt(1) + "\t" + result.getString(2) + "\t" + result.getInt(3)
							+ "\t" + result.getFloat(4));
							result.next();
						}
					}catch(SQLException e1) {
						e1.printStackTrace();
					}
					System.out.println("______________________________________________________");
					System.out.println("\t\t\t" + amount);
					System.out.println("\t\t\t" + gst);
					System.out.println("\t\t\t" + finalAmt);
					ps = con.prepareStatement("delete from cart where custId = ?");
					ps.setInt(1, custId);

					i = ps.executeUpdate();
					if(i > 0) {
						System.out.println("Thank You for shopping");
						ps = con.prepareStatement("update customer set openingbalance = ? where custId = ?");
						ps.setDouble(1, (balance - amount));
						ps.setInt(2, custId);

						ps.executeUpdate();
					}else {
						System.out.println("Something went wrong");
					}
				}else {
					System.out.println("Something went wrong");
				}
			}else {
				System.out.println("Insufficient balance Either add balance or remove items");
			}
		}catch(SQLException e1) {
			e1.printStackTrace();
		}


	}
	private void viewCart() {

		
		System.out.println("Id\tName\tQuantity\tAmount");
		try {/*
			ps = con.prepareStatement("select c.prodID, p.prodName, c.quantity, c.amount from cart c"
					+ ", product p where c.prodId = p.prodId");*/

			Statement state = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet result = state.executeQuery("select prodId, prodName, sum(quantity), sum(amount) from cart where custId = " + custId + " group by prodId, prodName order by prodID");
			result.next();



			int count = 0;
			try {
				result.last();
				count = result.getRow();
				result.beforeFirst();
				result.next();
			}catch(SQLException e2) {
				e2.printStackTrace();
			}
			for(int i = 0; i < count; i++) {
				System.out.println(result.getInt(1) + "\t" + result.getString(2) + "\t" + result.getInt(3)
				+ "\t" + result.getFloat(4));
				result.next();
			}
		}catch(SQLException e1) {
			e1.printStackTrace();
		}

		System.out.println();
		System.out.println("1.Confirm\n2.Remove an Item");
		switch(s.nextInt()) {
		case 1 :
			billing();
			break;
		case 2 :
			System.out.println("Enter the product Id to remove");
			try {
				ps = con.prepareStatement("delete from cart where prodId = ?");
				ps.setInt(1, s.nextInt());
				int i = ps.executeUpdate();

				if(i > 0) {
					System.out.println("Product removed");
					viewCart();
				}else {
					System.out.println("something went wrong");
				}
			}catch(SQLException e2) {
				e2.printStackTrace();
			}
			break;
		}
	}

	private void viewTransactions() {
		System.out.println("-------------Transactions--------------");
		try {
			state = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet result = state.executeQuery("select * from bill where custId = " + custId);
			if(result.next()) {
				int count = 0;
				try {
					result.last();
					count = result.getRow();
				}catch(SQLException e2) {
					e2.printStackTrace();
				}finally {
					result.beforeFirst();
					result.next();
				}

				/*System.out.println("Sr.No \tProducts \t Amount \tDate");

				for(int i = 0; i < count; i++) {
					System.out.println((i + 1) + "\t" + result.getString(2) + "\t" + result.getFloat(7) + "\t" + result.getTimestamp(6));
					result.next();
				}
				*/
				String leftAlignFormat = "| %-4d | %-11s | %-14f |  %-20s  |%n";
				System.out.format("+------+-------------+----------------+-------------------------+%n");
				System.out.format("|Sr.no |   Products  |     Amount     |            Date         |%n");
				System.out.format("+------+-------------+----------------+-------------------------+%n");
				for(int i = 0; i < count; i++) {
		System.out.format(leftAlignFormat, (i+1), result.getString(2), result.getFloat(7), result.getTimestamp(6));			
					result.next();
				}
				System.out.format("+------+-------------+----------------+-------------------------+%n");
			}else {
				System.out.println("No records Found");
			}
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
	}
	private void addBalance() {
		System.out.println("Enter the amount to be added");
		float amount = s.nextFloat();

		if(amount > 0) {
			System.out.print("Enter OTP:- ");
			int OTP = s.nextInt();
			if(OTP == 5968) {
				try {
					ps = con.prepareStatement("update customer set openingbalance = openingbalance + ?");
					ps.setFloat(1, amount);
					int i = ps.executeUpdate();
					if(i > 0) {
						System.out.println("Amount Added");
					}else {
						System.out.println("Something went wrong");
					}
				}catch(SQLException e2) {
					e2.printStackTrace();
				}
			}else {
				System.out.println("Incorrect OTP");
				addBalance();
			}
		}else {
			System.out.println("Enter valid amount");
			addBalance();
		}
	}
}

