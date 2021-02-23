//Bardia Parmoun 101143006
//Guy Morgenshtern 101151430

public class ShoppingCart extends Inventory {
    private int cartId;

    public ShoppingCart (int cartId) {
        super();
        this.cartId = cartId;
    }

    public void setId(int id){
        this.cartId = id;
    }

    public int getId(){
        return this.cartId ;
    }

}
