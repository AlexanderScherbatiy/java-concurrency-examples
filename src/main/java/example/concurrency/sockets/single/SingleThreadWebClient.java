package example.concurrency.sockets.single;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import example.concurrency.SampleProducer;
import example.concurrency.SampleWebClient;

public class SingleThreadWebClient implements SampleWebClient {

    private Thread clientThread;

    @Override
    public void send(String host, int port, int requestNumbers, SampleProducer producer) {

        clientThread = new Thread(() -> {
            try {
                for (int i = 0; i < requestNumbers; i++) {
                    try (Socket client = new Socket(host, port);
                         OutputStream os = client.getOutputStream()) {
                        producer.produce(os);
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
