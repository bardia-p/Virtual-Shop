//Bardia Parmoun
//101143006
//Guy Morgenshtern
//101151430

public class Product{
    private final String name;
    private final int id;
    private double price;

    public Product(String name, int id, double price){
        this.name = name;
        this.id = id;
        this.price = price;
    }

    // getting the name of the product
    public String getName (){return name;}

    // getting the id of the product
    public int getId (){return id;}

    // getting the price of the product
    public double getPrice (){return price;}
}