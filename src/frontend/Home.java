package frontend;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import frontend.AdminDashboard;
import model.Admin;
import model.User;

// Sakshi:
// 		-> Login Page
// 		-> Register Page
// 		-> Admin Dashboard Page

// Aman:
// 		-> View Product Page -> Buy option... eligibility
// 		-> User Dashboard Page


public class Home {
	
	static User isLoggedIn = null;
	static Admin isAdmin = null;

	public static void main(String args[]) {
		
		HomePage home = new HomePage();
		home.display(isLoggedIn, isAdmin);
		
		//AdminDashboard admin = new AdminDashboard();
		//admin.display( isLoggedIn, isAdmin);
	}
	

}

class Line{
	
	public void line(int len) {
		for(int i=0;i<len; i++) {
			System.out.print("-");
		}
		System.out.println();
	}
	
}


class HomePage{
	
	Line l = new Line();
	Scanner sc = new Scanner(System.in);
	
	public void display(User isLoggedIn, Admin isAdmin) {
		System.out.println("\n\t\t\t\tFinance Application");
		l.line(90);
		menu(isLoggedIn, isAdmin);
	}
	

	public void menu(User isLoggedIn, Admin isAdmin) {
		
		int option;
		
		if(isLoggedIn == null && isAdmin == null)
		{	
			System.out.println("1. Login");
			System.out.println("2. Register");
			System.out.println("3. View Products");
			System.out.print("Enter your choice: ");
			option = sc.nextInt();
			switch(option) {
			case 1:
				LoginPage lp = new LoginPage();
				lp.display(isLoggedIn, isAdmin);
				break;
			case 2: 
				System.out.println("Hello");
				RegisterPage rp = new RegisterPage();
				rp.display(isLoggedIn, isAdmin);
				System.out.println("register() Called");
				break;
			case 3:
				ViewProducts vp = new ViewProducts();
				vp.display(isLoggedIn, isAdmin);
				break;
			default:
				System.out.println("Please enter a valid choice next time!");
				break;
			}
		}
		else if(isAdmin == null) {
			System.out.println("1. View Products");
			System.out.println("2. My Dashboard");
			System.out.print("Enter your choice: ");
			option = sc.nextInt();
			switch(option) {
			case 1:
				System.out.println("viewProduct() Called");
				break;
			case 2:
				System.out.println("myDashboard() Called");
				break;
			default:
				System.out.println("Please enter a valid choice next time!");
				break;
			}
		}
		else {
			System.out.println("1. View Products");
			System.out.println("2. My Dashboard");
			System.out.println("3. View Admin Dashboard");
			System.out.print("Enter your choice: ");
			option = sc.nextInt();
			switch(option) {
			case 1:
				System.out.println("viewProduct() Called");
				break;
			case 2:
				System.out.println("myDashboard() Called");
				break;
			case 3:
				System.out.println("adminDashboard() Called");
				break;
			default:
				System.out.println("Please enter a valid choice next time!");
				break;
			}
		}
	}
}