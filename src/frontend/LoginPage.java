package frontend;

import java.sql.Connection;
import java.util.Scanner;

import dao.AdminDAO;
import dao.DatabaseConnection;
import dao.UserDAO;
import model.Admin;
import model.User;

public class LoginPage {

	String name;
	String password;
	
	//instances of UserDao
	Connection connection = DatabaseConnection.getConnection();
	UserDAO userDao = new UserDAO(connection);
	AdminDAO adminDao = new AdminDAO(connection);
	Line l = new Line();
	Scanner sc = new Scanner(System.in);
	
	public void display(User isLoggedIn, Admin isAdmin) {
		System.out.println("\n");
		System.out.println("\t\t\t\tLogin Page");
		l.line(90);
		System.out.println("Enter User name: ");
		String name = sc.nextLine();
		System.out.println("Enter Password: ");
		String password = sc.nextLine();
		
		l.line(50);
		
		// Is it a normal user or admin user?
		login(name,password, isLoggedIn, isAdmin);
		
		
	}
	
	public void login(String username, String password,User isLoggedIn, Admin isAdmin) {
		// Check if the username exists in Admin table.
		
		//ystem.out.println("Checking password!");
		
		 User userCheck = userDao.verifyUsernameAndPassword(username, password);
		 Admin adminCheck = adminDao.verifyUsernameAndPassword(username,password);
		 
		 
		 if(userCheck != null) {
			 	isLoggedIn = userCheck;
				System.out.println("Login Successfull");
				HomePage home = new HomePage();
				home.display(isLoggedIn, isAdmin);
		 }
		 else if(adminCheck != null) {
			 isAdmin  =  adminCheck;
			 System.out.println("Login Successfull");
			 System.out.println("Admin Logged in....");
			 HomePage home = new HomePage();
			home.display(isLoggedIn, isAdmin);
		 }
		 else {
			 System.out.println("User not found, kindly register");
		 }
      
	}
}
