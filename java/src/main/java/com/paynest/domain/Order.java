package com.paynest.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {

    private final int orderId;
    private final Customer customer;
    private final List<OrderItem> orderItems;

    private static final BigDecimal VAT_RATE = new BigDecimal("0.15");

   /**
     * Creates a new order for the given customer.
     *
     * @param id       unique identifier for the order
     * @param customer the customer placing the order
     */
    public Order(int orderId, Customer customer) {
        if (orderId <= 0) {
            throw new IllegalArgumentException("Order id must be a positive number.");
        }
        if (customer == null) {
            throw new IllegalArgumentException("Order must belong to a customer.");
        }
        this.orderId = orderId;
        this.customer = customer;
        this.orderItems = new ArrayList<>();
    }

    public int getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    //Read only view of the order items, so external callers can't modify the list directly.
    public List<OrderItem> getOrderItems() {
        return Collections.unmodifiableList(orderItems);
    }

    /**
     * Adds a product to the order with the specified quantity.
     *
     * @param product  the product to add
     * @param quantity the number of units
     */
    public void addOrderItem(Product product, int quantity) {
        orderItems.add(new OrderItem(product, quantity));
    }

    /** Sum of all line subtotals (excl. VAT). 
     *  @return the total amount
    */
    public BigDecimal calculateAllTotalPrice() {
        // Start with zero and add each line's total price to it.
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : orderItems) {
            total = total.add(item.calculateTotalPrice());
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateVat() {
        return calculateAllTotalPrice().multiply(VAT_RATE).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateTotalWithVat() {
        return calculateAllTotalPrice().add(calculateVat()).setScale(2, RoundingMode.HALF_UP);
    }

 /* @return the order summary as a String */
    public String buildSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("\n==================================================\n");
        summary.append("\tORDER SUMMARY\n");
        summary.append("==================================================\n");
        summary.append("Order Summary\t\t\t\tPayNest\n");
        summary.append("Order ID: \t\t\t\t").append(orderId).append('\n');
        summary.append("__________________________________________________\n\n");
        summary.append(customer.describe()).append('\n');
        summary.append("__________________________________________________\n\n");
        summary.append("Order Items:\n");
        summary.append(String.format("%-10s %-10s %8s%n", "\t\tProduct", "Quantity", "Total Price"));
        summary.append("---------------------------------\n\t\t");

        if (orderItems.isEmpty()) {
            summary.append("(no items on this order)");
        } else {
            for (OrderItem item : orderItems) {
                // Format the BigDecimal safely for printing (avoid passing BigDecimal into %f)
                String priceStr = item.calculateTotalPrice()
                                      .setScale(2, RoundingMode.HALF_UP)
                                      .toPlainString();
                summary.append(String.format("%-10s %-10d R%8s%n",
                        item.getProduct().getName(),
                        item.getQuantity(),
                        priceStr));
            }
        }

        summary.append("===============================\n");
        summary.append("Total Price exc. VAT:\t\t\tR").append(calculateAllTotalPrice()).append('\n');
        summary.append("VAT (15%): \t\t\t\tR").append(calculateVat()).append('\n');

        summary.append("================================================\n");
        summary.append("Total Price incl. VAT: \t\t\tR").append(calculateTotalWithVat()).append('\n');
        return summary.toString();
    }
}