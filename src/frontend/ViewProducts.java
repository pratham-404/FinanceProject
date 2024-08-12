package frontend;

import model.Product;
import dao.DatabaseConnection;
import dao.ProductDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class ViewProducts {
	
	public void display(boolean isLoggedIn, boolean isAdmin) {
		Connection connection = DatabaseConnection.getConnection();
		
		if (connection != null) {
	        // Create an instance of ProductDao
	        ProductDAO productDao = new ProductDAO(connection);

	        // Retrieve all products
	        List<Product> products = productDao.readAllProducts();

	        // Print out the products in a formatted card style
	        for (Product product : products) {
	            System.out.println(ProductFormatter.formatProductAsCard(product));
	        }

	        // Close the connection
	        try {
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    } else {
	        System.out.println("Failed to connect to the database.");
	    }
	}

}
