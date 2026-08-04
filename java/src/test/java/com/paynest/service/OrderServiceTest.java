package com.paynest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.paynest.domain.Customer;
import com.paynest.domain.Order;
import com.paynest.domain.Product;

class OrderServiceTest {

    @Test
    void addProductsToOrderReducesStockAndAddsLine() {
        OrderService service = new OrderService();
        Order order = new Order(1, new Customer("Ari", "ari@example.com"));
        Product laptop = new Product(1, "Laptop", new BigDecimal("100.00"), 5);

        service.addProductsToOrder(order, laptop, 2);

        assertEquals(3, laptop.getStock());
        assertEquals(1, order.getOrderItems().size());
    }

    @Test
    void addProductsToOrderThrowsWhenStockInsufficient() {
        OrderService service = new OrderService();
        Order order = new Order(1, new Customer("Ari", "ari@example.com"));
        Product laptop = new Product(1, "Laptop", new BigDecimal("100.00"), 1);

        assertThrows(InsufficientStockException.class,
                () -> service.addProductsToOrder(order, laptop, 5));

        // A failed attempt must not have any side effects.
        assertEquals(1, laptop.getStock());
        assertEquals(0, order.getOrderItems().size());
    }

    @Test
    void addProductsToOrderRejectsNonPositiveQuantity() {
        OrderService service = new OrderService();
        Order order = new Order(1, new Customer("Ari", "ari@example.com"));
        Product laptop = new Product(1, "Laptop", new BigDecimal("100.00"), 5);

        assertThrows(IllegalArgumentException.class,
                () -> service.addProductsToOrder(order, laptop, 0));
    }
}
