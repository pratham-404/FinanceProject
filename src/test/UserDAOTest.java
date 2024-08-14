package test;
import dao.UserDAO;
import model.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class UserDAOTest {
    private static Connection connection;
    private UserDAO userDAO;

    @BeforeClass
    public static void setUpBeforeClass() throws SQLException {
        // Set up database connection before any test method is run
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        connection.createStatement().execute("CREATE TABLE users (" +
                "user_id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), dob DATE, email VARCHAR(255), " +
                "phone_no VARCHAR(255), username VARCHAR(255), password VARCHAR(255), address VARCHAR(255), " +
                "card_type VARCHAR(255), bank_name VARCHAR(255), account_no VARCHAR(255), ifsc_code VARCHAR(255), " +
                "total_credit FLOAT, used_credit FLOAT, is_active BOOLEAN)");
    }

    @AfterClass
    public static void tearDownAfterClass() throws SQLException {
        // Clean up the database after all test methods have been run
        connection.createStatement().execute("DROP TABLE users");
        connection.close();
    }

    @Before
    public void setUp() {
        // Initialize UserDAO before each test
        userDAO = new UserDAO(connection);
    }

    @After
    public void tearDown() {
        // Cleanup after each test if necessary
    }

    @Test
    public void testCreateUser() {
        User user = new User("John Doe",  LocalDate.now(), "john.doe@example.com", "1234567890", "johndoe", "pass12345", "123 Main St", "Gold", "Bank A", "12345678", "IFSC001");

        boolean result = userDAO.createUser(user);
        assertTrue(result);
    }

    @Test
    public void testVerifyUsernameAndPassword() {
        User user = new User("John Doe",  LocalDate.now(), "john.doe@example.com", "1234567890", "johndoe", "password", "123 Main St", "Gold", "Bank A", "12345678", "IFSC001");
        userDAO.createUser(user);

        User verifiedUser = userDAO.verifyUsernameAndPassword("johndoe", "password");
        assertNotNull("User should be verified successfully", verifiedUser);
        assertEquals("Verified user's name should match", "John Doe", verifiedUser.getName());
    }

    @Test
    public void testVerifyUsernameAndPhone() {
        User user = new User("Jane Doe",  LocalDate.now(), "jane.doe@example.com", "0987654321", "janedoe", "password", "456 Main St", "Platinum", "Bank B", "87654321", "IFSC002");
        userDAO.createUser(user);

        boolean result = userDAO.verifyUsernameAndPhone("janedoe", "0987654321");
        assertTrue("User should be verified by username and phone number", result);
    }

    @Test
    public void testUpdatePassword() {
        User user = new User("John Doe",  LocalDate.now(), "john.doe@example.com", "1234567890", "johndoe", "password", "123 Main St", "Gold", "Bank A", "12345678", "IFSC001");
        userDAO.createUser(user);

        boolean result = userDAO.updatePassword("johndoe", "newpassword");
        assertTrue("Password should be updated successfully", result);

        User verifiedUser = userDAO.verifyUsernameAndPassword("johndoe", "newpassword");
        assertNotNull("User should be verified with the new password", verifiedUser);
    }


    @Test
    public void testReadAllInactiveUsers() {
        User user1 = new User("Inactive User 1",  LocalDate.now(), "inactive1@example.com", "1111111111", "inactive1", "password", "111 Main St", "Silver", "Bank C", "11111111", "IFSC003");
        User user2 = new User("Inactive User 2",  LocalDate.now(), "inactive2@example.com", "2222222222", "inactive2", "password", "222 Main St", "Bronze", "Bank D", "22222222", "IFSC004");
        user1.setActive(false);
        user2.setActive(false);
        userDAO.createUser(user1);
        userDAO.createUser(user2);

        List<User> inactiveUsers = userDAO.readAllInactiveUsers();
        assertEquals("There should be two inactive users", 2, inactiveUsers.size());
    }

    @Test
    public void testUpdateActivation() {
        User user = new User("John Doe",  LocalDate.now(), "john.doe@example.com", "1234567890", "johndoe", "password", "123 Main St", "Gold", "Bank A", "12345678", "IFSC001");
        userDAO.createUser(user);

        boolean result = userDAO.updateActivation(1, true);
        assertTrue(result);
    }


    @Test
    public void testDeactivateUser() {
        User user = new User("John Doe", LocalDate.now(), "john.doe@example.com", "1234567890", "johndoe", "password", "123 Main St", "Gold", "Bank A", "12345678", "IFSC001");
        userDAO.createUser(user);

        boolean result = userDAO.deactivateUser(1);
        assertTrue("User should be deactivated successfully", result);

        User deactivatedUser = userDAO.readCardDetails(1);
        assertFalse("User should be inactive", deactivatedUser.isActive());
    }
}
