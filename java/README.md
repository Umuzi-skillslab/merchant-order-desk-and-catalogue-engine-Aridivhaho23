[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-2e0aaae1b6195c2367325f4f02e2d04e9abb55f0b24a779b69b11b9e10269abc.svg)](https://classroom.github.com/online_ide?assignment_repo_id=24010664&assignment_repo_type=AssignmentRepo)
# Merchant-order-desk-and-catalogue-engine
# PayNest E-Commerce Order Management System

## Overview

PayNest is a simple Java console-based e-commerce application developed as part of a capstone project. The application demonstrates the core principles of Object-Oriented Programming (OOP) by modelling a basic online shopping process. Customers can create an order, add products with quantities, and receive an order summary displaying line subtotals and a grand total.

The project focuses on encapsulation, class relationships, data validation, and clean code practices while following standard Java development conventions.

---

## Features

* Create products with an ID, name and price.
* Create customer profiles with ID, name and email address.
* Create customer orders.
* Add multiple products to an order.
* Support quantities greater than one.
* Calculate line subtotals automatically.
* Calculate the overall order total.
* Display a formatted order summary.
* Validate user input.
* Demonstrate Object-Oriented Programming concepts.

---

## Technologies Used

* Java 17 (or later)
* Maven
* JUnit 5
* Visual Studio Code
* Git and GitHub

---

## Project Structure

merchant-order-desk-and-catalogue-engine-Aridivhaho23/
│
├────── src/
│       ├── main/java/com/paynest
│       │  └── classes/
│       │       ├── Product.java
│       │       ├── Customer.java
│       │       ├── OrderItem.java
│       │       ├── Order.java
│       │       ├── OrderService.java
│       │
│       └── PayNestApplication.java
│
├── pom.xml
└── README.md
```

---

## Class Responsibilities

### Product

Represents an item available for purchase.

Fields:

* Product ID
* Product Name
* Product Price

Responsibilities:

* Store product information.
* Provide getter methods for other classes.

---

### Customer

Represents a customer placing an order.

Fields:

* Customer ID
* Customer Name
* Customer Email

Responsibilities:

* Store customer information.
* Provide getter methods.

---

### OrderItem

Represents one line within an order.

Fields:

* Product
* Quantity

Responsibilities:

* Calculate the subtotal for a product.
* Link products with quantities.

---

### Order

Represents a customer's order.

Fields:

* Order ID
* Customer
* List of OrderItems

Responsibilities:

* Add products to an order.
* Calculate the grand total.
* Print the order summary.

---

### OrderService

Handles the creation and management of customer orders.

Responsibilities:

* Create new orders.
* Separate business logic from the user interface.

---

### PayNestApplication

The application's entry point.

Responsibilities:

* Prompt the user for input.
* Create sample products.
* Create customer orders.
* Display the final receipt.

---

## How to Run the Application

### Clone the repository

```bash
git clone <repository-url>
```

### Navigate to the project

```bash
cd merchant-order-desk-and-catalogue-engine-Aridivhaho23
```

### Compile the project

```bash
mvn clean compile
```
### Run the application

```bash
mvn exec:java
```

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

---

## Example Output

```
=============================
        PAYNEST STORE
=============================

Enter your name:
John Smith

Enter your email:
john@example.com

Available Products

1. Laptop     R12000.00
2. Mouse      R350.00

Select Product:
1

Enter Quantity:
2

Add another product? (Y/N):
Y

Select Product:
2

Enter Quantity:
3

=============================
        ORDER SUMMARY
=============================

Customer:
John Smith
john@example.com

Laptop
Quantity: 2
Subtotal: R24000.00

Mouse
Quantity: 3
Subtotal: R1050.00

-----------------------------
Grand Total: R25050.00
=============================
```

---

## Business Rules

* Product quantities must be greater than zero.
* Product prices cannot be negative.
* Order totals are calculated as:

```
Subtotal = Product Price × Quantity
```

* Grand Total equals the sum of all line subtotals.
* Customer email addresses should be validated before creating the customer.
* Invalid user input is rejected with an appropriate error message.

---

## Object-Oriented Programming Concepts Demonstrated

This project demonstrates:

* Encapsulation
* Classes and Objects(In other words OOP)
* Constructors
* Getter Methods
* Composition
* Lists (ArrayList)
* Method Reuse
* Separation of Concerns
* Data Validation

---

## Future Improvements

Possible enhancements include:

* Product inventory management.
* Remove items from an order.
* Update product quantities.
* Database integration using MySQL.
* JavaFX graphical user interface.
* User authentication.
* Discount and promotional pricing.
* File-based order persistence.

---

## Author

**Name:** Aridivhaho Junior Nemadodzi

Capstone 1 Project

Java Programming

---

## License

This project was developed for educational purposes as part of a Java programming capstone assessment.
