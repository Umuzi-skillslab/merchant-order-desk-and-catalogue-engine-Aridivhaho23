package com.paynest.service;

// Custom exception class for insufficient stock scenarios
public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(String message) {
        super(message);
    }
}
