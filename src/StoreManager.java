//Bardia Parmoun 101143006
//Guy Morgenshtern 101151430

import java.util.HashMap;
import java.util.ArrayList;

/**
 * This class manages the main interface of the store where the customers can check
 * how many of each object is available and make transactions
 * @name Guy Morgenshtern
 * @version 1.0
 * @date 2020/02/10
 */

public class StoreManager {
    private Inventory inv;
    private final int TOTALCARTS = 100;
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
     * @param inv
     */
    public StoreManager(Inventory inv) {

        this.inv = inv;
        carts = new boolean[TOTALCARTS];
        initializeCartIds();
    }

    private void initializeCartIds (){
        for (int i =0; i<TOTALCARTS; i++){
            carts[i] = true;
        }
    }

    /**
     * Checks the number of stocks available of a given product given its object
     * @param product
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
     * @param cart
     * @return the total cost of the transaction (-1 if it was not successful)
     */
    public double checkout(ShoppingCart cart){
        double total = 0;
        HashMap<Product, Integer> orders= cart.getProducts();
        int size = orders.size();

        // Checks to see if every part of the order is acceptable (i.e. there is enough of every product)
        for (Product product: orders.keySet()){
            if (checkStock(product)<orders.get(product)){
                //transaction failed
                return -1;
            }
        }

        // Proceeds to calculate cost of the order and removing it from the stock
        for (Product product: orders.keySet()){
            total +=product.getPrice()*orders.get(product);
            inv.removeStock(product,orders.get(product));
        }
        return Math.round(total);
    }

    public int generateCartId (){
        for (int i=0; i<TOTALCARTS; i++){
            if (carts[i]){
                return i;
            }
        }

        // there are not free carts
        return -1;
    }

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
