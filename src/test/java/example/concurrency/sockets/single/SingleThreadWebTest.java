package example.concurrency.sockets.single;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.concurrent.atomic.AtomicInteger;

import example.concurrency.SampleWebClient;
import example.concurrency.SampleWebServer;

public class SingleThreadWebTest {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;
    private static final int REQUESTS_NUMBER = 10;
    private static final long TIMEOUT = 1000;

    @Test
    public void test() throws Exception {

        SampleWebServer server = new SingleThreadWebServer();
        final AtomicInteger sum = new AtomicInteger();
        server.start(PORT, REQUESTS_NUMBER, is -> {
            try (DataInputStream dis = new DataInputStream(is)) {
                int value = dis.readInt();
                sum.addAndGet(value);
            }
        });

        final AtomicInteger counter = new AtomicInteger();
        SampleWebClient client = new SingleThreadWebClient();
        client.send(HOST, PORT, REQUESTS_NUMBER, os -> {
            DataOutputStream dos = new DataOutputStream(os);
            int value = counter.getAndIncrement();
            dos.writeInt(value);
        });

        client.join(TIMEOUT);
        server.join(TIMEOUT);

        int N = REQUESTS_NUMBER;
        int expected = N * (N - 1) / 2;
        Assertions.assertEquals(expected, sum.get());
        client.join(TIMEOUT);
    }
}
