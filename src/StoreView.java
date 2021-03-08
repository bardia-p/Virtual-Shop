//Bardia Parmoun 101143006
//Guy Morgenshtern 101151430

import java.util.Scanner;


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
     * Keeps track of the cartid
     */
    private int cartId;

    /**
     * The default constructor for storeview which creates an instance of a user
     * @param storeManager the manager of the store
     * @param cartId the id of the  cart
     */
    public StoreView(StoreManager storeManager, int cartId) {
        this.storeManager = storeManager;
        this.cartId = cartId;
    }

    /**
     * The main method that lets the users interact with the store
     * @param args used to get arguments when running the program (not used in this program)
     */
    public static void main(String[] args){
        StoreManager sm = new StoreManager();

        // The number of allowed active users
        int activeSV = 3;


        StoreView[] customers = new StoreView[activeSV];

        for (int i=0; i<activeSV; i++){
            customers[i] = new StoreView(sm, sm.assignNewCartID());
        }


        Scanner sc = new Scanner(System.in);

        // Keeps track of all the active users

        while (activeSV>0){
            System.out.printf("Number of actve users: %d\n",activeSV);
            System.out.println("CHOOSE YOUR STOREVIEW>>>");
            int storeId = Integer.parseInt(sc.nextLine());

            // Checks to see if the entered storeview is within the given range
            if (storeId >= 0 && storeId <customers.length){
                // Checks to see if the storeview is available
                if (customers[storeId]!=null){
                    String userChoice = "n";
                    while (!userChoice.equals("y")){
                        if (!customers[storeId].displayGUI()) {
                            customers[storeId] = null;
                            activeSV--;
                            break;
                        }
                        System.out.print("GO TO ANOTHER STOREVIEW? (y) >>> ");
                        userChoice = sc.nextLine().toLowerCase();
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
     * @return true if the UI finished successfully
     */
    public boolean displayGUI(){
        Scanner sc = new Scanner(System.in);
        String input;
        String product;
        int amount;



        input = "";
        System.out.println("--------Guy and Bardia, Pie and Media--------");
        System.out.println("Type 'help' for a list of commands");
        System.out.println("CART>>>"+storeManager.getCart(cartId).getTotalPrice());
        System.out.println("Enter a new command");
        input = sc.nextLine().toLowerCase();

        // Displays the possible commands that can be entered
        if (input.equals("help")){
            System.out.println("Type 'browse' to view all the items in the store");
            System.out.println("Type 'addtocart' to add new items to the cart");
            System.out.println("Type 'checkout' to checkout");
            System.out.println("Type 'quit' to quit");
            return true;
        }

        // Displays all the items in the store
        else if (input.equals("browse")){
            System.out.println("-------------browse--------------");
            displayItems();
            return true;
        }

        // Checks out the user
        else if (input.equals("checkout")){
            storeManager.checkout(storeManager.getCart(cartId));
            return false;
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
            System.out.println("CART>>>"+storeManager.getCart(cartId).getTotalPrice());
            return true;
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
            System.out.println("CART>>>"+storeManager.getCart(cartId).getTotalPrice());
            return true;
        }

        // quits the user
        else if (input.equals("quit")){
            emptyCart();
            storeManager.checkout(storeManager.getCart(cartId));
            return false;
        }

        else{
            System.out.println("Invalid command was entered");
            return true;
        }
    }

    /**
     * Displays all the available items in the store
     */
    public void displayItems() {
        String name;
        String stock;
        String price;
        String printMsg;

        System.out.println("Stock             Product                Price");
        System.out.println("__________________________________________________");
        for (int i = 0; i < storeManager.getAvailableProducts().size(); i++) {

            name = storeManager.getAvailableProducts().get(i).getName();
            stock = String.valueOf(storeManager.checkStock(storeManager.getAvailableProducts().get(i)));
            price = String.valueOf(storeManager.getAvailableProducts().get(i).getPrice());

            printMsg = String.format("%s" +"%" + (15-stock.length() + name.length()) +"s" + "%" +
                    (25 - name.length() + price.length()) + "s", stock,name, price);

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
                storeManager.getCart(cartId).addStock(p, amount);
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
        if (p!=null && amount<=storeManager.getCart(cartId).getStock(p.getId()) && amount>0){
            if (storeManager.getCart(cartId).removeStock(p, amount)){
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
        for (Product p: storeManager.getCart(cartId).getProducts().keySet()){
            removeFromCart(p.getName(), storeManager.getCart(cartId).getProducts().get(p));
            storeManager.getCart(cartId).getProducts().remove(p);
        }
    }
}
