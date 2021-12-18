package example.concurrency.sockets.single;

import example.concurrency.SampleWebServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleThreadWebServer implements SampleWebServer {

    private Thread serverThread;
    private volatile int sum;

    @Override
    public void start(final int port, final int requestsNumber) {

        serverThread = new Thread(() -> {
            try {
                ServerSocket socket = new ServerSocket(port);
                int s = 0;
                for (int i = 0; i < requestsNumber; i++) {

                    try (Socket connection = socket.accept();
                         InputStream is = connection.getInputStream();
                         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is))) {
                        String message = bufferedReader.readLine();
                        int value = Integer.parseInt(message);
                        s += value;
                    }
                }
                sum = s;
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

    @Override
    public String getResult() {
        return Integer.toString(sum);
    }

    private static int handleRequest(Socket connection) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String message = bufferedReader.readLine();
        int result = Integer.parseInt(message);
        return result;
    }
}
