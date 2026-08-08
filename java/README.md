# PayNest — Merchant Order Desk and Catalogue Engine

## Overview

A minimal Java commerce kernel for PayNest, an early-stage South African
fintech: products with prices, customers, orders with line items, and a
trustworthy order total printed as a human-readable summary.

## Project Structure

```
merchant-order-desk-and-catalogue-engine-Aridivhaho23/
│
├── src/
│   ├── main/java/com/paynest/
│   │   ├── domain/
│   │   │   ├── Product.java
│   │   │   ├── Customer.java
│   │   │   ├── OrderItem.java
│   │   │   └── Order.java
│   │   ├── service/
│   │   │   ├── OrderService.java
│   │   │   └── InsufficientStockException.java
│   │   └── app/
│   │       └── PayNestApplication.java
│   │
│   └── test/java/com/paynest/
│       ├── domain/  (ProductTest, CustomerTest, OrderItemTest, OrderTest)
│       └── service/ (OrderServiceTest)
│
├── pom.xml
└── README.md
```

## Features

* Create products with an id, name, price and stock level (price/stock validated, cannot be negative).
* Create customer profiles with an id, name and a validated email address.
* Create customer orders.
* Add multiple products to an order, including quantities greater than one.
* Calculate line subtotals and the overall order total (excl. and incl. 15% VAT) using `BigDecimal`.
* Display a formatted order summary.
* Validate all input (product id, quantity, name, email) and report a friendly message instead of crashing when it's invalid.
* Demonstrate Object-Oriented Programming concepts: encapsulation, composition, separation of concerns.

---

## Technologies Used

* Java 21
* Maven
* JUnit 5 (Jupiter)
* Visual Studio Code
* Git and GitHub

---

---

## Class Responsibilities

### Product (`domain`)

Represents an item available for purchase. Fields: id, name, price (`BigDecimal`), stock. Validates in the constructor that id/price/stock are non-negative and name is not blank, so a `Product` can never exist in an invalid state.

### Customer (`domain`)

Represents a customer placing an order. Fields: id (auto-generated), name, email. Validates that the name is present and the email matches a basic email pattern.

### OrderItem (`domain`)

Represents one line within an order (a `Product` + a `quantity`). Calculates the line subtotal (`unit price * quantity`, rounded to 2 decimal places).

### Order (`domain`)

Represents a customer's order: id, `Customer`, and its list of `OrderItem`s. The item list is only exposed as a **read-only** view (`Collections.unmodifiableList`) so callers cannot corrupt totals by mutating it directly. Computes the grand total, VAT, and total incl. VAT, and builds a summary **String** (it does not print — see Business Rules below).

### OrderService (`domain`)

Adds products to an order, including the stock check. Never prints to the console: it either succeeds or throws (`IllegalArgumentException` / `InsufficientStockException`) so the caller decides how to present the outcome.

### PayNestApplication (`app`)

The single entry point. Runs unattended (no console input) using a set of pre-written customer/order requests, validates every one of them, calls into `OrderService`, and prints the resulting order summary. This is the *only* class that performs console output.

---

## How to Run

```bash
mvn clean compile
mvn exec:java
```

This builds a small catalogue (Laptop, Wireless Mouse), one customer, and
one order with two line items (one with quantity > 1). It also deliberately
attempts to over-order stock once, to show that validation fails loudly
with a clear message instead of corrupting the total, then prints the
order summary — customer, each line, subtotal, VAT, and grand total.

## How to Test

```bash
mvn test
```

Tests cover: `Product`/`Customer` validation (negative price, blank name,
invalid email), line and grand totals across multiple items, VAT,
non-positive quantity rejection, stock reduction, an empty order, an
unmodifiable order-items list, and — most importantly — that
`Order.addItem()` enforces the stock check even when called directly,
without going through `OrderService`.

## Business Rules

