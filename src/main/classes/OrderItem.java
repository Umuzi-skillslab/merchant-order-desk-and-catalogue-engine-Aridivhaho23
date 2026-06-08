package main.classes;
public class OrderItem
{
    private final Product product;
    private final int quantity;
    
    public OrderItem(Product product, int quantity) 
    {
        this.product = product;
        this.quantity = quantity;
    }
    
    public Product getProduct() 
    {
        return product;
    }
    
    public int getQuantity() 
    {
        return quantity;
    }
    
    public double calculateTotalPrice() 
    {
        return product.getPrice() * quantity;
    }
    
    public void displayOrderItemInfo() 
    {
        /*product.displayProductInfo();
        System.out.println("Quantity: \t" + quantity);
        System.out.println("Total Price: \tR" + String.format("%.2f", calculateTotalPrice()));*/
        System.out.println(product.getName() + " x " + quantity + " \t\tR" + 
                            String.format( "%.2f", calculateTotalPrice()));

    }
}