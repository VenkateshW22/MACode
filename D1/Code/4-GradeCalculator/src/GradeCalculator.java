public class GradeCalculator {
    public static void main(String[] args) {
        // Student's numeric score out of 100.
        int score = 84;
        char grade; // Variable to store the letter grade.

        // Check score range and assign the correct letter grade.
        if (score >= 90) {
            grade = 'A';
        } else if (score >= 80) {
            grade = 'B';
        } else if (score >= 70) {
            grade = 'C';
        } else if (score >= 60) {
            grade = 'D';
        } else {
            grade = 'F'; // Anything less than 60.
        }

        // Print the score and its grade.
        System.out.println("A score of " + score + " results in a grade of: " + grade);
    }
}
