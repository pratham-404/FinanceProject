package test;

import model.Admin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import dao.AdminDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

public class AdminDAOTest {
    private Connection connection;
    private AdminDAO adminDAO;

    @Before
    public void setUp() throws SQLException {
        // Initialize in-memory H2 database and AdminDAO
        connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
        createSchema();
        adminDAO = new AdminDAO(connection);
    }

    @After
    public void tearDown() throws SQLException {
        // Clean up and close the database connection
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS admin");
        }
        connection.close();
    }

    private void createSchema() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // Check if the table exists before creating it
            stmt.execute("CREATE TABLE IF NOT EXISTS admin (" +
                    "admin_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(255) UNIQUE, " +
                    "password VARCHAR(255))");
        }
    }

    private void insertAdmin(String username, String password) throws SQLException {
        String insertSQL = "INSERT INTO admin (username, password) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertSQL)) {
            stmt.setString(1, username);
            stmt.setString(2, BCrypt.hashpw(password, BCrypt.gensalt()));
            stmt.executeUpdate();
        }
    }

    @Test
    public void testVerifyUsernameAndPasswordSuccess() throws SQLException {
        // Insert a test admin into the database
        insertAdmin("admin_user", "admin_pass");

        Admin admin = adminDAO.verifyUsernameAndPassword("admin_user", "admin_pass");
        assertNotNull(admin);
        assertEquals("admin_user", admin.getUsername());
    }

    @Test
    public void testVerifyUsernameAndPasswordFailure() throws SQLException {
        // Insert a test admin into the database
        insertAdmin("admin_user", "admin_pass");

        Admin admin = adminDAO.verifyUsernameAndPassword("admin_user", "wrong_pass");
        assertNull(admin);
    }

    @Test
    public void testVerifyUsernameNotFound() {
        Admin admin = adminDAO.verifyUsernameAndPassword("nonexistent_user", "password");
        assertNull(admin);
        System.out.println("USER NOT FOUND");
    }
}
