package com.paynest.service;

import com.paynest.domain.Customer;
import com.paynest.domain.Order;
import com.paynest.domain.Product;

public class OrderService {

    /**
     * Creates a new order for the given customer.
     *
     * @param orderId  unique identifier for the order
     * @param customer the customer placing the order
     * @return the newly created order
     */
    public Order createOrder(int orderId, Customer customer) {
        return new Order(orderId, customer);
    }
    /**
     * Adds `quantity` units of `product` to `order`, reducing stock.
     *
     * @throws IllegalArgumentException    if order/product is null or quantity is not positive
     * @throws InsufficientStockException  if there isn't enough stock to fulfil the request
     */

    public void addProductsToOrder(Order order, Product product, int quantity) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null.");
        }
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        if (quantity > product.getStock()) {
            throw new InsufficientStockException("Insufficient stock for " + product.getName()
                            + " (requested " + quantity + ", available " + product.getStock() + ").");
        }

        // Order.addOrderItem validates product/quantity again via OrderItem's
        // constructor -- this keeps Order safe to use even if some future
        // caller bypasses OrderService entirely.
        order.addOrderItem(product, quantity);
        product.reduceStock(quantity);
    }
}