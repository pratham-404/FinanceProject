package test;

import model.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.ProductDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;

public class ProductDAOTest {
    private Connection connection;
    private ProductDAO productDAO;

    @Before
    public void setUp() throws SQLException {
        // Initialize in-memory H2 database and ProductDAO
        connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
        createSchema();
        productDAO = new ProductDAO(connection);
        insertTestData(); // Insert some test products
    }

    @After
    public void tearDown() throws SQLException {
        // Clean up and close the database connection
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS products");
        }
        connection.close();
    }

    private void createSchema() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // Create tables with relevant schema
            stmt.execute("CREATE TABLE IF NOT EXISTS products (" +
                         "product_id INT AUTO_INCREMENT PRIMARY KEY, " +
                         "product_name VARCHAR(255), " +
                         "product_details VARCHAR(255), " +
                         "cost DOUBLE)");
        }
    }

    private void insertTestData() throws SQLException {
        String insertSQL = "INSERT INTO products (product_name, product_details, cost) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertSQL)) {
            stmt.setString(1, "Product A");
            stmt.setString(2, "Details about Product A");
            stmt.setDouble(3, 100.0);
            stmt.executeUpdate();

            stmt.setString(1, "Product B");
            stmt.setString(2, "Details about Product B");
            stmt.setDouble(3, 150.0);
            stmt.executeUpdate();
        }
    }

    @Test
    public void testReadAllProducts() {
        List<Product> products = productDAO.readAllProducts();
        assertNotNull(products);
        assertEquals(2, products.size());

        // Verify the content of the first product
        Product firstProduct = products.get(0);
        assertEquals("Product A", firstProduct.getProductName());
        assertEquals("Details about Product A", firstProduct.getProductDetails());
        assertEquals(100.0, firstProduct.getCost(), 0.01);

        // Verify the content of the second product
        Product secondProduct = products.get(1);
        assertEquals("Product B", secondProduct.getProductName());
        assertEquals("Details about Product B", secondProduct.getProductDetails());
        assertEquals(150.0, secondProduct.getCost(), 0.01);
    }
}
