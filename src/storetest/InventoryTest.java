//Bardia Parmoun 101143006
//Guy Morgenshtern 101151430

package storetest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import mystore.Inventory;
import mystore.Product;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


/**
 * The class used to test the Inventory class
 * @author Guy Morgenshtern 101151430
 * @version 2.0
 * @date 2020/03/21
 */

class InventoryTest {

    /**
     * Inventory object to be tested
     */
    private static Inventory inv;

    /**
     * Product and stock list to keep track on items being added during testing
     */
    private static HashMap<Product, Integer> initialProducts;

    /**
     * product ID that is never used in test
     * For testing "___ProductDoesntExist" functions
     */
    private static final int invalidID = 99;

    /**
     * reinitializing test variables for each test case
     */
    @BeforeEach
    public void setUp() {
         //setting up initial products for testGet methods
         initialProducts = new HashMap<>();
         initialProducts.put(new Product("Apple Pie", 1, 9.99,""), 10);
         initialProducts.put(new Product("Peach Pie", 2, 9.99,""), 5);
         initialProducts.put(new Product("Rhubarb Pie", 3, 9.99,""), 1);
         initialProducts.put(new Product("Pumpkin Pie", 4, 9.99,""), 20);

         //creating test inv with initial products
         inv = new Inventory(initialProducts);
    }

    /**
     * Notify that test has finished after each test
     */
    @AfterEach
    public void tearDown() {
        System.out.println("Test has finished");
    }

    /**
     * Test getting hashmap of all products
     */
    @Test
    public void testGetProducts() {

        //Hashmap is never null
        assertEquals(initialProducts, inv.getProducts(), "getProduct does not return correct HashMap");
    }
    /**
     * Testing get product info for a product that doesn't exist
     */
    @Test
    public void testGetProductInfoProductDoesntExists() {

        //testing getProductInfo with ID that does not exist
        assertEquals(null, inv.getProductInfo(invalidID), "getProductInfo did not output required value");
    }

    /**
     * Testing get product info for a product exist
     */
    @Test
    public void testGetProductInfoProductExists() {

        //checks that proper item is returned from inventory
        for (Product p: initialProducts.keySet()) {
            assertEquals(p, inv.getProductInfo(p.getId()), "getProductInfo did not output required value");
        }
    }

    /**
     * Testing get stock for a product that doesn't exist
     */
    @Test
    public void testGetStockNoProductExists() {

        //expects "-1" for stock of an invalid product
        assertEquals(-1, inv.getStock(invalidID), "getStock did not output required value");
    }

    /**
     * Testing get stock for a product that exists
     */
    @Test
    public void testGetStockProductExists() {

        //checks proper stock is being returned from inv
        for (Product p: initialProducts.keySet()) {
            assertEquals(initialProducts.get(p), inv.getStock(p.getId()), "getStock did not output required value");
        }
    }

    /**
     * Testing adding a negative stock
     */
    @Test
    public void testAddNegativeStock() {

        //adding a negative amount of stock ONLY to inventory, for each product in initialProducts
        for (Product p: initialProducts.keySet()) {
            inv.addStock(p, -10);
            //stock should not have changed
            assertEquals(initialProducts.get(p), inv.getStock(p.getId()), "addStock did not output required value");
        }
    }

    /**
     * Testing adding stock to a product that doesn't exist yet
     */
    @Test
    public void testAddStockNoProductExists() {
        Product newProduct = new Product("Barak Obama's Biography", 5, 10.99, "");

        //adding new product to inventory
        inv.addStock(newProduct, 10);

        //assert product is in inventory HashMap
        assertEquals(newProduct, inv.getProductInfo(newProduct.getId()), "addStock did not output required value");

        //assert stock of new product is correct
        assertEquals(10, inv.getStock(newProduct.getId()), "New product does not have correct stock");

        //assert size of inventory incremented by 1
        assertEquals(initialProducts.size(), inv.getProducts().size(),
                "New product was not added to product HashMap in inventory");
    }

    /**
     * testing add stock to a product that already exists
     */
    @Test
    public void testAddStockProductExists() {

        //add 10 to the stock of each initial product
        for (Product p: initialProducts.keySet()) {
            inv.addStock(p, 10);

            //assert that the stock has increased by appropriate amount
            assertEquals(initialProducts.get(p), inv.getStock(p.getId()), "addStock did not correctly add stock");
        }
    }

    /**
     * testing removing a negative amount of stock
     */
    @Test
    public void testRemoveNegativeStock() {

        //removing a negative amount of stock ONLY to inventory, for each product in initialProducts
        for (Product p: initialProducts.keySet()) {
            assertEquals(false, inv.removeStock(p, -10),
                    "removeStock did not output required value");
            //asserting stock did not change
            assertEquals(initialProducts.get(p), inv.getStock(p.getId()),
                    "removeStock incorrectly changed the stock of product");
        }
    }

    /**
     * testing removing stock from a product that doesn't exist
     */
    @Test
    public void testRemoveStockNoProductExists() {
        Product newProduct = new Product("Lego set", invalidID, 15.99, "");

        //assert correct output from function
        assertEquals(false, inv.removeStock(newProduct, 10),
                "removeStock did not output required value");

    }

    /**
     * testing removing more stock than available from a product
     */
    @Test
    public void testRemoveTooMuchStock() {
        for (Product p: initialProducts.keySet()) {
            //assert correct output
            assertEquals(true, inv.removeStock(p, 1000),
                    "removeStock did not output required value");

            //assert that product stock is not negative
            assertEquals(0, inv.getStock(p.getId()),
                    "removeStock incorrectly changed stock in inventory");
        }
    }

    /**
     * testing removing all available stock from a product
     */
    @Test
    public void testRemoveAllStockAvailable() {
        int productStock;
        for (Product p: initialProducts.keySet()) {
            productStock = initialProducts.get(p);
            assertEquals(true, inv.removeStock(p, productStock),
                    "removeStock did not output required value");
            assertEquals(0, inv.getStock(p.getId()),
                    "removeStock incorrectly changed stock in inventory");
        }
    }

    /**
     * testing removing 1 stock from an existing product
     */
    @Test
    public void testRemoveStockProductExists() {
        for (Product p: initialProducts.keySet()) {

            //assert method produces correct output
            assertEquals(true, inv.removeStock(p, 1));

            //assert that stock changes in inventory
            assertEquals(initialProducts.get(p), inv.getStock(p.getId()),
                    "removeStock incorrectly changed stock in inventory");
        }
    }
}