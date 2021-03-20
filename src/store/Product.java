//Bardia Parmoun 101143006
//Guy Morgenshtern 101151430

package store;

/**
 * This class defines the basic type of the store which is a product. The attributes of
 * a product are final and cann be changed
 * @author Bardia Parmoun 101143006
 * @version 2.0
 * @date 2020/02/27
 */

public class Product{
    /**
     * Keeps track of the name of the product
     */
    private final String NAME;

    /**
     * Keeps track of the ID of the product
     */
    private final int ID;

    /**
     * Keeps track of the price of the product
     */
    private final double PRICE;

    /**
     * The constructor for the product class
     * @param name the name of the product
     * @param id the id of the product
     * @param price the price of the product
     */
    public Product(String name, int id, double price){
        this.NAME = name;
        this.ID = id;
        this.PRICE = price;
    }

    /**
     * getting the name of the product
     * @return the name of the product
     */
    public String getName (){return NAME;}

    /**
     * getting the id of the product
     * @return the id of the product
     */
    public int getId (){return ID;}

    /**
     * getting the price of the product
     * @return the price of the product
     */
    public double getPrice (){return PRICE;}
}