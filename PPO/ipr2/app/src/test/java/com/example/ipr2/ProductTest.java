package com.example.ipr2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ProductTest {
    @Test
    public void constructorTest() {
        Product product = new Product("57", "rx8", 614);
        assertEquals("57", product.getName());
        assertEquals("rx8",product.getType());
        assertEquals(614,product.getCost());
    }
    @Test
    public void setUsernameTest() {
        Product product = new Product("", "type", 1000);
        product.setName("name");
        assertEquals("name", product.getName());

    }
}