package com.paynest.payment.rails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

class EftPaymentTest {

    private static String captureOutput(Runnable action) {
        PrintStream original = System.out;
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captured));
        try {
            action.run();
        } finally {
            System.setOut(original);
        }
        return captured.toString();
    }

    @Test
    void theDefaultEftIsStandard() {
        assertEquals(EftMode.STANDARD, new EftPayment().getMode());
    }

    @Test
    void bothModesPayAndKeepTheSameLabel() {
        for (EftMode mode : EftMode.values()) {
            EftPayment eft = new EftPayment(mode);

            assertTrue(eft.processPayment(230.00), mode + " should succeed");
            assertEquals("EFT", eft.getPaymentType());
            assertEquals(mode, eft.getMode());
        }
    }

    @Test
    void theMessageNamesTheModeAndTheAmount() {
        String standard = captureOutput(() -> new EftPayment(EftMode.STANDARD).processPayment(230.00));
        String instant = captureOutput(() -> new EftPayment(EftMode.INSTANT).processPayment(230.00));

        assertTrue(standard.contains("[EFT]") && standard.contains("Standard EFT") && standard.contains("R230.00"));
        assertTrue(instant.contains("[EFT]") && instant.contains("Instant EFT") && instant.contains("R230.00"));
        assertFalse(instant.contains("Standard EFT"));
    }

    @Test
    void aModeIsRequired() {
        assertThrows(NullPointerException.class, () -> new EftPayment(null));
    }

    @Test
    void theSharedAmountCheckStillAppliesInEveryMode() {
        for (EftMode mode : EftMode.values()) {
            EftPayment eft = new EftPayment(mode);

            assertFalse(eft.processPayment(0));
            assertFalse(eft.processPayment(-5));
            assertFalse(eft.processPayment(Double.NaN));
        }
    }
}
