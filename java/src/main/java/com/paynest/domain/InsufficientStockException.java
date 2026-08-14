package com.paynest.domain;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(Product product, int requested)
    {
        super("Insufficient stock for " + product.getName()
                + " (requested " + requested + ", available " + product.getStock() + ").");
    }
}
