import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class BinaryHeap<T> {

    private final List<T> heap;
    private final Comparator<? super T> comparator;

    public BinaryHeap() {
        this(null);
    }

    public BinaryHeap(Comparator<? super T> comparator) {
        this.comparator = comparator;
        this.heap = new ArrayList<>();
    }

    public void insert(T item) {
        heap.add(item);
        swim(heap.size() - 1);
    }

    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        return heap.get(0);
    }

    public T extract() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        T top = heap.get(0);
        T last = heap.remove(heap.size() - 1);
        if (!isEmpty()) {
            heap.set(0, last);
            sink(0);
        }
        return top;
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    private void sink(int i) {
        int smallest = i;
        int left = leftChild(i);
        int right = rightChild(i);

        if (left < heap.size() && compare(heap.get(left), heap.get(smallest)) < 0) {
            smallest = left;
        }
        if (right < heap.size() && compare(heap.get(right), heap.get(smallest)) < 0) {
            smallest = right;
        }
        if (smallest != i) {
            swap(i, smallest);
            sink(smallest);
        }
    }

    private void swim(int i) {
        while (i > 0 && compare(heap.get(parent(i)), heap.get(i)) > 0) { // corrected sign
            swap(i, parent(i));
            i = parent(i);
        }
    }

    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }

    private int rightChild(int i) {
        return 2 * i + 2;
    }

    @SuppressWarnings("unchecked")
    private int compare(T o1, T o2) {
        if (comparator != null) {
            return comparator.compare(o1, o2);
        }
        return ((Comparable<? super T>) o1).compareTo(o2);
    }

    public static void main(String[] args) {

        System.out.println("--- Testing Min Heap ---");
        BinaryHeap<Integer> minHeap = new BinaryHeap<>();

        minHeap.insert(10);
        minHeap.insert(4);
        minHeap.insert(15);
        minHeap.insert(20);
        minHeap.insert(1);

        System.out.println("Peek: " + minHeap.peek()); // output 1
        System.out.println("Extract: "+ minHeap.extract()); // output 1
        System.out.println("Peek after Extract: " + minHeap.peek()); // output 4
        System.out.println("Extract: "+ minHeap.extract()); // output 4
        System.out.println("Size: "+ minHeap.size()); // output 3

        System.out.println();

        System.out.println("\n --- Testing Max Heap ---");
        BinaryHeap<Integer> maxHeap = new BinaryHeap<>((a, b) -> b - a);
        maxHeap.insert(10);
        maxHeap.insert(4);
        maxHeap.insert(15);
        maxHeap.insert(20);
        maxHeap.insert(1);

        System.out.println("Peek: " + maxHeap.peek()); // output 20
        System.out.println("Extract: " + maxHeap.extract()); // output 20
        System.out.println("Peek after Extract: " + maxHeap.peek()); // output 15
        System.out.println("Size: " + maxHeap.size()); // output 3

        System.out.println();


    }
}
