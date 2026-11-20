package com.paynest.app;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class PayNestApplicationTest {
    @Test
    void applicationDemonstratesEveryPaymentMethod() {
        PrintStream original = System.out;
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captured));
        try {
            PayNestApplication.main(new String[0]);
        } finally {
            System.setOut(original);
        }
        String output = captured.toString();
        int summaryAt = output.indexOf("Order Items:");
        int completionAt = output.indexOf("Order #1 completed via CARD for R18457.50");
        assertTrue(summaryAt >= 0, "main demo should print the order #1 summary");
        // \s+ so the test does not depend on the exact column spacing of the summary table.
        assertTrue(Pattern.compile("Total:\\s+R\\s*18457\\.50").matcher(output).find(),
                "the printed summary should show the expected grand total");
        assertTrue(completionAt > summaryAt,
                "the order summarised first (order #1, same grand total) must be the first one paid");
        assertFalse(output.contains("NOT completed"), "every demonstrated payment should complete");
        for (String rail : new String[] { "CARD", "EFT", "WALLET", "VOUCHER", "BITCOIN",
                "BUY_NOW_PAY_LATER", "SPLIT" }) {
            assertTrue(output.contains("--- Testing " + rail + " ---"), rail + " should be demonstrated");
            assertTrue(output.contains("completed via " + rail), rail + " should complete its test order");
        }
    }
}
