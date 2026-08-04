package com.paynest.app;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.paynest.domain.Customer;
import com.paynest.domain.Order;
import com.paynest.domain.Product;
import com.paynest.service.InsufficientStockException;
import com.paynest.service.OrderService;

/**
 * Main entry point for the PayNest application.
 * Demonstrates the core commerce flow (Capstone 1).
 */
public final class PayNestApplication {

    //Will be used to validate the customer creation process. The first entry is invalid, the second is valid.
    private static final String[] CUSTOMER_ATTEMPTS = {
            "Aridivhaho23,not-an-email",
            "Aridivhaho23,aridivhaho23@example.com"
    };

    /**
     * Pre-written product requests to simulate a customer order.
     * Each entry is a string in the format "productId,quantity".
     */
    private static final String[] REQUESTED_ITEMS = {/*productId,quantit*/
                                                  "1,2",
                                                  "2,1",
                                                  "3,3",
                                                  "2,999",
                                                  "9,1",
                                                  "3,abc"
    };

    private PayNestApplication() {
        // Entry-point class - never instantiated.
    }

    public static void main(String[] args) {
        System.out.println("================================");
        System.out.println("\tPAYNEST STORE");
        System.out.println("================================");

        List<Product> catalogue = buildSampleCatalogue();
        printCatalogue(catalogue);

        Customer customer = createCustomer();
        int orderId = 1 + (int) (Math.random() * 999);

        OrderService orderService = new OrderService();
        Order order = orderService.createOrder(orderId, customer);

        for (String request : REQUESTED_ITEMS) {
            addRequestedItem(orderService, order, catalogue, request);
        }
        System.out.println(order.buildSummary());
    }

    /** A small starter catalogue so the demo has products to sell immediately. */
    private static List<Product> buildSampleCatalogue() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Laptop", new BigDecimal("19.99"), 100));
        products.add(new Product(2, "Phone", new BigDecimal("29.99"), 50));
        products.add(new Product(3, "Tablet", new BigDecimal("39.99"), 75));
        return products;
    }

    private static void printCatalogue(List<Product> catalogue) {
        System.out.println("\nAvailable products:");
        for (Product p : catalogue) {
            String priceStr = p.getPrice().setScale(2, RoundingMode.HALF_UP).toPlainString();
            System.out.printf("  [%d] %-10s R%8s (stock: %d)%n",
                    p.getId(), p.getName(), priceStr, p.getStock());
        }
        System.out.println();
    }


    /** Creates a customer from the first valid pre-written entry in CUSTOMER_ATTEMPTS.
     *  @return a valid Customer object
     */
    private static Customer createCustomer() {
        for (String attempt : CUSTOMER_ATTEMPTS) {
            String[] parts = attempt.split(",", 2);
            if (parts.length < 2) {
                System.out.println("Invalid customer entry (malformed): " + attempt + " — skipping.");
                continue;
            }
            String name = parts[0];
            String email = parts[1];
            try {
                return new Customer(name, email);
            } catch (IllegalArgumentException ex) {
                System.out.println("Invalid customer details (" + attempt + "): " + ex.getMessage()
                        + " Trying next entry.");
            }
        }
        throw new IllegalStateException("No valid pre-written customer entry was found.");
    }


    /** Adds a product to the order based on a pre-written request string.

     * @param orderService the service layer to handle business logic
     * @param order the order to which the product should be added
     * @param catalogue the list of available products
     * @param request a string in the format "productId,quantity"
    */
    private static void addRequestedItem(OrderService orderService, Order order,
                                          List<Product> catalogue, String request) {
        String[] parts = request.split(",", 2);
        if (parts.length < 2) {
            System.out.println("Could not add item (" + request + "): malformed request string.");
            return;
        }

        int productId;
        int quantity;

        try {
            productId = Integer.parseInt(parts[0].trim());
        } catch (NumberFormatException ex) {
            System.out.println("Could not add item (" + request + "): \"" + parts[0] + "\" is not a valid product id.");
            return;
        }

        try {
            quantity = Integer.parseInt(parts[1].trim());
        } catch (NumberFormatException ex) {
            System.out.println("Could not add item (" + request + "): \"" + parts[1] + "\" is not a valid whole number.");
            return;
        }

        Product chosen = findProduct(catalogue, productId);
        if (chosen == null) {
            System.out.println("Could not add item (" + request + "): no product with id " + productId + ".");
            return;
        }

        try {
            orderService.addProductsToOrder(order, chosen, quantity);
            System.out.println(quantity + " x " + chosen.getName() + " added successfully.");
        } catch (InsufficientStockException | IllegalArgumentException ex) {
            // The service layer threw instead of printing; this is the one
            // place that turns that into a friendly, readable message.
            System.out.println("Could not add item (" + request + "): " + ex.getMessage());
        }
    }

    private static Product findProduct(List<Product> catalogue, int id) {
        for (Product prod : catalogue) {
            if (prod.getId() == id) {
                return prod;
            }
        }
        return null;
    }
}