package mystore;

import java.util.HashMap;

/**
 * The abstract class used to manager the Inventory and the ShoppingCart
 * @author Guy Morgenshtern 101151430 & Bardia Parmoun 101143006
 * @version 1.0
 * @date 2020/04/10
 */
public abstract class ProductStockContainer {
    /**
     * Keeps track of the products and their stock
     */
    protected HashMap<Product, Integer> products = new HashMap<>();

    /**
     * Find the number of stocks available for the product given its id
     * @param product the product to be removed
     * @return int of product's stock
     */
    public int getProductQuantity(Product product){
        if (products.containsKey(product)){
            return products.get(product);
        }
        return -1;
    }

    /**
     * Increases the number of products
     * @param product the product to add stocks to
     * @param newStock the number of stocks to add to the given products
     */
    public void addProductQuantity(Product product, int newStock){
        //if negative stock is being added
        if (newStock < 0) {
            return;
        }

        // If the product already exists it adds to the existing value
        else if (getProductQuantity(product) != -1) {
            products.put(product, products.get(product) + newStock);
        }

        // If this is a new product it adds it to both the products and stocks arraylists
        else {
            products.put(product, newStock);
        }
    }

    /**
     * Removes a certain number stocks from a product
     * @param product the product to remove stocks from
     * @param newStock the number of stocks to remove from the product
     * @return boolean if removeStock was successful
     */
    public boolean removeProductQuantity(Product product, int newStock){
        // If the product already exists it removes from the existing values

        if (newStock < 0) {
            return false;
        }

        if (getProductQuantity(product)!=-1) {
            if (products.get(product)-newStock>=0){
                products.put(product,products.get(product)- newStock);
            }

            // If the final value is less than 0 it just sets it to 0
            else{
                products.put(product,0);
            }
            return true;
        }

        else{
            //product does not exist
            return false;
        }
    }

    /**
     * Returns the number of products
     * @return the number of product
     */
    public int getNumOfProducts(){
        return products.size();
    }

    /**
     * Returns the product hashmap
     * @return the product hashmap
     */
    public HashMap<Product, Integer> getProducts(){
        return products;
    }
}
