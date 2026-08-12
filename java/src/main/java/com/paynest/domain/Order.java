package com.paynest.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Order {

    private final int id;
    private final Customer customer;
    private final List<OrderItem> items = new ArrayList<>();
    private static final BigDecimal VAT_RATE = new BigDecimal("0.15");

/**
 * @param id the unique identifier for this order
 * @param customer the person placing the order
 */
    public Order(int id, Customer customer) {
        if (id <= 0) {
            throw new IllegalArgumentException("id must be positive.");
        }
        this.id = id;
        this.customer = Objects.requireNonNull(customer, "customer cannot be null.");
    }

    public int getId() {
        return id;
    }
    public Customer getCustomer() {
        return customer;
    }
    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    /** The single path for adding a line. Validates, checks stock, reduces stock — atomically. */
    public void addItem(Product product, int quantity) {
        Objects.requireNonNull(product, "product cannot be null.");

        if (quantity <= 0) {
                throw new IllegalArgumentException("quantity must be > 0.");
            }
        if (quantity > product.getStock()){
            throw new InsufficientStockException(product, quantity);
        }
        // only Order can do this — package-private
        product.reduceStock(quantity);
        items.add(new OrderItem(product, quantity));
    }

    public BigDecimal calculateAllTotal() {
        return items.stream()
                .map(OrderItem::calculateTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateVat() {
        return calculateAllTotal().multiply(VAT_RATE).setScale(2, RoundingMode.HALF_UP);
    }

    public String printSummary() {
    StringBuilder strib = new StringBuilder();

    strib.append("Order #").append(id).append(" — ").append(customer.getName()).append('\n');
    strib.append("Order Items:\n");
    // Header
    strib.append(String.format("  %-20s %-10s %12s%n","Product", "Quantity", "Total Price"));

    if (items.isEmpty()) {
        strib.append("  (no items)\n");
    } else {
        for (OrderItem item : items) {
            strib.append(String.format(
                "  %-20s %-10d R%11.2f%n",
                item.getProduct().getName(),
                item.getQuantity(),
                item.calculateTotal()
            ));
        }
    }

    BigDecimal total = calculateAllTotal();
    BigDecimal vat = calculateVat();

    strib.append(String.format("  %-20s R%11.2f%n", "Subtotal:", total));
    strib.append(String.format("  %-20s R%11.2f%n", "VAT (15%):", vat));
    strib.append(String.format("  %-20s R%11.2f%n", "Total:", total.add(vat)));

    return strib.toString();
}
}