//Bardia Parmoun
//101143006
//Guy Morgenshtern
//101151430

/*
- Create a new Inventory object upon object creation (i.e., when a StoreManager object is created).
- Have functionality to check how much stock of a given Product is in the Inventory.
- Have functionality to process a transaction given an Array of Product information. For example, imagine that the content of a user’s shopping cart is as follows: “[[productID1, quantity], [productID2, quantity], [productID3, quantity]]”. Given each productID, your method should:
o Check that the desired quantity exists in the Inventory and return the total for all of the Products.
o If there is insufficient quantity of any of the products, your method should return some indication of this failure. It could be, for example -1.
o Remember to subtract the quantities from the Inventory stock if the transaction is successful.
 */
import java.util.HashMap;

public class StoreManager {
    private Inventory inv;

    public StoreManager() {
        inv = new Inventory();
    }

    public StoreManager(Inventory input_inv) {
        inv = input_inv;
    }

    public Integer checkStock (Product product){
        return inv.getStock(product.getId());
    }

    public double transaction(int[][] orders){
        double total = 0;
        for (int i=0; i<orders.length;i++){
            if (inv.getStock(orders[i][0])<orders[i][1]){
                //transaction failed
                return -1;
            }
            total +=inv.getProductPrice(orders[i][0])*orders[i][1];
        }

        for (int i=0; i<orders.length;i++){
            total +=inv.getProductPrice(orders[i][0])*orders[i][1];
            inv.removeStock(inv.findProduct(orders[i][0]),orders[i][1]);
        }
        return Math.round(total);
    }
}
