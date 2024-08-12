package frontend;

import java.util.Scanner;

// Sakshi:
// 		-> Login Page
// 		-> Register Page
// 		-> Admin Dashboard Page

// Aman:
// 		-> View Product Page -> Buy option... eligibility
// 		-> User Dashboard Page


public class Home {
	
	static boolean isLoggedIn = false;
	static boolean isAdmin = false;

	public static void main(String args[]) {
		HomePage home = new HomePage();
		home.display(isLoggedIn, isAdmin);
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
	
	public void display(boolean isLoggedIn, boolean isAdmin) {
		System.out.println("\n\t\t\t\tFinance Application");
		l.line(90);
		menu(isLoggedIn, isAdmin);
	}
	
	public void menu(boolean isLoggedIn, boolean isAdmin) {
		
		Scanner sc = new Scanner(System.in);
		
		int option;
		
		if(!isLoggedIn && !isAdmin)
		{	
			System.out.println("1. Login");
			System.out.println("2. Register");
			System.out.println("3. View Products");
			System.out.println("Enter your choice: ");
			option = sc.nextInt();
			switch(option) {
			case 1:
				LoginPage lp = new LoginPage();
				lp.display(isLoggedIn, isAdmin);
				break;
			case 2: 
				System.out.println("register() Called");
				break;
			case 3:
				ViewProducts vp = new ViewProducts();
				vp.display(isLoggedIn, isAdmin);
				System.out.println("viewProduct() Called");
				break;
			default:
				System.out.println("Please enter a valid choice next time!");
				break;
			}
		}
		else if(!isAdmin) {
			System.out.println("1. View Products");
			System.out.println("2. My Dashboard");
			System.out.println("Enter your choice: ");
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
			System.out.println("Enter your choice: ");
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