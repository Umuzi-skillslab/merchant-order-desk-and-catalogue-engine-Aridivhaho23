# PayNest — Merchant Order Desk and Catalogue Engine

## Overview

A minimal Java commerce kernel for PayNest, an early-stage South African
fintech: products with prices, customers, orders with line items, and a
trustworthy order total printed as a human-readable summary.

## Project Structure
merchant-order-desk-and-catalogue-engine-Aridivhaho23/
│
├── src/
│ ├── main/java/com/paynest/
│ │ ├── domain/
│ │ │ ├── Product.java
│ │ │ ├── Customer.java
│ │ │ ├── OrderItem.java
│ │ │ ├── Order.java
│ │ │ └── InsufficientStockException.java
│ │ ├── service/
│ │ │ └── OrderService.java
│ │ └── app/
│ │ └── PayNestApplication.java
│ │
│ └── test/java/com/paynest/
│ ├── domain/ (ProductTest, CustomerTest, OrderItemTest, OrderTest)
│ └── service/ (OrderServiceTest)
│
├── pom.xml
└── README.md
## Features

* Create products with an id, name, price and stock level (price/stock validated, cannot be negative).
* Create customer profiles with an id, name and a validated email address.
* Create customer orders.
* Add multiple products to an order, including quantities greater than one.
* Calculate line subtotals and the overall order total (excl. and incl. 15% VAT) using `BigDecimal`.
* Display a formatted order summary.
* Validate all input (product id, quantity, name, email) and fail loudly with a clear message instead of corrupting the order.
* Demonstrate Object-Oriented Programming concepts: encapsulation, composition, separation of concerns.

---

## Technologies Used

* Java 21
* Maven
* JUnit 5 (Jupiter)
* Visual Studio Code
* Git and GitHub

---

## Class Responsibilities

### Product (`domain`)

Represents an item available for purchase. Fields: id, name, price (`BigDecimal`), stock. Validates in the constructor that id/price/stock are non-negative and name is not blank, so a `Product` can never exist in an invalid state. `reduceStock()` is package-private — only `Order` may call it.

### Customer (`domain`)

Represents a customer placing an order. Fields: id, name, email. Validates that the name is present and the email matches a basic email pattern.

### OrderItem (`domain`)

Represents one line within an order (a `Product` + a `quantity`). Calculates the line subtotal (`unit price * quantity`, rounded to 2 decimal places). Its constructor is package-private — only `Order` may construct one, since a line item has no meaningful existence outside an order.

### Order (`domain`)

Represents a customer's order: id, `Customer`, and its list of `OrderItem`s. `addItem()` is the single path for adding a line — it validates the quantity, checks stock, reduces stock, and adds the line atomically, so the stock invariant cannot be bypassed by any caller (including a caller that skips `OrderService` entirely). The item list is exposed only as a **read-only** view (`Collections.unmodifiableList`), so callers cannot corrupt totals by mutating it directly. Computes the grand total, VAT, and total incl. VAT, and builds the summary as a **String** rather than printing directly.

### InsufficientStockException (`domain`)

Thrown by `Order.addItem()` when a requested quantity exceeds available stock. Lives in `domain`, not `service`, because it's thrown directly by `Order` — a `service`-layer exception being thrown from `domain` would invert the natural dependency direction.

### OrderService (`service`)

A thin coordination layer over `Order`: `createOrder()` and `addToOrder()` simply delegate to `Order`'s constructor and `addItem()`. It does not duplicate the stock check — that logic lives once, in `Order`, so it can't drift out of sync or be bypassed by a caller that talks to `Order` directly.

### PayNestApplication (`app`)

The single entry point. Builds a small catalogue and one order, deliberately triggers one over-stock request to demonstrate fail-fast validation, and prints the resulting order summary. This is the *only* class that performs console output — `domain` and `service` never print.

---

## How to Run the Application

### Clone the repository

```bash
git clone <repository-url>
cd merchant-order-desk-and-catalogue-engine-Aridivhaho23
cd java
```

### Compile and run

```bash
mvn clean compile
mvn exec:java
```

This builds a small catalogue (Laptop, Wireless Mouse), one customer, and one order with two line items (one with quantity > 1). It also deliberately attempts to over-order stock once, to show that validation fails loudly with a clear message instead of corrupting the total, then prints the order summary — customer, each line, subtotal, VAT, and grand total.

## How to Test

```bash
mvn test
```

Tests cover: `Product`/`Customer` validation (negative price, blank name, invalid email), line and grand totals across multiple items, VAT, non-positive quantity rejection, stock reduction, an empty order, an unmodifiable order-items list, and — most importantly — that `Order.addItem()` enforces the stock check even when called directly, without going through `OrderService`.

---

## Business Rules

* Product prices and stock cannot be negative; product id must be positive.
* Customer email must be a valid email address (`local-part@domain.tld` pattern).
* Product quantities added to an order must be greater than zero.
* Money (`Product.price`, line totals, VAT, grand total) is `BigDecimal`, not `double`. The starter brief's default is `double` arithmetic, but `double` can silently misrepresent currency (e.g. `0.1 + 0.2 != 0.3`). `BigDecimal` with `HALF_UP` rounding to 2 decimal places is used instead, documenting the deviation as the brief allows ("document any rounding policy if you introduce one").
* Rounding happens once, at the line level (`OrderItem.calculateTotal()`), so the grand total is always the exact sum of what's printed per line — no drift between the displayed lines and the displayed total.
* All summary output is formatted with a fixed locale (`Locale.US`) so decimal separators are consistent regardless of the machine it runs on.
* Stock cannot go negative; an order that would oversell a product is rejected with `InsufficientStockException`, not silently truncated.
* `Order.getItems()` returns an unmodifiable list — callers cannot corrupt the order total by mutating the backing collection directly.
* An empty order prints sane, zeroed totals rather than crashing or printing nothing.

---

## Why the Design Extends Cleanly

`OrderItem` only reads `product.getPrice()`; `Order` only reads `OrderItem.calculateTotal()`. Neither ever reaches into `Product`'s other fields. That means adding a new `Product` field (e.g. `sku`, `category`) touches zero lines in `OrderItem`, `Order`, or checkout — the domain model was built around that boundary specifically so catalogue changes and order logic never have to change together.

The stock-check invariant lives in `Order.addItem()` itself — not in `OrderService` — and `Product.reduceStock()` / the `OrderItem` constructor are package-private, so nothing outside `domain` can bypass the check or construct an inconsistent line item. `OrderService` is a thin coordination layer over `Order`, not where the business rule is enforced; this is what lets a future caller (a REST controller, a batch importer, another service) reuse `Order` safely without re-implementing the stock check.

---

## Object-Oriented Programming Concepts Demonstrated

* Encapsulation (private/package-private fields, validated constructors, read-only collection views)
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

Capstone 1 Project · Java Programming

---

## License

This project was developed for educational purposes as part of a Java programming capstone assessment.