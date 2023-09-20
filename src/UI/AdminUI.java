package UI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

import dao.ConnectionClass;
import model.Product;

public class AdminUI {
	Scanner s = new Scanner(System.in);
	Connection con = null;
	PreparedStatement ps = null;

	public void loginAdmin() {
		String adminName = null;
		String password = null;
		boolean logined = false;
		int choice = 0;

		ConnectionClass c = new ConnectionClass();
		System.out.println("Admin");

		do {
			System.out.println("User Name:- ");
			adminName = s.next();
			System.out.println("Password:- ");
			password = s.next();

			if(adminName.equals("admin")) {
				if(password.equals("admin")) {

					// Get the connection
					con = c.getConnection();

					// check whether the admin has logined
					logined = true;

					// for the continuation of the Admin Menu
					int choice1 = 0;
					/**
					 * Repeat the process utill the Admin Logouts.
					 */
					do {

						System.out.println("1.Add product \n2.Display All Products \n3.Search Product \n4.Delete Prodcut \n5.Update \n6.Logout");
						choice = s.nextInt();

						switch(choice) {

						case 1 :
							addProduct();
							break;

						case 2 :
							c.displayPrdoucts();
							//displayAllProducts();
							break;

						case 3 :
							c.searchProduct();
							break;

						case 4 :
							c.deleteProduct();
							break;

						case 5 :
							c.updateProduct();
							break;

						case 6:
							choice1 = 1;
							System.exit(0);
							break;
						}
					}while(choice1 != 1);
				}else {
					System.out.println("Wrong password");
				}
			}else {
				System.out.println("Invalid user Name");
			}
		}while(!logined);
	}

	/**
	 * Adding product menu
	 */
	private void addProduct() {

		System.out.println("Enter Product Id:- ");
		int prodId = s.nextInt();
		System.out.println("Enter Product Name:- ");
		String prodName = s.next();
		System.out.println("Enter Product cost:- ");
		float cost = s.nextFloat();
		System.out.println("Enter Product Stock:- ");
		int stock = s.nextInt();

		Product p = new Product(prodId, prodName, cost, stock);

		int i = new ConnectionClass().insertData(p);
		if(i > 0) {
			System.out.println("Product Added");;
		}else {
			System.out.println("OOPS something went wrong");
		}
	}

	/*
	/**
	 * displaying all products menu

	public void displayAllProducts() {

		try {
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

			System.out.println("ID\tName\tPrice\tStock");
			for(int i = 0; i < count; i++) {
				System.out.println(result.getInt(1) + "\t" + result.getString(2) + "\t" + result.getFloat(3) + "\t" + result.getInt(4));
				result.next();
			}
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
	}*/
	/*
	/**
	 * Searching Menu

	private void searchProduct() {

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
	 */
	/**
	 * Delete Product Menu
	 */
	/*private void deleteProduct() {
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
	 */
	/**
	 * Update Product Menu
	 */
	/*	private void updateProduct() {
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
	 */
}
