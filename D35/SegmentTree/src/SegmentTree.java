import java.util.function.BinaryOperator;
import java.util.Arrays;

// Recursive Segment Tree (sum / lazy)
public class SegmentTree {
    private final int n;
    private final long[] tree;
    private final long[] lazy; // memos
    private final long[] source;
    private final long neturalValue;
    private final BinaryOperator<Long> merger;

    public SegmentTree(long[] source, long neturalValue, BinaryOperator<Long> merger) {
        this.n = source.length;
        this.source = source;
        this.neturalValue = neturalValue;
        this.merger = merger;
        this.tree = new long[4 * n];
        this.lazy = new long[4 * n];
        // corrected: root node = 0, range = [0, n-1]
        build(0, 0, n - 1);
    }

    private void build(int node, int start, int end) {
        if (start == end){
            tree[node] = source[start];
            return;
        }
        int mid = start + (end - start) / 2;
        build(2 * node + 1, start, mid);
        build(2 * node + 2, mid + 1, end);
        tree[node] = merger.apply(tree[2 * node + 1], tree[2 * node + 2]);
    }

    private void push(int node, int start, int end){
        if (lazy[node] == 0) return;

        tree[node] += lazy[node] * (end - start + 1);

        if (start != end){
            lazy[2 * node + 1] += lazy[node];
            lazy[2 * node + 2] += lazy[node];
        }
        lazy[node] = 0;
    }

    // public wrapper for user-facing update call
    public void updateRange(int l, int r, int value) {
        updateRange(0, 0, n - 1, l, r, value);
    }

    private void updateRange(int node, int start, int end, int l, int r, int value){
        push(node, start, end);

        // corrected boundary check
        if (start > end || start > r || end < l) return;

        if (l <= start && end <= r){
            lazy[node] += value;
            push(node, start, end);
            return;
        }

        int mid = start + (end - start) / 2;
        updateRange(2 * node + 1, start, mid, l, r, value);
        updateRange(2 * node + 2, mid + 1, end, l, r, value);
        tree[node] = merger.apply(tree[2 * node + 1], tree[2 * node + 2]);
    }

    // public wrapper for user-facing query call
    public long query(int l, int r) {
        return query(0, 0, n - 1, l, r);
    }

    private long query(int node, int start, int end, int l, int r){
        // corrected boundary check
        if (start > end || start > r || end < l){
            return neturalValue;
        }
        push(node, start, end);

        if (l <= start && end <= r){
            return tree[node];
        }

        int mid = start + (end - start) / 2;
        long left = query(2 * node + 1, start, mid, l, r);
        long right = query(2 * node + 2, mid + 1, end, l, r);
        return merger.apply(left, right);
    }



    public static void main(String[] args) {
        System.out.println("============== TESTING SUM SEGMENT TREE ==============");
        long[] arr = {1, 2, 3, 4, 5, 6, 7, 8};

        // safer lambda-based mergers to avoid method-reference/boxing issues
        SegmentTree sumTree = new SegmentTree(arr, 0L, (a, b) -> a + b);

        System.out.println("Original array: " + Arrays.toString(arr));
        System.out.println("----------------------------------------------------");

        System.out.println("Sum of range [2, 5]: " + sumTree.query(2, 5) + " (Expected: 18)");
        long totalSum = Arrays.stream(arr).sum();
        System.out.println("Sum of range [0, 7]: " + sumTree.query(0, 7) + " (Expected: " + totalSum + ")");
        System.out.println("Sum of range [4, 4]: " + sumTree.query(4, 4) + " (Expected: 5)");
        System.out.println("----------------------------------------------------");

        int updateValue = 10;
        int updateL = 1;
        int updateR = 4;
        System.out.println("Updating range [" + updateL + ", " + updateR + "] by adding " + updateValue + " to each element.");
        sumTree.updateRange(updateL, updateR, updateValue);
        System.out.println("----------------------------------------------------");

        System.out.println("Queries After Update:");
        System.out.println("Sum of range [2, 3]: " + sumTree.query(2, 3) + " (Expected: 27)");
        System.out.println("Sum of range [3, 6]: " + sumTree.query(3, 6) + " (Expected: 42)");
        System.out.println("Sum of range [0, 7]: " + sumTree.query(0, 7) + " (Expected: 76)");
        System.out.println("\n");


        System.out.println("============== TESTING MIN SEGMENT TREE ==============");
        // use lambda for min merger
        SegmentTree minTree = new SegmentTree(arr, Long.MAX_VALUE, (a, b) -> Math.min(a, b));

        System.out.println("Original array: " + Arrays.toString(arr));
        System.out.println("----------------------------------------------------");

        System.out.println("Min of range [2, 5]: " + minTree.query(2, 5) + " (Expected: 3)");
        System.out.println("Min of range [0, 7]: " + minTree.query(0, 7) + " (Expected: 1)");
        System.out.println("----------------------------------------------------");

        int updateValueMin = 5;
        int updateLMin = 0;
        int updateRMin = 3;
        System.out.println("Updating range [" + updateLMin + ", " + updateRMin + "] by adding " + updateValueMin + " to each element.");
        minTree.updateRange(updateLMin, updateRMin, updateValueMin);
        System.out.println("----------------------------------------------------");

        System.out.println("Queries After Update:");
        System.out.println("Min of range [0, 3]: " + minTree.query(0, 3) + " (Expected: 6)");
        System.out.println("Min of range [2, 5]: " + minTree.query(2, 5) + " (Expected: 5)");
        System.out.println("Min of range [0, 7]: " + minTree.query(0, 7) + " (Expected: 5)");
    }

}
