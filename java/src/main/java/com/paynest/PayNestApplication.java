package com.paynest;
// Import the Scanner class
import com.paynest.classes.Customer;
import com.paynest.classes.Order;
import com.paynest.classes.OrderService;
import com.paynest.classes.Product;

class PayNestApplication {
  public static void main(String[] args) 
  { 
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

    //Try it yourself
    System.out.println("\nNow you can try it yourself!");
    PayNestApplication2.main(args);
  }
}