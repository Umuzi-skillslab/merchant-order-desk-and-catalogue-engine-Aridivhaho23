package com.paynest.domain;

import java.util.regex.Pattern;

public class Customer {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    private final int customerId;
    private final String name;
    private final String email;

    // Auto-incrementing id counter shared across all Customer instances.
    private static int nextId = 1;

    /**
     * Creates a new customer.
     *
     * @param id    unique identifier for the customer
     * @param name  full name of the customer
     * @param email email address for contact
     */

    public Customer(String name, String email) {
        // --- name must be present ---
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Customer name cannot be null or blank.");
        }
        // --- email must be present and well-formed (this was the gap flagged in feedback) ---
        if (email == null || !EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new IllegalArgumentException("Customer email is not a valid email address: " + email);
        }

        this.name = name.trim();
        this.email = email.trim();
        this.customerId = nextId++;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return customerId;
    }

    public String describe() {
        return "Customer ID: \t\t\t\t" + customerId + System.lineSeparator()
                + "Customer Name: \t\t" + name;
    }

    // Test helper: reset the id counter for deterministic tests.
    // package-private (no modifier) so it can be used from tests in the same package.
    static void resetNextIdForTests(int startValue) {
        nextId = startValue;
    }
}