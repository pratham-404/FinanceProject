package test;

import org.junit.Before;
import org.junit.Test;

import model.Installment;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class Installment_Test {
    
    private Installment installment;

    @Before
    public void setUp() {
        // Initialize an Installment object before each test
        installment = new Installment(1, new Date(), 100.0, "paid");
    }

    @Test
    public void testConstructorAndGetters() {
        // Test constructor and getters
        Installment inst = new Installment(1, 2, new Date(), 200.0, "pending", new Date());
        assertEquals(1, inst.getInstallmentId());
        assertEquals(2, inst.getPurchaseId());
        assertNotNull(inst.getInstallmentDueDate()); // Test if Date object is not null
        assertEquals(200.0, inst.getAmount(), 0.01);
        assertEquals("pending", inst.getPaymentStatus());
        assertNotNull(inst.getPaymentDate()); // Test if Date object is not null
    }

    @Test
    public void testSetters() {
        // Test setters
        installment.setInstallmentId(2);
        installment.setPurchaseId(3);
        installment.setInstallmentDueDate(new Date());
        installment.setAmount(150.0);
        installment.setPaymentStatus("paid");
        installment.setPaymentDate(new Date());

        assertEquals(2, installment.getInstallmentId());
        assertEquals(3, installment.getPurchaseId());
        assertNotNull(installment.getInstallmentDueDate()); // Test if Date object is not null
        assertEquals(150.0, installment.getAmount(), 0.01);
        assertEquals("paid", installment.getPaymentStatus());
        assertNotNull(installment.getPaymentDate()); // Test if Date object is not null
    }

   /* @Test
    public void testToString() {
        // Test toString method
        String expectedString = "Installment [installmentId=1, purchaseId=0, installmentDueDate=" 
                                + installment.getInstallmentDueDate() + ", amount=100.0, paymentStatus=paid, paymentDate=null]";
        assertEquals(expectedString, installment.toString());
    }*/
}
