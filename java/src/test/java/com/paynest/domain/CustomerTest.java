package com.paynest.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class CustomerTest {

    @Test
    void constructorAcceptsValidNameAndEmail() {
        Customer customer = new Customer("Ari", "ari@example.com");

        assertEquals("Ari", customer.getName());
        assertEquals("ari@example.com", customer.getEmail());
    }

    @Test
    void constructorRejectsInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> new Customer("Ari", "not-an-email"));
    }

    @Test
    void constructorRejectsMissingAtSymbol() {
        assertThrows(IllegalArgumentException.class, () -> new Customer("Ari", "ari.example.com"));
    }

    @Test
    void constructorRejectsBlankName() {
        assertThrows(IllegalArgumentException.class, () -> new Customer("   ", "ari@example.com"));
    }

    @Test
    void constructorRejectsNullEmail() {
        assertThrows(IllegalArgumentException.class, () -> new Customer("Ari", null));
    }
}
