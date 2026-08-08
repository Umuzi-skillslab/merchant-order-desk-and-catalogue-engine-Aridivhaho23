package com.paynest.service;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.paynest.domain.Customer;
import com.paynest.domain.Order;
import com.paynest.domain.Product;

class OrderServiceTest {

    @Test
    void addToOrderDelegatesToOrder() {
        OrderService service = new OrderService();
        Order order = service.createOrder(1, new Customer(1, "Thandiwe", "t@example.com"));
        Product laptop = new Product(1, "Laptop", new BigDecimal("100.00"), 5);

        service.addToOrder(order, laptop, 2);

        assertEquals(1, order.getItems().size());
        assertEquals(3, laptop.getStock());
    }
}
