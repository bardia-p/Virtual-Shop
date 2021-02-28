//Bardia Parmoun 101143006
//Guy Morgenshtern 101151430

import java.util.Scanner;
import java.util.HashMap;


/**
 * The class that assigns each user a store view and keeps track of all the storeviews
 * @author Guy Morgenshtern 101151430 & Bardia Parmoun 101143006
 * @version 1.0
 * @date 2020/02/27
 */
public class StoreView {
    /**
     * Keeps track of the main store using StoreManager
     */
    private StoreManager storeManager;

    /**
     * Keeps track of the cart for the user
     */
    private ShoppingCart cart;

    /**
     * The default constructor for storeview which creates an instance of a user
     * @param storeManager the manager of the store
     * @param cartId the id of the  cart
     */
    public StoreView(StoreManager storeManager, int cartId) {
        this.storeManager = storeManager;
        this.cart = new ShoppingCart(cartId);
    }

    /**
     * The main method that lets the users interact with the store
     * @param args
     */
    public static void main(String[] args){
        StoreManager sm = new StoreManager();

        // The number of allowed active users
        int activeSV = 3;


        StoreView[] customers = new StoreView[activeSV];

        for (int i=0; i<activeSV; i++){
            customers[i] = new StoreView(sm, sm.generateCartId());
        }


        Scanner sc = new Scanner(System.in);

        // Keeps track of all the active users

        while (activeSV>0){
            System.out.printf("Number of actve users: %d\n",activeSV);
            System.out.println("CHOOSE YOUR STOREVIEW>>>");
            int storeId = sc.nextInt();

            // Checks to see if the entered storeview is within the given range
            if (storeId >= 0 && storeId <customers.length){
                // Checks to see if the storeview is available
                if (customers[storeId]!=null){
                    if (customers[storeId].displayGUI()) {
                        customers[storeId] = null;
                        activeSV--;
                    }
                } else{
                    System.out.println("MAIN > ERROR > BAD CHOICE\nTHAT STOREVIEW WAS DEACTIVATED");
                }
            } else{
                System.out.println( String.format("MAIN > ERROR > BAD CHOICE\nPLEASE CHOOSE IN RANGE [%d, %d]", 0, customers.length - 1) );
            }
        }
        System.out.println("ALL STOREVIEWS DEACTIVATED");
    }

    /**
     * Calls the main UI for the program to allow for different items to be called
     * @return
     */
    public boolean displayGUI(){
        Scanner sc = new Scanner(System.in);
        String input;
        String product;
        int amount;



        input = "";
        while (!input.equals("quit")){
            System.out.println("--------Guy and Bardia, Pie and Media--------");
            System.out.println("Type 'help' for a list of commands");
            System.out.println("CART>>>"+cart.getTotalPrice());
            System.out.println("Enter a new command");
            input = sc.nextLine().toLowerCase();

            // Displays the possible commands that can be entered
            if (input.equals("help")){
                System.out.println("Type 'browse' to view all the items in the store");
                System.out.println("Type 'addtocart' to add new items to the cart");
                System.out.println("Type 'checkout' to checkout");
                System.out.println("Type 'quit' to quit");
            }

            // Displays all the items in the store
            else if (input.equals("browse")){
                System.out.println("-------------browse--------------");
                displayItems();

                // Give the program the option to switch to another user
                String chooseAnother = "";
                while (!chooseAnother.toLowerCase().equals("n")){
                    if (chooseAnother.toLowerCase().equals("y")) {
                        return false;
                    }
                    System.out.print("GO TO ANOTHER STOREVIEW? (y) >>> ");
                    chooseAnother = sc.nextLine().toLowerCase();
                }

            }

            // Checks out the user
            else if (input.equals("checkout")){
                storeManager.checkout(cart);
                return true;
            }

            // Adds a new item to the user cart
            else if (input.equals("addtocart")){
                System.out.println("-------------ADD--------------");
                displayItems();
                System.out.println("Enter the name of the product");
                product = sc.nextLine().toLowerCase();
                System.out.println("Enter the amount");
                amount = Integer.parseInt(sc.nextLine());

                // Makes sure the product and its stock exist in the inventory
                while (!addToCart(product,amount)){
                    System.out.println("Invalid product name or amount");
                    System.out.println("Enter the name of the product");
                    product = sc.nextLine().toLowerCase();
                    System.out.println("Enter the amount");
                    amount = Integer.parseInt(sc.nextLine());
                }
            }

            // Makes sure the product and its stock exist in the cart
            else if (input.equals("removefromcart")){
                System.out.println("Enter the name of the product");
                product = sc.nextLine().toLowerCase();
                System.out.println("Enter the amount");
                amount = Integer.parseInt(sc.nextLine());
                while (!removeFromCart(product,amount)){
                    System.out.println("Invalid product name or amount");
                    System.out.println("Enter the name of the product");
                    product = sc.nextLine().toLowerCase();
                    System.out.println("Enter the amount");
                    amount = Integer.parseInt(sc.nextLine());
                }
            }

            // quits the user
            else if (input.equals("quit")){
                emptyCart();
                storeManager.checkout(cart);
            }

            else{
                System.out.println("Invalid command was entered");
            }
        }

        return true;
    }

    /**
     * Displays all the available items in the store
     */
    public void displayItems() {
        String name;
        String stock;
        String price;
        String printMsg;

        System.out.println("Stock                 Product                Price");
        System.out.println("__________________________________________________");
        for (int i = 0; i < storeManager.getAvailableProducts().size(); i++) {

            name = storeManager.getAvailableProducts().get(i).getName();
            stock = String.valueOf(storeManager.checkStock(storeManager.getAvailableProducts().get(i)));
            price = String.valueOf(storeManager.getAvailableProducts().get(i).getPrice());

            printMsg = String.format("%s" +"%" + (20-stock.length() + name.length()) +"s" + "%" +
                    (20 - name.length() + price.length()) + "s", stock,name, price);

            System.out.println(printMsg);

        }
    }

    /**
     * Find the product item given its name
     * @param product the name of the product
     * @return the item of the product
     */
    private Product findProduct (String product){
        for (Product p: storeManager.getAvailableProducts()){
            if (p.getName().equals(product)){
                return p;
            }
        }
        return null;
    }

    /**
     * Adds a certain amount of a product to the cart
     * @param product the name of the product to add to the cart
     * @param amount the amount of the product to remove from the cart
     * @return true if the add process was successful
     */
    public boolean addToCart(String product, int amount) {
        Product p = findProduct(product);
        if (p!=null && amount<=storeManager.getInventory().getStock(p.getId()) && amount>0){
            if (storeManager.getInventory().removeStock(p, amount)) {
                cart.addStock(findProduct(product), amount);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes an item from the cart and its amount
     * @param product the name of the product to be removed
     * @param amount the amount of the product to remove
     * @return true if the removal process was sucessful
     */
    public boolean removeFromCart(String product, int amount) {
        Product p = findProduct(product);
        if (findProduct(product)!=null && amount<=cart.getStock(p.getId()) && amount>0){
            if (cart.removeStock(findProduct(product), amount)){
                storeManager.getInventory().addStock(p, amount);
                return true;
            }
        }
        return false;
    }

    /**
     * Empties the cart and removes all of its items in cases theuser quits
     */
    public void emptyCart(){
        for (Product p: cart.getProducts().keySet()){
            removeFromCart(p.getName(), cart.getProducts().get(p));
            cart.getProducts().remove(p);
        }
    }
}
