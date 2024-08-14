package test;

import org.junit.Before;
import org.junit.Test;

import model.User;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class User_Test {

    private User user;

    @Before
    public void setUp() {
        // Initialize a User object before each test
        user = new User("John Doe",  LocalDate.now(), "john.doe@example.com", "1234567890", "johndoe", "password123", 
                        "123 Elm Street", "Gold", "ABC Bank", "1234567890", "ABC1234");
    }

    @Test
    public void testConstructorAndGetters() {
        // Test constructor and getters
        User u = new User("Jane Smith",  LocalDate.now(), "jane.smith@example.com", "0987654321", "janesmith", "password456", 
                          "456 Oak Avenue", "Silver", "XYZ Bank", "0987654321", "XYZ5678");

        assertEquals("Jane Smith", u.getName());
        assertEquals("jane.smith@example.com", u.getEmail());
        assertEquals("0987654321", u.getPhoneNo());
        assertEquals("janesmith", u.getUsername());
        assertEquals("password456", u.getPassword());
        assertEquals("456 Oak Avenue", u.getAddress());
        assertEquals("Silver", u.getCardType());
        assertEquals("XYZ Bank", u.getBankName());
        assertEquals("0987654321", u.getAccountNo());
        assertEquals("XYZ5678", u.getIfscCode());
    }

    @Test
    public void testSetters() {
        // Test setters
        user.setName("Alice Johnson");
        user.setDob(new Date());
        user.setEmail("alice.johnson@example.com");
        user.setPhoneNo("1122334455");
        user.setUsername("alicejohnson");
        user.setPassword("newpassword789");
        user.setAddress("789 Pine Road");
        user.setCardType("Platinum");
        user.setBankName("LMN Bank");
        user.setAccountNo("1122334455");
        user.setIfscCode("LMN1234");
        user.setUserId(1);
        user.setTotalCredit(50000.0f);
        user.setUsedCredit(10000.0f);
        user.setActive(true);

        assertEquals("Alice Johnson", user.getName());
        assertEquals("alice.johnson@example.com", user.getEmail());
        assertEquals("1122334455", user.getPhoneNo());
        assertEquals("alicejohnson", user.getUsername());
        assertEquals("newpassword789", user.getPassword());
        assertEquals("789 Pine Road", user.getAddress());
        assertEquals("Platinum", user.getCardType());
        assertEquals("LMN Bank", user.getBankName());
        assertEquals("1122334455", user.getAccountNo());
        assertEquals("LMN1234", user.getIfscCode());
        assertEquals(1, user.getUserId());
        assertEquals(50000.0f, user.getTotalCredit(), 0.01);
        assertEquals(10000.0f, user.getUsedCredit(), 0.01);
        assertEquals(true, user.isActive());
    }

    @Test
    public void testToString() {
        // Test toString method
        String expectedString = "User [name=John Doe, dob=" + user.getDob() + ", email=john.doe@example.com, phoneNo=1234567890, " +
                                "username=johndoe, address=123 Elm Street, cardType=Gold, bankName=ABC Bank, " +
                                "accountNo=1234567890, ifscCode=ABC1234]";
        assertEquals(expectedString, user.toString());
    }
}
