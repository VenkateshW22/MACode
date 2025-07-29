//Analysis Exercise to identify the primitive and non-primitive data types
//what primitive variables and references are on the Stack?
// what objects are on the Heap?

import java.util.ArrayList;
import java.util.List;

public class AnalysisExercise {
    public static void main(String[] args) {
        int count = 5;
        calculateSum(count);
    }

    public static void calculateSum(int limit) {
        long sum = 0L;
        List<String> labels = new ArrayList<>();
        labels.add("Initial");

        for (int i = 0; i < limit; i++) {
            sum += i;
            String label = "Value-" + i;
            labels.add(label);
        }

        // <-- ANALYSIS
//        what primitive variables and references are on the Stack, and what objects are on the Heap?
        /*
        Stack:

        main method's stack frame: count (int primitive)

        calculateSum method's stack frame: limit (int primitive), sum (long primitive), i (int primitive), labels (reference), label (reference)

        Heap:

        String[] args object

        ArrayList object (referenced by labels)

        Multiple String objects: "Initial", "Value-0", "Value-1", "Value-2", "Value-3", "Value-4"
         */
    }
}