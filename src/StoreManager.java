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
     * @param cartId cart that needs to be checked out
     * @return the total cost of the transaction (-1 if it was not successful)
     */
    public double checkout(int cartId){
        ShoppingCart cart = carts.get(cartId);
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
        return total;
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
     * Returns all the current products in a ShoppingCart
     * @param cartId the id of the cart to get products from
     * @return the products of the cart
     */
    public HashMap<Product, Integer> getCartProducts (int cartId){
        return carts.get(cartId).getProducts();
    }

    /**
     * Returns the total price in the cart
     * @param id the id of the cart
     * @return the total price of the cart
     */
    public double getCartTotalPrice(int id){
        return carts.get(id).getTotalPrice();
    }

    /**
     * Adds a certain amount of a product to the cart
     * @param product the name of the product to add to the cart
     * @param amount the amount of the product to remove from the cart
     * @param cartId the id of the cart to remove products from
     * @return true if the add process was successful
     */
    public boolean addToCart(String product, int amount, int cartId) {
        Product p = findProduct(product, cartId);
        ShoppingCart cart = carts.get(cartId);
        if (p!=null && amount<=inv.getStock(p.getId()) && amount>0){
            if (inv.removeStock(p, amount)) {
                cart.addStock(p, amount);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes an item from the cart and its amount
     * @param product the name of the product to be removed
     * @param amount the amount of the product to remove
     * @param cartId the id of the cart to remove products from
     * @return true if the removal process was sucessful
     */
    public boolean removeFromCart(String product, int amount, int cartId) {
        Product p = findProduct(product, cartId);
        ShoppingCart cart = carts.get(cartId);
        if (p!=null && amount<=cart.getStock(p.getId()) && amount>0){
            if (carts.get(cartId).removeStock(p, amount)){
                inv.addStock(p, amount);
                return true;
            }
        }
        return false;
    }

    /**
     * Empties the cart and removes all of its items in cases the user quits
     * @param cartId the id of the carts to empty
     */
    public void emptyCart(int cartId){
        ShoppingCart cart = carts.get(cartId);
        for (Product p: cart.getProducts().keySet()){
            removeFromCart(p.getName(), cart.getProducts().get(p), cartId);
        }

        cart.getProducts().clear();
    }

    /**
     * Find the product item given its name
     * @param product the name of the product
     * @param cartId the id of the cart to look for the product for
     * @return the item of the product
     */
    private Product findProduct (String product, int cartId){
        //checks for the product in the inventory
        for (Product p: getAvailableProducts()){
            if (p.getName().equals(product)){
                return p;
            }
        }
        //checks for the product in the cart (in case the ordered all of something)
        for (Product p: getCartProducts(cartId).keySet()){
            if (p.getName().equals(product)){
                return p;
            }
        }
        return null;
    }
}
