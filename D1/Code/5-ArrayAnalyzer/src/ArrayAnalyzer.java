public class ArrayAnalyzer {
    public static void main(String[] args) {
        // Declare and initialize an array of integers (student scores).
        int[] scores = { 88, 97, 72, 100, 65, 81 };

        // Always check for empty arrays to avoid runtime errors when accessing elements.
        if (scores.length == 0) {
            System.out.println("Array is empty!");
            return; // Immediately exit the program if array is empty.
        }

        // Assume the first score is both min and max initially.
        int maxScore = scores[0];
        int minScore = scores[0];

        // The enhanced for loop is a clean way to traverse all values in an array.
        for (int score : scores) {
            if (score > maxScore) maxScore = score; // Update max if a larger value is found.
            if (score < minScore) minScore = score; // Update min if a smaller value is found.
        }

        // Print the maximum and minimum scores found.
        System.out.println("Highest Score: " + maxScore);
        System.out.println("Lowest Score: " + minScore);
    }
}
