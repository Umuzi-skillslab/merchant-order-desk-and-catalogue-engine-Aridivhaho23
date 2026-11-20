package com.paynest.payment.rails;

import java.util.Objects;

import com.paynest.shared.RandFormat;

/**
 * Rail 2: pay by EFT (bank transfer). Simulated: the transfer always succeeds.
 *
 * <p>An EFT runs in one of two {@link EftMode}s. The no-argument constructor uses
 * {@link EftMode#STANDARD}. The payment label stays "EFT" in both modes, so receipts and logs
 * group them together; the mode only changes the wording of the rail's own message.
 */
public class EftPayment extends AbstractPaymentMethod {

    private final EftMode mode;

    /** A standard EFT. */
    public EftPayment() {
        this(EftMode.STANDARD);
    }

    /** @param mode how this EFT runs (must not be null) */
    public EftPayment(EftMode mode) {
        super("EFT");
        this.mode = Objects.requireNonNull(mode, "EFT mode is required");
    }

    @Override
    protected boolean authorise(double amount) {
        System.out.println("[EFT] Initiating " + mode.getDisplayName() + " bank transfer of "
                + RandFormat.format(amount));
        return true; // Simulated payment.
    }

    public EftMode getMode() {
        return mode;
    }
}
