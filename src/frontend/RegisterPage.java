package frontend;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.regex.Pattern;

import dao.DatabaseConnection;
import dao.UserDAO;
import model.Admin;
import model.User;

import java.sql.Connection;
import java.time.*; 

// import model.User;
// import dao.UserDAO; // Import the DAO class

public class RegisterPage {
	
	private Scanner sc = new Scanner(System.in);
    // private UserDAO userDao = new UserDAO(); // Instantiate UserDao
	
	public void display(User isLoggedIn, Admin isAdmin) {
        String name;
        String dob;
        String email;
        String phoneNo;
        String username;
        String password;
        String address;
        String cardType;
        String bankName;
        String accountNo;
        String ifscCode;
        double totalCredit;
        double usedCredit;
        boolean isActive;

       
        System.out.print("Enter your name: ");
        name = sc.nextLine();

        System.out.print("Enter your Date of Birth (YYYY-MM-DD): ");
        dob = sc.nextLine();
        LocalDate d1 = LocalDate.parse(dob); 


        System.out.print("Enter your Email: ");
        email = sc.nextLine();

        System.out.print("Enter your phone number: ");
        phoneNo = sc.nextLine();

        System.out.print("Enter a username: ");
        username = sc.nextLine();

        System.out.print("Enter a password: ");
        password = sc.nextLine();

        System.out.print("Enter your address: ");
        address = sc.nextLine();

        System.out.print("Enter your card type (gold/platinum): ");
        cardType = sc.nextLine();

        System.out.print("Enter your Bank Name: ");
        bankName = sc.nextLine();

        System.out.print("Enter your account number: ");
        accountNo = sc.nextLine();

        System.out.print("Enter your IFSC code: ");
        ifscCode = sc.nextLine();

        System.out.print("Enter your total credit balance: ");
        totalCredit = sc.nextDouble();

        System.out.print("Enter your used credit: ");
        usedCredit = sc.nextDouble();

        System.out.print("Is your account active (true/false): ");
        isActive = sc.nextBoolean();

        // Create a new User object
        User newUser = new User(name, d1, email,  phoneNo,  username, password,  address,
    			cardType,  bankName,  accountNo,  ifscCode);
        Connection connection = DatabaseConnection.getConnection();
        UserDAO userDAO = new UserDAO(connection);
		// Save the user to the database
        userDAO.createUser(newUser);

        // Redirect to login page
        LoginPage lp = new LoginPage();
        lp.display(isLoggedIn, isAdmin); // Show login page after registration
    }


}
