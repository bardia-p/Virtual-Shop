//Bardia Parmoun 101143006
//Guy Morgenshtern 101151430

package storetest;


import mystore.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;


/**
 * The class used to test the StoreManager class
 * @author Bardia Parmoun 101143006
 * @version 1.0
 * @date 2020/03/20
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StoreManagerTest {
    /**
     * Keeps track of all the carts
     */
    private static ShoppingCart[] carts;

    /**
     * The inventory of the store
     */
    private static StoreManager sm;

    /**
     * Keeps track of all the products
     */
    private static Product[] products;

    /**
     * Initializes all the carts and products that will be used during the testing
     */
    @BeforeAll
    public static void init() {
        sm = new StoreManager();
        carts = new ShoppingCart[4];
        products = new Product[6];
        products[0] = new Product("apple pie", 001,2.99, "");
        products[1] = new Product("blueberry pie", 002,3.99, "");
        products[2] = new Product("cake", 003,4.99, "");
        products[3] = new Product("cooking book", 101,15.99, "");
        products[4] = new Product("baking recipes", 102,8.99, "");
        products[5] = new Product("cooking tutorials", 103,19.99, "");
    }

    /**
     * Tests the getStock method in StoreManager
     */
    @Test
    @Order(1)
    public void testCheckStock(){
        int expected [] = new int[]{20,10,5,8,8,8};
        for (int i =0; i < 6; i++){
            assertEquals(expected[i],sm.checkStock(products[i]),
                    "checkStock function did not return the correct value");
        }
    }

    /**
     * Testing the getAvailableProduct() method on the StoreManager
     */
    @Test
    @Order(2)
    public void testGetAvailableProducts() {
        // comparing the size
        assertEquals(6,sm.getAvailableProducts().size(),
                "getAvailableProducts did not return the expected number of products");

        // comparing every element
        for (Product p: sm.getAvailableProducts()){
            assertTrue(sm.checkStock(p)!=0,
                    "getAvailableProducts returned an unexpected product");
        }
    }

    /**
     * Testing the assignNewCartID() method on the StoreManager
     */
    @Test
    @Order(3)
    public void testAssignNewCartID() {
        int expected[] = new int[]{0,1,2,3};

        // comparing every element
        for (int i = 0; i<4; i++){
            carts[i] = new ShoppingCart(sm.assignNewCartID());
            assertEquals(expected[i],carts[i].getId(),
                    "assignNewCartID assigned an incorrect id to the cart");
        }
    }

    /**
     * Testing the addToCart() method on the StoreManager
     */
    @Test
    @Order(4)
    public void testAddToCartNormal() {
        int expected [] = new int[]{18,7,1};

        for (int i = 0; i <3 ; i++){
            // checks the output of the addToCart method
            assertEquals(true, sm.addToCart(products[i].getName(),i+2,i),
                    "addToCart does not work properly for normal values");

            // comparing the size of the cart
            assertEquals(1,sm.getCartProducts(i).size(),
                    "addToCart did not add the product to the cart");

            // checking if the amount was added properly
            assertEquals(i+2,sm.getCartProducts(i).get(products[i]),
                    "addToCart did not add the proper amount of the product to the cart");

            // checking if the inventory removed that amount
            assertEquals(expected[i],sm.checkStock(products[i]),
                    "addToCart did not removed that amount of the product from the inventory");
        }
    }

    /**
     * Tests the getCartProducts in StoreManager
     */
    @Test
    @Order(5)
    public void testGetCartProducts(){
        int expectedSize [] = new int[]{1,1,1,0,0,0};

        for (int i =0; i < 3; i++){
            assertEquals(expectedSize[i],sm.getCartProducts(i).size(),
                    "getCartProducts did not remove the expected number of the products");
            if (sm.getCartProducts(i).size()!=0){
                assertEquals(i+2,sm.getCartProducts(i).get(products[i]),
                        "Checks to see the number of each product is as expected");
            }
        }
    }

    /**
     * Testing the addToCart() method on the StoreManager with an item that is not in the inventory
     */
    @Test
    @Order(6)
    public void testAddToCartProductNotThere() {
        Product newProduct = new Product("cookie", 34, 2.99, "");

        // checks the output of the addToCart method
        assertEquals(false, sm.addToCart(newProduct.getName(),1,0),
                "addToCart does not work properly for when the product is not in the inventory");

        // comparing the size of the cart (it should remain 1)
        assertEquals(1,sm.getCartProducts(0).size(),
                "addToCart changed the size of the cart even though the product does not exist");

        // checking if the amount was added properly
        assertEquals(null,sm.getCartProducts(0).get(newProduct),
                "addToCart added the product to the cart even though the product does not exist");
    }


    /**
     * Testing the addToCart() method on the StoreManager with an illegal amount (more than total or -1)
     */
    @Test
    @Order(7)
    public void testAddToCartIllegalAmount() {
        // more than the total amount in the inventory, a negative number, ordering 0 products
        int illegalAmount[] = new int[]{10, -1, 0};

        for (int i =0; i<illegalAmount.length; i++) {
            // checks the output of the addToCart method
            assertEquals(false, sm.addToCart(products[3].getName(), illegalAmount[i], 0),
                    "addToCart fails on edge cases (adding more than total, negative, or 0 amount)");

            // comparing the size of the cart (it should remain 1)
            assertEquals(1, sm.getCartProducts(0).size(),
                    "addToCart changes the size of the products even though it is not supposed to");

            // checking if the product was added to the cart (it should not be added)
            assertEquals(null,sm.getCartProducts(0).get(products[3]),
                    "addToCart adds the product to the cart even though the amount was illegal");

            // checking if inventory was untouched
            assertEquals(8,sm.checkStock(products[3]),
                    "addToCart changes the amount of the product in inventory even though it is not supposed to");
        }
    }

    /**
     * Testing the addToCart() method on the StoreManager with ordering the total amount of a product
     */
    @Test
    @Order(8)
    public void testAddToCartTotalAmount() {
        // checks the output of the addToCart method
        assertEquals(true, sm.addToCart(products[3].getName(),8,3),
                "addToCart fails when adding the total number of a product");

        // comparing the size of the cart should be 1
        assertEquals(1,sm.getCartProducts(3).size(),
                "addToCart changes the size of the products even though it is not supposed to");

        // checking if the amount was added properly
        assertEquals(8,sm.getCartProducts(3).get(products[3]),
                "addToCart does not add all of the product to the cart");

        // checking if the inventory removed that amount
        assertEquals(0,sm.checkStock(products[3]),
                "addToCart does not remove all the products from the inventory");
    }

    /**
     * Testing the removeFromCart() in the StoreManager
     */
    @Test
    @Order(9)
    public void testRemoveFromCartNormal() {
        int expected [] = new int[]{19,8,2};

        for (int i = 0; i <3 ; i++){
            // checks the output of the addToCart method
            assertEquals(true, sm.removeFromCart(products[i].getName(),1,i),
                    "removeFromCart fails when a normal amount is being removed from the cart");

            // comparing the size of the cart
            assertEquals(1,sm.getCartProducts(i).size(),
                    "removeFromCart changes the number of products even thought it is not supposed to");

            // checking if the product removed from the cart properly
            assertEquals(i+1,sm.getCartProducts(i).get(products[i]),
                    "removeFromCart does not remove the proper amount from the cart");

            // checking if the inventory got that removed amount back
            assertEquals(expected[i],sm.checkStock(products[i]),
                    "removeFromCart does not add the proper amount to the inventory");
        }
    }


    /**
     * Testing the removeFromCart() method on the StoreManager with an item that is not in the cart
     */
    @Test
    @Order(10)
    public void testRemoveFromCartProductNotThere() {
        // checks the output of the addToCart method
        assertEquals(false, sm.removeFromCart(products[4].getName(),1,0),
                "removeFromCart fails when the product is not in the inventory");

        // comparing the size of the cart (size should not change)
        assertEquals(1,sm.getCartProducts(0).size(),
                "removeFromCart changed the size of the product even thought it should remain the same");

        // checking if the cart shows that it does not contain it
        assertEquals(null,sm.getCartProducts(0).get(products[4]),
                "removeFromCart changed the amount of the product even though the amount was illegal");

        // checking if the inventory removed any of that product's amount
        assertEquals(8,sm.checkStock(products[4]),
                "removeFromCart changed the number of products in the inventory even though the amount was illegal");
    }


    /**
     * Testing the removeFromCart() method on the StoreManager with an illegal amount (more than total or -1)
     */
    @Test
    @Order(11)
    public void testRemoveFromCartIllegalAmount() {
        // more than the total amount in the inventory, a negative number, ordering 0 products
        int illegalAmount[] = new int[]{10, -1, 0};

        for (int i =0; i<illegalAmount.length; i++) {
            // checks the output of the addToCart method
            assertEquals(false, sm.removeFromCart(products[0].getName(), illegalAmount[i], 0),
                    "removeFromCart fails on edge cases (removing more than total, negative, or 0 amount)");

            // comparing the size of the cart (it should remain 1)
            assertEquals(1, sm.getCartProducts(0).size(),
                    "removeFromCart changed the size of the product even thought it should remain the same");

            // checking if the amount was removed properly (should be untouched and 1 since we already removed one)
            assertEquals(1,sm.getCartProducts(0).get(products[0]),
                    "removeFromCart changed the amount of the product even though the amount was illegal");

            // checking if the inventory removed any of that product's amount
            assertEquals(8,sm.checkStock(products[4]),
                    "removeFromCart changed the number of products in the inventory even though the amount is illegal");
        }
    }


    /**
     * Testing the removeFromCart() method on the StoreManager with ordering the total amount of a product in cart
     */
    @Test
    @Order(12)
    public void testRemoveFromCartTotalAmount() {
        // checks the output of the addToCart method
        assertEquals(true, sm.removeFromCart(products[3].getName(),8,3),
                "removeFromCart fails when removing all the products from the cart");

        // comparing the size of the cart should be 0 since its only product was removed
        assertEquals(0,sm.getCartProducts(3).size(),
                "The size of the cart was not 0 as expected");

        // checking if the product was fully removed from the cart
        assertEquals(null,sm.getCartProducts(3).get(products[3]),
                "The product is still in the cart even though it was supposed to be removed");

        // checking if the amount was returned to the inventory
        assertEquals(8,sm.checkStock(products[3]),
                "The items were not properly returned to the inventory");
    }

    /**
     * Tests the getCartTotalPrice method in StoreManager
     */
    @Test
    @Order(13)
    public void testGetCartTotalPrice(){
        double expected[] = new double[]{2.99,7.98,14.97,0};
        for (int i =0 ; i<4; i++){
            assertEquals(expected[i], sm.getCartTotalPrice(i), 0.001,
                    "getTotalPrice output did not match the expected output");
        }
    }

    /**
     * Tests the emptyCart method in StoreManager
     */
    @Test
    @Order(14)
    public void testEmptyCart(){
        for (int i =0 ; i<2; i++){
            sm.emptyCart(i);
            assertEquals(0,sm.getCartProducts(i).size(),
                    "emptyCart did not remove all of the cart's products");
        }

        // checking to see if the amount was returned to the inventory
        int expected [] = new int[]{20,10,2,8,8,8};
        for (int i =0; i < 6; i++){
            assertEquals(expected[i],sm.checkStock(products[i]),
                    "The products that were removed from the cart were not added properly to the inventory");
        }
    }

    /**
     * Tests the checkout method in StoreManager
     */
    @Test
    @Order(15)
    public void testCheckout(){
        double expected[] = new double[]{0,0,14.97,0};
        for (int i =0 ; i<4; i++){
            assertEquals(expected[i], sm.checkout(i), 0.001,
                    "The total price of the cart did not match the output of checkout");
            assertEquals(null,sm.getCartProducts(i),
                    "checkOut did not remove all of the products in the carts");
        }
    }

    /**
     * Lets the user that all tests are done
     */
    @AfterAll
    public static void tearDown() {
        System.out.println("Finished testing");
    }

}
