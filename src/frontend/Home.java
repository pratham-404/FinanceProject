package frontend;

// Sakshi:
// 		-> Login Page
// 		-> Register Page
// 		-> Admin Dashboard Page

// Aman:
// 		-> View Product Page -> Buy option... eligibility
// 		-> User Dashboard Page


public class Home {
	
	static boolean isLoggedIn = true;
	static boolean isAdmin = false;

	public static void main(String[] args) {
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
		if(!isLoggedIn && !isAdmin)
		{	
			System.out.println("1. Login");
			System.out.println("2. Register");
			System.out.println("3. View Products");
		}
		else if(!isAdmin) {
			System.out.println("1. View Products");
			System.out.println("2. My Dashboard");
		}
		else {
			System.out.println("1. View Products");
			System.out.println("2. My Dashboard");
			System.out.println("3. View Admin Dashboard");
		}
	}
}