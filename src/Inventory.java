//Bardia Parmoun
//101143006
//Guy Morgenshtern
//101151430

import java.util.ArrayList;

public class Inventory{
    private ArrayList<Product> products;
    private ArrayList<Integer> stocks;


    public Inventory(){
        this.products = new ArrayList<Product>();
        this.stocks = new ArrayList<Integer>();
    }

    public int findProductIndex(int id){
        for (int i=0; i<products.size(); i++){
            if (products.get(i).getId()==id){
                return i;
            }
        }
        return -1;
    }

    public Product findProduct(int id){
        return products.get(findProductIndex(id));
    }

    public String getProductName(int id){
        return findProduct(id).getName();
    }

    public double getProductPrice(int id){
        return findProduct(id).getPrice();
    }

    public Integer getStock (Integer id) {
        int index = findProductIndex(id);
        if (index>=0) {
            return stocks.get(index);
        }
        //product does not exist
        return -1;
    }

    public void addStock (Product product, int newStock){
        int currentStock;
        int index = products.indexOf(product);
        if (index != -1) {
            stocks.set(index,stocks.get(index)+newStock);
        }
        else{
            products.add(product);
            stocks.add(newStock);
        }
    }

    public boolean removeStock (Product product, int newStock){
        int currentStock;
        int index = products.indexOf(product);
        if (index != -1) {
            if (stocks.get(index)-newStock>=0){
                stocks.set(index,stocks.get(index)-newStock);
            }
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