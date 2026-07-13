package com.paynest;
// Import the Scanner class
import java.util.Scanner;

import com.paynest.classes.Customer;
import com.paynest.classes.Order;
import com.paynest.classes.OrderService;
import com.paynest.classes.Product;

class PayNestApplication {
  public static void main(String[] args) 
  {
    // Create a Scanner object
    Scanner myObj = new Scanner(System.in);  

    //Heading of the store
    System.out.println("================================");   
    System.out.println("\tPAYNEST STORE");
    System.out.println("================================");

    Customer customer = new Customer("Aridivhaho23", "aridivhaho23@example.com");
    Product laptop = new Product(1, "Laptop", 19.99, 100);
    Product phone = new Product(2, "Phone", 29.99, 50);
    Product tablet = new Product(3, "Tablet", 39.99, 75);

    // Create one order
    Order order = new Order(1, customer);

    // Create one OrderService
    OrderService orderService = new OrderService(); 
    //Add product to order
    orderService.addProductsToOrder(order, laptop, 2);
    orderService.addProductsToOrder(order, phone, 1);
    orderService.addProductsToOrder(order, tablet, 3);

    //Print summary
    order.printSummary();


/*   //Prompt the user to enter their name
    System.out.print( "Enter your name:\t");
    // Read user input
    String name = myObj.nextLine(); 

    //Prompt the user to enter their email
    System.out.print("Enter your email:\t");
    // Read user input
    String email = myObj.nextLine();

    //Randomize the order ID
    int orderId = (int)(Math.random() * 1000);

// Customer details
Customer customer = new Customer(name, email);

// Create one order
Order order = new Order(orderId, customer);


String choice;

do {

    // Product name
    System.out.print("Enter your product name: ");
    String productName = myObj.nextLine();

    // Product price
    System.out.print("Enter your product price: ");
    double productPrice = myObj.nextDouble();

    // Quantity
    System.out.print("Enter quantity: ");
    int quantity = myObj.nextInt();
    myObj.nextLine(); //consume newline

    int stockTake = 100;

    //Create product
    Product product = new Product(orderId, productName, productPrice, stockTake);

    //Add product to order
    orderService.addProductsToOrder(order, product, quantity);

    //Ask whether to continue
    System.out.print("Do you want to add another product? (Y/N): ");
    choice = myObj.nextLine();

} while (choice.equalsIgnoreCase("Y"));

//Print summary
order.printSummary();

//Close scanner after all input is finished
myObj.close();

  }*/
  }
}