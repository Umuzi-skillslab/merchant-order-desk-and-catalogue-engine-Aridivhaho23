package com.paynest.classes;

public class Customer 
{   
    //Creating all variables;
    private int customerId;
    private final String name;
    private final String email;

    //Counter for all customers
    private static int nextId = 1;

    public Customer(String name, String email) 
    {
        this.name = name;
        this.email = email;
        this.customerId = nextId++;
    }
    //Can be used to get the name only of the customer
    public String getName() 
    {
        return name;
    }
    //Get be used  to get only the email of the customer
    public String getEmail() 
    {
        return email;
    }
    //return the auto generated id for customer
    public int getId()
    {
        return customerId;
    }
    //This function can be called to display the customer's name
    //I am guessing the email will be used in the near future probably to send receipt via email
    public void displayCustomerInfo() 
    {
        //Customer ID
        System.out.println("Customer ID: \t\t" + customerId);
        System.out.println("Customer Name: \t\t" + name);
        //System.out.println("Email: \t\t" + email);
    }
}