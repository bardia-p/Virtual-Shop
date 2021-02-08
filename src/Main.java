//Guy Morgenshtern
//101151430

public class Main {
    public static void main(String[] args) {

        // you can write some test code here
        // Creating a store manager
        // StoreManager store = new StoreManager();
        // Product p = new Product("n",1,1.0);
        // System.out.println(store.makeTransaction(orders));
        // System.out.println(store.checkStock(p));

        Inventory inv = new Inventory();
        StoreManager store1 = new StoreManager(inv);

        Product p1 = new Product("milk", 123,2.99);
        Product p2 = new Product("juice", 456,3.99);
        Product p3 = new Product("cake", 789,4.99);
        Product p4 = new Product("apple", 000,1.99);

        inv.addStock(p1,20);
        inv.addStock(p2,10);
        inv.addStock(p3,5);

        System.out.println("Number of this product available: " + store1.checkStock(p1));
        System.out.println("Number of this product available: " + store1.checkStock(p4));

        int[][] orders1 ={{p1.getId(),15},{p2.getId(),6},{p3.getId(),3}};
        System.out.println("The cost of the transaction? $"+ store1.makeTransaction(orders1));

        int[][] orders2 ={{p1.getId(),5},{p2.getId(),1},{p3.getId(),2}};
        System.out.println("The cost of the transaction? $"+ store1.makeTransaction(orders2));

        int[][] orders3 ={{p4.getId(),15}};
        System.out.println("The cost of the transaction? $"+ store1.makeTransaction(orders3));

        inv.addStock(p4,30);
        inv.addStock(p1,10);

        int[][] orders4 ={{p4.getId(),15},{p1.getId(),5}};
        System.out.println("The cost of the transaction? $"+ store1.makeTransaction(orders4));

        System.out.println("Number of this product available: " + store1.checkStock(p1));
        System.out.println("Number of this product available: " + store1.checkStock(p2));
        System.out.println("Number of this product available: " + store1.checkStock(p3));
        System.out.println("Number of this product available: " + store1.checkStock(p4));
    }
}