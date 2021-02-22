//Bardia Parmoun 101143006
//Guy Morgenshtern 101151430

import java.util.HashMap;

/**
 * Inventory class: Holds an inventory of available products and stock
 * @author Bardia Parmoun
 * @version 1.0
 * @date 2020/02/10
 */

public class Inventory{
    private HashMap<Product, Integer> products; // keeps track of the products of the stocks

    /**
     * Constructor
     * initializes parallel arraylists
     * creates default products
     */
    public Inventory(){
        this.products = new HashMap<>();

        // making some default products
        Product p1 = new Product("milk", 123,2.99);
        Product p2 = new Product("juice", 456,3.99);
        Product p3 = new Product("cake", 789,4.99);

        this.addStock(p1,20);
        this.addStock(p2,10);
        this.addStock(p3,5);
    }

    public HashMap<Product, Integer> getProducts(){
        return this.products;
    }

    /**
     * Finds the index of a product in the arraylist of products given the id of the product
     * @param id
     * @return int of product's index
     */
    private Product findProduct(int id){
        for (Product product: products.keySet()){
            if (product.getId()==id){
                return product;
            }
        }
        return null;
    }

    /**
     * Finds the product given its id
     * @param id
     * @return Product object
     */
    public Product getProductInfo(int id){
        return findProduct(id);
    }

    /**
     * Find the number of stocks available for the product given its id
     * @param id
     * @return int of product's stock
     */
    public int getStock (int id) {
        Product product = findProduct(id) ;
        if (product!=null) {
            return products.get(product);
        }
        //product does not exist
        return -1;
    }

    /**
     * Increases the number of products
     * @param product
     * @param newStock
     */
    public void addStock (Product product, int newStock){

        // If the product already exists it adds to the existing value
        if (getStock(product.getId())!=-1) {
            products.put(product,products.get(product)+ newStock);
        }

        // If this is a new product it adds it to both the products and stocks arraylists
        else{
            products.put(product, newStock);
        }
    }

    /**
     * Removes a certain number stocks from a product
     * @param product
     * @param newStock
     * @return boolean if removeStock was successful
     */
    public boolean removeStock (Product product, int newStock){

        // If the product already exists it removes from the existing values
        if (getStock(product.getId())!=-1) {
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
}