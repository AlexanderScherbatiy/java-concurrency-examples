package example.concurrency.sockets.single;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import example.concurrency.SampleConsumer;
import example.concurrency.SampleWebServer;

public class SingleThreadWebServer implements SampleWebServer {

    private Thread serverThread;

    @Override
    public void start(final int port, final int requestsNumber, SampleConsumer consumer) {

        serverThread = new Thread(() -> {
            try {
                ServerSocket socket = new ServerSocket(port);
                for (int i = 0; i < requestsNumber; i++) {
                    try (Socket connection = socket.accept();
                         InputStream is = connection.getInputStream()) {
                        consumer.consume(is);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        serverThread.start();
    }

    @Override
    public void join(long millis) throws InterruptedException {
        serverThread.join(millis);
    }
}
