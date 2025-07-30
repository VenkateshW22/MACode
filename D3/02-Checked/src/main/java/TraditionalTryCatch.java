import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TraditionalTryCatch {

    public static void main(String[] args) {
        System.out.println("Program starting.");

        try {
            // Attempt to read from a file. This method declares that it throws
            // a checked exception (FileNotFoundException).
            readFile("my-test-file.txt");
        } catch (FileNotFoundException e) {
            // The main method catches the exception and provides a user-friendly message.
            System.err.println("Error in main: The file could not be found. Please check the file path.");
        }

        System.out.println("Program finished.");
    }

    /**
     * Reads content from a file and prints it.
     * This method demonstrates the 'throws' keyword for checked exceptions.
     * @param fileName The name of the file to read.
     * @throws FileNotFoundException if the file does not exist.
     */
    public static void readFile(String fileName) throws FileNotFoundException {
        // We create a File object.
        File file = new File(fileName);

        // The Scanner constructor can throw a FileNotFoundException, which is a checked exception.
        // Instead of handling it here, we declare that this method 'throws' it,
        // passing the responsibility to the calling method (main).
        Scanner scanner = new Scanner(file);

        System.out.println("File found. Reading contents:");
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
        scanner.close(); // Close the scanner to free resources.
    }
}