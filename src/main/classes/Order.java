package main.classes;

import java.util.ArrayList;
import java.util.List;

public class Order 
{
    private final int orderId;
    private final Customer customer;
    private final List<OrderItem> orderItems;
    //VAT rate of 15%
    private static final double VAT_RATE = 0.15; 
    
    public Order(int orderId, Customer customer){
        this.orderId = orderId;
        this.customer = customer;
        this.orderItems = new ArrayList<>();
    }
    
    public int getOrderId() 
    {
        return orderId;
    }
    public Customer getCustomer() 
    {
        return customer;
    }
    public List<OrderItem> getOrderItems() 
    {
        return orderItems;
    }
    public void addOrderItem(Product product, int quantity) 
    {
        OrderItem orderItem = new OrderItem(product, quantity);
        orderItems.add(orderItem);
    }
    
    public double calculateAllTotalPrice() 
    {
        double total = 0;
        for (OrderItem orderItem : orderItems) {
            total += orderItem.calculateTotalPrice();
        }
        return total;
    }
    
    public void printSummary() 
    {
        System.out.println("Order Summary\t\tPayNest");
        System.out.println("Barcode: \t\t" + orderId);
        System.out.println("============================");

        //Displaying the customer information
        customer.displayCustomerInfo();

        System.out.println("============================");
        //Displaying the order items header
        System.out.println("Order Items:");

        //Displaying the order items in the order
        for (OrderItem orderItem : orderItems) {
            orderItem.displayOrderItemInfo();
        }

        //Displaying the total price of the order
        System.out.println("Total Price: \t\tR" + String.format("%.2f", calculateAllTotalPrice()));

        //Displaying the VAT amount
        System.out.println("\nVAT (15%): \t\tR" + String.format("%.2f", calculateAllTotalPrice() * VAT_RATE));
        //Displaying the total price including VAT
        System.out.println("Total Price incl. VAT: \tR" + String.format("%.2f", calculateAllTotalPrice() * (1 + VAT_RATE))); 

    }
}