//Bardia Parmoun
//101143006


import java.util.ArrayList;

public class Inventory{
    private ArrayList<Product> products; // Keeps track of all the products
    private ArrayList<Integer> stocks; // Keeps track of the number of products available


    public Inventory(){
        this.products = new ArrayList<Product>();
        this.stocks = new ArrayList<Integer>();
    }

    // Finds the index of a product in the arraylist of products given the id of the product
    private int findProductIndex(int id){
        for (int i=0; i<products.size(); i++){
            if (products.get(i).getId()==id){
                return i;
            }
        }
        return -1;
    }

    // Finds the product given its id
    public Product findProduct(int id){
        return products.get(findProductIndex(id));
    }

    // Gets the name of the product given its id
    public String getProductName(int id){
        return findProduct(id).getName();
    }

    // Gets the price of the product given its id
    public double getProductPrice(int id){
        return findProduct(id).getPrice();
    }

    // Find the number of stocks available for the product given its id
    public Integer getStock (int id) {
        int index = findProductIndex(id);
        if (index>=0) {
            return stocks.get(index);
        }
        //product does not exist
        return -1;
    }

    // Increases the number of products
    public void addStock (Product product, int newStock){
        int currentStock;
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

    // Removes a certain number stocks from a product
    public boolean removeStock (Product product, int newStock){
        int currentStock;
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