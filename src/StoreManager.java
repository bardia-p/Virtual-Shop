/**
 * This class manages the main interface of the store where the customers can check
 * how many of each object is available and make transactions
 * @name Guy Morgenshtern
 * @version 1.0
 * @date 2020/02/10
 */

public class StoreManager {
    private Inventory inv;

    /**
     * The constructor for the StoreManager class in case there are not inputs
     */
    public StoreManager() {
        this(new Inventory());
    }

    /**
     * The second constrcutror for the StoreManager class
     * If there is an inventory already in place
     * @param input_inv
     */
    public StoreManager(Inventory input_inv) {
        inv = input_inv;
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
     * @param orders
     * @return the total cost of the transaction (-1 if it was not successful)
     */
    public double makeTransaction(int[][] orders){
        double total = 0;

        // Checks to see if every part of the order is acceptable (i.e. there is enough of every product)
        for (int i=0; i<orders.length;i++){
            if (checkStock(inv.getProductInfo(orders[i][0]))<orders[i][1]){
                //transaction failed
                return -1;
            }
        }

        // Proceeds to calculate cost of the order and removing it from the stock
        for (int i=0; i<orders.length;i++){
            total +=inv.getProductInfo(orders[i][0]).getPrice()*orders[i][1];
            inv.removeStock(inv.getProductInfo(orders[i][0]),orders[i][1]);
        }
        return Math.round(total);
    }
}
