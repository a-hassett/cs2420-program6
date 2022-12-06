public class UnionFind {
    private static final int DEFAULT_SIZE = 121;
    private int[] parent;

    public UnionFind(){
        this(DEFAULT_SIZE);
    }
    public UnionFind(int size){
        parent = new int[size];
    }

    /**
     * Smart union by size
     * Bind two trees to the same parent and change the parent to count the size
     * @param first first item to unite
     * @param second second item to unite
     */
    public void union(int first, int second){
        int index1 = find(first);  // finds the parent(index) of first spot
        int index2 = find(second);

        if(index1 == index2){
            return;
        }
        if(parent[index1] < parent[index2]){
            parent[index1] += parent[index2]; // update the root to mark the size of the whole thing
            parent[index2] = index1;  // index 1 is new root of both
        } else{
            parent[index2] += parent[index1];
            parent[index1] = index2;
            int i = 0;
        }
    }

    /**
     * Find the parent of a certain spot and returns its index
     * Also acknowledges existence of a new item if not there before
     * @param index whose parent we want to find
     * @return index of the parent
     */
    public int find(int index){
        if(parent[index] == 0){
            parent[index] = -1;
            return index;
        }

        while (parent[index] > 0) {
            index = parent[index];
        }
        return index;
    }

    /**
     * Print the array holding the data to test unionFind
     */
    public void print(){
        StringBuilder array = new StringBuilder();
        array.append("[ ");
        for(int item: parent){
            array.append(item).append(", ");
        }
        array.append("]");
        System.out.println(array);
    }
}
