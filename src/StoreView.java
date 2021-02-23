import java.util.Locale;
import java.util.Scanner;

public class StoreView {

    private StoreManager storeManager;
    private ShoppingCart cart;

    public StoreView() {
        storeManager  = new StoreManager();
        cart = new ShoppingCart(storeManager.generateCartId());
        main();
    }

    public void main() {
        Scanner sc = new Scanner(System.in);
        String input;

        System.out.println("--------Guy and Bardia, Pie and Media--------");
        System.out.println("Cart ID: " + cart.getId()); // necessary?


        System.out.print("-> ");
        input = sc.nextLine().toLowerCase();

        while (!input.equals("quit")) {

            if (input.equals("browse")) {
                this.displayItems();
            }
            sc.nextLine();
        }
    }

    public void displayItems() {
        String name;
        String stock;
        String price;
        String printMsg;

        System.out.println("Stock        Product        Price");
        System.out.println("_________________________________");
        for (int i = 0; i < storeManager.getAvailableProducts().size(); i++) {

            name = storeManager.getAvailableProducts().get(i).getName();
            stock = String.valueOf(storeManager.checkStock(storeManager.getAvailableProducts().get(i)));
            price = String.valueOf(storeManager.getAvailableProducts().get(i).getPrice());

            printMsg = String.format("%s" +"%" + (13-stock.length() + name.length()) +"s" + "%" +
                    (15 - name.length() + price.length()) + "s", stock,name, price);

            System.out.println(printMsg);

        }
    }

    public void addToCart(String product, int amount) {
    }

}
