package usage;

import dao.PurchaseDAO;
import model.Purchase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class PurchaseTest {
    private static Connection connection;
    private static PurchaseDAO purchaseDao;

    public static void main(String[] args) {
        try {
            // Set up the database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/finance_app", "root", "root");
            purchaseDao = new PurchaseDAO(connection);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter User ID to view purchases: ");
            int userId = scanner.nextInt();

            // Retrieve and display all purchases for the given user ID
            List<Purchase> purchases = purchaseDao.readAllPurchasesByUserId(userId);
            if (purchases.isEmpty()) {
                System.out.println("No purchases found for user ID: " + userId);
            } else {
                System.out.println("Purchases for User ID: " + userId);
                for (Purchase purchase : purchases) {
                    System.out.println(purchase);
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
}
