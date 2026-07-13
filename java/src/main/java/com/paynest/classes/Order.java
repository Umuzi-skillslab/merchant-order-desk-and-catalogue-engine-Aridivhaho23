package com.paynest.classes;

import java.util.ArrayList;
import java.util.List;


public class Order 
{
    private final int orderId;
    private final Customer customer;
    private final List<OrderItem> orderItems;
    //VAT rate of 15% as a constant variable
    private static final double VAT_RATE = 0.15; 
    
    public Order(int orderId, Customer customer){
        this.orderId = orderId;
        this.customer = customer;
        this.orderItems = new ArrayList<>();
    }
    //returns order ID
    public int getOrderId() 
    {
        return orderId;
    }
    //returns customer class
    public Customer getCustomer() 
    {
        return customer;
    }
    //returns an ArrayList
    public List<OrderItem> getOrderItems() 
    {
        return orderItems;
    }

    //Adding items
    public void addOrderItem(Product product, int quantity) 
    {
        //Check if the quantity is valid and product is not null
        if (quantity <= 0) 
        {
            //throw an exception if the quantity is less than or equal to zero
            throw new IllegalArgumentException("Quantity must be greater than zero.");
         //Check if the product is null   
        }else if(product == null)
        {
            //throw an exception if the product is null
            throw new IllegalArgumentException("Product cannot be null.");
        }
        OrderItem orderItem = new OrderItem(product, quantity);
        orderItems.add(orderItem);
    }
    
    //Calculate the total price
    public double calculateAllTotalPrice() 
    {
        double total = 0.0;
        for (OrderItem orderItem : orderItems) {
            total += orderItem.calculateTotalPrice();
        }
        return total;
    }
    
    public void printSummary() 
    {
        System.out.println("\n\n\t\t==================================");
        System.out.println("\tORDER SUMMARY");
        System.out.println("==================================");

        System.out.println("Order Summary\t\tPayNest");
        System.out.println("Order ID: \t\t" + orderId);
        System.out.println("__________________________________\n");

        //Displaying the customer information
        customer.displayCustomerInfo();

        System.out.println("__________________________________\n");
        //Displaying the order items header
        System.out.println("Order Items:");

        System.out.printf("%-10s %-10s %8s%n", "Product", "Quantity", "Total Price");
        //Displaying the order items in the order
        for (OrderItem orderItem : orderItems) {
            // %-20s = left-align product name in a 20-character field
            // %-10d = left-align quantity in a 10-character field
            // %8.2f = display total price with 2 decimal places in an 8-character field
            System.out.printf("%-10s %-10d R%8.2f%n",
                    orderItem.getProduct().getName(),
                    orderItem.getQuantity(),
                    orderItem.calculateTotalPrice());
        }

        //Displaying the total price of the order
        System.out.println("Total Price exc. VAT:\tR" + String.format("%.2f", calculateAllTotalPrice()));
        //Displaying the VAT amount
        System.out.println("\nVAT (15%): \t\t\t\tR" + String.format("%.2f", calculateAllTotalPrice() * VAT_RATE));
        //Displaying the total price including VAT
        System.out.println("\nTotal Price incl. VAT: \t\t\tR" + String.format("%.2f", calculateAllTotalPrice() * (1 + VAT_RATE))); 

    }
}