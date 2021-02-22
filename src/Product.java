//Bardia Parmoun 101143006
//Guy Morgenshtern 101151430

/**
 * This class defines the basic type of the store which is a product. The attributes of
 * a product are final and cann be changed
 * @author Bardia Parmoun
 * @version 1.0
 * @date 2020/02/10
 */

public class Product{
    private final String NAME;
    private final int ID;
    private final double PRICE;

    /**
     * The constructor for the product class
     * @param name
     * @param id
     * @param price
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