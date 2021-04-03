//Bardia Parmoun 101143006
//Guy Morgenshtern 101151430

package mystore;

import java.util.HashMap;

/**
 * Inventory class: Holds an inventory of available products and stock
 * @author Bardia Parmoun 101143006
 * @version 3.0
 * @date 2020/03/21
 */

public class Inventory{

    /**
     * Keeps track of the products and their stock
     */
    private HashMap<Product, Integer> products;

    /**
     * Constructor
     * initializes parallel arraylists
     * creates default products
     */
    public Inventory(){
        this(new HashMap<>());
        initializeInventory();
    }

    /**
     * Another constructor for the inventory class that gets a HashMap of products as its input
     * @param products the already made inventory that can be used to initialize the inventory with
     */
    public Inventory(HashMap<Product, Integer> products){
        this.products = products;
    }


    /**
     * Initializes the inventory with some default products
     */
    private void initializeInventory(){
        // making some default products
        Product p1 = new Product("apple pie", 001,2.99,
                "images/apple_pie.png");
        Product p2 = new Product("blueberry pie", 002,3.99,
                "images/blueberry_pie.png");
        Product p3 = new Product("cake", 003,4.99,
                "images/cake.png");

        Product p4 = new Product("cooking book", 101,15.99,
                "images/cooking_book.jpg");
        Product p5 = new Product("baking recipes", 102,8.99,
                "images/baking_recipes.jpg");
        Product p6 = new Product("cooking tutorials", 103,19.99,
                "images/cooking_tutorials.jpg");

        this.addStock(p1,20);
        this.addStock(p2,10);
        this.addStock(p3,5);
        this.addStock(p4,8);
        this.addStock(p5,8);
        this.addStock(p6,8);
    }

    /**
     * Returns the products of the Inventory
     * @return the products hashmap
     */
    public HashMap<Product, Integer> getProducts(){
        return this.products;
    }

    /**
     * Finds the index of a product in the arraylist of products given the id of the product
     * @param id the id of the product
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
     * @param id the id of the product
     * @return Product object
     */
    public Product getProductInfo(int id){
        return findProduct(id);
    }

    /**
     * Find the number of stocks available for the product given its id
     * @param id the id of the product
     * @return int of product's stock
     */
    public int getStock (int id) {
        Product product = findProduct(id) ;
        try{
            return products.get(product);
        }
        catch(NullPointerException e){
            //product does not exist
            return -1;
        }
    }

    /**
     * Increases the number of products
     * @param product the product to add stocks to
     * @param newStock the number of stocks to add to the given products
     */
    public void addStock (Product product, int newStock) {

        //if negative stock is being added
        if (newStock < 0) {
            return;
        }

        // If the product already exists it adds to the existing value
        else if (getStock(product.getId()) != -1) {
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
    public boolean removeStock (Product product, int newStock){
        // If the product already exists it removes from the existing values

        if (newStock < 0) {
            return false;
        }

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