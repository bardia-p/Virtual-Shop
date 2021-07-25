package mystore;

import java.util.HashMap;

/**
 * The class to keep track of shopping cart (it is a subclass of the inventory class)
 * @author Bardia Parmoun 101143006
 * @version 2.0
 * @date 2020/04/10
 */
public class ShoppingCart extends ProductStockContainer {
    /**
     * Keeps track of the id of the cart
     */
    private int cartId;

    /**
     * The constructor for the shopping cart given a cart id
     * @param cartId the id that is assigned ot the cart
     */
    public ShoppingCart (int cartId) {
        this.products = new HashMap<>();
        this.cartId = cartId;
    }

    /**
     * Sets the id of the cart
     * @param id the id that is given to the cart
     */
    public void setId(int id){
        this.cartId = id;
    }

    /**
     * Gets the id of the cart
     * @return returns the id of the cart
     */
    public int getId(){
        return this.cartId ;
    }

    /**
     * Calculates the total price in the cart
     * @return the total price of the cart
     */
    public double getTotalPrice(){
        double total = 0;
        HashMap<Product, Integer> orders= this.getProducts();
        for (Product product: orders.keySet()){
            total +=product.getPrice()*orders.get(product);
        }
        return total;
    }
}
