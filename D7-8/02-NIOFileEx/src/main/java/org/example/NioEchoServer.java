package org.example;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioEchoServer {
    private static final int PORT = 8080;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) throws IOException {
        // 1. Create a Selector
        Selector selector = Selector.open();

        // 2. Open a ServerSocketChannel and configure it
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", PORT));
        serverSocketChannel.configureBlocking(false); // crucial for non-blocking I/O

        // 3. Register the ServerSocketChannel with the Selector for ACCEPT events
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("Echo Server started on port " + PORT);

        // 4. The main event loop
        while (true) {
            // Blocks until at least one registered channel is ready for an I/O event
            selector.select();

            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                keyIterator.remove();

                if (key.isAcceptable()) {
                    // A new connection has been accepted by the server socket
                    accept(selector, key);
                } else if (key.isReadable()) {
                    // Data is ready to be read from a client socket
                    readAndWrite(key);
                }
            }
        }
    }

    private static void accept(Selector selector, SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverChannel.accept(); // Accept the connection
        clientChannel.configureBlocking(false); // Make the new client channel non-blocking
        clientChannel.register(selector, SelectionKey.OP_READ); // Register client channel for READ events
        System.out.println("Accepted new connection from " + clientChannel.getRemoteAddress());
    }

    private static void readAndWrite(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

        int bytesRead = clientChannel.read(buffer);

        if (bytesRead == -1) {
            // Client closed the connection
            clientChannel.close();
            System.out.println("Client disconnected from " + clientChannel.getRemoteAddress());
        } else if (bytesRead > 0) {
            buffer.flip(); // Prepare buffer for writing
            clientChannel.write(buffer); // Echo the data back
            buffer.clear(); // Clear the buffer for the next read
        }
    }
}