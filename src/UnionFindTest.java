import java.util.ArrayList;

public class UnionFindTest {
    public static void main(String[] args){
        //write comprehensive testing

        // it's working!!
        UnionFind uf = new UnionFind(20);
        uf.union(2,3);  // 3 becomes root [2,3]
        System.out.println("Union of 2 and 3 should give 3: " + uf.find(2) + " " + uf.find(3));
        uf.union(5,6);  // 6 becomes root [5,6]
        System.out.println("Union of 5 and 6 should give 6: " + uf.find(5) + " " + uf.find(6));
        uf.union(10,8);  // 8 becomes root [8,10]
        System.out.println("Union of 8 and 10 should give 8: " + uf.find(8) + " " + uf.find(10));
        uf.union(2,10);  // combines trees of root 3 and root 8 to make 8 new root [8,3,2,10]
        System.out.println("Union of 2 and 10 should give 8: " + uf.find(2) + " " + uf.find(3) + " " + uf.find(8) + " " + uf.find(10));
        uf.print();

        System.out.println("\nFinding brand new item (1) should return itself as a lonely tree: " + uf.find(1));
        System.out.println("Another new item (11): " + uf.find(11));
        uf.print();

        System.out.println("\nShould not add anything bigger than the size of the array which I've set to be 20");
        try{
            System.out.println("What happens when I try to add 30: " + uf.find(30));
        } catch(IndexOutOfBoundsException e){
            System.out.println("!! hit an index out of bounds error !!");
        }

        UnionFind uf2 = new UnionFind();
        System.out.println("\nI made a new UnionFind object with the default size of 121 items");
        uf2.union(50, 51);
        uf2.union(21, 22);
        uf2.print();
    }
}
