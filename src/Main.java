//Bardia Parmoun
//101143006

public class Main {
    public static void main(String[] args) {

        // you can write some test code here
        //creating a store manager
        StoreManager store1 = new StoreManager();
        Product p1 = new Product("milk", 123,2.99);
        Product p2 = new Product("juice", 456,3.99);
        Product p3 = new Product("cake", 789,4.99);
        Product p4 = new Product("apple", 000,1.99);

        store1.inv.addStock(p1,20);
        store1.inv.addStock(p2,10);
        store1.inv.addStock(p3,5);

        System.out.println("Number of this product available: " + store1.checkStock(p1));
        System.out.println("Number of this product available: " + store1.checkStock(p4));

        int[][] orders1 ={{p1.getId(),15},{p2.getId(),6},{p3.getId(),3}};
        System.out.println("The cost of the transaction? $"+ store1.transaction(orders1));

        int[][] orders2 ={{p1.getId(),6},{p2.getId(),1},{p3.getId(),2}};
        System.out.println("The cost of the transaction? $"+ store1.transaction(orders2));

        int[][] orders3 ={{p4.getId(),15}};
        System.out.println("The cost of the transaction? $"+ store1.transaction(orders3));

        store1.inv.addStock(p4,30);
        store1.inv.addStock(p1,10);

        int[][] orders4 ={{p4.getId(),15},{p1.getId(),5}};
        System.out.println("The cost of the transaction? $"+ store1.transaction(orders4));
    }
}