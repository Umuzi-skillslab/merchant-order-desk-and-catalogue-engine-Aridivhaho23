# Merchant-order-desk-and-catalogue-engine
# PayNest E-Commerce Order Management System

## Overview

PayNest is a simple Java console-based e-commerce application developed as part of a capstone project. The application demonstrates the core principles of Object-Oriented Programming (OOP) by modelling a basic online shopping process. Customers can create an order, add products with quantities, and receive an order summary displaying line subtotals, VAT, and a grand total.

The project focuses on encapsulation, class relationships, data validation, and clean code practices while following standard Java development conventions.

---

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

### OrderService (`service`)

Adds products to an order, including the stock check. Never prints to the console: it either succeeds or throws (`IllegalArgumentException` / `InsufficientStockException`) so the caller decides how to present the outcome.

### PayNestApplication (`app`)

The single entry point. Runs unattended (no console input) using a set of pre-written customer/order requests, validates every one of them, calls into `OrderService`, and prints the resulting order summary. This is the *only* class that performs console output.

---

## How to Run the Application

### Clone the repository

```bash
git clone <repository-url>
```

### Navigate to the project

```bash
cd merchant-order-desk-and-catalogue-engine-Aridivhaho23
cd java
```

### Compile the project

```bash
mvn clean compile
```

## Running the Tests

Execute all unit tests using:

```bash
mvn test
```

### Run the application

```bash
mvn exec:java
```

This runs `com.paynest.app.PayNestApplication`, which prints a sample product catalogue, then works through a set of **pre-written** customer details and add-to-order requests (no console input required). A few of the pre-written entries are deliberately invalid (a bad email, a request over stock, an unknown product id, a non-numeric quantity) so the run also demonstrates the validation/error-handling paths — each is reported with a friendly message instead of crashing the program. It finishes by printing the order summary.

---

## Running the Tests

Execute all unit tests using:

```bash
mvn test
```

A successful build should display:

```text
BUILD SUCCESS
```

Tests cover: product/customer validation (negative price, blank name, invalid email), order-item line totals, order grand-total/VAT calculations (including an empty order), encapsulation (the order-items list rejects direct mutation), and `OrderService` stock handling (successful add, insufficient stock, invalid quantity).
```
Ouput Sample:
[INFO] [stdout] ================================
[INFO] [stdout]         PAYNEST STORE
[INFO] [stdout] ================================
[INFO] [stdout] 
Available products:
[INFO] [stdout]   [1] Laptop     R   19,99 (stock: 100)
[INFO] [stdout]   [2] Phone      R   29,99 (stock: 50)
[INFO] [stdout]   [3] Tablet     R   39,99 (stock: 75)
[INFO] [stdout] 
[INFO] [stdout] Invalid customer details (Aridivhaho23,not-an-email): Customer email is not a valid email address: not-an-email Trying next entry.
[INFO] [stdout] 2 x Laptop added successfully.
[INFO] [stdout] 1 x Phone added successfully.
[INFO] [stdout] 3 x Tablet added successfully.
[INFO] [stdout] Could not add item (2,999): Insufficient stock for Phone (requested 999, available 49).
[INFO] [stdout] Could not add item (9,1): no product with id 9.
[INFO] [stdout] Could not add item (3,abc): "abc" is not a valid whole number.
[INFO] [stdout] 
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