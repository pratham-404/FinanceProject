package frontend;

import java.util.Scanner;

import dao.UserDAO;

public class LoginPage {

	String name;
	String password;
	
	//instances of UserDao
	UserDAO userDao = new UserDAO();
	Line l = new Line();
	Scanner sc = new Scanner(System.in);
	
	public void display(boolean isLoggedIn, boolean isAdmin) {
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
	
	public void login(String username, String password,boolean isLoggedIn, boolean isAdmin) {
		// Check if the username exists in Admin table.
		
		System.out.println("Checking password!");
		
		 boolean isUser = UserDao.isUsernameInUser(username);
     boolean isAdmin = UserDao.isUsernameInAdmin(username);
      
     if(!isUser && !isAdmin) {
	    	 System.out.println("User not found, kindly register");
	     } 
		 else if(isAdmin){
			User result = adminDAO.verifyUsernameAndPassword(username, password);
 			
		}
	else {
		
 			 User result = adminDAO.verifyUsernameAndPassword(username, password);
		

	        
		
	}

}
