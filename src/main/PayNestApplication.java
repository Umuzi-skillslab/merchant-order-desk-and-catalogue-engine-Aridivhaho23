package main;
// Import the Scanner class
import java.util.Scanner;
import main.classes.Customer;
import main.classes.Order;
import main.classes.Product;

class PayNestApplication {
  public static void main(String[] args) {
    // Create a Scanner object
    Scanner myObj = new Scanner(System.in);  

    System.out.println("Dear Customer, Welcome to PayNest!!!");

    //Prompt the user to enter their name
    System.out.println("Enter your name:");
    // Read user input
    String name = myObj.nextLine(); 

    //Prompt the user to enter their email
    System.out.println("Enter your email:");
    // Read user input
    String email = myObj.nextLine();

    //Randomize the order ID
    int orderId = (int)(Math.random() * 1000);

    //Prompt the user to enter their product name
    System.out.println("Enter your product name:");
    // Read user input
    String productName = myObj.nextLine();  

    //Prompt the user to enter their product price
    System.out.println("Enter your product price:");
    // Read user input
    double productPrice = myObj.nextDouble();
    myObj.nextLine(); // consume newline left by nextDouble()

    int quantity = 0;
    while(true) {
      //Prompt the user to enter the quantity of the product
      System.out.println("Enter the quantity or press Enter to skip/stop:");

      // Read user input
      String qtyNumber = myObj.nextLine();
      if (qtyNumber.trim().isEmpty()) {
         break;
      }
      quantity = Integer.parseInt(qtyNumber);
      break;
    }

    //Passing the scanner input to the objects
    Customer customer = new Customer(name, email);
    Product product = new Product(orderId, productName, productPrice);

    //Creating an order item and adding it to the order
    //OrderService orderService = new OrderService();
    //orderService.addOrderItem(orderItem);

    Order order = new Order(orderId, customer);

    if (quantity > 0) {
      order.addOrderItem(product, quantity);
    }
    
    //Printing the order summary
    order.printSummary();

  }
}