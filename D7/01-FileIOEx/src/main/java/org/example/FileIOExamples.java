package org.example;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileIOExamples {

    private static final String BYTE_FILE_SOURCE = "byte_source.txt";
    private static final String BYTE_FILE_DEST = "byte_destination.txt";
    private static final String CHAR_FILE_SOURCE = "char_source.txt";
    private static final String CHAR_FILE_DEST = "char_destination.txt";
    private static final String LARGE_FILE_SOURCE = "large_file.txt";
    private static final String LARGE_FILE_DEST_BUFFERED = "large_file_buffered.txt";

    public static void main(String[] args) {
        // Create dummy files for demonstration
        createDummyFiles();

        System.out.println("--- Byte Stream Example ---");
        copyFileByteStream(BYTE_FILE_SOURCE, BYTE_FILE_DEST);
        System.out.println("File copied using Byte Stream.");

        System.out.println("\n--- Character Stream Example ---");
        copyFileCharStream(CHAR_FILE_SOURCE, CHAR_FILE_DEST);
        System.out.println("File copied using Character Stream.");

        System.out.println("\n--- Buffered Stream Example (for large files) ---");
        copyFileBufferedStream(LARGE_FILE_SOURCE, LARGE_FILE_DEST_BUFFERED);
        System.out.println("Large file copied using Buffered Stream.");
    }

    private static void createDummyFiles() {
        try (FileOutputStream fos = new FileOutputStream(BYTE_FILE_SOURCE);
             FileWriter fw = new FileWriter(CHAR_FILE_SOURCE);
             FileWriter fwLarge = new FileWriter(LARGE_FILE_SOURCE)) {

            fos.write("Hello, Byte Stream!".getBytes(StandardCharsets.UTF_8));
            fw.write("Hello, Character Stream!");

            // Create a large file
            for (int i = 0; i < 100000; i++) {
                fwLarge.write("This is a line in the large file. Line number: " + i + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error creating dummy files: " + e.getMessage());
        }
    }

    /**
     * Copies a file using FileInputStream and FileOutputStream.
     * Suitable for binary data.
     */
    private static void copyFileByteStream(String sourcePath, String destPath) {
        try (FileInputStream fis = new FileInputStream(sourcePath);
             FileOutputStream fos = new FileOutputStream(destPath)) {
            int byteRead;
            while ((byteRead = fis.read()) != -1) {
                fos.write(byteRead);
            }
        } catch (IOException e) {
            System.err.println("Error copying file with Byte Stream: " + e.getMessage());
        }
    }

    /**
     * Copies a file using FileReader and FileWriter.
     * Suitable for text data.
     */
    private static void copyFileCharStream(String sourcePath, String destPath) {
        try (FileReader fr = new FileReader(sourcePath);
             FileWriter fw = new FileWriter(destPath)) {
            int charRead;
            while ((charRead = fr.read()) != -1) {
                fw.write(charRead);
            }
        } catch (IOException e) {
            System.err.println("Error copying file with Character Stream: " + e.getMessage());
        }
    }

    /**
     * Copies a file using BufferedReader and BufferedWriter for improved performance,
     * especially with large text files.
     */
    private static void copyFileBufferedStream(String sourcePath, String destPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(sourcePath));
             BufferedWriter bw = new BufferedWriter(new FileWriter(destPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine(); // Add new line since readLine() strips it
            }
        } catch (IOException e) {
            System.err.println("Error copying file with Buffered Stream: " + e.getMessage());
        }
    }
}