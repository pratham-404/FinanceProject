package test;

import org.junit.Before;
import org.junit.Test;

import model.Product;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class Product_Test {

    private Product product;

    @Before
    public void setUp() {
        // Initialize a Product object before each test
        product = new Product(1, "Laptop", "High-performance laptop", 1200.0);
    }

    @Test
    public void testConstructorAndGetters() {
        // Test constructor and getters
        Product prod = new Product(2, "Smartphone", "Latest model smartphone", 800.0);
        assertEquals(2, prod.getProductId());
        assertEquals("Smartphone", prod.getProductName());
        assertEquals("Latest model smartphone", prod.getProductDetails());
        assertEquals(800.0, prod.getCost(), 0.01);
    }

    @Test
    public void testSetters() {
        // Test setters
        product.setProductId(3);
        product.setProductName("Tablet");
        product.setProductDetails("10-inch tablet with pen support");
        product.setCost(500.0);

        assertEquals(3, product.getProductId());
        assertEquals("Tablet", product.getProductName());
        assertEquals("10-inch tablet with pen support", product.getProductDetails());
        assertEquals(500.0, product.getCost(), 0.01);
    }

    @Test
    public void testToString() {
        // Test toString method
        String expectedString = "Product [productId=1, productName=Laptop, productDetails=High-performance laptop, cost=1200.0]";
        assertEquals(expectedString, product.toString());
    }
}
