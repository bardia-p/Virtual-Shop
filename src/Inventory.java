import java.util.ArrayList;

/**
 * Inventory class: Holds an inventory of available products and stock
 * @author Bardia Parmoun
 * @version 1.0
 * @date 2020/02/10
 */

public class Inventory{
    private ArrayList<Product> products; // Keeps track of all the products
    private ArrayList<Integer> stocks; // Keeps track of the number of products available

    /**
     * Constructor
     * initializes parallel arraylists
     * creates default products
     */
    public Inventory(){
        this.products = new ArrayList<>();
        this.stocks = new ArrayList<>();

        // making some default products
        Product p1 = new Product("milk", 123,2.99);
        Product p2 = new Product("juice", 456,3.99);
        Product p3 = new Product("cake", 789,4.99);

        this.addStock(p1,20);
        this.addStock(p2,10);
        this.addStock(p3,5);
    }

    /**
     * Finds the index of a product in the arraylist of products given the id of the product
     * @param id
     * @return int of product's index
     */
    private int findProductIndex(int id){
        for (int i=0; i<products.size(); i++){
            if (products.get(i).getId()==id){
                return i;
            }
        }
        return -1;
    }

    /**
     * Finds the product given its id
     * @param id
     * @return Product object
     */
    public Product getProductInfo(int id){
        if (findProductIndex(id)!=-1){
            return products.get(findProductIndex(id));
        }
        return null;
    }

    /**
     * Find the number of stocks available for the product given its id
     * @param id
     * @return int of product's stock
     */
    public int getStock (int id) {
        int index = findProductIndex(id);
        if (index>=0) {
            return stocks.get(index);
        }
        //product does not exist
        return -1;
    }

    /**
     * Increases the number of products
     * @param product
     * @param newStock
     */
    public void addStock (Product product, int newStock){
        int index = products.indexOf(product);

        // If the product already exists it adds to the existing value
        if (index != -1) {
            stocks.set(index,stocks.get(index)+newStock);
        }

        // If this is a new product it adds it to both the products and stocks arraylists
        else{
            products.add(product);
            stocks.add(newStock);
        }
    }

    /**
     * Removes a certain number stocks from a product
     * @param product
     * @param newStock
     * @return boolean if removeStock was successful
     */
    public boolean removeStock (Product product, int newStock){
        int index = products.indexOf(product);

        // If the product already exists it removes from the existing values
        if (index != -1) {
            if (stocks.get(index)-newStock>=0){
                stocks.set(index,stocks.get(index)-newStock);
            }

            // If the final value is less than 0 it just sets it to 0
            else{
                stocks.set(index,0);
            }
            return true;
        }

        else{
            //product does not exist
            return false;
        }
    }
}