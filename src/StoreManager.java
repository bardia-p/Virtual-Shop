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
     * an id counter used to assign each cart a new id
     */
    private static int id_counter = 0;

    /**
     * An array used to keep track of the all the carts
     */
    private HashMap<Integer,ShoppingCart> carts ;


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
       carts = new HashMap<>();
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


        // Proceeds to calculate cost of the order and removing it from the stock
        for (Product product: orders.keySet()){
            System.out.printf("%s: %d = $%.2f\n", product.getName(), orders.get(product),
                    product.getPrice()*orders.get(product));

            total +=product.getPrice()*orders.get(product);
            orders.remove(product);
        }

        System.out.printf("%s = $%.2f\n", "Total", total);

        // Making the cart available again
        carts.remove(cart.getId());
        return Math.round(total);
    }

    /**
     * Generates a cart id based on the fire available cart
     * @return the generated cart id
     */
    public int assignNewCartID(){
        int newId = id_counter++;
        carts.put(newId, new ShoppingCart(newId));
        return newId;
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

    /**
     * Returns the cart for a given id
     * @param id the id for the cart to retrieve
     * @return the cart
     */
    public ShoppingCart getCart(int id){
        return carts.get(id);
    }
}
