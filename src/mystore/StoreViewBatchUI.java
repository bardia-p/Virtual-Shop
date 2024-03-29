package mystore;

import java.util.Scanner;

/**
 * The class that assigns each user a store view and keeps track of all the storeviews
 * @author Guy Morgenshtern 101151430 & Bardia Parmoun 101143006
 * @version 2.0
 * @date 2020/03/20
 */
public class StoreViewBatchUI {
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
    public StoreViewBatchUI(StoreManager storeManager, int cartId) {
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

        StoreViewBatchUI[] customers = new StoreViewBatchUI[activeSV];

        for (int i=0; i<activeSV; i++){
            customers[i] = new StoreViewBatchUI(sm, sm.assignNewCartID());
        }

        Scanner sc = new Scanner(System.in);

        int storeId = -1;

        // Keeps track of all the active users
        while (activeSV>0){
            System.out.printf("Number of actve users: %d\n",activeSV);
            System.out.println("CHOOSE YOUR STOREVIEW>>>");
            String availableStores = "THE AVAILABLE STOREVIEWS ARE: ";

            for (int i = 0; i <customers.length; i++){
                if (customers[i]!=null){
                    availableStores+=i+" ";
                }
            }
            System.out.println(availableStores);

            try {
                storeId = Integer.parseInt(sc.nextLine());
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

            // If the input is not integer
            catch (NumberFormatException e){
                System.out.println("MAIN > ERROR > BAD CHOICE");
                System.out.println("Input must be an integer value");
                storeId = -1;
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

        System.out.println("--------Guy and Bardia, Pie and Media--------");
        System.out.println("Type 'help' for a list of commands");
        System.out.printf("CART>>> $%.2f\n", storeManager.getCartTotalPrice(cartId));
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
            for (Product product: storeManager.getCartProducts(cartId).keySet()){
                int amount = storeManager.getCartProducts(cartId).get(product);
                System.out.printf("%s: %d = $%.2f\n", product.getName(), amount, product.getPrice()*amount);
            }

            System.out.printf("%s = $%.2f\n", "Total", storeManager.checkout(cartId));
            return false;
        }

        // Adds a new item to the user cart
        else if (input.equals("addtocart")){
            System.out.println("-------------ADD--------------");
            displayItems();
            addToCartUI();
            return true;
        }

        // Makes sure the product and its stock exist in the cart
        else if (input.equals("removefromcart")){
            System.out.println("--------------REMOVE--------------");
            displayCart();
            removeFromCartUI();
            return true;
        }

        // quits the user
        else if (input.equals("quit")){
            storeManager.emptyCart(cartId);
            System.out.printf("%s = $%.2f\n", "Total", storeManager.checkout(cartId));
            return false;
        }

        // command does not exist
        else{
            System.out.println("Invalid command was entered");
            return true;
        }
    }

    /**
     * Displays the removefromcart interface where the user is prompted to choose a product and a proper amount which
     * is then removed from the cart
     */
    private void removeFromCartUI(){
        Scanner sc = new Scanner(System.in);
        String product;
        int amount;

        if (storeManager.getCartProducts(cartId).size()!=0) {
            System.out.println("Enter the name of the product (type 'back' to exit this mode)");
            product = sc.nextLine().toLowerCase();

            if (!product.equals("back")) {
                System.out.println("Enter the amount");

                try {
                    amount = Integer.parseInt(sc.nextLine());
                }
                catch (NumberFormatException e){
                    System.out.println("REMOVEFROMCART > ERROR > BAD CHOICE");
                    System.out.println("Input must be an integer value");
                    amount = -1;
                }

                // Makes sure the product and its stock exist in the cart
                int numTries = 2;
                while (!storeManager.removeFromCart(product, amount, cartId)) {
                    if (numTries==0){
                        System.out.println("You ran out of tries");
                        break;
                    }
                    System.out.println("Invalid product name or amount");
                    System.out.printf("You have %d tries left\n", numTries);

                    System.out.println("Enter the name of the product (type 'back' to exit this mode)");

                    product = sc.nextLine().toLowerCase();

                    if (product.equals("back")) {
                        break;
                    }

                    System.out.println("Enter the amount");

                    try {
                        amount = Integer.parseInt(sc.nextLine());
                    }
                    catch (NumberFormatException  e){
                        System.out.println("REMOVEFROMCART > ERROR > BAD CHOICE");
                        System.out.println("Input must be an integer value");
                        amount = -1;
                    }

                    numTries--;
                }
            }
            System.out.printf("CART>>> $%.2f\n", storeManager.getCartTotalPrice(cartId));
        }
    }

    /**
     * Displays the addToCart interface where the user is prompted to choose a product and a proper amount which is then
     * added to the cart
     */
    public void addToCartUI(){
        Scanner sc = new Scanner(System.in);
        String product;
        int amount;

        System.out.println("Enter the name of the product (type 'back' to exit this mode)");
        product = sc.nextLine().toLowerCase();

        if (!product.equals("back")) {
            System.out.println("Enter the amount");

            try {
                amount = Integer.parseInt(sc.nextLine());
            }
            catch (NumberFormatException e){
                System.out.println("ADDTOCART > ERROR > BAD CHOICE");
                System.out.println("Input must be an integer value");
                amount = -1;
            }

            // Makes sure the product and its stock exist in the inventory
            int numTries = 2;
            while (!storeManager.addToCart(product, amount, cartId)) {
                if (numTries==0){
                    System.out.println("You ran out of tries");
                    break;
                }
                System.out.println("Invalid product name or amount");
                System.out.printf("You have %d tries left\n", numTries);

                System.out.println("Enter the name of the product (type 'back' to exit this mode)");
                product = sc.nextLine().toLowerCase();

                if (product.equals("back")) {
                    break;
                }

                System.out.println("Enter the amount");

                try {
                    amount = Integer.parseInt(sc.nextLine());
                }
                catch (NumberFormatException e){
                    System.out.println("ADDTOCART > ERROR > BAD CHOICE");
                    System.out.println("Input must be an integer value");
                    amount = -1;
                }

                numTries--;
            }
        }
        System.out.printf("CART>>> $%.2f\n", storeManager.getCartTotalPrice(cartId));
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
     * Displays all the available items in the cart
     */
    public void displayCart() {
        String name;
        String stock;
        String price;
        String printMsg;

        boolean isCartEmpty = true;

        System.out.println("Stock             Product                Price");
        System.out.println("__________________________________________________");
        for (Product p : storeManager.getCartProducts(cartId).keySet()) {
            if (storeManager.getCartProducts(cartId).get(p)>0){
                isCartEmpty = false;
                name = p.getName();
                stock = String.valueOf(storeManager.getCartProducts(cartId).get(p));
                price = String.valueOf(p.getPrice());

                printMsg = String.format("%s" + "%" + (15 - stock.length() + name.length()) + "s" + "%" +
                        (25 - name.length() + price.length()) + "s", stock, name, price);

                System.out.println(printMsg);
            }
        }

        if (isCartEmpty){
            System.out.println("Cart is empty");
        }
    }
}