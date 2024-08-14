package test;

import org.junit.Before;
import org.junit.Test;

import model.Purchase;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class Purchase_Test {

    private Purchase purchase;

    @Before
    public void setUp() {
        // Initialize a Purchase object before each test
        purchase = new Purchase(1, 1001, 5001, "6 months", new Date(), 1200.0, 600.0, 6, 200.0, "completed");
    }

    @Test
    public void testConstructorAndGetters() {
        // Test constructor and getters
        Purchase p = new Purchase(2, 1002, 5002, "12 months", new Date(), 2400.0, 1200.0, 12, 200.0, "pending");

        assertEquals(2, p.getPurchaseId());
        assertEquals(1002, p.getUserId());
        assertEquals(5002, p.getProductId());
        assertEquals("12 months", p.getEmiPeriod());
        assertEquals(2400.0, p.getTotalAmount(), 0.01);
        assertEquals(1200.0, p.getAmountPaid(), 0.01);
        assertEquals(12, p.getInstallmentCount());
        assertEquals(200.0, p.getInstallmentAmount(), 0.01);
        assertEquals("pending", p.getPaymentStatus());
    }

    @Test
    public void testSetters() {
        // Test setters
        purchase.setPurchaseId(3);
        purchase.setUserId(1003);
        purchase.setProductId(5003);
        purchase.setEmiPeriod("9 months");
        purchase.setTotalAmount(1800.0);
        purchase.setAmountPaid(900.0);
        purchase.setInstallmentCount(9);
        purchase.setInstallmentAmount(200.0);
        purchase.setPaymentStatus("failed");

        assertEquals(3, purchase.getPurchaseId());
        assertEquals(1003, purchase.getUserId());
        assertEquals(5003, purchase.getProductId());
        assertEquals("9 months", purchase.getEmiPeriod());
        assertEquals(1800.0, purchase.getTotalAmount(), 0.01);
        assertEquals(900.0, purchase.getAmountPaid(), 0.01);
        assertEquals(9, purchase.getInstallmentCount());
        assertEquals(200.0, purchase.getInstallmentAmount(), 0.01);
        assertEquals("failed", purchase.getPaymentStatus());
    }

    @Test
    public void testToString() {
        // Test toString method
        String expectedString = "Purchase [purchaseId=1, userId=1001, productId=5001, emiPeriod=6 months, purchaseDate=" +
                                purchase.getPurchaseDate() + ", totalAmount=1200.0, amountPaid=600.0, installmentCount=6, " +
                                "installmentAmount=200.0, paymentStatus=completed]";
        assertEquals(expectedString, purchase.toString());
    }
}
