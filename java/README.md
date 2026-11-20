# PayNest: Merchant Order Desk, Catalogue Engine and Payment Rails

## Overview

PayNest is a small Java application developed for an early-stage South African fintech scenario.

- **Capstone 1** covers products, customers, orders with line items, and order totals. It calculates the subtotal, 15% VAT and grand total, then prints an order summary.
- **Capstone 3** builds on that foundation by adding a checkout process that can use interchangeable payment methods. The order code does not need to know which payment method is selected.

## Project Structure

```text
java/
├── pom.xml
└── src/
    ├── main/java/com/paynest/
    │   ├── app/
    │   │   └── PayNestApplication
    │   ├── checkout/
    │   │   └── CheckoutService
    │   ├── domain/
    │   │   ├── Customer
    │   │   ├── InsufficientStockException
    │   │   ├── Order
    │   │   ├── OrderItem
    │   │   ├── PaymentStatus
    │   │   └── Product
    │   ├── payment/
    │   │   ├── PaymentMethod
    │   │   ├── PaymentProcessing
    │   │   ├── PaymentProcessor
    │   │   └── rails/
    │   │       ├── AbstractPaymentMethod
    │   │       ├── BitcoinExchangeRate
    │   │       ├── BitcoinPayment
    │   │       ├── BuyNowPayLaterPayment
    │   │       ├── CardDetails
    │   │       ├── CardPayment
    │   │       ├── CardScheme
    │   │       ├── EftMode
    │   │       ├── EftPayment
    │   │       ├── Refundable
    │   │       ├── SplitPayment
    │   │       ├── VoucherPayment
    │   │       └── WalletPayment
    │   ├── service/
    │   │   ├── OrderService
    │   │   └── OrderSummaryPrinter
    │   └── shared/
    │       └── RandFormat
    └── test/java/com/paynest/
        └── mirrors the main source packages
```

## Features

### Capstone 1

- Products have an ID, name, price and stock quantity. Product data is validated, and stock cannot be negative.
- Customers have validated email addresses.
- Orders can contain multiple line items. Stock is checked and reduced when an item is added to an order.
- The order subtotal, 15% VAT and grand total are calculated using `BigDecimal`, with `RoundingMode.HALF_UP` and two decimal places.
- `OrderSummaryPrinter` produces a formatted order summary. Currency formatting uses `Locale.US` to keep the decimal separator consistent across computers.

### Capstone 3

- `PaymentMethod` defines the common payment contract: `boolean processPayment(double amount)` and `String getPaymentType()`.
- `AbstractPaymentMethod` provides shared payment-label handling and amount validation. Individual payment classes implement their own payment rules.
- The application demonstrates seven payment options: `CardPayment`, `EftPayment`, `WalletPayment`, `VoucherPayment`, `BitcoinPayment`, `BuyNowPayLaterPayment` and `SplitPayment`.
- `EftPayment` runs in one of two `EftMode` values, `STANDARD` (the default) or `INSTANT`. The payment label stays `EFT` in both, and the demonstration runs both modes.
- `SplitPayment` is a composite payment method that combines allocations across multiple payment instruments.
- Card details are validated for card number format, Luhn checksum, supported network, expiry date and CVV. Only the last four card-number digits are retained.
- `CheckoutService` coordinates checkout, while `PaymentProcessor` processes the selected `PaymentMethod`. The `Order` class remains responsible for order state and totals.
- Voucher payments check the available voucher balance before redemption.
- Bitcoin payments convert the Rand order total using an injected ZAR-per-BTC quote provider. The demonstration uses a simulated quote, not a live market rate.
- `Refundable` is a separate capability implemented by card, wallet and voucher payments. The simulation tracks successful charges and prevents refunds beyond the remaining refundable amount. Bitcoin, EFT and buy-now-pay-later payments do not implement this interface.
- `CheckoutService` receives the `PaymentProcessing` abstraction through its constructor. This keeps checkout independent of the concrete payment processor.
- Split payment accepts refundable payment instruments and compensates earlier successful allocations in reverse order if a later allocation fails.
- Failure scenarios include insufficient wallet or voucher balances, invalid amounts, a missing payment method, a payment method that throws an exception, an empty order, duplicate payment attempts, split-allocation mismatches, BNPL credit-limit failures and refund-limit violations.
- `PaymentStatus` represents an order's payment state: `UNPAID`, `PAYMENT_FAILED` or `PAID`. `Order.getPaymentStatus()` exposes the state, while `isPaid()` remains available as a convenience method.
- `PayNestApplication` prints the summary for order #1 and passes that same order to the first demonstration checkout. The remaining payment demonstrations use fresh orders so that an order is not charged more than once.

