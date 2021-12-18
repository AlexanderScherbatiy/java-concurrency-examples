package example.concurrency.sockets.single;

import example.concurrency.SampleWebClient;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class SingleThreadWebClient implements SampleWebClient {

    private Thread clientThread;

    @Override
    public void send(String host, int port, int requestNumbers) {

        clientThread = new Thread(() -> {
            try {
                for (int i = 0; i < requestNumbers; i++) {
                    try (Socket client = new Socket(host, port);
                         OutputStream os = client.getOutputStream();
                         PrintWriter writer = new PrintWriter(os, true)) {
                        writer.println(i);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        clientThread.start();
    }

    @Override
    public void join(long millis) throws InterruptedException {
        clientThread.join(millis);
    }
}