- Money (`Product.price`, line totals, VAT, grand total) is `BigDecimal`,
  not `double`. The starter brief's default is `double` arithmetic, but
  `double` can silently misrepresent currency (e.g. `0.1 + 0.2 != 0.3`).
  `BigDecimal` with `HALF_UP` rounding to 2 decimal places is used instead,
  documenting the deviation as the brief allows ("document any rounding
  policy if you introduce one").
- Rounding happens once, at the line level (`OrderItem.calculateTotal()`),
  so the grand total is always the exact sum of what's printed per line —
  no drift between the displayed lines and the displayed total.
- Quantities added to an order must be greater than zero.
- Stock cannot go negative; an order that would oversell a product is
  rejected with `InsufficientStockException`, not silently truncated.
- `Order.getItems()` returns an unmodifiable list — callers cannot corrupt
  the order total by mutating the backing collection directly.

## Why the Design Extends Cleanly

`OrderItem` only reads `product.getPrice()`; `Order` only reads
`OrderItem.calculateTotal()`. Neither ever reaches into `Product`'s other
fields. That means adding a new `Product` field (e.g. `sku`, `category`)
touches zero lines in `OrderItem`, `Order`, or checkout — the domain model
was built around that boundary specifically so catalogue changes and order
logic never have to change together.

The stock-check invariant lives in `Order.addItem()` itself — not in
`OrderService` — and `Product.reduceStock()` / the `OrderItem` constructor
are package-private, so nothing outside `domain` can bypass the check or
construct an inconsistent line item. `OrderService` is a thin coordination
layer over `Order`, not where the business rule is enforced; this is what
lets a future caller (a REST controller, a batch importer, another
service) reuse `Order` safely without re-implementing the stock check.

## Diagram

*(Optional but valued per the brief — a sequence diagram of
`OrderService → Order.addItem → OrderItem → printSummary` can be added
here, e.g. as a Mermaid `sequenceDiagram` block.)*
```
==================================================
        ORDER SUMMARY
==================================================
Order Summary                           PayNest
Order ID:                               903
__________________________________________________

Customer ID:                            1
[INFO] [stdout] Customer Name:          Aridivhaho23
__________________________________________________

Order Items:
                Product  Quantity   Total Price
[INFO] [stdout] ---------------------------------
                Laptop     2          R   39,98
[INFO] [stdout] Phone      1          R   29,99
[INFO] [stdout] Tablet     3          R  119,97
[INFO] [stdout] ===============================
Total Price exc. VAT:                   R189.94
VAT (15%):                              R28.49
================================================
Total Price incl. VAT:                  R218.43
```
---

## Business Rules

* Product prices and stock cannot be negative; product id must be positive.
* Customer email must be a valid email address (`local-part@domain.tld` pattern).
* Product quantities added to an order must be greater than zero.
* Money (prices, subtotals, VAT, totals) is stored and calculated using `BigDecimal`, never `double`, to avoid floating-point rounding errors.
* Line subtotal = unit price × quantity, rounded to 2 decimal places at the line level.
* Grand total (excl. VAT) equals the sum of the (already rounded) line subtotals, so the printed grand total always reconciles with the printed line amounts.
* VAT is 15% of the grand total (excl. VAT), rounded to 2 decimal places.
* An empty order prints sane, zeroed totals rather than crashing or printing nothing.
* Invalid input (non-numeric quantity/id, malformed email, blank name) is caught and reported with a clear message instead of the program crashing.
* Domain objects (`Order`, `Customer`) never print to the console directly — they return data (a `String` summary, getters) and the application layer decides how to display it.

---

## Object-Oriented Programming Concepts Demonstrated

This project demonstrates:

* Encapsulation (private fields, validated constructors, read-only collection views)
* Classes and Objects
* Constructors
* Getter Methods
* Composition
* Lists (`ArrayList`, exposed as `Collections.unmodifiableList`)
* Custom exceptions (`InsufficientStockException`)
* Separation of Concerns (domain / service / app layers)
* Data Validation

---

## Future Improvements

Possible enhancements include:

* Product inventory management (restocking).
* Remove/update items already on an order.
* Database integration using MySQL.
* JavaFX graphical user interface.
* User authentication.
* Discount and promotional pricing.
* File-based order persistence.

---

## Author

**Name:** Aridivhaho Junior Nemadodzi

Capstone 1 Project

Java Programming

---

## License

This project was developed for educational purposes as part of a Java programming capstone assessment.