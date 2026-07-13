package com.paynest.classes;

public class OrderItem
{
    private final Product product;
    private final int quantity;
    //Creating an order
    public OrderItem(Product product, int quantity) 
    {
        this.product = product;
        this.quantity = quantity;
    }
    //returns the product class
    public Product getProduct() 
    {
        return product;
    }
    //return the quantity of the product wanted
    public int getQuantity() 
    {
        return quantity;
    }
    //Calculate the total price
    public double calculateTotalPrice() 
    {
        //to get the price which will be used with the quantity to calculate the new price
        return product.getPrice() * quantity;
    }
}