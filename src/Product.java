//Bardia Parmoun
//101143006


public class Product{
    private final String name;
    private final int id;
    private final double price;

    /**
     *
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
     * @return
     */
    public String getName (){return name;}

    /**
     * getting the id of the product
     * @return
     */
    public int getId (){return id;}

    /**
     * getting the price of the product
     * @return
     */
    public double getPrice (){return price;}
}