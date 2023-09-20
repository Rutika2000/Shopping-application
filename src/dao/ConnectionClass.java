package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Scanner;

import UI.CustomerUI;
import model.Customer;
import model.Product;

public class ConnectionClass {

	Scanner s = new Scanner(System.in);
	static Connection con;
	PreparedStatement ps;
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userName = "system";
	String password = "qwerty";

	LinkedList<Product> productResult = new LinkedList<Product>();
	LinkedList<Customer> customerResult = new LinkedList<Customer>();
	public Connection getConnection() {

		Connection con2 = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			con2 = DriverManager.getConnection(url, userName, password);
		}catch(SQLException e) {
			System.out.println(e);
		}catch(ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		return con2;
	}

	/*
	 * Methods for the Admin
	 * 
	 */
	public int insertData(Object object) {

		con = getConnection();

		if(object instanceof Customer) {

			Customer c = (Customer)object;
			try {

				ps = con.prepareStatement("select cust_seq.nextval from dual");
				ResultSet resultSerialNumber = ps.executeQuery();
				resultSerialNumber.next();

				ps = con.prepareStatement("insert into customer values(?,?,?,?,?,?,?)");
				ps.setInt(1,resultSerialNumber.getInt(1));
				ps.setString(2, c.getCustName());
				ps.setString(3, c.getCustAddress());
				ps.setString(4, c.getCustMobileNumber());
				ps.setString(5, c.getUserName());
				ps.setString(6, c.getPassword());
				ps.setDouble(7, c.getOpeningBalance());
				int i = ps.executeUpdate();
				return i;
			}catch(SQLException e1) {
				e1.printStackTrace();
			}

		}else if(object instanceof Product) {

			Product p = (Product)object;


			try {
				ps = con.prepareStatement("select prod_seq.nextval from dual");
				ResultSet resultSerialNumber = ps.executeQuery();
				resultSerialNumber.next();
				ps = con.prepareStatement("insert into product values(?,?,?,?)");
				ps.setInt(1, p.getProdId());
				ps.setString(2, p.getProdName());
				ps.setFloat(3, p.getProdPrice());
				ps.setInt(4, p.getProdStock());

				int i = ps.executeUpdate();
				return i;		
			}catch(SQLException e1) {
				e1.printStackTrace();
			}		

		}
		return 0;
	}

	public void displayPrdoucts() {
		try {
			con = getConnection();
			Statement state = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet result = state.executeQuery("select * from product");
			result.next();

			int count = 0;
			try {
				result.last();
				count = result.getRow();

			}catch(SQLException e1) {
				e1.printStackTrace();	
			}finally {
				result.beforeFirst();
				result.next();
			}
			
			
			//complete the table format thing
			String leftAllignment = "| %4d";

			System.out.println("ID\tName\tPrice\tStock");
			for(int i = 0; i < count; i++) {
				System.out.println(result.getInt(1) + "\t" + result.getString(2) + "\t" + result.getFloat(3) + "\t" + result.getInt(4));
				result.next();
			}
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
	}
	/*public ResultSet retriveInfo(String name) {
		con = getConnection();


	}*/

	public void searchProduct() {
		con = getConnection();
		System.out.println("1.Search with ID \n2.Search with Name");
		switch(s.nextInt()) {
		case 1 :
			System.out.println("Enter Product Id");
			int id = s.nextInt();
			try {
				ps = con.prepareStatement("select * from product where prodid = " + id);
				ResultSet result = ps.executeQuery();

				//check if the result has some value or not 
				// if yes then display the result
				if(result.next()) {
					System.out.println("ID\tName\tPrice\tStock");
					System.out.println(result.getInt(1) + "\t" + result.getString(2) + "\t" + result.getFloat(3) + "\t" + result.getInt(4));
					System.out.println();
				}
				// if No then show that Error Message
				else {
					System.out.println("Product is not available");
					System.out.println();
				}
			}catch(SQLException e1) {
				e1.printStackTrace();
			}
			break;
		case 2 :
			System.out.println("Enter Product Name");
			String prodName = s.next();
			try {
				ps = con.prepareStatement("select * from product where prodName = '" + prodName + "'");
				ResultSet result = ps.executeQuery();
				if(result.next()) {
					System.out.println("ID\tName\tPrice\tStock");
					System.out.println(result.getInt(1) + "\t" + result.getString(2) + "\t" + result.getFloat(3) + "\t" + result.getInt(4));
					System.out.println();
				}else {
					System.out.println("Product is not available");
					System.out.println();
				}
			}catch(SQLException e1) {
				e1.printStackTrace();
			}
			break;
		default :
			System.out.println("Invalid Choice");
		}
	}

	public void deleteProduct() {
		con = getConnection();
		System.out.println("Enter product Id to be deleted");
		int prodId = s.nextInt();
		try {
			ps = con.prepareStatement("delete from product where prodId = " + prodId);
			int i = ps.executeUpdate();
			if(i > 0) {
				System.out.println("Deleted");
			}else {
				System.out.println("Product does not exist");
			}
		}catch(SQLException e1) {

		}
	}

	public void updateProduct() {
		con = getConnection();
		System.out.println("Enter product Id");
		int prodId = s.nextInt();
		ResultSet result = null;
		try {
			ps = con.prepareStatement("select * from product where prodId = " + prodId);
			result = ps.executeQuery();
			if(result.next()) {

				System.out.println("What do you want to update");
				System.out.println("1.Name \n2.Price \n3.Stock \n4.All Details");
				int i = 0;
				switch(s.nextInt()) {
				case 1 :
					System.out.println("new Name");
					ps = con.prepareStatement("update product set prodName = '" + s.next() + "' where prodId = " + prodId);
					i = ps.executeUpdate();
					if(i > 0) {
						System.out.println("Updated");
						System.out.println();
					}else {
						System.out.println("Something went wrong");
						System.out.println();
					}
					break;

				case 2 :
					System.out.println("new Price");
					ps = con.prepareStatement("update product set rate = ? where prodId = ?");
					ps.setFloat(1, s.nextFloat());
					ps.setInt(2, prodId);
					i = ps.executeUpdate();
					if(i > 0) {
						System.out.println("Updated");
						System.out.println();
					}else {
						System.out.println("Something went wrong");
						System.out.println();
					}
					break;

				case 3 :
					System.out.println("new Stock");
					ps = con.prepareStatement("update product set stock = ? where prodId = ?");
					ps.setInt(1, s.nextInt());
					ps.setInt(2, prodId);
					i = ps.executeUpdate();
					if(i > 0) {
						System.out.println("Updated");
						System.out.println();
					}else {
						System.out.println("Something went wrong");
						System.out.println();
					}
					break;

				case 4 :
					ps = con.prepareStatement("update product set prodName = ?, rate = ?, stock = ? where prodId = ?");
					System.out.println("new Name");
					ps.setString(1, s.next());
					System.out.println("new Price");
					ps.setFloat(2, s.nextFloat());
					System.out.println("new Stock");
					ps.setInt(3, s.nextInt());
					ps.setInt(4, prodId);

					i = ps.executeUpdate();				
					if(i > 0 ) {
						System.out.println("Updated");
					}else {
						System.out.println("Something went wrong");
						System.out.println();

					}
					break;
				}
			}else {
				System.out.println("Product does not exist");
				System.out.println();
			}
		}catch(SQLException e1) {

		}
	}
	
	/*
	 * Methods for the Customer
	 */
	
	public void registerCustomer(Customer cust) {
		
	}
	public void loginCustomer(String userName) {
		
	}
}
