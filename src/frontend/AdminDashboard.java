package frontend;

import dao.DatabaseConnection;
import dao.UserDAO;
import model.User;
import model.Admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AdminDashboard {
	Connection connection = DatabaseConnection.getConnection();
    private UserDAO userDao;

    public void display(User isLoggedIn, Admin isAdmin) {
    	
        userDao = new UserDAO(connection);
        Scanner scanner = new Scanner(System.in);
    
        while (true) {
            System.out.println("\n\t\t\t\tAdmin Dashboard");
            System.out.println("1. View all users");
            System.out.println("2. Activate/Deactivate user");
            System.out.println("3. Go Back");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    viewAllUsers();
                    break;
                case 2:
                	System.out.println("User_id: ");
                    int u_id = scanner.nextInt();

                    System.out.println("Activate(1) / Deactivate(0): ");
                    boolean is_active = scanner.nextBoolean();

                   activateDeactivateUser(u_id,is_active);
                    break;
                case 3:
                	HomePage hp = new HomePage();
                	hp.display(isLoggedIn, isAdmin);
                	break;
                case 4:
                    System.exit(0);
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
			e.printStackTrace();
		}
    	 if (ls != null) {
    	        for (User usr : ls) { 
    	            printUserCard(usr);
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
		e.printStackTrace();
	}
	  return false;
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
    
	 private void printUserCard(User user) {
	     StringBuilder card = new StringBuilder();
	     String border = "+----------------------------------+";
	
	     card.append(border).append("\n");
	     card.append(String.format("| %-30s |\n", "User ID: " + user.getUserId()));
	     card.append(String.format("| %-30s |\n", "Name: " + user.getName()));
	     card.append(String.format("| %-30s |\n", "Username: " + user.getUsername()));
	     card.append(String.format("| %-30s |\n", "DOB: " + user.getDob()));
	     card.append(String.format("| %-30s |\n", "Email: " + user.getEmail()));
	     card.append(String.format("| %-30s |\n", "Phone No: " + user.getPhoneNo()));
	     card.append(String.format("| %-30s |\n", "Address: " + user.getAddress()));
	     card.append(String.format("| %-30s |\n", "Card Type: " + user.getCardType()));
	     card.append(String.format("| %-30s |\n", "Bank Name: " + user.getBankName()));
	     card.append(String.format("| %-30s |\n", "Account No: " + user.getAccountNo()));
	     card.append(String.format("| %-30s |\n", "IFSC Code: " + user.getIfscCode()));
	     card.append(String.format("| %-30s |\n", "Total Credit: " + user.getTotalCredit()));
	     card.append(String.format("| %-30s |\n", "Used Credit: " + user.getUsedCredit()));
	     card.append(String.format("| %-30s |\n", "Active: " + (user.isActive() ? "Yes" : "No")));
	     card.append(border).append("\n");
	
	     System.out.println(card.toString());
	 }
    
    
   
    
}