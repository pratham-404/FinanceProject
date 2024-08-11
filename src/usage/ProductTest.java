package usage;

import dao.ProductDao;
import model.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class ProductTest {
    private static Connection connection;
    private static ProductDao productDao;

    public static void main(String[] args) {
        try {
            // Set up the database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/finance_app", "root", "root");
            productDao = new ProductDao(connection);

            // Read all products and display them
            List<Product> products = productDao.readAllProducts();
            if (products.isEmpty()) {
                System.out.println("No products found.");
            } else {
                System.out.println("Product List:");
                for (Product product : products) {
                    System.out.println(product);
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
