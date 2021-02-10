/**
 * This class defines the basic type of the store which is a product. The attributes of
 * a product are final and cann be changed
 * @author Bardia Parmoun
 * @version 1.0
 * @date 2020/02/10
 */

public class Product{
    private final String name;
    private final int id;
    private final double price;

    /**
     * The constructor for the product class
     * @param name
     * @param id
     * @param price
     */
    public Product(String name, int id, double price){
        this.name = name;
        this.id = id;
        this.price = price;
    }

    /**
     * getting the name of the product
     * @return the name of the product
     */
    public String getName (){return name;}

    /**
     * getting the id of the product
     * @return the id of the product
     */
    public int getId (){return id;}

    /**
     * getting the price of the product
     * @return the price of the product
     */
    public double getPrice (){return price;}
}