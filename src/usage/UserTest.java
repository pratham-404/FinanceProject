package usage;

import dao.UserDAO;
import model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class UserTest {
    private static Connection connection;
    private static UserDAO userDao;

    public static void main(String[] args) {
        try {
            // Set up the database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/finance_app", "root", "root");
            userDao = new UserDAO(connection);

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Update Password");
                System.out.println("4. List Inactive Users");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        // Register a new user
                        registerUser(scanner);
                        break;
                    case 2:
                        // Login
                        loginUser(scanner);
                        break;
                    case 3:
                        // Update Password
                        updatePassword(scanner);
                        break;
                    case 4:
                        // List Inactive Users
                        listInactiveUsers();
                        break;
                    case 5:
                        // Exit
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the connection if it's not null
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void registerUser(Scanner scanner) {
        try {
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Date of Birth (yyyy-MM-dd): ");
            String dobInput = scanner.nextLine();
            Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(dobInput);
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Phone Number: ");
            String phoneNo = scanner.nextLine();
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();
            System.out.print("Address: ");
            String address = scanner.nextLine();
            System.out.print("Card Type (gold/platinum): ");
            String cardType = scanner.nextLine();
            System.out.print("Bank Name: ");
            String bankName = scanner.nextLine();
            System.out.print("Account Number: ");
            String accountNo = scanner.nextLine();
            System.out.print("IFSC Code: ");
            String ifscCode = scanner.nextLine();

            User user = new User(name, dob, email, phoneNo, username, password, address, cardType, bankName, accountNo, ifscCode);
            user.setTotalCredit(cardType.equalsIgnoreCase("gold") ? 100000 : 300000); // Set default total credit
            user.setUsedCredit(0); // Initial used credit
            user.setActive(false); // Set inactive by default

            if (userDao.createUser(user)) {
                System.out.println("User registered successfully!");
            } else {
                System.out.println("User registration failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loginUser(Scanner scanner) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = userDao.verifyUsernameAndPassword(username, password);
        if (user != null) {
            System.out.println("Login successful! Welcome, " + user.getName() + ".");
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static void updatePassword(Scanner scanner) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("New Password: ");
        String newPassword = scanner.nextLine();

        if (userDao.updatePassword(username, newPassword)) {
            System.out.println("Password updated successfully!");
        } else {
            System.out.println("Failed to update password.");
        }
    }

    private static void listInactiveUsers() {
        List<User> inactiveUsers = userDao.readAllInactiveUsers();
        if (inactiveUsers.isEmpty()) {
            System.out.println("No inactive users found.");
        } else {
            System.out.println("Inactive Users:");
            for (User user : inactiveUsers) {
                System.out.println(user);
            }
        }
    }
}
