package example.concurrency.sockets.single;

import example.concurrency.SampleWebClient;
import example.concurrency.SampleWebServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SingleThreadWebTest {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;
    private static final int REQUESTS_NUMBER = 10;
    private static final long TIMEOUT = 1000;

    @Test
    public void test() throws Exception {

        SampleWebServer server = new SingleThreadWebServer();
        server.start(PORT, REQUESTS_NUMBER);

        SampleWebClient client = new SingleThreadWebClient();
        client.send(HOST, PORT, REQUESTS_NUMBER);

        server.join(TIMEOUT);
        String result = server.getResult();

        int actual = Integer.parseInt(result);
        int N = REQUESTS_NUMBER;
        int expected = N * (N - 1) / 2;
        Assertions.assertEquals(expected, actual);
        client.join(TIMEOUT);
    }
}
