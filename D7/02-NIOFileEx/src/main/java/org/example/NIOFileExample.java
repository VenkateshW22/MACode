package org.example;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class NIOFileExample {

    private static final String NIO_FILE_SOURCE = "nio_source.txt";
    private static final String NIO_FILE_DEST = "nio_destination.txt";

    public static void main(String[] args) {
        // Create dummy source file
        try {
            Files.write(Paths.get(NIO_FILE_SOURCE), "Hello, NIO World!".getBytes());
        } catch (IOException e) {
            System.err.println("Error creating dummy NIO source file: " + e.getMessage());
            return;
        }

        System.out.println("--- NIO File Copy Example ---");
        copyFileNIO(NIO_FILE_SOURCE, NIO_FILE_DEST);
        System.out.println("File copied using NIO.");
    }

    /**
     * Copies a file using Java NIO Channels and Buffers.
     * More efficient for large files and can be non-blocking (though this example is blocking).
     */
    private static void copyFileNIO(String sourcePathStr, String destPathStr) {
        Path sourcePath = Paths.get(sourcePathStr);
        Path destPath = Paths.get(destPathStr);

        try (FileChannel sourceChannel = FileChannel.open(sourcePath, StandardOpenOption.READ);
             FileChannel destChannel = FileChannel.open(destPath, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {

            ByteBuffer buffer = ByteBuffer.allocate(1024); // 1KB buffer
            while (sourceChannel.read(buffer) != -1) { // Read from source channel into buffer
                buffer.flip(); // Prepare buffer for reading (limit becomes current position, position becomes 0)
                destChannel.write(buffer); // Write from buffer to destination channel
                buffer.clear(); // Clear buffer for next read (position becomes 0, limit becomes capacity)
            }
            System.out.println("File copied successfully from " + sourcePath + " to " + destPath);
        } catch (IOException e) {
            System.err.println("Error copying file with NIO: " + e.getMessage());
        }
    }
}