## How to Test

Requires Java 21 and Maven.

After extracting the ZIP file, open a terminal in the directory that contains the
`merchant-order-desk-and-catalogue-engine-Aridivhaho23` folder. Run:

```bash
cd merchant-order-desk-and-catalogue-engine-Aridivhaho23/java
mvn clean test
```

## How to Run

From the `java` directory, run:

```bash
mvn exec:java
```

The application builds order #1, prints its summary and passes that same `Order` object to the first demonstration checkout. The first payment demonstration uses a card, so the completion message should identify order #1 and show the same grand total as the summary. Each subsequent payment demonstration uses a newly created order. The application also demonstrates rejection of invalid card details.

## Why the Payment Interface Matters

`CheckoutService` works with the `PaymentMethod` interface instead of checking for specific payment classes. Each payment class provides its own implementation of `processPayment()`, and Java selects the correct implementation at runtime through polymorphism.

When a new payment method is introduced, the usual extension is to create a new class that implements `PaymentMethod` and provide that object to checkout. Checkout does not need another `if` or `switch` branch for the new payment type, and the `Order` class does not need to be changed.

## Refund Design and Real-World Behaviour

`Refundable` is a separate capability rather than part of the `PaymentMethod` contract. Card, wallet and voucher payment classes implement it. The simulation tracks successful charges and prevents refunds above the remaining refundable amount.

In a real card-payment system, the processor or acquirer must process a refund against the original transaction. The application would need to retain the transaction reference and follow the relevant card-scheme and provider rules. A wallet refund is credited back to the same wallet, subject to its ledger and applicable terms. This sample restores voucher value to the same simulated voucher balance; real voucher refunds depend on the voucher terms and applicable consumer law.

EFT returns are generally handled through bank or payment-provider processes and are not guaranteed by a generic application-level reversal method. Bitcoin transactions are generally irreversible once confirmed. A Bitcoin refund is a separate outgoing transaction, not a reversal of the original transfer, so `BitcoinPayment` does not implement `Refundable`.

`BitcoinExchangeRate` supplies the ZAR value of one BTC. A production implementation would need a trusted provider quote with a timestamp and expiry, handling for quote changes and network fees, and a policy for the required blockchain confirmations. This project only simulates the payment request; it does not connect to a wallet, exchange, card processor or blockchain.

## Business Rules

- The amount charged at checkout is the order grand total (subtotal plus VAT), calculated by `Order.calculateTotalInclVat()`. The printed summary uses the same total calculation.
- The payment contract accepts a `double`, as specified in the project brief. The Capstone 1 total is calculated as a `BigDecimal` and converted to `double` in one place: `CheckoutService.checkout()`.
- Amounts are formatted with two decimal places and a consistent decimal separator, for example `R18457.50`.
- A failed payment leaves the order unpaid and available for another payment attempt.
- A paid order cannot be charged again, and its items can no longer be changed.

## Known Limitations

- Payment rails are simulations. The application does not connect to a bank, card network or payment gateway. Do not use real card details.
- Stock is reduced when an item is added to an order and is not automatically restored if the order is abandoned.
- `double` cannot represent all decimal monetary values exactly. It is used for payment processing because the project brief specifies it; a production financial application should use an appropriate decimal-money representation throughout.

## Author

**Name:** Aridivhaho Junior Nemadodzi

**Coursework:** Capstone 1 and Capstone 3 — Java Programming

## Academic Use

This project was developed for educational purposes as part of a Java programming capstone assessment.
