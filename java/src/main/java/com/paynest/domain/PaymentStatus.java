package com.paynest.domain;

/** Lifecycle state of an order's payment. */
public enum PaymentStatus {
    UNPAID,
    PAYMENT_FAILED,
    PAID
}
