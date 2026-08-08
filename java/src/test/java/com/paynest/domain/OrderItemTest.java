package com.paynest.domain;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class OrderItemTest {

    @Test
    void calculateTotalMultipliesPriceByQuantity() {
        Product p = new Product(1, "Laptop", new BigDecimal("100.00"), 10);
        OrderItem item = new OrderItem(p, 3);
        assertEquals(0, new BigDecimal("300.00").compareTo(item.calculateTotal()));
    }
}