package mystore;

import java.util.HashMap;

/**
 * Inventory class: Holds an inventory of available products and stock
 * @author Bardia Parmoun 101143006
 * @version 4.0
 * @date 2020/04/10
 */

public class Inventory extends ProductStockContainer{
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

        this.addProductQuantity(p1,20);
        this.addProductQuantity(p2,10);
        this.addProductQuantity(p3,5);
        this.addProductQuantity(p4,8);
        this.addProductQuantity(p5,8);
        this.addProductQuantity(p6,8);
    }
}