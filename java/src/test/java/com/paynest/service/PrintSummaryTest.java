package com.paynest.service;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.paynest.domain.Customer;
import com.paynest.domain.Order;
import com.paynest.domain.Product;

class PrintSummaryTest {

    @Test
    void printSummaryContainsFormattedTotals() {
        Customer customer = new Customer(1, "Ari", "ari@example.com");
        Order order = new Order(1, customer);
        OrderSummaryPrinter printer = new OrderSummaryPrinter();

        Product laptop = new Product(1, "Laptop", new BigDecimal("39.99"), 10); // line total 79.98
        Product phone  = new Product(2, "Phone",  new BigDecimal("29.99"), 10); // line total 29.99

        order.addItem(laptop, 2);
        order.addItem(phone, 1);

        BigDecimal subtotal = order.calculateAllTotal();
        BigDecimal vat = order.calculateVat();
        BigDecimal total = subtotal.add(vat);

        String output = printer.printSummary(order);

        // printSummary uses "R%11.2f" formatting — assert that the formatted values appear
        assertTrue(output.contains(String.format("R%11.2f", subtotal.doubleValue())),
                   "Summary should contain the formatted subtotal");
        assertTrue(output.contains(String.format("R%11.2f", vat.doubleValue())),
                   "Summary should contain the formatted VAT");
        assertTrue(output.contains(String.format("R%11.2f", total.doubleValue())),
                   "Summary should contain the formatted total");
    }
}
