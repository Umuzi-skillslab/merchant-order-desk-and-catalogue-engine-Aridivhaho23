package com.paynest.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class OrderItem {

    private final Product product;
    private final int quantity;

    public OrderItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be a positive integer, got: " + quantity);
        }
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    /** Total price for this line (unit price * quantity). */
    public BigDecimal calculateTotalPrice() {
        return product.getPrice()
                .multiply(BigDecimal.valueOf(quantity))//instead of using x, I used multiply method to multiply the unit price by quantity
                .setScale(2, RoundingMode.HALF_UP);
    }
}
