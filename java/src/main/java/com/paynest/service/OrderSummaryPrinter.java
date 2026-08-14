package com.paynest.service;

import java.math.BigDecimal;

import com.paynest.domain.Customer;
import com.paynest.domain.Order;
import com.paynest.domain.OrderItem;

public class OrderSummaryPrinter
{
    public String printSummary(Order order) {

        int id = order.getId();
        Customer customer = order.getCustomer();

    StringBuilder strib = new StringBuilder();

    strib.append("Order #").append(id).append(" — ").append(customer.getName()).append('\n');
    strib.append("Order Items:\n");
    // Header
    strib.append(String.format("  %-20s %-10s %12s%n","Product", "Quantity", "Total Price"));

    if (order.getItems().isEmpty()) {
        strib.append("  (no items)\n");
    } else {
        for (OrderItem item : order.getItems()) {
            strib.append(String.format(
                "  %-20s %-10d R%11.2f%n",
                item.getProduct().getName(),
                item.getQuantity(),
                item.calculateTotal()
            ));
        }
    }

    BigDecimal total = order.calculateAllTotal();
    BigDecimal vat = order.calculateVat();

    strib.append(String.format("  %-20s R%11.2f%n", "Subtotal:", total));
    strib.append(String.format("  %-20s R%11.2f%n", "VAT (15%):", vat));
    strib.append(String.format("  %-20s R%11.2f%n", "Total:", total.add(vat)));

    return strib.toString();
    }
}