package test;

import model.Installment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.InstallmentDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.*;

public class InstallmentDAOTest {
    private Connection connection;
    private InstallmentDAO installmentDAO;

    @Before
    public void setUp() throws SQLException {
        // Initialize in-memory H2 database and InstallmentDAO
        connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
        createSchema();
        installmentDAO = new InstallmentDAO(connection);
    }

    @After
    public void tearDown() throws SQLException {
        // Clean up and close the database connection
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS installments");
            stmt.execute("DROP TABLE IF EXISTS purchases");
        }
        connection.close();
    }

    private void createSchema() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // Create tables with relevant schema
            stmt.execute("CREATE TABLE IF NOT EXISTS purchases (" +
                         "purchase_id INT AUTO_INCREMENT PRIMARY KEY, " +
                         "user_id VARCHAR(255))");

            stmt.execute("CREATE TABLE IF NOT EXISTS installments (" +
                         "installment_id INT AUTO_INCREMENT PRIMARY KEY, " +
                         "purchase_id INT, " +
                         "installment_due_date TIMESTAMP, " +
                         "amount DOUBLE, " +
                         "payment_status VARCHAR(255), " +
                         "payment_date TIMESTAMP, " +
                         "FOREIGN KEY (purchase_id) REFERENCES purchases(purchase_id))");
        }
    }

    private void insertPurchase(int purchaseId, String userId) throws SQLException {
        String insertSQL = "INSERT INTO purchases (purchase_id, user_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertSQL)) {
            stmt.setInt(1, purchaseId);
            stmt.setString(2, userId);
            stmt.executeUpdate();
        }
    }

    private void insertInstallment(int installmentId, int purchaseId, Timestamp dueDate, double amount, String status, Timestamp paymentDate) throws SQLException {
        String insertSQL = "INSERT INTO installments (installment_id, purchase_id, installment_due_date, amount, payment_status, payment_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertSQL)) {
            stmt.setInt(1, installmentId);
            stmt.setInt(2, purchaseId);
            stmt.setTimestamp(3, dueDate);
            stmt.setDouble(4, amount);
            stmt.setString(5, status);
            stmt.setTimestamp(6, paymentDate);
            stmt.executeUpdate();
        }
    }

    @Test
    public void testCreateInstallment() throws SQLException {
        // Insert a test purchase
        insertPurchase(1, "user123");

        Installment installment = new Installment(1, 1, new Timestamp(System.currentTimeMillis()), 100.0, "PENDING", null);
        boolean result = installmentDAO.createInstallment(installment);
        assertTrue(result);
    }

    @Test
    public void testGetInstallmentsByUserId() throws SQLException {
        // Insert a test purchase and installments
        insertPurchase(1, "user123");
        insertInstallment(1, 1, new Timestamp(System.currentTimeMillis() - 10000), 100.0, "PENDING", null);
        insertInstallment(2, 1, new Timestamp(System.currentTimeMillis() + 10000), 150.0, "COMPLETED", new Timestamp(System.currentTimeMillis()));

        List<Installment> installments = installmentDAO.getInstallmentsByUserId("user123");
        assertNotNull(installments);
        assertEquals(2, installments.size());
        
        // Check installment order
        assertTrue(installments.get(0).getInstallmentDueDate().before(installments.get(1).getInstallmentDueDate()));
    }

    @Test
    public void testGetInstallmentsByUserIdNoInstallments() throws SQLException {
        // Insert a test purchase without installments
        insertPurchase(1, "user123");

        List<Installment> installments = installmentDAO.getInstallmentsByUserId("user123");
        assertNotNull(installments);
        assertTrue(installments.isEmpty());
    }
}
