// Removed package declaration to match source file location
package main.classes;
//Product catalogue
//Product class

public class Product 
{
    //private variables to store product name and price
    private final int id;
    private final String name;
    private final double price;
    //constructor to initialize the product name and price
    public Product(int id, String name, double price) 
    {
        this.id = id;
        this.name = name;
        this.price = price;
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
    //display product information
    public void displayProductInfo() 
    {
        System.out.println("Product ID: \t" + id);
        System.out.println("Product Name: \t" + name);
        System.out.println("Price: \t\tR" + price);
    }
}

