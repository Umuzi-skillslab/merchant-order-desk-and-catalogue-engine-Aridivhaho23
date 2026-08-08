package com.paynest.domain;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class OrderTest {

    private Customer customer() {
        return new Customer(1, "Thandiwe", "t@example.com");
    }

    @Test
    void emptyOrderHasZeroTotal() {
        Order order = new Order(1, customer());
        assertEquals(0, new BigDecimal("0.00").compareTo(order.calculateAllTotal()));
    }

    @Test
    void grandTotalEqualsSumOfLineTotals() {
        Order order = new Order(1, customer());
        Product laptop = new Product(1, "Laptop", new BigDecimal("100.00"), 10);
        Product mouse  = new Product(2, "Mouse", new BigDecimal("50.00"), 10);

        order.addItem(laptop, 2);  // 200.00
        order.addItem(mouse, 3);   // 150.00

        assertEquals(0, new BigDecimal("350.00").compareTo(order.calculateAllTotal()));
    }

    @Test
    void vatIsFifteenPercentOfSubtotal() {
        Order order = new Order(1, customer());
        order.addItem(new Product(1, "Laptop", new BigDecimal("100.00"), 10), 1);

        assertEquals(0, new BigDecimal("15.00").compareTo(order.calculateVat()));
    }

    @Test
    void addItemRejectsNonPositiveQuantity() {
        Order order = new Order(1, customer());
        Product laptop = new Product(1, "Laptop", new BigDecimal("100.00"), 10);
        assertThrows(IllegalArgumentException.class, () -> order.addItem(laptop, 0));
    }

    @Test
    void addItemReducesProductStock() {
        Order order = new Order(1, customer());
        Product laptop = new Product(1, "Laptop", new BigDecimal("100.00"), 10);
        order.addItem(laptop, 4);
        assertEquals(6, laptop.getStock());
    }

    @Test
    void getItemsReturnsUnmodifiableList() {
        Order order = new Order(1, customer());
        assertThrows(UnsupportedOperationException.class, () -> order.getItems().add(null));
    }

    /**
     * This is the invariant test: it calls Order.addItem directly, with no
     * OrderService involved at all, and proves the stock check still fires.
     * The rule lives in Order, not in whichever caller happens to check
     * first — this is what "extensibility without breaking callers" means
     * in practice.
     */
    @Test
    void addItemEnforcesStockEvenWithoutOrderService() {
        Order order = new Order(1, customer());
        Product laptop = new Product(1, "Laptop", new BigDecimal("19999.00"), 2);

        assertThrows(InsufficientStockException.class, () -> order.addItem(laptop, 5));
        assertEquals(2, laptop.getStock());       // failed attempt must not touch stock
        assertTrue(order.getItems().isEmpty());   // and must not add a line
    }
}