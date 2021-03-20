//Bardia Parmoun
// 101143006

package storetest;


import store.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
     * keeps track of the inventory used in the storemanager
     */
    private static HashMap<String, Integer> expectedInv;

    /**
     * Initializes all the polynomials that will be used during the testing
     */
    @BeforeAll
    public static void init() {
        sm = new StoreManager();
        carts = new ShoppingCart[3];
        expectedInv = new HashMap<>();
        expectedInv.put("apple pie",20);
        expectedInv.put("blueberry pie",10);
        expectedInv.put("cake",5);
        expectedInv.put("cooking book",8);
        expectedInv.put("baking recipes",8);
        expectedInv.put("cooking tutorials",8);
    }

    /**
     * Used to the convert the key of the hashmap with product to string for easier comparsion
     * @return the new hashamp
     */
    private HashMap<String, Integer> convertKeyToString(HashMap<Product, Integer> original){
        HashMap<String, Integer> output = new HashMap<>();
        for (Product p: original.keySet()){
            output.put(p.getName(), original.get(p));
        }
        return output;
    }
    
    /**
     * Testing the getAvailableProduct() method on the StoreManager
     */
    @Test
    @Order(1)
    public void testGetAvailableProducts() {
        List<String> expected = Arrays.asList("apple pie","blueberry pie", "cake", "cooking book",
                "baking recipes", "cooking tutorials");

        // comparing the size
        assertEquals(expected.size(),sm.getAvailableProducts().size(),
                "The output of the function did not mention the expected value");

        // comparing every element
        for (Product p: sm.getAvailableProducts()){
            assertTrue(expected.contains(p.getName()),
                    "The output of the function did not mention the expected value");
        }
    }

    /**
     * Testing the assignNewCartID() method on the StoreManager
     */
    @Test
    @Order(2)
    public void testAssignNewCartID() {
        int expected[] = new int[]{0,1,2};

        // comparing every element
        for (int i = 0; i<3; i++){
            carts[i] = new ShoppingCart(sm.assignNewCartID());
            assertEquals(expected[i],carts[i].getId(),
                    "The output of the function did not mention the expected value");
        }
    }

    /**
     * Testing the addToCart() method on the StoreManager
     */
    @Test
    @Order(3)
    public void testAddToCartNormal() {
        String products[] = new String[]{"cake", "apple pie", "blueberry pie"};

        for (int i = 0; i <3 ; i++){
            // checks the output of the addToCart method
            assertEquals(true, sm.addToCart(products[i],i+1,i));

            // converting the contents of the cart to string and int
            HashMap<String, Integer> actualCartProducts = convertKeyToString(sm.getCartProducts(i));

            // converting the contents of the inventory to string and int
            HashMap<String, Integer> actualInventoryProducts = convertKeyToString(sm.getInventoryProducts());

            // comparing the size of the cart
            assertEquals(1,sm.getCartProducts(i).size(),
                    "The output of the function did not mention the expected value");

            // checking if the product was added to the cart
            assertTrue(actualCartProducts.keySet().contains(products[i]),
                    "The output of the function did not mention the expected value");

            // checking if the amount was added properly
            assertEquals(i+1,actualCartProducts.get(products[i]),
                    "The output of the function did not mention the expected value");

            expectedInv.put(products[i],expectedInv.get(products[i])-i-1);

            // checking if the inventory removed that amount
            assertEquals(expectedInv.get(products[i]),actualInventoryProducts.get(products[i]),
                    "The output of the function did not mention the expected value");
        }
    }

    /**
     * Testing the addToCart() method on the StoreManager with an item that is not in the inventory
     */
    @Test
    @Order(4)
    public void testAddToCartProductNotThere() {
        // checks the output of the addToCart method
        assertEquals(false, sm.addToCart("cookie",1,0));

        // converting the contents of the cart to string and int
        HashMap<String, Integer> actualCartProducts = convertKeyToString(sm.getCartProducts(0));

        // comparing the size of the cart (it should remain 1)
        assertEquals(1,sm.getCartProducts(0).size(),
                "The output of the function did not mention the expected value");

        // checking if the product was added to the cart (it should not be addded)
        assertFalse(actualCartProducts.keySet().contains("cookie"),
                "The output of the function did not mention the expected value");

        // checking if the amount was added properly
        assertEquals(null,actualCartProducts.get("cookie"),
                "The output of the function did not mention the expected value");
    }

    /**
     * Testing the addToCart() method on the StoreManager with an illegal amount (more than total or -1)
     */
    @Test
    @Order(5)
    public void testAddToCartIllegalAmount() {
        // more than the total amount in the inventory, a negative number, ordering 0 products
        int illegalAmount[] = new int[]{10, -1, 0};

        for (int i =0; i<illegalAmount.length; i++) {
            // checks the output of the addToCart method
            assertEquals(false, sm.addToCart("cooking book", illegalAmount[i], 0));

            // converting the contents of the cart to string and int
            HashMap<String, Integer> actualCartProducts = convertKeyToString(sm.getCartProducts(0));

            // comparing the size of the cart (it should remain 1)
            assertEquals(1, sm.getCartProducts(0).size(),
                    "The output of the function did not mention the expected value");

            // checking if the product was added to the cart (it should not be added)
            assertFalse(actualCartProducts.keySet().contains("cooking book"),
                    "The output of the function did not mention the expected value");

            // checking if the amount was added properly
            assertEquals(null, actualCartProducts.get("cooking book"),
                    "The output of the function did not mention the expected value");
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
