package com.paynest.app;

import java.math.BigDecimal;

import com.paynest.domain.Customer;
import com.paynest.domain.InsufficientStockException;
import com.paynest.domain.Order;
import com.paynest.domain.Product;
import com.paynest.service.OrderService;

/**
 * Demonstration entry point. Builds a small catalogue, one customer, and one
 * order — printing the summary a reviewer can check by hand. Also
 * deliberately triggers one invalid add (over stock) to show that
 * validation fails loudly instead of corrupting the total.
 */
public final class PayNestApplication {

    private PayNestApplication() { }

    public static void main(String[] args) {
        Product laptop = new Product(1, "Laptop", new BigDecimal("12000.00"), 5);
        Product mouse  = new Product(2, "Wireless Mouse", new BigDecimal("350.00"), 20);

        Customer customer = new Customer(1, "Ari Nemadodzi", "ari@example.com");

        OrderService orderService = new OrderService();
        Order order = orderService.createOrder(1, customer);

        // quantity > 1, per the demo requirement
        orderService.addToOrder(order, laptop, 1);
        orderService.addToOrder(order, mouse, 3);

        // deliberately over stock
        try {
            orderService.addToOrder(order, mouse, 100);
        } catch (InsufficientStockException ex) {
            System.out.println("Rejected as expected: " + ex.getMessage());
        }

        System.out.println(order.printSummary());
    }
}