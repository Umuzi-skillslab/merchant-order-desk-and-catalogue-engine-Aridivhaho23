package com.paynest;
// Import the Scanner class
import java.util.Scanner;

import com.paynest.classes.Customer;
import com.paynest.classes.Order;
import com.paynest.classes.OrderService;
import com.paynest.classes.Product;

class PayNestApplication2 {
  public static void main(String[] args) 
  {
    // Create a Scanner object
    Scanner scanner  = new Scanner(System.in);  

    //Heading of the store
    System.out.println("================================");   
    System.out.println("\tPAYNEST STORE");
    System.out.println("================================");

   //Prompt the user to enter their name
    System.out.println( "Enter your name:\t");
    // Read user input
    String name = scanner.nextLine(); 

    //Prompt the user to enter their email
    System.out.println("Enter your email:\t");
    // Read user input
    String email = scanner.nextLine();

    //Randomize the order ID
    int orderId = (int)(Math.random() * 1000);

    // Customer details
    Customer customer = new Customer(name, email);

    // Create one order
    Order order = new Order(orderId, customer);


    String choice;

    do {

        // Product name
        System.out.println("Enter your product name: ");
        String productName = scanner.nextLine();

        // Product price
        System.out.println("Enter your product price: ");
        double productPrice = scanner.nextDouble();

        // Quantity
        System.out.println("Enter quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); //consume newline

        int stockTake = 100;

        //Create product
        Product product = new Product(orderId, productName, productPrice, stockTake);
        OrderService orderService = new OrderService();

        //Add product to order
        orderService.addProductsToOrder(order, product, quantity);

        //Ask whether to continue
        System.out.println();
        System.out.println("Y to add another product, N to finish: ");
        System.out.println("Do you want to add another product? (Y/N): ");
        choice = scanner.nextLine();

    } while (choice.equalsIgnoreCase("Y"));

    //Print summary
    order.printSummary();

    //Close scanner after all input is finished
    scanner.close();

  }
}