package frontend;

import dao.UserDAO;
import model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AdminDashboard {
	private Connection connection;
    private UserDAO userDao;

    public void display(boolean isLoggedIn, boolean isAdmin) {
    	try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/finance_app", "root", "Naruto@4523");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        userDao = new UserDAO(connection);
        Scanner scanner = new Scanner(System.in);
    
        while (true) {
            System.out.println("\n\t\t\t\tAdmin Dashboard");
            System.out.println("1. View all users");
            System.out.println("2. Activate/Deactivate user");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    viewAllUsers();
                    break;
                case 2:
//                    activateDeactivateUser();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }
    
    private  void viewAllUsers(){
    	UserDAO dao = new UserDAO(connection); 
    	List<User> ls = null; 
    	 try {
    		 ls = dao.readAllUsers();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 if (ls != null) {
    	        for (User usr : ls) { 
    	            System.out.println(usr); // Print the User object directly
    	        }
    	    }
		//List<User> ls = List<dao.UserDAO.readAllUsers()>;
    	//for (User usr: ls) {
    	//System.out.println("the users are "+  usr);    					
		//}
    };
    
  private boolean activateDeactivateUser(int userId, boolean isActive) {
	  try {
		return userDao.updateActivation(userId, isActive);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return activateDeactivateUser(userId, isActive) ;
  }
  
  
 
 private  List<User> readAllInactiveUsers(){
 	 try {
			return userDao.readAllInactiveUsers();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return readAllInactiveUsers();
 };
    
    /*private static void listInactiveUsers() {
        List<User> inactiveUsers = userDAO.readAllInactiveUsers();
        if (inactiveUsers.isEmpty()) {
            System.out.println("No inactive users found.");
        } else {
            System.out.println("Inactive Users:");
            for (User user : inactiveUsers) {
                System.out.println(user);
            }
        }*/
    
    
    
   
    
}