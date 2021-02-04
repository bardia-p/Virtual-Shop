//Bardia Parmoun
//101143006
//Guy Morgenshtern
//101151430

public class StoreManager {
    private Inventory inv;

    public StoreManager() {
        inv = new Inventory();
    }


    // If there is an inventory already in place
    public StoreManager(Inventory input_inv) {
        inv = input_inv;
    }

    // Checks the number of stocks available of a given product given its object
    public Integer checkStock (Product product){
        return inv.getStock(product.getId());
    }

    // Takes a 2D array of orders in the form of [[product1, number1],...]] and applies the transaction
    public double transaction(int[][] orders){
        double total = 0;

        // Checks to see if every part of the order is acceptable (i.e. there is enough of every product)
        for (int i=0; i<orders.length;i++){
            if (inv.getStock(orders[i][0])<orders[i][1]){
                //transaction failed
                return -1;
            }
        }

        // Proceeds to calculate cost of the order and removing it from the stock
        for (int i=0; i<orders.length;i++){
            total +=inv.getProductPrice(orders[i][0])*orders[i][1];
            inv.removeStock(inv.findProduct(orders[i][0]),orders[i][1]);
        }
        return Math.round(total);
    }
}
