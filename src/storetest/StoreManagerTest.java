//Bardia Parmoun
// 101143006

package storetest;


import store.*;
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
        products[0] = new Product("apple pie", 001,2.99);
        products[1] = new Product("blueberry pie", 002,3.99);
        products[2] = new Product("cake", 003,4.99);
        products[3] = new Product("cooking book", 101,15.99);
        products[4] = new Product("baking recipes", 102,8.99);
        products[5] = new Product("cooking tutorials", 103,19.99);
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
                    "The output of the function did not match the expected value");
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
                "The output of the function did not match the expected value");

        // comparing every element
        for (Product p: sm.getAvailableProducts()){
            assertTrue(sm.checkStock(p)!=0,
                    "The output of the function did not match the expected value");
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
                    "The output of the function did not match the expected value");
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
            assertEquals(true, sm.addToCart(products[i].getName(),i+2,i));

            // comparing the size of the cart
            assertEquals(1,sm.getCartProducts(i).size(),
                    "The output of the function did not match the expected value");

            // checking if the amount was added properly
            assertEquals(i+2,sm.getCartProducts(i).get(products[i]),
                    "The output of the function did not match the expected value");

            // checking if the inventory removed that amount
            assertEquals(expected[i],sm.checkStock(products[i]),
                    "The output of the function did not match the expected value");
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
                    "The output of the function did not match the expected value");
            if (sm.getCartProducts(i).size()!=0){
                assertEquals(i+2,sm.getCartProducts(i).get(products[i]));
            }
        }
    }

    /**
     * Testing the addToCart() method on the StoreManager with an item that is not in the inventory
     */
    @Test
    @Order(6)
    public void testAddToCartProductNotThere() {
        Product newProduct = new Product("cookie", 34, 2.99);

        // checks the output of the addToCart method
        assertEquals(false, sm.addToCart(newProduct.getName(),1,0));

        // comparing the size of the cart (it should remain 1)
        assertEquals(1,sm.getCartProducts(0).size(),
                "The output of the function did not match the expected value");

        // checking if the amount was added properly
        assertEquals(null,sm.getCartProducts(0).get(newProduct),
                "The output of the function did not match the expected value");
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
            assertEquals(false, sm.addToCart(products[3].getName(), illegalAmount[i], 0));

            // comparing the size of the cart (it should remain 1)
            assertEquals(1, sm.getCartProducts(0).size(),
                    "The output of the function did not match the expected value");

            // checking if the product was added to the cart (it should not be added)
            assertEquals(null,sm.getCartProducts(0).get(products[3]),
                    "The output of the function did not match the expected value");

            // checking if inventory was untouched
            assertEquals(8,sm.checkStock(products[3]),
                    "The output of the function did not match the expected value");
        }
    }

    /**
     * Testing the addToCart() method on the StoreManager with ordering the total amount of a product
     */
    @Test
    @Order(8)
    public void testAddToCartTotalAmount() {
        // checks the output of the addToCart method
        assertEquals(true, sm.addToCart(products[3].getName(),8,3));

        // comparing the size of the cart should be 1
        assertEquals(1,sm.getCartProducts(3).size(),
                "The output of the function did not match the expected value");

        // checking if the amount was added properly
        assertEquals(8,sm.getCartProducts(3).get(products[3]),
                "The output of the function did not match the expected value");


        // checking if the inventory removed that amount
        assertEquals(0,sm.checkStock(products[3]),
                "The output of the function did not match the expected value");
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
            assertEquals(true, sm.removeFromCart(products[i].getName(),1,i));

            // comparing the size of the cart
            assertEquals(1,sm.getCartProducts(i).size(),
                    "The output of the function did not match the expected value");

            // checking if the product removed from the cart properly
            assertEquals(i+1,sm.getCartProducts(i).get(products[i]),
                    "The output of the function did not match the expected value");

            // checking if the inventory got that removed amount back
            assertEquals(expected[i],sm.checkStock(products[i]),
                    "The output of the function did not match the expected value");
        }
    }


    /**
     * Testing the removeFromCart() method on the StoreManager with an item that is not in the cart
     */
    @Test
    @Order(10)
    public void testRemoveFromCartProductNotThere() {
        // checks the output of the addToCart method
        assertEquals(false, sm.removeFromCart(products[4].getName(),1,0));

        // comparing the size of the cart (size should not change)
        assertEquals(1,sm.getCartProducts(0).size(),
                "The output of the function did not match the expected value");

        // checking if the cart shows that it does not contain it
        assertEquals(null,sm.getCartProducts(0).get(products[4]),
                "The output of the function did not match the expected value");

        // checking if the inventory removed any of that product's amount
        assertEquals(8,sm.checkStock(products[4]),
                "The output of the function did not match the expected value");
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
            assertEquals(false, sm.removeFromCart(products[0].getName(), illegalAmount[i], 0));

            // comparing the size of the cart (it should remain 1)
            assertEquals(1, sm.getCartProducts(0).size(),
                    "The output of the function did not match the expected value");

            // checking if the amount was removed properly (should be untouched and 1 since we already removed one)
            assertEquals(1,sm.getCartProducts(0).get(products[0]),
                    "The output of the function did not match the expected value");
        }
    }


    /**
     * Testing the removeFromCart() method on the StoreManager with ordering the total amount of a product in cart
     */
    @Test
    @Order(12)
    public void testRemoveFromCartTotalAmount() {
        // checks the output of the addToCart method
        assertEquals(true, sm.removeFromCart(products[3].getName(),8,3));

        // comparing the size of the cart should be 0 since its only product was removed
        assertEquals(0,sm.getCartProducts(3).size(),
                "The output of the function did not match the expected value");

        // checking if the product was fully removed from the cart
        assertEquals(null,sm.getCartProducts(3).get(products[3]),
                "The output of the function did not match the expected value");

        // checking if the amount was returned to the inventory
        assertEquals(8,sm.checkStock(products[3]),
                "The output of the function did not match the expected value");
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
                    "The output of the function did not match the expected value" );
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
                    "The output of the function did not match the expected value");
        }

        // checking to see if the amount was returned to the inventory
        int expected [] = new int[]{20,10,2,8,8,8};
        for (int i =0; i < 6; i++){
            assertEquals(expected[i],sm.checkStock(products[i]),
                    "The output of the function did not match the expected value");
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
                    "The output of the function did not match the expected value");
            assertEquals(null,sm.getCartProducts(i),
                    "The output of the function did not match the expected value");
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
