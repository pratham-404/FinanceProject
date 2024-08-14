package test;

import org.junit.Before;
import org.junit.Test;

import model.Admin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class Admin_test {
    
    private Admin admin;

    @Before
    public void setUp() {
        // Initialize an Admin object before each test
        admin = new Admin(1, "adminUser", "adminPass");
    }

    @Test
    public void testConstructorAndGetters() {
        // Test constructor and getters
        assertEquals(1, admin.getAdminId());
        assertEquals("adminUser", admin.getUsername());
        assertEquals("adminPass", admin.getPassword());
    }

    @Test
    public void testSetters() {
        // Test setters
        admin.setAdminId(2);
        admin.setUsername("newUser");
        admin.setPassword("newPass");

        assertEquals(2, admin.getAdminId());
        assertEquals("newUser", admin.getUsername());
        assertEquals("newPass", admin.getPassword());
    }

    @Test
    public void testToString() {
        // Test toString method
        String expectedString = "Admin [adminId=1, username=adminUser, password=adminPass]";
        assertEquals(expectedString, admin.toString());
    }
}
