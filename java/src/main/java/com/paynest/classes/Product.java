// Removed package declaration to match source file location
package com.paynest.classes;
//Product catalogue
//Product class

public class Product 
{
    //private variables to store product name and price
    private final int id;
    private final String name;
    private final double price;
    private int stockTake;

    //constructor to initialize the product name and price
    public Product(int id, String name, double price, int stockTake) 
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockTake = stockTake;
    }
    //return the ID of the product
    public int getId() 
    {
        return id;
    }
    //return the name of the product
    public String getName() 
    {
        return name;
    }
    //return the price of the product
    public double getPrice() 
    {
        return price;
    }
    //Stoke Take
    public int getStock() 
    {
        return stockTake;
    }
    //Everytime the stock will decrease
    public void reduceStock(int quantity) 
    {
        //decriment
        stockTake -= quantity;
    }
}

