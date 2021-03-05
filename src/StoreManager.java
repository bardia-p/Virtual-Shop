//Bardia Parmoun 101143006
//Guy Morgenshtern 101151430

import java.util.HashMap;
import java.util.ArrayList;

/**
 * This class manages the main interface of the store where the customers can check
 * how many of each object is available and make transactions
 * @author Guy Morgenshtern 101151430
 * @version 2.0
 * @date 2020/02/27
 */

public class StoreManager {
    /**
     * The main inventory for the  store to keep track of all the products
     */
    private Inventory inv;

    /**
     * A constant used to keep track of the total number of carts that can be one at the same time in the store
     */
    private final int TOTALCARTS = 100;

    /**
     * An array used to keep track of the all the carts to see if there are any new ones available to assign them to new
     * customers
     */
    private boolean carts[] ;


    /**
     * The constructor for the StoreManager class in case there are not inputs
     */
    public StoreManager() {
        this(new Inventory());
    }

    /**
     * The second constrcutror for the StoreManager class
     * If there is an inventory already in place
     * @param inv the inventory that is already made
     */
    public StoreManager(Inventory inv) {
        this.inv = inv;
        carts = new boolean[TOTALCARTS];
        initializeCartIds();
    }

    /**
     * Initializing all the carts to be free so they can be assigned to new users
     */
    private void initializeCartIds (){
        for (int i =0; i<TOTALCARTS; i++){
            carts[i] = true;
        }
    }


    /**
     * Returns the main inventory for the store
     * @return the  inventory of the store
     */
    public Inventory getInventory() {
        return inv;
    }

    /**
     * Checks the number of stocks available of a given product given its object
     * @param product the prodcut to check its stock for
     * @return the number of productors available and 0 if the product does not exist
     */
    public int checkStock (Product product){
        if (product!=null){
            return inv.getStock(product.getId());
        }
        return 0;
    }

    /**
     * Takes a 2D array of orders in the form of [[product1, number1],...]] and applies the transaction
     * @param cart cart that needs to be checked out
     * @return the total cost of the transaction (-1 if it was not successful)
     */
    public double checkout(ShoppingCart cart){
        double total = 0;
        HashMap<Product, Integer> orders= cart.getProducts();

        /*
        // Checks to see if every part of the order is acceptable (i.e. there is enough of every product)
        for (Product product: orders.keySet()){
            if (checkStock(product)<orders.get(product)){
                // transaction failed
                return -1;
            }
        }*/

        // Proceeds to calculate cost of the order and removing it from the stock
        for (Product product: orders.keySet()){
            System.out.printf("%s: %d = $%.2f\n", product.getName(), orders.get(product),
                    product.getPrice()*orders.get(product));

            total +=product.getPrice()*orders.get(product);
            orders.remove(product);
        }

        System.out.printf("%s = $%.2f\n", "Total", total);

        // Making the cart available again
        carts[cart.getId()] = true;
        return Math.round(total);
    }

    /**
     * Generates a cart id based on the fire available cart
     * @return the generated cart id
     */
    public int generateCartId (){
        for (int i=0; i<TOTALCARTS; i++){
            if (carts[i]){
                return i;
            }
        }

        // there are no free carts
        return -1;
    }

    /**
     * Returns an arraylist of all the available products (products that have a stock of more than 0)
     * @return an arraylist of the available products
     */
    public ArrayList<Product> getAvailableProducts () {
        ArrayList<Product> availableProducts = new ArrayList<>();

        for (Product product : inv.getProducts().keySet()) {
            if (checkStock(product)>0){
                availableProducts.add(product);
            }
        }

        return availableProducts;
    }
}
