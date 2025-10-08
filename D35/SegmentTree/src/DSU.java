public class DSU {
    private final int n;
    private final int[] parent;
    private final int[] size;
    private int numSets;

    public DSU(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be >= 0");
        this.n = n;
        parent = new int[n];
        size = new int[n];
        numSets = n;
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    private void checkIndex(int i) {
        if (i < 0 || i >= n) {
            throw new IndexOutOfBoundsException("Index " + i + " out of bounds [0," + (n-1) + "]");
        }
    }

    /**
     * Iterative find with path halving (keeps tree shallow, avoids recursion).
     * Returns the root of i.
     */
    public int find(int i) {
        checkIndex(i);
        while (i != parent[i]) {
            // Path halving: make i point to its grandparent
            parent[i] = parent[parent[i]];
            i = parent[i];
        }
        return i;
    }

    /**
     * Union by size. Returns true if a merge happened, false if already in same set.
     */
    public boolean union(int i, int j) {
        checkIndex(i);
        checkIndex(j);
        int rootI = find(i);
        int rootJ = find(j);
        if (rootI == rootJ) return false;

        // attach smaller to larger
        if (size[rootI] < size[rootJ]) {
            parent[rootI] = rootJ;
            size[rootJ] += size[rootI];
        } else {
            parent[rootJ] = rootI;
            size[rootI] += size[rootJ];
        }
        numSets--;
        return true;
    }

    public boolean isConnected(int i, int j) {
        checkIndex(i);
        checkIndex(j);
        return find(i) == find(j);
    }

    public int getNumSets() {
        return numSets;
    }

    /**
     * Return the size of the component containing i.
     */
    public int sizeOf(int i) {
        checkIndex(i);
        return size[find(i)];
    }

    // Simple Test
    public static void main(String[] args) {
        DSU dsu = new DSU(5); // Elements 0..4

        System.out.println("Initial sets: " + dsu.getNumSets()); // 5

        dsu.union(0, 1);
        dsu.union(2, 3);
        System.out.println("Sets after {0,1}, {2,3} unions: " + dsu.getNumSets()); // 3

        System.out.println("Are 0 and 1 connected? " + dsu.isConnected(0, 1)); // true
        System.out.println("Are 0 and 2 connected? " + dsu.isConnected(0, 2)); // false

        dsu.union(0, 4);
        System.out.println("Are 1 and 4 connected? " + dsu.isConnected(1, 4)); // true
        System.out.println("Sets after {0,4} union: " + dsu.getNumSets()); // 2

        System.out.println("Size of component containing 0: " + dsu.sizeOf(0)); // 3
        System.out.println("Size of component containing 2: " + dsu.sizeOf(2)); // 2
    }
}
