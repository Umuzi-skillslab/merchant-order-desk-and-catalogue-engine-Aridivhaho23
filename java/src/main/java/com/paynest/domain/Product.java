package com.paynest.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Product {

    /**
     * Creates a new product.
     *
     * @param id    unique identifier for the product
     * @param name  display name of the product
     * @param price price in the local currency (e.g. Rands)
     */
    private final int id;
    private final String name;
    private final BigDecimal price;
    private int stock;

    /**
     * Primary constructor. All validation happens here (fail fast) so that
     * once construction succeeds, callers can trust the object's state.
     */
    public Product(int id, String name, BigDecimal price, int stock) {
        // --- id must be positive ---
        if (id <= 0) {
            throw new IllegalArgumentException("Product id must be a positive number.");
        }
        // --- name must be present ---
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or blank.");
        }
        // --- price must be present and cannot be negative (this was the gap flagged in feedback) ---
        if (price == null) {
            throw new IllegalArgumentException("Product price cannot be null.");
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Product price cannot be negative: " + price);
        }
        // --- stock cannot be negative ---
        if (stock < 0) {
            throw new IllegalArgumentException("Product stock cannot be negative: " + stock);
        }

        this.id = id;
        this.name = name.trim();
        // Round to 2 decimal places (cents) once, up front, so every later calculation and every printed value uses the same rounded price.
        this.price = price.setScale(2, RoundingMode.HALF_UP);
        this.stock = stock;
    }

    /**
     * Convenience constructor so existing call sites can keep passing a
     * plain numeric literal, e.g. new Product(1, "Laptop", 19.99, 100).
     * Delegates to the BigDecimal constructor so validation only lives in
     * one place.
     */
    public Product(int id, String name, double price, int stock) {
        this(id, name, BigDecimal.valueOf(price), stock);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    /**
     * Reduces stock after a sale.
     * Validated so stock can never silently go negative or be reduced by a
     * non-positive amount.
     */
    public void reduceStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity to reduce must be greater than zero.");
        }
        if (quantity > stock) {
            throw new IllegalArgumentException(
                    "Cannot reduce stock below zero for " + name + " (have " + stock + ", asked for " + quantity + ").");
        }
        stock -= quantity;
    }

    @Override
    public String toString() {
        return name + " (R" + price + ")";
    }
}

