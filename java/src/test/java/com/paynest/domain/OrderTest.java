package com.paynest.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class OrderTest {

    private Customer sampleCustomer() {
        return new Customer("Ari", "ari@example.com");
    }

    @Test
    void emptyOrderHasZeroTotal() {
        Order order = new Order(1, sampleCustomer());

        assertEquals(0, new BigDecimal("0.00").compareTo(order.calculateAllTotalPrice()));
        assertEquals(0, new BigDecimal("0.00").compareTo(order.calculateVat()));
    }

    @Test
    void grandTotalEqualsSumOfLineSubtotals() {
        Order order = new Order(1, sampleCustomer());
        Product laptop = new Product(1, "Laptop", new BigDecimal("100.00"), 10);
        Product phone = new Product(2, "Phone", new BigDecimal("50.00"), 10);

        order.addOrderItem(laptop, 2); // 200.00
        order.addOrderItem(phone, 3);  // 150.00

        assertEquals(0, new BigDecimal("350.00").compareTo(order.calculateAllTotalPrice()));
    }

    @Test
    void vatIsFifteenPercentOfSubtotal() {
        Order order = new Order(1, sampleCustomer());
        order.addOrderItem(new Product(1, "Laptop", new BigDecimal("100.00"), 10), 1);

        assertEquals(0, new BigDecimal("15.00").compareTo(order.calculateVat()));
        assertEquals(0, new BigDecimal("115.00").compareTo(order.calculateTotalWithVat()));
    }

    @Test
    void addOrderItemRejectsInvalidQuantity() {
        Order order = new Order(1, sampleCustomer());
        Product laptop = new Product(1, "Laptop", new BigDecimal("100.00"), 10);

        assertThrows(IllegalArgumentException.class, () -> order.addOrderItem(laptop, 0));
    }

    @Test
    void getOrderItemsReturnsUnmodifiableList() {
        Order order = new Order(1, sampleCustomer());
        order.addOrderItem(new Product(1, "Laptop", new BigDecimal("100.00"), 10), 1);

        // Callers must go through addOrderItem - direct mutation of the
        // returned list must fail so totals can't be corrupted silently.
        assertThrows(UnsupportedOperationException.class,
                () -> order.getOrderItems().add(new OrderItem(new Product(2, "Phone", new BigDecimal("1.00"), 1), 1)));
    }

    @Test
    void constructorRejectsNullCustomer() {
        assertThrows(IllegalArgumentException.class, () -> new Order(1, null));
    }

    @Test
    void constructorRejectsNonPositiveOrderId() {
        assertThrows(IllegalArgumentException.class, () -> new Order(0, sampleCustomer()));
    }
}
