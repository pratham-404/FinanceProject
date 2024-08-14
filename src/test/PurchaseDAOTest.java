package test;
import model.Purchase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.PurchaseDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class PurchaseDAOTest {
    private Connection connection;
    private PurchaseDAO purchaseDAO;

    @Before
    public void setUp() throws SQLException {
        // Initialize in-memory H2 database and PurchaseDAO
        connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
        createSchema();
        purchaseDAO = new PurchaseDAO(connection);
    }

    @After
    public void tearDown() throws SQLException {
        // Clean up and close the database connection
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS purchases");
        }
        connection.close();
    }

    private void createSchema() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // Create tables with relevant schema
            stmt.execute("CREATE TABLE IF NOT EXISTS purchases (" +
                         "purchase_id INT AUTO_INCREMENT PRIMARY KEY, " +
                         "user_id INT, " +
                         "product_id INT, " +
                         "purchase_date TIMESTAMP, " +
                         "total_amount DOUBLE, " +
                         "amount_paid DOUBLE, " +
                         "emi_period VARCHAR(255), " +
                         "installment_count INT, " +
                         "installment_amount DOUBLE, " +
                         "payment_status VARCHAR(255))");
        }
    }

    private void insertPurchase(int purchaseId, int userId, int productId, Date purchaseDate, double totalAmount, double amountPaid, String emiPeriod, int installmentCount, double installmentAmount, String paymentStatus) throws SQLException {
        String insertSQL = "INSERT INTO purchases (purchase_id, user_id, product_id, purchase_date, total_amount, amount_paid, emi_period, installment_count, installment_amount, payment_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertSQL)) {
            stmt.setInt(1, purchaseId);
            stmt.setInt(2, userId);
            stmt.setInt(3, productId);
            stmt.setTimestamp(4, new Timestamp(purchaseDate.getTime()));
            stmt.setDouble(5, totalAmount);
            stmt.setDouble(6, amountPaid);
            stmt.setString(7, emiPeriod);
            stmt.setInt(8, installmentCount);
            stmt.setDouble(9, installmentAmount);
            stmt.setString(10, paymentStatus);
            stmt.executeUpdate();
        }
    }

    @Test
    public void testCreatePurchase() throws SQLException {
        Purchase purchase = new Purchase(0, 1, 101, "6 months", new Date(), 6000.0, 1000.0, 6, 1000.0, "PENDING");
        boolean result = purchaseDAO.createPurchase(purchase);
        assertTrue(result);
    }

    @Test
    public void testReadAllPurchasesByUserId() throws SQLException {
        // Insert test purchases
        insertPurchase(1, 1, 101, new Date(), 6000.0, 1000.0, "6 months", 6, 1000.0, "PENDING");
        insertPurchase(2, 1, 102, new Date(), 8000.0, 2000.0, "12 months", 12, 1000.0, "COMPLETED");

        List<Purchase> purchases = purchaseDAO.readAllPurchasesByUserId(1);
        assertNotNull(purchases);
        assertEquals(2, purchases.size());
        
        // Verify the content of the first purchase
        Purchase firstPurchase = purchases.get(0);
        assertEquals(1, firstPurchase.getPurchaseId());
        assertEquals(1, firstPurchase.getUserId());
        assertEquals(101, firstPurchase.getProductId());
        assertEquals("6 months", firstPurchase.getEmiPeriod());
        assertEquals("PENDING", firstPurchase.getPaymentStatus());
    }

    @Test
    public void testReadAllPurchasesByUserIdNoPurchases() throws SQLException {
        // Test with no purchases for the given user ID
        List<Purchase> purchases = purchaseDAO.readAllPurchasesByUserId(999);
        assertNotNull(purchases);
        assertTrue(purchases.isEmpty());
    }
}

