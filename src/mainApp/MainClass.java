package mainApp;

import java.util.Scanner;
import UI.*;

public class MainClass {

	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);
		int userChoice = 0;


		System.out.println("-------------Welcome to Shopping Application-------------");

		System.out.println("Who are you?");
		System.out.println("1.Customer \n2.Admin");
		userChoice = s.nextInt();

		if(userChoice == 1) {

			CustomerUI c = new CustomerUI();

			System.out.println("1.Register \n2.Login");
			switch(s.nextInt()) {
			case 1 : 
				c.registerCustomer();
				break;
			case 2:
				c.loginCutomer();
				break;
			default :
				System.out.println("Wrong Choice");
			}

		}else if(userChoice == 2){

			AdminUI a = new AdminUI();
			a.loginAdmin();

		}else {
			System.out.println("Wrong Choice");

		}


		s.close();
	}
}
