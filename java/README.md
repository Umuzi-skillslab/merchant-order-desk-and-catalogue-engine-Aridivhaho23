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
[INFO] [stdout] ================================
[INFO] [stdout]         PAYNEST STORE
[INFO] [stdout] ================================
[INFO] [stdout] 2 x Laptop added successfully.
[INFO] [stdout] 1 x Phone added successfully.
[INFO] [stdout] 3 x Tablet added successfully.
[INFO] [stdout] 

                ==================================
[INFO] [stdout]         ORDER SUMMARY
[INFO] [stdout] ==================================
[INFO] [stdout] Order Summary           PayNest
[INFO] [stdout] Order ID:               1
[INFO] [stdout] __________________________________

[INFO] [stdout] Customer ID:            1
[INFO] [stdout] Customer Name:          Aridivhaho23
[INFO] [stdout] __________________________________

[INFO] [stdout] Order Items:
[INFO] [stdout] Product    Quantity   Total Price
[INFO] [stdout] Laptop     2          R   39,98
[INFO] [stdout] Phone      1          R   29,99
[INFO] [stdout] Tablet     3          R  119,97
[INFO] [stdout] Total Price exc. VAT:   R189,94
[INFO] [stdout] 
VAT (15%):                              R28,49
[INFO] [stdout] 
Total Price incl. VAT:                  R218,43
```

## Example Output 2(Try it yourself)

```
[INFO] [stdout] ================================
[INFO] [stdout]         PAYNEST STORE
[INFO] [stdout] ================================
[INFO] [stdout] Enter your name:
Ari
[INFO] [stdout] Enter your email:
arinemadodzi@gmail.com
[INFO] [stdout] Enter your product name: 
laptop
[INFO] [stdout] Enter your product price: 
1200
[INFO] [stdout] Enter quantity: 
2
[INFO] [stdout] 2 x laptop added successfully.
[INFO] [stdout] Do you want to add another product? (Y/N): 
y
[INFO] [stdout] Enter your product name: 
phone
[INFO] [stdout] Enter your product price: 
1400
[INFO] [stdout] Enter quantity: 
2
[INFO] [stdout] 2 x phone added successfully.
[INFO] [stdout] Do you want to add another product? (Y/N): 
n
[INFO] [stdout] 

               ==================================
[INFO] [stdout]         ORDER SUMMARY
[INFO] [stdout] ==================================
[INFO] [stdout] Order Summary           PayNest
[INFO] [stdout] Order ID:               111
[INFO] [stdout] __________________________________

[INFO] [stdout] Customer ID:            2
[INFO] [stdout] Customer Name:          Ari
[INFO] [stdout] __________________________________

[INFO] [stdout] Order Items:
[INFO] [stdout] Product    Quantity   Total Price
[INFO] [stdout] laptop     2          R 2400,00
[INFO] [stdout] phone      2          R 2800,00
[INFO] [stdout] Total Price exc. VAT:   R5200,00
[INFO] [stdout] 
VAT (15%):                              R780,00
[INFO] [stdout] 
Total Price incl. VAT:                  R5980,00
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
