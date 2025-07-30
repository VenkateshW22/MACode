import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Demonstrates the "throw early, catch late" principle with checked exceptions.
 * The readFileAndPrint method propagates the exception to the caller.
 */
public class CheckedExceptionHandling {

    public static void main(String[] args) {
        System.out.println("Program starting.");

        try {
            // Attempt to read a file that does not exist.
            readFileAndPrint("non_existent_file.txt");
        } catch (FileNotFoundException e) {
            // The main method catches the exception and handles it gracefully.
            System.err.println("Error in main: The file could not be found. Please check the path.");
        }

        System.out.println("Program finished.");
    }

    /**
     * Reads from a file. It declares that it can throw a FileNotFoundException,
     * leaving the handling to the calling method.
     * @param fileName The name of the file to read.
     * @throws FileNotFoundException if the file is not found.
     */
    public static void readFileAndPrint(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        // The try-with-resources statement ensures the Scanner is closed.
        try (Scanner scanner = new Scanner(file)) {
            System.out.println("File found. Contents:");
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        }
    }
}
