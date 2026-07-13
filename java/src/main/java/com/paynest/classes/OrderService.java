package com.paynest.classes;

public class OrderService {
    //Creating a new Customer
    /*public Order createOrder(int orderId, Customer customer)
    {
        return new Order(orderId, customer);
    }*/
    // Adding those individual items
    public void addProductsToOrder(Order order, Product product, int quantity)
    {
        if (quantity > product.getStock()) {
            System.out.println("Insufficient stock for " + product.getName());
            System.out.println("Available stock: " + product.getStock());
            return;
        }

        order.addOrderItem(product, quantity);

        //Whatever quantity added will be reduced from the stock
        product.reduceStock(quantity);

        //The Display message to say an order has been added.
        System.out.println(quantity + " x " + product.getName() + " added successfully.");

    }
   
    //Calculating all added items all together
    //Will mofiy in the future for the VAT to be added to the total price
    /*public Double calculateTotals(Order order)
    {
        return order.calculateAllTotalPrice();
    }*/
}