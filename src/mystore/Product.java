package mystore;

import java.util.Objects;

/**
 * This class defines the basic type of the store which is a product. The attributes of
 * a product are final and cann be changed
 * @author Bardia Parmoun 101143006
 * @version 3.0
 * @date 2020/03/21
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
     * The name of the image for the product
     */
    private final String IMAGEPATH;

    /**
     * The constructor for the product class
     * @param name the name of the product
     * @param id the id of the product
     * @param price the price of the product
     */
    public Product(String name, int id, double price, String imagePath){
        this.NAME = name;
        this.ID = id;
        this.PRICE = price;
        this.IMAGEPATH = imagePath;
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

    /**
     * getting the image name of the product
     * @return the image name of the product
     */
    public String getImagePath (){return IMAGEPATH;}

    /**
     * Overides the equals method in product
     * @param o the object to compare product to
     * @return true if two products are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return ID == product.ID &&
                Double.compare(product.PRICE, PRICE) == 0 &&
                Objects.equals(NAME, product.NAME);
    }

    /**
     * Overrides the hashcode method for product
     * @return the hashcode of the product
     */
    @Override
    public int hashCode() {
        return Objects.hash(NAME, ID, PRICE);
    }

}