package com.paynest.domain;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class OrderItemTest {

    private Product sampleProduct() {
        return new Product(1, "Laptop", new BigDecimal("100.00"), 10);
    }

    @Test
    void calculateTotalPriceMultipliesUnitPriceByQuantity() {
        OrderItem item = new OrderItem(sampleProduct(), 3);

        assertEquals(0, new BigDecimal("300.00").compareTo(item.calculateTotalPrice()));
    }

    @Test
    void constructorRejectsZeroQuantity() {
        assertThrows(IllegalArgumentException.class, () -> new OrderItem(sampleProduct(), 0));
    }

    @Test
    void constructorRejectsNegativeQuantity() {
        assertThrows(IllegalArgumentException.class, () -> new OrderItem(sampleProduct(), -1));
    }

    @Test
    void constructorRejectsNullProduct() {
        assertThrows(IllegalArgumentException.class, () -> new OrderItem(null, 1));
    }
}
